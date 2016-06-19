package j.jave.platform.jpa.springjpa.query;

import j.jave.kernal.jave.model.JModel;

import java.util.Map;

public class JSingleEntityQueryMeta implements JModel {

	public static final String ALIAS="als";
	
	private Class<?> entityClass;
	
	private JCondition condition;
	
	private JOrder order;
	
	private JSingleEntityQuery singleEntityQuery;
	
	public JSingleEntityQueryMeta(Class<?> entityClass) {
		this.entityClass=entityClass;
	}
	
	public JSingleEntityQueryMeta(Class<?> entityClass,JSingleEntityQuery singleEntityQuery) {
		this.entityClass=entityClass;
		this.singleEntityQuery=singleEntityQuery;
	}
	
	void setSingleEntityQuery(JSingleEntityQuery singleEntityQuery) {
		this.singleEntityQuery = singleEntityQuery;
	}
	
	public JCondition condition(){
		condition= new JCondition(entityClass);
		condition.setSingleEntityQuery(singleEntityQuery);
		return condition;
	}
	
	public JCondition conditionDefault(){
		condition= new JCondition(entityClass).equals("deleted","N");
		condition.setSingleEntityQuery(singleEntityQuery);
		return condition;
	}
	
	public JOrder order() {
		order=new JOrder(entityClass);
		order.setSingleEntityQuery(singleEntityQuery);
		return order;
	}
	
	public String toJPQL(){
		String clause="from "+entityClass.getSimpleName()+" "+ALIAS;
		if(condition!=null){
			clause=clause+" "+condition.toWhereClause();
		}
		if(order!=null){
			clause=clause+" "+order.toOrderClause();
		}
		return clause;
	}
	
	public Map<String, Object> toParams(){
		return condition.toParams();
	}
	
}
