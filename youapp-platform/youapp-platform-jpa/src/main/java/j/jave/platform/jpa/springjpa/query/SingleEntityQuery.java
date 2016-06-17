package j.jave.platform.jpa.springjpa.query;

import j.jave.kernal.jave.model.JPage;
import j.jave.kernal.jave.model.JPageable;
import j.jave.platform.jpa.springjpa.query.Condition.LinkType;

import java.util.List;

import javax.persistence.EntityManager;

public class SingleEntityQuery {

	private SingleEntityQueryMeta singleEntityQueryMeta;
	
	private EntityManager entityManager;

	public SingleEntityQuery(Class<?> entityClass,
			EntityManager entityManager) {
		this.singleEntityQueryMeta = new SingleEntityQueryMeta(entityClass,this);
		this.entityManager = entityManager;
	}
	
	public Condition condition(LinkType linkType){
		return singleEntityQueryMeta.condition(linkType);
	}
	
	public Condition condition(){
		return singleEntityQueryMeta.condition();
	}
	
	public Order order() {
		return singleEntityQueryMeta.order();
	}
	
	public <T> List<T> executeList(){
		return QueryBuilder.get(entityManager)
		.setJpql(singleEntityQueryMeta.toJPQL())
		.setParams(singleEntityQueryMeta.toParams())
		.build().execute();
	}
	
	public <T> JPage<T> executePageable(JPageable pageable){
		return QueryBuilder.get(entityManager)
		.setJpql(singleEntityQueryMeta.toJPQL())
		.setParams(singleEntityQueryMeta.toParams())
		.setPageable(pageable)
		.build().execute();
	}
	
}
