package j.jave.platform.basicwebcomp.core.service;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.data.jpa.repository.query.QueryUtils;

public class NativeQueryMeta extends QueryMeta {

	private String sql;
	
	private String countSql;
	
	public NativeQueryMeta(EntityManager em) {
		super(em);
	}

	@Override
	public String getQueryString() {
		return sql;
	}

	@Override
	public String getCountQueryString() {
		if(countSql==null){
			countSql=QueryUtils.createCountQueryFor(sql);
		}
		return countSql;
	}

	@Override
	public Query getCountQuery() {
		return em.createNativeQuery(getCountQueryString());
	}
	
	@Override
	public Query getQuery() {
		return em.createNamedQuery(sql);
	}

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	public String getCountSql() {
		return countSql;
	}

	public void setCountSql(String countSql) {
		this.countSql = countSql;
	}
	
}
