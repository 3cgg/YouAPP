package j.jave.platform.jpa.springjpa.query;

import j.jave.kernal.jave.service.JService;

import java.util.List;

import javax.persistence.Query;

public interface JSPIQueryService extends JService {

	<T> T getSingleResult(Query query,JQuery<?> _query);
	
	<T> List<T> getResultList(Query query,JQuery<?> _query);
	
	Query createJPQLQuery(String jpql,JQuery<?> _query);
	
	Query createNamedQuery(String namedSql,JQuery<?> _query);
	
	Query createNativeQuery(String nativeSql,JQuery<?> _query);
}
