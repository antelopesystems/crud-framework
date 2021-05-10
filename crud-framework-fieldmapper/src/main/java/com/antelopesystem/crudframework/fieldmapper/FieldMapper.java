package com.antelopesystem.crudframework.fieldmapper;

import com.antelopesystem.crudframework.fieldmapper.annotation.*;
import com.antelopesystem.crudframework.fieldmapper.dto.EntityStructureDTO;
import com.antelopesystem.crudframework.fieldmapper.exception.InvalidConfigurationException;
import com.antelopesystem.crudframework.fieldmapper.transformer.DefaultTransformer;
import com.antelopesystem.crudframework.fieldmapper.transformer.base.FieldTransformer;
import com.antelopesystem.crudframework.utils.utils.ReflectionUtils;
import kotlin.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FieldMapper {

	private Logger log = LoggerFactory.getLogger(getClass());

	private Map<String, FieldTransformer> fieldTransformersByRef = new HashMap<>();

	private Map<Class<? extends FieldTransformer>, FieldTransformer> fieldTransformersByType = new HashMap<>();

	private Map<Pair<Class<?>, Class<?>>, FieldTransformer> defaultTransformers = new HashMap();

	private static Map<Pair<Class<?>, Class<?>>, EntityStructureDTO> entityStructures = new HashMap<>();

	private static Map<Class<?>, Map<String, Field>> entityFieldMaps = new HashMap<>();

	private static final String NODE_DELIMITER = ".";

	public void registerTransformer(String ref, FieldTransformer transformer) {
		fieldTransformersByRef.put(ref, transformer);
		fieldTransformersByType.put(transformer.getClass(), transformer);
		if(transformer.isDefault()) {
			registerDefaultTransformer(transformer);
		}
	}

	public void registerTransformer(Class<? extends FieldTransformer> clazz, FieldTransformer transformer) {
		fieldTransformersByType.put(clazz, transformer);
		if(transformer.isDefault()) {
			registerDefaultTransformer(transformer);
		}
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

		FieldTransformer transformer = getTransformer(annotation, fromPair, toPair);
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
			value = transformer.transform(fromPair.getField(), toPair.getField(), value, fromPair.getObject(), toPair.getObject());
		}

		if(value != null) {
			try {
				ReflectionUtils.setField(toPair.getField(), toPair.getObject(), value);
			} catch(Exception e) {
				IllegalStateException newException = new IllegalStateException("Could not map value " + fromPair.getField().getName() + " of class " + fromPair.getObject().getClass().getSimpleName() + " to " + toPair.getField().getName() + " of class" + toPair.getObject().getClass().getSimpleName());
				newException.initCause(e);
				throw newException;
			}
		}
	}

	private FieldTransformer getTransformer(MappedField annotation, ObjectFieldPair fromPair, ObjectFieldPair toPair) {
		Pair transformationPair = new Pair(fromPair.getField().getType(), toPair.getField().getType());
		log.trace("Attempting to find transformer for transformation pair [ " + transformationPair + " ]");
		FieldTransformer transformer = null;
		log.trace("Checking transformerRef field");
		if(!annotation.transformerRef().isEmpty()) {
			log.trace("transformerRef is not empty with value [ " + annotation.transformerRef() + " ]");
			transformer = fieldTransformersByRef.get(annotation.transformerRef());
			log.trace("Found transformer of type [ " + transformer.getClass().getName() + " ]");
		}

		if(transformer == null) {
			log.trace("Checking transformer field");
			if(annotation.transformer() == DefaultTransformer.class) {
				log.trace("Transformer is DefaultTransformer, attempting to find a default transformer");
				Pair key = new Pair(fromPair.getField().getType(), toPair.getField().getType());

				FieldTransformer defaultTransformer = defaultTransformers.get(key);
				if(defaultTransformer != null) {
					log.trace("Found a default transformer of type [ " + defaultTransformer.getClass().getName() + " ]");
					return defaultTransformer;
				}

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
		EntityStructureDTO entityStructureDTO = entityStructures.get(new Pair<Class<?>, Class<?>>(fromClass, toClass));
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

		entityStructures.put(new Pair<Class<?>, Class<?>>(fromClass, toClass), entityStructureDTO);
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

	private void registerDefaultTransformer(FieldTransformer transformer) {
		if(transformer.isDefault()) {
			Pair key = new Pair<>(transformer.fromType(), transformer.toType());
			FieldTransformer existing = defaultTransformers.get(key);
			if(existing != null) {
				throw new IllegalStateException("Cannot register default transformer for pair [ " + key + " ] - already registered by [ " + existing.getClass().getName() + " ]");
			}

			defaultTransformers.put(key, transformer);
		}
	}

	private enum SourceType {
		TO, FROM
	}

}
