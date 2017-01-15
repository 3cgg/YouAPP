package me.bunny.kernel._c.support.parser;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import me.bunny.kernel.JConfiguration;
import me.bunny.kernel.JProperties;
import me.bunny.kernel._c.reflect.JClassUtils;
import me.bunny.kernel._c.utils.JAssert;
import me.bunny.kernel._c.utils.JDateUtils;
import me.bunny.kernel._c.utils.JStringUtils;

public class JDefaultSimpleDataParser {
	
	private Map<Class<?>, JSimpleDataParser<?>> parsers=new HashMap<Class<?>, JSimpleDataParser<?>>();
	
	public static JDefaultSimpleDataParser getDefault(){
		return build(JConfiguration.get());
	}
	
	public static JDefaultSimpleDataParser build(JConfiguration configuration){
		JDefaultSimpleDataParser dataParser=new JDefaultSimpleDataParser();
		dataParser.parsers.put(String.class, new StringParser());
		dataParser.parsers.put(Integer.class, new IntegerParser());
		dataParser.parsers.put(int.class, new IntegerParser());
		dataParser.parsers.put(Long.class, new LongParser());
		dataParser.parsers.put(long.class, new LongParser());
		dataParser.parsers.put(Float.class, new FloatParser());
		dataParser.parsers.put(float.class, new FloatParser());
		dataParser.parsers.put(Double.class, new DoubleParser());
		dataParser.parsers.put(double.class, new DoubleParser());
		dataParser.parsers.put(Boolean.class, new BooleanParser());
		dataParser.parsers.put(boolean.class, new BooleanParser());
		dataParser.parsers.put(BigDecimal.class, new BigDecimalParser());
		dataParser.parsers.put(Date.class, new TimestampParser(
				configuration.getString(JProperties.DATE_FORMAT, JDateUtils.yyyyMMddHHmmss)));
		dataParser.parsers.put(Timestamp.class, new TimestampParser(
				configuration.getString(JProperties.DATE_FORMAT, JDateUtils.yyyyMMddHHmmss)));

		//for array
		dataParser.parsers.put(String[].class, new SimpleTypeArrayParser(String[].class));
		dataParser.parsers.put(Byte[].class, new SimpleTypeArrayParser(Byte[].class));
		dataParser.parsers.put(byte[].class, new SimpleTypeArrayParser(byte[].class));
		dataParser.parsers.put(Integer[].class, new SimpleTypeArrayParser(Integer[].class));
		dataParser.parsers.put(int[].class, new SimpleTypeArrayParser(int[].class));
		dataParser.parsers.put(Long[].class, new SimpleTypeArrayParser(Long[].class));
		dataParser.parsers.put(long[].class, new SimpleTypeArrayParser(long[].class));
		dataParser.parsers.put(Float[].class, new SimpleTypeArrayParser(Float[].class));
		dataParser.parsers.put(float[].class, new SimpleTypeArrayParser(float[].class));
		dataParser.parsers.put(Double[].class, new SimpleTypeArrayParser(Double[].class));
		dataParser.parsers.put(double[].class, new SimpleTypeArrayParser(double[].class));
		dataParser.parsers.put(Boolean[].class, new SimpleTypeArrayParser(Boolean[].class));
		dataParser.parsers.put(boolean[].class, new SimpleTypeArrayParser(boolean[].class));
		dataParser.parsers.put(Date[].class, new SimpleTypeArrayParser(Date[].class));
		dataParser.parsers.put(Timestamp[].class, new SimpleTypeArrayParser(Timestamp[].class));
		dataParser.parsers.put(BigDecimal[].class, new SimpleTypeArrayParser(BigDecimal[].class));
		
		
		return dataParser;
	}
	
	@SuppressWarnings("unchecked")
	public <T> T parse(Class<T> clazz,Object object){
		JSimpleDataParser<?> parser=getParser(clazz, object);
		try {
			return parser==null?(T) object:(T) parser.parse(object);
		} catch (Exception e) {
			throw new JParsingException(e.getMessage(),e);
		}
	}

	private JSimpleDataParser<?> getParser(Class<?> clazz,Object object) {
		if(JClassUtils.isSimpleTypeArray(clazz)){
			return parsers.get(SimpleTypeArrayParser.class);
		}
		return parsers.get(clazz);
	}
	
