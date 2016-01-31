package j.jave.kernal.jave.random;

import java.lang.reflect.Field;

public abstract class JFieldRandom<T> implements JRandom<T> {
	
	private String fieldName;
	
	public String getFieldName() {
		return fieldName;
	}
	
	public JFieldRandom(String fieldName){
		this.fieldName=fieldName;
	}
	
	public JFieldRandom(){
	}
	
	@Override
	public final T random() {
		return random(null, null);
	}
	
	/**
	 * 
	 * @param field
	 * @param objectClass  the object instance class
	 * @return
	 */
	public abstract T random(Field field,Class<?> objectClass);  
	
	public boolean matches(Field field,Class<?> objectClass){
		return matches(field.getName());
	}
	
	public boolean matches(String fieldName){
		return fieldName.equals(this.fieldName);
	}
	
}
