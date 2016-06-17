package j.jave.platform.jpa.springjpa.query;

import j.jave.kernal.jave.model.JModel;

import java.util.Map;

public class SingleEntityQueryMeta implements JModel {

	public static final String ALIAS="als";
	
	private Class<?> entityClass;
	
	private Condition condition;
	
	private Order order;
	
	private SingleEntityQuery singleEntityQuery;
	
	public SingleEntityQueryMeta(Class<?> entityClass) {
		this.entityClass=entityClass;
	}
	
	public SingleEntityQueryMeta(Class<?> entityClass,SingleEntityQuery singleEntityQuery) {
		this.entityClass=entityClass;
		this.singleEntityQuery=singleEntityQuery;
	}
	
	void setSingleEntityQuery(SingleEntityQuery singleEntityQuery) {
		this.singleEntityQuery = singleEntityQuery;
	}
	
	public Condition condition(){
		condition= new Condition(entityClass);
		condition.setSingleEntityQuery(singleEntityQuery);
		return condition;
	}
	
	public Condition conditionDefault(){
		condition= new Condition(entityClass).equals("deleted","N");
		condition.setSingleEntityQuery(singleEntityQuery);
		return condition;
	}
	
	public Order order() {
		order=new Order(entityClass);
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
