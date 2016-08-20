package j.jave.platform.jpa.springjpa.query;

import j.jave.kernal.eventdriven.servicehub.JServiceHubDelegate;
import j.jave.kernal.jave.model.JModel;

import java.util.Collection;
import java.util.Map;

public class JSingleEntityQueryMeta implements JModel {

	private JEntityUtilService entityUtilService
	=JServiceHubDelegate.get().getService(this, JEntityUtilService.class);
	
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
	
	public String toCountJPQL(){
		return "select count(1) "+toJPQL();
	}
	
	public String toJPQL(){
		String clause=" from "+entityClass.getSimpleName()+" "+ALIAS;
		if(condition!=null){
			clause=clause+" "+condition.toWhereClause();
		}
		if(order!=null){
			clause=clause+" "+order.toOrderClause();
		}
		return clause;
	}
	
	public String toAliasJPQL(){
		String jpql=toJPQL();
		JEntityModelMeta entityModelMeta=entityUtilService.getEntityModelMeta(entityClass);
		Collection<JEntityColumnMeta> entityColumnMetas=entityModelMeta.columnMetas();
		String selectCause="select ";
		for(JEntityColumnMeta columnMeta:entityColumnMetas){
			selectCause=selectCause+ALIAS+"."+columnMeta.getProperty()
					+" as "+columnMeta.getProperty()+" ,";
		}
		selectCause=selectCause.substring(0, selectCause.lastIndexOf(","));
		return selectCause+jpql;
	}
	
	public Map<String, Object> toParams(){
		return condition.toParams();
	}
	
}
