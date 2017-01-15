package me.bunny.app._c.jpa.springjpa.query;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.data.jpa.repository.query.QueryUtils;

public class JNativeQuery extends JQuery<JNativeQuery> {

	private String sql;
	
	private String countSql;
	
	public JNativeQuery(EntityManager em) {
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
		Matcher matcher=Pattern.compile("^(.+)(count[(][^)]+[)])(.+)$").matcher(countSql);
		if(matcher.find()){
			matcher.replaceFirst("count(1)"); 
			countSql=matcher.group(1)+"  count(1) "+matcher.group(3);
		}
		return countSql;
	}

	@Override
	Query getCountQuery() {
		return em.createNativeQuery(getCountQueryString());
	}
	
	@Override
	Query getQuery() {
		return spiQueryService.createNativeQuery(sql, this);
	}

	public String getSql() {
		return sql;
	}

	public JNativeQuery setSql(String sql) {
		this.sql = sql;
		return this;
	}

	public String getCountSql() {
		return countSql;
	}

	public JNativeQuery setCountSql(String countSql) {
		this.countSql = countSql;
		return this;
	}
	
}