	public static class IntegerParser implements JSimpleDataParser<Integer>{
		@Override
		public Integer parse(Object object) throws Exception {
			if(object==null) return null;
			else if(Integer.class.isInstance(object)){
				return (Integer) object;
			}
			else{
				return Integer.parseInt(String.valueOf(object));
			}
		}
	}
	
	public static class LongParser implements JSimpleDataParser<Long>{
		@Override
		public Long parse(Object object) throws Exception{
			if(object==null) return null;
			else if(Long.class.isInstance(object)){
				return (Long) object;
			}
			else{
				return Long.parseLong(String.valueOf(object));
			}
		}
	}
	
	public static class DoubleParser implements JSimpleDataParser<Double>{
		
		@Override
		public Double parse(Object object) throws Exception {
			if(object==null) return null;
			else if(Double.class.isInstance(object)){
				return (Double) object;
			}
			else{
				return Double.parseDouble(String.valueOf(object));
			}
		}
	}
	
	public static class StringParser implements JSimpleDataParser<String>{
		
		@Override
		public String parse(Object object) throws Exception {
			if(object==null) return null;
			else if(String.class.isInstance(object)){
				return (String) object;
			}
			else{
				return String.valueOf(object);
			}
		}
	}
	
	public static class FloatParser implements JSimpleDataParser<Float>{
		
		@Override
		public Float parse(Object object) throws Exception {
			if(object==null) return null;
			else if(Float.class.isInstance(object)){
				return (Float) object;
			}
			else{
				return Float.parseFloat(String.valueOf(object));
			}
		}
	}
	
	public static class ByteParser implements JSimpleDataParser<Byte>{
		
		@Override
		public Byte parse(Object object) throws Exception {
			if(object==null) return null;
			else if(Byte.class.isInstance(object)){
				return (Byte) object;
			}
			else{
				return Byte.parseByte(String.valueOf(object));
			}
		}
	}
	
	public static class BooleanParser implements JSimpleDataParser<Boolean>{
		
		@Override
		public Boolean parse(Object object) throws Exception {
			if(object==null) return null;
			else if(Boolean.class.isInstance(object)){
				return (Boolean) object;
			}
			else{
				return Boolean.parseBoolean(String.valueOf(object));
			}
		}
		
	}
	
	public static class TimestampParser implements JSimpleDataParser<Timestamp>{
		private String pattern="";
		
		public TimestampParser(String pattern ){
			this.pattern=pattern;
		}
		
		
		@Override
		public Timestamp parse(Object object) throws Exception {
			if(object==null||JStringUtils.isNullOrEmpty(String.valueOf(object))) return null;
			else if(Timestamp.class.isInstance(object)){
				return (Timestamp) object;
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
	
	public static class BigDecimalParser implements JSimpleDataParser<BigDecimal>{
		
		@Override
		public BigDecimal parse(Object object) throws Exception {
			if(object==null) return null;
			else if(BigDecimal.class.isInstance(object)){
				return (BigDecimal) object;
			}
			else{
				return new BigDecimal(String.valueOf(object));
			}
		}
	}
	
	public class CollectionParser implements JSimpleDataParser<Collection<?>>{
		
		@Override
		public Collection<?> parse(Object object) throws Exception {
			// TODO  do later
			return (Collection<?>) object;
		}
	}
	
	
	public static class SimpleTypeArrayParser implements JSimpleDataParser<Object>{
		
		private Class<?> clazz;
		
		public SimpleTypeArrayParser(Class<?> clazz) {
			this.clazz=clazz;
		}
		
		@Override
		public Object parse(Object object) throws Exception {
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

		@SuppressWarnings("unchecked")
		private <T> T[] resultOnClass(Class<T> clazz, Object[] objs , T[] ref) throws Exception {
			List<T> result=new ArrayList<T>();
			for(int i=0;i<objs.length;i++){
				Object obj=objs[i];
				result.add((T) JDefaultSimpleDataParser.getDefault().getParser(clazz,obj).parse(obj));
			}
			return result.toArray(ref);
		}
	}
	
}
