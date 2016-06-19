package j.jave.platform.jpa.springjpa.query;

import j.jave.kernal.jave.model.JPage;
import j.jave.kernal.jave.model.JPageable;

import java.util.List;

import javax.persistence.EntityManager;

public class JSingleEntityQuery {
	
	private JSingleEntityQueryMeta singleEntityQueryMeta;
	
	private EntityManager entityManager;

	public JSingleEntityQuery(Class<?> entityClass,
			EntityManager entityManager) {
		this.singleEntityQueryMeta = new JSingleEntityQueryMeta(entityClass,this);
		this.entityManager = entityManager;
	}
	
	public JCondition condition(){
		return singleEntityQueryMeta.condition();
	}
	
	public JCondition conditionDefault(){
		return singleEntityQueryMeta.conditionDefault();
	}
	
	public JOrder order() {
		return singleEntityQueryMeta.order();
	}
	
	public <T> List<T> executeList(){
		return JQueryBuilder.get(entityManager).jpqlQuery()
		.setJpql(singleEntityQueryMeta.toJPQL())
		.setParams(singleEntityQueryMeta.toParams())
		.execute();
	}
	
	public <T> JPage<T> executePageable(JPageable pageable){
		return JQueryBuilder.get(entityManager).jpqlQuery()
		.setJpql(singleEntityQueryMeta.toJPQL())
		.setParams(singleEntityQueryMeta.toParams())
		.setPageable(pageable)
		.execute();
	}
	
}
