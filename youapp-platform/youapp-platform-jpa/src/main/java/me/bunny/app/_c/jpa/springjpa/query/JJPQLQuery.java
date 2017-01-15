package me.bunny.app._c.jpa.springjpa.query;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.data.jpa.repository.query.QueryUtils;

public class JJPQLQuery extends JQuery<JJPQLQuery>{

	private String jpql;
	
	private String countSql;
	
	public JJPQLQuery(EntityManager em) {
		super(em);
	}
	
	public String getQueryString() {
		return this.jpql;
	}
	
	@Override
	public String getCountQueryString() {
		if(countSql==null){
			countSql=QueryUtils.createCountQueryFor(jpql);
		}
		return countSql;
	}

	@Override
	Query getCountQuery() {
		return em.createQuery(getCountQueryString());
	}

	@Override
	Query getQuery() {
		return spiQueryService.createJPQLQuery(jpql, this);
	}

	public String getJpql() {
		return jpql;
	}

	public JJPQLQuery setJpql(String jpql) {
		this.jpql = jpql;
		return this;
	}

	public JJPQLQuery setCountSql(String countSql) {
		this.countSql = countSql;
		return this;
	}
	
	
}
