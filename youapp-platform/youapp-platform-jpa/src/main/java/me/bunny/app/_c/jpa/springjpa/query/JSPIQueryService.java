package me.bunny.app._c.jpa.springjpa.query;

import java.util.List;

import javax.persistence.Query;

import me.bunny.kernel._c.service.JService;

public interface JSPIQueryService extends JService {

	<T> T getSingleResult(Query query,JQuery<?> _query);
	
	<T> List<T> getResultList(Query query,JQuery<?> _query);
	
	Query createJPQLQuery(String jpql,JQuery<?> _query);
	
	Query createNamedQuery(String namedSql,JQuery<?> _query);
	
	Query createNativeQuery(String nativeSql,JQuery<?> _query);
}
