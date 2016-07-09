package j.jave.platform.jpa.springjpa.spi;

import j.jave.kernal.eventdriven.servicehub.JServiceFactorySupport;
import j.jave.platform.jpa.springjpa.query.JQuery;
import j.jave.platform.jpa.springjpa.query.JQueryUtil;
import j.jave.platform.jpa.springjpa.query.JSPIQueryService;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

public class JGenericQueryService 
extends JServiceFactorySupport<JGenericQueryService>
implements JSPIQueryService
{
	
	@Override
	public <T> T getSingleResult(Query query, JQuery<?> _query) {
		Object object= query.getSingleResult();
		Class<?> result=_query.getResult();
		return result==null?(T)object:(T)result.cast(object);
	}

	@Override
	public <T> List<T> getResultList(Query query, JQuery<?> _query) {
		return query.getResultList();
	}

	@Override
	public JGenericQueryService doGetService() {
		return this;
	}

	@Override
	public Query createJPQLQuery(String jpql, JQuery<?> _query) {
		Class<?> result=_query.getResult();
		EntityManager em=JQueryUtil.getEntityManager(_query);
		if(result!=null){
			return em.createQuery(jpql,result);
		}
		return em.createQuery(jpql);
	}

	@Override
	public Query createNamedQuery(String namedSql, JQuery<?> _query) {
		Class<?> result=_query.getResult();
		EntityManager em=JQueryUtil.getEntityManager(_query);
		if(result!=null){
			return em.createNamedQuery(namedSql,result);
		}
		return em.createNamedQuery(namedSql);
	}

	@Override
	public Query createNativeQuery(String nativeSql, JQuery<?> _query) {
		Class<?> result=_query.getResult();
		String resultSetMapping=_query.getResultSetMapping();
		EntityManager em=JQueryUtil.getEntityManager(_query);
		if(result!=null){
			return em.createNativeQuery(nativeSql,result);
		}
		if(resultSetMapping!=null){
			return em.createNativeQuery(nativeSql,resultSetMapping);
		}
		return em.createNativeQuery(nativeSql);
	}
	
}
