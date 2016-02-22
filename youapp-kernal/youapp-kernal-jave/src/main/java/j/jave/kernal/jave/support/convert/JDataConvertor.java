package j.jave.kernal.jave.support.convert;

import j.jave.kernal.JConfiguration;
import j.jave.kernal.JProperties;
import j.jave.kernal.jave.utils.JDateUtils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JDataConvertor {
	
	public static JDataConvertor build(JConfiguration configuration){
		JDataConvertor dataConvertor=new JDataConvertor();
		dataConvertor.converts.put(Integer.class, new IntegerConvert());
		dataConvertor.converts.put(int.class, new IntegerConvert());
		dataConvertor.converts.put(Long.class, new LongConvert());
		dataConvertor.converts.put(long.class, new LongConvert());
		dataConvertor.converts.put(Float.class, new FloatConvert());
		dataConvertor.converts.put(float.class, new FloatConvert());
		dataConvertor.converts.put(Double.class, new DoubleConvert());
		dataConvertor.converts.put(double.class, new DoubleConvert());
		dataConvertor.converts.put(Boolean.class, new BooleanConvert());
		dataConvertor.converts.put(boolean.class, new BooleanConvert());
		dataConvertor.converts.put(BigDecimal.class, new BigDecimalConvert());
		dataConvertor.converts.put(Date.class, new TimestampConvert(
				configuration.getString(JProperties.DATE_FORMAT, JDateUtils.yyyyMMddHHmmss)));
		dataConvertor.converts.put(Timestamp.class, new TimestampConvert(
				configuration.getString(JProperties.DATE_FORMAT, JDateUtils.yyyyMMddHHmmss)));
		return dataConvertor;
	}
	
	public Object convert(Class<?> clazz,Object object){
		Convert convert=converts.get(clazz);
		return convert==null?object:convert.convert(object,clazz);
	}
	
	public static interface Convert{
		Object convert(Object object,Class<?> clazz);
	}
	
	
	public static class IntegerConvert implements Convert{
		@Override
		public Object convert(Object object,Class<?> clazz) {
			if(object==null) return null;
			else if(Integer.class.isInstance(object)){
				return object;
			}
			else{
				return Integer.parseInt(String.valueOf(object));
			}
		}
	}
	
	public static class LongConvert implements Convert{
		@Override
		public Object convert(Object object,Class<?> clazz) {
			if(object==null) return null;
			else if(Long.class.isInstance(object)){
				return object;
			}
			else{
				return Long.parseLong(String.valueOf(object));
			}
		}
	}
	
	public static class DoubleConvert implements Convert{
		@Override
		public Object convert(Object object,Class<?> clazz) {
			if(object==null) return null;
			else if(Double.class.isInstance(object)){
				return object;
			}
			else{
				return Double.parseDouble(String.valueOf(object));
			}
		}
	}
	
	public static class FloatConvert implements Convert{
		@Override
		public Object convert(Object object,Class<?> clazz) {
			if(object==null) return null;
			else if(Float.class.isInstance(object)){
				return object;
			}
			else{
				return Float.parseFloat(String.valueOf(object));
			}
		}
	}
	
	public static class ByteConvert implements Convert{
		@Override
		public Object convert(Object object,Class<?> clazz) {
			if(object==null) return null;
			else if(Byte.class.isInstance(object)){
				return object;
			}
			else{
				return Byte.parseByte(String.valueOf(object));
			}
		}
	}
	
	public static class BooleanConvert implements Convert{
		@Override
		public Object convert(Object object,Class<?> clazz) {
			if(object==null) return null;
			else if(Boolean.class.isInstance(object)){
				return object;
			}
			else{
				return Boolean.parseBoolean(String.valueOf(object));
			}
		}
	}
	
	public static class TimestampConvert implements Convert{
		private String pattern="";
		
		public TimestampConvert(String pattern ){
			this.pattern=pattern;
		}
		@Override
		public Object convert(Object object,Class<?> clazz) {
			if(object==null) return null;
			else if(Date.class.isInstance(object)){
				return object;
			}
			else{
				return JDateUtils.parseTimestampWithSeconds(String.valueOf(object), pattern);
			}
		}
	}
	
	public static class BigDecimalConvert implements Convert{
		@Override
		public Object convert(Object object,Class<?> clazz) {
			if(object==null) return null;
			else if(BigDecimal.class.isInstance(object)){
				return object;
			}
			else{
				return new BigDecimal(String.valueOf(object));
			}
		}
	}
	
	public static class CollectionConvert implements Convert{
		@Override
		public Object convert(Object object,Class<?> clazz) {
			if(object==null) return null;
			else if(Collection.class.isInstance(object)){
				return object;
			}
			else{
				Type[] types=((ParameterizedType)clazz.getGenericSuperclass()).getActualTypeArguments();
				
				return new BigDecimal(String.valueOf(object));
			
			}
		}
	}
	
	private Map<Class<?>, Convert> converts=new HashMap<Class<?>, JDataConvertor.Convert>();
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
