package j.jave.platform.basicwebcomp.spirngjpa.query;

import j.jave.kernal.jave.model.JPageable;
import j.jave.kernal.jave.utils.JAssert;
import j.jave.kernal.jave.utils.JStringUtils;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;

public class QueryBuilder {
	
	private String jpql;
	
	private String countJpql;
	
	private String namedSql;
	
	private String countNamedSql;
	
	private String nativeSql;
	
	private String countNativeSql;
	
	private Class<?> result;
	
	private Map<?, Object> params=new HashMap<Object, Object>();
	
	private JPageable pageable;
	
	private EntityManager entityManager;
	
	private boolean isSingle;
	
	protected String resultSetMapping;
	
	public static QueryBuilder get(EntityManager entityManager){
		return new QueryBuilder().setEntityManager(entityManager);
	}
	
	public QueryExecution build(){
		JAssert.isNotNull(entityManager, "entity manager must be provided.");
		QueryMeta queryMeta=null;
		if(JStringUtils.isNotNullOrEmpty(namedSql)){
			NamedQueryMeta namedQueryMeta=new NamedQueryMeta(entityManager);
			namedQueryMeta.setNamedSql(namedSql);
			namedQueryMeta.setCountNamedSql(countNamedSql);
			queryMeta=namedQueryMeta;
		}
		else if(JStringUtils.isNotNullOrEmpty(jpql)){
			JPQLQueryMeta jpqlQueryMeta=new JPQLQueryMeta(entityManager);
			jpqlQueryMeta.setJpql(jpql);
			jpqlQueryMeta.setCountSql(countJpql);;
			queryMeta=jpqlQueryMeta;
		}
		else if(JStringUtils.isNotNullOrEmpty(nativeSql)){
			NativeQueryMeta nativeQueryMeta=new NativeQueryMeta(entityManager);
			nativeQueryMeta.setSql(nativeSql);
			nativeQueryMeta.setCountSql(countNativeSql);
			queryMeta=nativeQueryMeta;
		}
		JAssert.isNotNull(queryMeta, "Any one of JPQL,NamedSQL or NativeSQL must be provided.");
		
		queryMeta.setPageable(pageable);
		queryMeta.setParams(params);
		queryMeta.setResult(result);
		queryMeta.setSingle(isSingle);
		queryMeta.setResultSetMapping(resultSetMapping);
		QueryExecution queryExecution=null;
		if(queryMeta.isPageable()){
			queryExecution=new QueryExecution.PagedExecution(queryMeta);
		}
		else if(queryMeta.isSingle()){
			queryExecution=new QueryExecution.SingleExecution(queryMeta);
		}
		else{
			queryExecution=new QueryExecution.ListExecution(queryMeta);
		}
		return queryExecution;
	}

	public QueryBuilder setJpql(String jpql) {
		this.jpql = jpql;
		return this;
	}

	public QueryBuilder setCountJpql(String countJpql) {
		this.countJpql = countJpql;
		return this;
	}

	public QueryBuilder setNamedSql(String namedSql) {
		this.namedSql = namedSql;
		return this;
	}

	public QueryBuilder setCountNamedSql(String countNamedSql) {
		this.countNamedSql = countNamedSql;
		return this;
	}

	public QueryBuilder setResult(Class<?> result) {
		this.result = result;
		return this;
	}

	public QueryBuilder setParams(Map<?, Object> params) {
		this.params = params;
		return this;
	}

	public QueryBuilder setPageable(JPageable pageable) {
		this.pageable = pageable;
		return this;
	}
	
	private QueryBuilder setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
		return this;
	}
	
	public QueryBuilder setNativeSql(String nativeSql) {
		this.nativeSql = nativeSql;
		return this;
	}
	
	public QueryBuilder setCountNativeSql(String countNativeSql) {
		this.countNativeSql = countNativeSql;
		return this;
	}
	
	public QueryBuilder setSingle(boolean isSingle) {
		this.isSingle = isSingle;
		return this;
	}
	
	public QueryBuilder setResultSetMapping(String resultSetMapping) {
		this.resultSetMapping = resultSetMapping;
		return this;
	}
}
