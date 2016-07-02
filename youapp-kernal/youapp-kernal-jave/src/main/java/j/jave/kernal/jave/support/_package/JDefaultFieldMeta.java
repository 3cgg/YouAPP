package j.jave.kernal.jave.support._package;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

public class JDefaultFieldMeta implements JFieldMeta{

	private String fieldName;
	
	/**
	 * the scanning class from the field is hit ,  not declared class of the field
	 */
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

	/**
	 * the scanning class from the field is hit ,  not declared class of the field
	 * @return
	 */
	public Class<?> getClazz() {
		return clazz;
	}

	/**
	 * @param clazz the scanning class from the field is hit ,  not declared class of the field
	 */
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
