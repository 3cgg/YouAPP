package j.jave.platform.jpa.springjpa.query;

import j.jave.kernal.jave.model.JModel;
import j.jave.kernal.jave.utils.JStringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Condition implements JModel {

	private Class<?> entityClass;
	
	private LinkType linkType;
	
	public Condition(Class<?> entityClass) {
		this(entityClass,LinkType.AND);
	}
	
	public Condition(Class<?> entityClass,LinkType linkType) {
		this.entityClass=entityClass;
		this.linkType=linkType;
	}
	
	private List<String> whereClause=new ArrayList<String>();
	
	private Map<String, Object> params=new HashMap<String, Object>();
	
	private boolean validate(String property) throws IllegalArgumentException{
		return true;
	}
	
	public Map<String, Object> getParams() {
		return params;
	}
	
	public String toWhereClause(){
		StringBuffer stringBuffer=new StringBuffer("");
		String prefix=linkType.name();
		for(String clause:whereClause){
			stringBuffer.append(prefix);
			stringBuffer.append(" "+clause+" ");
		}
		String inner=stringBuffer.toString().replaceFirst(prefix, "").trim();
		return JStringUtils.isNullOrEmpty(inner)?"":("("+inner+")");
	}
	
	public static enum LinkType{
		AND("AND"),OR("OR");
		private LinkType(String type){
			
		}
		
	}
	
	private interface Ope{
		String EQUAL=" = ";
		String NOT_EQUAL=" != ";
		String LARGER=" > ";
		String LARGER_EQUAL=" >= ";
		String SMALLER=" < ";
		String SMALLER_EQUAL=" <= ";
	}
	
	private Condition append(String property,Object value,String opeType){
		whereClause.add(property+opeType+" :"+property);
		params.put(property, value);
		return this;
	}
	
	public Condition equals(String property,Object value){
		validate(property);
		append(property, value, Ope.EQUAL);
		return this;
	}
	
	public Condition notEquals(String property,Object value){
		validate(property);
		append(property, value, Ope.NOT_EQUAL);
		return this;
	}
	
	public Condition larger(String property,Object value){
		validate(property);
		append(property, value, Ope.LARGER);
		return this;
	}

	public Condition largerAndEquals(String property,Object value){
		validate(property);
		append(property, value, Ope.LARGER_EQUAL);
		return this;
	}
	
	public Condition smaller(String property,Object value){
		validate(property);
		append(property, value, Ope.SMALLER);
		return this;
	}
	
	public Condition smallerAndEqual(String property,Object value){
		validate(property);
		append(property, value, Ope.SMALLER_EQUAL);
		return this;
	}
	
	
	
	
	
	
	
	
	
	
}
