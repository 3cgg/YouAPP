package j.jave.platform.jpa.springjpa.query;

import j.jave.kernal.jave.model.JModel;
import j.jave.kernal.jave.utils.JStringUtils;

import java.util.ArrayList;
import java.util.List;

public class Order implements JModel {

	private Class<?> entityClass;
	
	public Order(Class<?> entityClass) {
		this.entityClass=entityClass;
	}
	
	private List<String> orderClause=new ArrayList<String>();
	
	private boolean validate(String property) throws IllegalArgumentException{
		return true;
	}
	
	public String toOrderClause(){
		StringBuffer stringBuffer=new StringBuffer("");
		String prefix=",";
		for(String clause:orderClause){
			stringBuffer.append(prefix);
			stringBuffer.append(" "+clause+" ");
		}
		String inner=stringBuffer.toString().replaceFirst(prefix, "").trim();
		return JStringUtils.isNullOrEmpty(inner)?"":(" order by "+inner);
	}
	
	public static enum OrderType{
		ASC("ASC"),DESC("DESC");
		private OrderType(String type){
			
		}
	}
	
	private Order append(String property,String orderType){
		orderClause.add(property+" "+orderType);
		return this;
	}
	
	public Order asc(String property){
		validate(property);
		append(property, OrderType.ASC.name());
		return this;
	}
	
	public Order desc(String property){
		validate(property);
		append(property, OrderType.DESC.name());
		return this;
	}
	
	
}
