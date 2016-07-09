package j.jave.platform.jpa.springjpa.spi;

import j.jave.kernal.eventdriven.servicehub.JServiceFactorySupport;
import j.jave.kernal.eventdriven.servicehub.JServiceHubDelegate;
import j.jave.kernal.jave.utils.JStringUtils;
import j.jave.platform.jpa.springjpa.query.JQuery;
import j.jave.platform.jpa.springjpa.query.JQueryUtil;
import j.jave.platform.jpa.springjpa.query.JSPIQueryService;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.hibernate.jpa.HibernateQuery;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.hibernate.transform.ResultTransformer;
import org.hibernate.transform.Transformers;

public class JHibernateQueryService 
extends JServiceFactorySupport<JHibernateQueryService>
implements JSPIQueryService
{
	
	private JGenericQueryService genericQueryService=JServiceHubDelegate
			.get().getService(this, JGenericQueryService.class);

	private ResultTransformer getResultTransformer(JQuery<?> _query){
		Class<?> result=_query.getResult();
		if(result!=null){
			return new AliasToBeanResultTransformer(result);
		}
		if(_query.isUseAlias()){
			return Transformers.ALIAS_TO_ENTITY_MAP;
		}
		return null;
	}
	
	@Override
	public <T> T getSingleResult(Query query, JQuery<?> _query) {
		if(JStringUtils.isNotNullOrEmpty(_query.getResultSetMapping())){
			return genericQueryService.getSingleResult(query, _query);
		}
		HibernateQuery hibernateQuery=(HibernateQuery) query;
		org.hibernate.Query hQuery=hibernateQuery.getHibernateQuery();
		hQuery.setResultTransformer(getResultTransformer(_query));
		List<?> list=hQuery.list();
		return list.isEmpty()?null:(T)(list.get(0));
	}

	@Override
	public <T> List<T> getResultList(Query query, JQuery<?> _query) {
		if(JStringUtils.isNotNullOrEmpty(_query.getResultSetMapping())){
			return genericQueryService.getResultList(query, _query);
		}
		HibernateQuery hibernateQuery=(HibernateQuery) query;
		org.hibernate.Query hQuery=hibernateQuery.getHibernateQuery();
		hQuery.setResultTransformer(getResultTransformer(_query));
		return hQuery.list();
	}

	@Override
	public JHibernateQueryService doGetService() {
		return this;
	}
	
	@Override
	public Query createJPQLQuery(String jpql, JQuery<?> _query) {
//		Class<?> result=_query.getResult();
		EntityManager em=JQueryUtil.getEntityManager(_query);
//		if(result!=null){
//			return em.createQuery(jpql,result);
//		}
		return em.createQuery(jpql);
	}

	@Override
	public Query createNamedQuery(String namedSql, JQuery<?> _query) {
//		Class<?> result=_query.getResult();
		EntityManager em=JQueryUtil.getEntityManager(_query);
//		if(result!=null){
//			return em.createNamedQuery(namedSql,result);
//		}
		return em.createNamedQuery(namedSql);
	}

	@Override
	public Query createNativeQuery(String nativeSql, JQuery<?> _query) {
		String resultSetMapping=_query.getResultSetMapping();
		EntityManager em=JQueryUtil.getEntityManager(_query);
//		if(result!=null){
//			return em.createNativeQuery(nativeSql,result);
//		}
		if(resultSetMapping!=null){
			return em.createNativeQuery(nativeSql,resultSetMapping);
		}
		return em.createNativeQuery(nativeSql);
	}
	
	
}
