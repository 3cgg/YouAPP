package j.jave.kernal.jave.support._package;

import j.jave.kernal.jave.model.JModel;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

public class JDefaultFieldMeta implements JModel{

	private String fieldName;
	
	private Class<?> clazz;
	
	private Annotation[] annotations;
	
	private int access;
	
	private Field field;

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public Class<?> getClazz() {
		return clazz;
	}

	public void setClazz(Class<?> clazz) {
		this.clazz = clazz;
	}

	public Annotation[] getAnnotations() {
		return annotations;
	}

	public void setAnnotations(Annotation[] annotations) {
		this.annotations = annotations;
	}

	public int getAccess() {
		return access;
	}

	public void setAccess(int access) {
		this.access = access;
	}

	public Field getField() {
		return field;
	}

	public void setField(Field field) {
		this.field = field;
	}
	
}
