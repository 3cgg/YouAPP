package j.jave.kernal.jave.support.convert;

import j.jave.kernal.JConfiguration;
import j.jave.kernal.JProperties;
import j.jave.kernal.jave.reflect.JClassUtils;
import j.jave.kernal.jave.utils.JAssert;
import j.jave.kernal.jave.utils.JDateUtils;
import j.jave.kernal.jave.utils.JStringUtils;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class JDataConvertor {
	
	public static JDataConvertor build(JConfiguration configuration){
		JDataConvertor dataConvertor=new JDataConvertor();
		dataConvertor.converts.put(String.class, new StringConvert());
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
		dataConvertor.converts.put(SimpleTypeArrayConvert.class, dataConvertor.new SimpleTypeArrayConvert());
		return dataConvertor;
	}
	
	public <T> T convert(Class<T> clazz,Object object){
		Convert convert=getConvert(clazz);
		return convert==null?(T) object:(T) convert.convert(object,clazz);
	}

	private Convert getConvert(Class<?> clazz) {
		if(JClassUtils.isSimpleTypeArray(clazz)){
			return converts.get(SimpleTypeArrayConvert.class);
		}
		return converts.get(clazz);
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
	
	public static class StringConvert implements Convert{
		@Override
		public Object convert(Object object,Class<?> clazz) {
			if(object==null) return null;
			else if(String.class.isInstance(object)){
				return object;
			}
			else{
				return String.valueOf(object);
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
			if(object==null||JStringUtils.isNullOrEmpty(String.valueOf(object))) return null;
			else if(Date.class.isInstance(object)){
				return object;
			}
			else{
				String dateString=String.valueOf(object).trim();
				String dateFormat="";
				if(dateString.contains("-")){
					dateFormat="yyyy-MM-dd";
				}
				else if(dateString.contains("/")){
					dateFormat="dd/MM/yyyy";
				}
				Pattern patternHH=Pattern.compile("\\d +\\d");
				if(patternHH.matcher(dateString).find()){
					dateFormat=dateFormat+" HH";
				}
				int count=0;
				for(int i=0;i<dateString.length();i++){
					char ch=dateString.charAt(i);
					if(':'==ch){
						count++;
						if(count==1){
							dateFormat=dateFormat+":mm";
						}
						else if(count==2){
							dateFormat=dateFormat+":ss";
						}
					}
					else if('.'==ch){
						dateFormat=dateFormat+".SSS";
					}
				}
				try{
					return JDateUtils.parseTimestampWithSeconds(dateString, dateFormat);
				}catch(Exception e){
					return JDateUtils.parseTimestampWithSeconds(dateString, pattern);
				}
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
	
	public class CollectionConvert implements Convert{
		@Override
		public Object convert(Object object,Class<?> clazz) {
			// future do
			return object;
		}
	}
	
	
	public class SimpleTypeArrayConvert implements Convert{
		
		@Override
		public Object convert(Object object,Class<?> clazz) {
			if(object==null) return null;
			JAssert.state(object.getClass().isArray(), "the argument object must be array.");

			Object[] objs=(Object[]) object;
			if(String[].class.isAssignableFrom(clazz)){
				return resultOnClass(String.class, objs,new String[0]);
			}
			if(Byte[].class.isAssignableFrom(clazz)){
				return resultOnClass(Byte.class, objs,new Byte[0]);
			}
			if(byte[].class.isAssignableFrom(clazz)){
				return resultOnClass(byte.class, objs,new Byte[0]);
			}
			if(Integer[].class.isAssignableFrom(clazz)){
				return resultOnClass(Integer.class, objs,new Integer[0]);
			}
			if(int[].class.isAssignableFrom(clazz)){
				return resultOnClass(int.class, objs,new Integer[0]);
			}
			if(Long[].class.isAssignableFrom(clazz)){
				return resultOnClass(Long.class, objs,new Long[0]);
			}
			if(long[].class.isAssignableFrom(clazz)){
				return resultOnClass(long.class, objs,new Long[0]);
			}
			if(Float[].class.isAssignableFrom(clazz)){
				return resultOnClass(Float.class, objs,new Float[0]);
			}
			if(float[].class.isAssignableFrom(clazz)){
				return resultOnClass(float.class, objs,new Float[0]);
			}
			if(Double[].class.isAssignableFrom(clazz)){
				return resultOnClass(Double.class, objs,new Double[0]);
			}
			if(double[].class.isAssignableFrom(clazz)){
				return resultOnClass(double.class, objs,new Double[0]);
			}
			if(Date[].class.isAssignableFrom(clazz)){
				return resultOnClass(Date.class, objs,new Date[0]);
			}
			if(Timestamp[].class.isAssignableFrom(clazz)){
				return resultOnClass(Timestamp.class, objs,new Timestamp[0]);
			}
			if(BigDecimal[].class.isAssignableFrom(clazz)){
				return resultOnClass(BigDecimal.class, objs,new BigDecimal[0]);
			}
		
			return object;
		}

		private <T> T[] resultOnClass(Class<T> clazz, Object[] objs , T[] ref) {
			List<T> result=new ArrayList<T>();
			for(int i=0;i<objs.length;i++){
				result.add((T) JDataConvertor.this.getConvert(clazz).convert(objs[i], clazz));
			}
			return result.toArray(ref);
		}
	}
	
	private Map<Class<?>, Convert> converts=new HashMap<Class<?>, JDataConvertor.Convert>();
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
