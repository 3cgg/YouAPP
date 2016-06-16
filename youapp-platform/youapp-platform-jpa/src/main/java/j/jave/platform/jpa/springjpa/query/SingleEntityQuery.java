package j.jave.platform.jpa.springjpa.query;

import j.jave.kernal.jave.model.JModel;
import j.jave.platform.jpa.springjpa.query.Condition.LinkType;

public class SingleEntityQuery implements JModel {

	private Class<?> entityClass;
	
	private Condition condition;
	
	private Order order;
	
	public SingleEntityQuery(Class<?> entityClass) {
		this.entityClass=entityClass;
	}

	public Condition condition(LinkType linkType){
		condition= new Condition(entityClass);
		return condition;
	}
	
	public Order order() {
		order=new Order(entityClass);
		return order;
	}
	
	public String toJPQL(){
		return "from "+entityClass.getSimpleName()+" where 1=1 and "+condition.toWhereClause()+order.toOrderClause();
	}
	
	
}
