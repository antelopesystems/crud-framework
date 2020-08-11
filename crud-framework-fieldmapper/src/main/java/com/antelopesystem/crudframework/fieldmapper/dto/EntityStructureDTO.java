package com.antelopesystem.crudframework.fieldmapper.dto;

import com.antelopesystem.crudframework.fieldmapper.annotation.MappedField;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

public class EntityStructureDTO {

	private List<MappedField> typeAnnotations;

	private Map<Field, List<MappedField>> annotations;

	public EntityStructureDTO(List<MappedField> typeAnnotations, Map<Field, List<MappedField>> annotations) {
		this.typeAnnotations = typeAnnotations;
		this.annotations = annotations;
	}

	public List<MappedField> getTypeAnnotations() {
		return typeAnnotations;
	}

	public void setTypeAnnotations(List<MappedField> typeAnnotations) {
		this.typeAnnotations = typeAnnotations;
	}

	public Map<Field, List<MappedField>> getAnnotations() {
		return annotations;
	}

	public void setAnnotations(Map<Field, List<MappedField>> annotations) {
		this.annotations = annotations;
	}
}
