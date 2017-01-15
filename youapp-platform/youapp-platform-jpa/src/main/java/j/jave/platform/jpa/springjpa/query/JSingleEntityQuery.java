package j.jave.platform.jpa.springjpa.query;

import java.util.List;

import javax.persistence.EntityManager;

import me.bunny.kernel.jave.model.JPage;
import me.bunny.kernel.jave.model.JPageable;

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
	
	public <T> List<T> models(){
		return JQueryBuilder.get(entityManager).jpqlQuery()
		.setJpql(singleEntityQueryMeta.toJPQL())
		.setParams(singleEntityQueryMeta.toParams())
		.models();
	}
	
	public <T> List<T> models(Class<T> clazz){
		return JQueryBuilder.get(entityManager).jpqlQuery()
		.setJpql(singleEntityQueryMeta.toAliasJPQL())
		.setParams(singleEntityQueryMeta.toParams())
		.models(clazz);
	}
	
	public <T> T model(){
		return JQueryBuilder.get(entityManager).jpqlQuery()
		.setJpql(singleEntityQueryMeta.toJPQL())
		.setParams(singleEntityQueryMeta.toParams())
		.model();
	}
	
	public <T> T model(Class<T> clazz){
		return JQueryBuilder.get(entityManager).jpqlQuery()
		.setJpql(singleEntityQueryMeta.toAliasJPQL())
		.setParams(singleEntityQueryMeta.toParams())
		.model(clazz);
	}
	
	public long count(){
		return JQueryBuilder.get(entityManager).jpqlQuery()
				.setJpql(singleEntityQueryMeta.toCountJPQL())
				.setParams(singleEntityQueryMeta.toParams())
				.model();
	}
	
	public <T> JPage<T> modelPage(JPageable pageable){
		return JQueryBuilder.get(entityManager).jpqlQuery()
		.setJpql(singleEntityQueryMeta.toJPQL())
		.setParams(singleEntityQueryMeta.toParams())
		.setPageable(pageable)
		.modelPage();
	}
	
	public <T> JPage<T> modelPage(JPageable pageable,Class<T> clazz){
		return JQueryBuilder.get(entityManager).jpqlQuery()
		.setJpql(singleEntityQueryMeta.toAliasJPQL())
		.setParams(singleEntityQueryMeta.toParams())
		.setPageable(pageable)
		.modelPage(clazz);
	}
	
}
