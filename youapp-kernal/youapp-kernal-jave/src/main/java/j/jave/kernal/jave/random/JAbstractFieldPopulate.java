package j.jave.kernal.jave.random;

import j.jave.kernal.jave.support.detect.JFieldDetect.JFieldFilter;
import j.jave.kernal.jave.utils.JCollectionUtils;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public abstract class JAbstractFieldPopulate implements JClassFieldPopulate {

	protected JFieldFilter fieldFilter;
	
	protected JStringRandom stringRandom;
	
	protected List<JFieldRandom<?>> fieldRandoms=new ArrayList<JFieldRandom<?>>();
	
	public void addFieldRandom(JFieldRandom<?> fieldRandom) {
		fieldRandoms.add(fieldRandom);
	}
	
	public void setFieldRandoms(List<JFieldRandom<?>> fieldRandoms) {
		this.fieldRandoms = fieldRandoms;
	}

	public void setStringRandom(JStringRandom stringRandom) {
		this.stringRandom = stringRandom;
	}
	
	@Override
	public void setFieldFilter(JFieldFilter fieldFilter) {
		this.fieldFilter=fieldFilter;
	}
	
	
	public static class FieldRelated{
		private Field field;
		private JRandom<?> random;
		private Class<?> objectClass;
		
		public Field getField() {
			return field;
		}
		public void setField(Field field) {
			this.field = field;
		}
		public JRandom<?> getRandom() {
			return random;
		}
		public void setRandom(JRandom<?> random) {
			this.random = random;
		}
		public Class<?> getObjectClass() {
			return objectClass;
		}
		public void setObjectClass(Class<?> objectClass) {
			this.objectClass = objectClass;
		}
		
	}
	
	protected JRandom<?> getRandom(Field field,Class<?> objectClass){
		
		// if field random matches the field, defined by the caller.
		if(JCollectionUtils.hasInCollect(fieldRandoms)){
			for(int i=0;i<this.fieldRandoms.size();i++){
				JFieldRandom<?> fieldRandom=fieldRandoms.get(i);
				if(fieldRandom.matches(field, objectClass)){
					return fieldRandom;
				}
			}
		}
		// use default.
		
		JRandom<?> random=null;
		Class<?> type= field.getType();
		if(String.class==type){
			random=stringRandom==null?new JDefaultStringRandom():stringRandom; 
		}
		else if(Integer.class==type||int.class==type){
			random=new JDefaultIntRandom(); 
		}
		else if(Long.class==type||long.class==type){
			random=new JDefaultLongRandom(); 
		}
		else if(Double.class==type||double.class==type){
			random=new JDefaultDoubleRandom(); 
		}
		else if(Boolean.class==type||boolean.class==type){
			random=new JDefaultBooleanRandom(); 
		}
		else if(Float.class==type||float.class==type){
			random=new JDefaultFloatRandom(); 
		}
		else if(Date.class==type){
			random=new JDefaultDateRandom(); 
		}
		else if(Timestamp.class==type){
			random=new JDefaultTimestampRandom(); 
		}
		else if(BigDecimal.class==type){
			random=new JDefaultBigDecimalRandom(); 
		}
		return random;
	}
	
}
