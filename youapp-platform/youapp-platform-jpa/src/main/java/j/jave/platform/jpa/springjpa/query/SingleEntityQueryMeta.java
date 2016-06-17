package j.jave.platform.jpa.springjpa.query;

import java.util.Map;

import j.jave.kernal.jave.model.JModel;
import j.jave.platform.jpa.springjpa.query.Condition.LinkType;

public class SingleEntityQueryMeta implements JModel {

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
	
	public Condition condition(LinkType linkType){
		condition= new Condition(entityClass,linkType);
		condition.setSingleEntityQuery(singleEntityQuery);
		return condition;
	}
	
	public Condition condition(){
		condition= new Condition(entityClass);
		condition.setSingleEntityQuery(singleEntityQuery);
		return condition;
	}
	
	
	public Order order() {
		order=new Order(entityClass);
		order.setSingleEntityQuery(singleEntityQuery);
		return order;
	}
	
	public String toJPQL(){
		return "from "+entityClass.getSimpleName()+" where 1=1 "+condition.toWhereClause()+order.toOrderClause();
	}
	
	public Map<String, Object> toParams(){
		return condition.toParams();
	}
	
}
