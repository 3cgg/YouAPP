package me.bunny.kernel._c.xml.xmldb.jql;

import java.util.Map;

import me.bunny.kernel._c.model.JBaseModel;
import me.bunny.kernel._c.utils.JNumberUtils;

public abstract class JIF extends JAbstractFilter  {

	public final static String EQUAL = "=";
	public final static String LIKE = "LIKE";
	public final static String LT = "<";
	public final static String GT = ">";
	public final static String GTEQUAL = ">=";
	public final static String LTEQUAL = "<=";
	public final static String NOTEQUAL = "<>";
	
	protected String compare;
	protected String param;
	public JIF() {
	}

	public String getCompare() {
		return compare;
	}

	public void setCompare(String compare) {
		if (EQUAL.equals(compare) 
				|| LIKE.equals(compare)
				||LT.equals(compare) 
				||GT.equals(compare) 
				||GTEQUAL.equals(compare) 
				||LTEQUAL.equals(compare) 
				||NOTEQUAL.equals(compare) 
				
				) {
			this.compare = compare;
		} else {
			throw new RuntimeException("parameter must be " + EQUAL 
					+ " or "+ LIKE
					+" or "+ LT
					+" or "+ GT
					+" or "+ GTEQUAL
					+" or "+ LTEQUAL
					+" or "+ NOTEQUAL
					);
		}
	}
	
	protected boolean validate(Object key,Object value){
		
		String keyString=key==null?"":String.valueOf(key);
		String valueString=value==null?"":String.valueOf(value);
		
		Comparable keyValue=keyString;
		Comparable valueValue=valueString;
		
		if(JNumberUtils.isNumber(keyString)
				&&JNumberUtils.isNumber(valueString)){
			keyValue=Double.valueOf(keyString);
			valueValue=Double.valueOf(valueString);
		}
		
		if(EQUAL.equals(compare)){
			return keyValue.equals(valueValue);
		}
		else if(LIKE.equals(compare)){
			return String.valueOf(keyValue).toUpperCase().indexOf(String.valueOf(valueValue).toUpperCase())!=-1;
		}
		else if(LT.equals(compare)){
			return keyValue.compareTo(valueValue)<0;
		}
		else if(GT.equals(compare)){
			return keyValue.compareTo(valueValue)>0;
		}
		else if(LTEQUAL.equals(compare)){
			return keyValue.compareTo(valueValue)<=0;
		}
		else if(GTEQUAL.equals(compare)){
			return keyValue.compareTo(valueValue)>=0;
		}
		else if(NOTEQUAL.equals(compare)){
			return keyValue.compareTo(valueValue)!=0;
		}
		return false;
	}
	
	

	public String getParam() {
		return param;
	}

	public void setParam(String param) {
		this.param = param;
	}

	@Override
	public String name() {
		return JIF.class.getName();
	}

	@Override
	public void push(JFilter filter) {
		throw new UnsupportedOperationException("IF cannot contain other filter. ");
	}
	
	@Override
	public void validateExpress(Map<String, Class<? extends JBaseModel>> from) {
		
	}
}
