package j.jave.platform.jpa.springjpa.query;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.data.jpa.repository.query.QueryUtils;

class JPQLQueryMeta extends QueryMeta{

	private String jpql;
	
	private String countSql;
	
	public JPQLQueryMeta(EntityManager em) {
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
	public Query getCountQuery() {
		return em.createQuery(getCountQueryString());
	}

	@Override
	public Query getQuery() {
		if(result!=null){
			return em.createQuery(jpql,result);
		}
		return em.createQuery(jpql);
	}

	public String getJpql() {
		return jpql;
	}

	public void setJpql(String jpql) {
		this.jpql = jpql;
	}

	public void setCountSql(String countSql) {
		this.countSql = countSql;
	}
	
	
}
