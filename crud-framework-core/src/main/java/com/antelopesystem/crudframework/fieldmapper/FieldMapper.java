package com.antelopesystem.crudframework.fieldmapper;

import com.antelopesystem.crudframework.fieldmapper.annotation.DefaultMappingTarget;
import com.antelopesystem.crudframework.fieldmapper.annotation.MappedField;
import com.antelopesystem.crudframework.fieldmapper.annotation.ObjectFieldPair;
import com.antelopesystem.crudframework.fieldmapper.dto.EntityStructureDTO;
import com.antelopesystem.crudframework.fieldmapper.exception.InvalidConfigurationException;
import com.antelopesystem.crudframework.fieldmapper.transformer.DefaultTransformer;
import com.antelopesystem.crudframework.fieldmapper.transformer.base.FieldTransformer;
import com.antelopesystem.crudframework.utils.ReflectionUtils;
import com.antelopesystem.crudframework.utils.Tuple;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FieldMapper {

	private Map<String, FieldTransformer> fieldTransformersByRef = new HashMap<>();

	private Map<Class<? extends FieldTransformer>, FieldTransformer> fieldTransformersByType = new HashMap<>();

	private static Map<Tuple<Class<?>, Class<?>>, EntityStructureDTO> entityStructures = new HashMap<>();

	private static Map<Class<?>, Map<String, Field>> entityFieldMaps = new HashMap<>();

	private static final String NODE_DELIMITER = ".";

	public void registerTransformer(String ref, FieldTransformer transformer) {
		fieldTransformersByRef.put(ref, transformer);
	}

	public void registerTransformer(Class<? extends FieldTransformer> clazz, FieldTransformer transformer) {
		fieldTransformersByType.put(clazz, transformer);
	}

	public <T> T processMappedFields(Object object, Class<T> toClazz) {
		T toObject = ReflectionUtils.instantiateClass(toClazz);
		processMappedFields(object, toObject);
		return toObject;
	}

	public <T> void processMappedFields(Object object, T toObject) {

		EntityStructureDTO es = getEntityStructure(object.getClass(), toObject.getClass());

		for(MappedField typeAnnotation : es.getTypeAnnotations()) {
			if(typeAnnotation.mapFrom().trim().isEmpty()) {
				throw new RuntimeException("mapFrom can not be empty when used at a type level");
			}

			String fromPath = typeAnnotation.mapFrom();

			if(fromPath.toLowerCase().startsWith(object.getClass().getSimpleName().toLowerCase() + NODE_DELIMITER)) {
				fromPath = fromPath.substring(fromPath.indexOf(NODE_DELIMITER) + 1);
			}

			String toPath = typeAnnotation.mapTo();

			if(toPath.toLowerCase().startsWith(toObject.getClass().getSimpleName().toLowerCase() + NODE_DELIMITER)) {
				toPath = toPath.substring(toPath.indexOf(NODE_DELIMITER) + 1);
			}

			processMappedField(typeAnnotation, object, toObject, fromPath, toPath);
		}

		for(Map.Entry<Field, List<MappedField>> entry : es.getAnnotations().entrySet()) {
			for(MappedField annotation : entry.getValue()) {
				String fromPath = "";
				if(annotation.mapFrom().isEmpty()) {
					fromPath = entry.getKey().getName();
				} else {
					if(!annotation.mapFrom().startsWith(entry.getKey().getName() + NODE_DELIMITER)) {
						fromPath = entry.getKey().getName() + NODE_DELIMITER + annotation.mapFrom();
					} else {
						fromPath = annotation.mapFrom();
					}
				}
				processMappedField(annotation, object, toObject, fromPath, annotation.mapTo());
			}
		}
	}

	private void processMappedField(MappedField annotation, Object fromObject, Object toObject, String fromPath, String toPath) {
		if(fromPath == null || fromPath.trim().isEmpty()) {
			throw new RuntimeException("fromPath cannot be null or empty on class " + fromObject.getClass().getSimpleName());
		}

		ObjectFieldPair fromPair = getFieldByPath(fromPath, fromObject, SourceType.FROM);
		if(fromPair == null) {
			return;
		}

		toPath = toPath.isEmpty() ? fromPair.getField().getName() : toPath;
		ObjectFieldPair toPair = getFieldByPath(toPath, toObject, SourceType.TO);

		FieldTransformer transformer = getTransformer(annotation);
		mapField(fromPair, toPair, transformer);
	}

	private ObjectFieldPair getFieldByPath(String path, Object object, SourceType type) {
		if(object == null) {
			if(type == SourceType.FROM) {
				return null;
			}
		}


		List<String> nodes = Stream.of(path.split("\\" + NODE_DELIMITER)).collect(Collectors.toList());


		Field field = getField(nodes.get(0), object.getClass());
		Objects.requireNonNull(field, "Field " + nodes.get(0) + " not found on class " + object.getClass().getSimpleName());
		if(nodes.size() == 1) {
			return new ObjectFieldPair(object, field);
		}

		nodes.remove(0);

		ReflectionUtils.makeAccessible(field);

		Object subObject = ReflectionUtils.getField(field, object);

		if(subObject == null && type == SourceType.TO) {
			try {
				subObject = ReflectionUtils.instantiateClass(field.getType());
				ReflectionUtils.setField(field, object, subObject);
			} catch(IllegalStateException e) {
				throw new RuntimeException("Could not instantiate " + field.getName() + " of type " + field.getType().getSimpleName() + " on class " + object.getClass().getSimpleName());
			}
		}

		return getFieldByPath(String.join(NODE_DELIMITER, nodes), subObject, type);
	}


	private void mapField(ObjectFieldPair fromPair, ObjectFieldPair toPair, FieldTransformer transformer) {
		ReflectionUtils.makeAccessible(fromPair.getField());
		ReflectionUtils.makeAccessible(toPair.getField());

		Object value = ReflectionUtils.getField(fromPair.getField(), fromPair.getObject());
		if(transformer != null) {
			value = transformer.transform(fromPair.getField(), toPair.getField(), value);
		}

		if(value != null) {
			try {
				ReflectionUtils.setField(toPair.getField(), toPair.getObject(), value);
			} catch(Exception e) {
				throw new RuntimeException("Could not map value " + fromPair.getField().getName() + " of class " + fromPair.getObject().getClass().getSimpleName() + " to " + toPair.getField().getName() + " of class" + toPair.getObject().getClass().getSimpleName());
			}
		}
	}

	private FieldTransformer getTransformer(MappedField annotation) {
		FieldTransformer transformer = null;
		if(!annotation.transformerRef().isEmpty()) {
			transformer = fieldTransformersByRef.get(annotation.transformerRef());
		}

		if(transformer == null) {
			if(annotation.transformer() == DefaultTransformer.class) {
				return null;
			}

			transformer = fieldTransformersByType.get(annotation.transformer());
			if(transformer == null) {
				transformer = ReflectionUtils.instantiateClass(annotation.transformer());
				fieldTransformersByType.put(annotation.transformer(), transformer);
			}
		}

		return transformer;
	}

	private EntityStructureDTO getEntityStructure(Class<?> fromClass, Class<?> toClass) {
		EntityStructureDTO entityStructureDTO = entityStructures.get(new Tuple<>(fromClass, toClass));
		if(entityStructureDTO != null) {
			return entityStructureDTO;
		}

		Map<Field, List<MappedField>> annotations = new HashMap<>();
		List<MappedField> typeAnnotations = new ArrayList<>();
		Class clazz = fromClass;
		while(clazz != null) {
			Field[] fields = clazz.getDeclaredFields();
			DefaultMappingTarget defaultMappingTarget = (DefaultMappingTarget) clazz.getDeclaredAnnotation(DefaultMappingTarget.class);
			Class<?> defaultFromClass = defaultMappingTarget == null ? Void.class : defaultMappingTarget.value();

			typeAnnotations.addAll(Arrays.stream((MappedField[]) clazz.getDeclaredAnnotationsByType(MappedField.class))
					.filter(mappedField -> {
						try {
							return isOfType(defaultFromClass, mappedField.target(), toClass);
						} catch(InvalidConfigurationException e) {
							throw new RuntimeException("Could not create entity structure for <" + fromClass.getSimpleName() + ", " + toClass.getSimpleName() + ">: " + e.getMessage());
						}

					})
					.collect(Collectors.toList())
			);

			for(Field field : fields) {
				List<MappedField> availableAnnotations = Arrays.asList(field.getDeclaredAnnotationsByType(MappedField.class));
				availableAnnotations = availableAnnotations.stream()
						.filter(mappedField -> {
							try {
								return isOfType(defaultFromClass, mappedField.target(), toClass);
							} catch(InvalidConfigurationException e) {
								throw new InvalidConfigurationException("Could not create entity structure for <" + fromClass.getSimpleName() + ", " + toClass.getSimpleName() + ">: " + e.getMessage());
							}

						})
						.collect(Collectors.toList());

				annotations.put(field, availableAnnotations);
			}

			clazz = clazz.getSuperclass();
		}

		entityStructureDTO = new EntityStructureDTO(typeAnnotations, annotations);

		entityStructures.put(new Tuple<>(fromClass, toClass), entityStructureDTO);
		return entityStructureDTO;
	}

	private Map<String, Field> getFieldsMap(Class<?> clazz) {
		if(entityFieldMaps.get(clazz) != null) {
			return entityFieldMaps.get(clazz);
		}

		Map<String, Field> fieldsMap = new HashMap<>();
		Class classToGetFields = clazz;
		while(classToGetFields != null) {
			Field[] fields = classToGetFields.getDeclaredFields();
			for(Field field : fields) {
				fieldsMap.put(field.getName(), field);
			}
			classToGetFields = classToGetFields.getSuperclass();
		}

		entityFieldMaps.put(clazz, fieldsMap);

		return fieldsMap;
	}

	private Field getField(String name, Class<?> clazz) {
		return getFieldsMap(clazz).get(name);
	}

	private boolean isOfType(Class<?> defaultFromClass, Class<?> fromClass, Class<?> toClass) {
		if(fromClass == Void.class) {
			if(defaultFromClass == Void.class) {
				throw new InvalidConfigurationException("No mapping target or default mapping target specified");
			}

			fromClass = defaultFromClass;
		}

		return fromClass.isAssignableFrom(toClass);
	}

	private enum SourceType {
		TO, FROM
	}

}
