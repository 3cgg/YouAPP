package j.jave.platform.basicwebcomp.core.service;

import j.jave.kernal.eventdriven.exception.JServiceException;
import j.jave.kernal.jave.exception.JConcurrentException;
import j.jave.kernal.jave.logging.JLogger;
import j.jave.kernal.jave.logging.JLoggerFactory;
import j.jave.kernal.jave.model.JBaseModel;
import j.jave.kernal.jave.model.JModel;
import j.jave.kernal.jave.model.JPage;
import j.jave.kernal.jave.model.JPageImpl;
import j.jave.kernal.jave.model.JPageRequest;
import j.jave.kernal.jave.model.JPageable;
import j.jave.kernal.jave.model.support.interceptor.JDefaultModelInvocation;
import j.jave.kernal.jave.persist.JIPersist;
import j.jave.kernal.jave.utils.JAssert;
import j.jave.kernal.jave.utils.JStringUtils;
import j.jave.kernal.jave.utils.JUniqueUtils;
import j.jave.platform.basicwebcomp.core.model.JpaCalendarParam;
import j.jave.platform.basicwebcomp.core.model.JpaDateParam;
import j.jave.platform.basicwebcomp.core.model.SimplePageRequest;
import j.jave.platform.basicwebcomp.login.model.User;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.provider.PersistenceProvider;
import org.springframework.data.jpa.repository.query.QueryUtils;

/**
 * delegate service operation of a certain table, 
 * <p>include insert, update, delete(default set "DELETE" as "Y" ), get(one record according)
 * <p>sub-class should implements method of {@code getRepo()} .
 * @author J
 *
 * @param <T>
 */
public abstract class ServiceSupport<T extends JBaseModel> implements Service<T>{
	
	private static final JLogger LOGGER=JLoggerFactory.getLogger(ServiceSupport.class);
	
	@PersistenceContext
	private EntityManager em;
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void saveOnly(ServiceContext context, T object)
			throws JServiceException {
		proxyOnSave(getRepo(), context.getUser(), object);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void updateOnly(ServiceContext context, T object)
			throws JServiceException {
		proxyOnUpdate(getRepo(), context.getUser(), object);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void delete(ServiceContext context, String id) {
		getRepo().markModelDeleted(id);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public T getById(ServiceContext context, String id) {
		return getRepo().getModel(id);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public JPage<T> getsByPage(ServiceContext context, JPageable pagination) {
		JPageImpl<T> page=new JPageImpl<T>();
		List<T> records=getRepo().getModelsByPage(pagination);
		page.setPageable(pagination);
		page.setTotalRecordNumber(records.size());
		page.setContent(records);
		page.setTotalPageNumber(JPageImpl.caculateTotalPageNumber(records.size(), pagination.getPageSize()));
		return page;
	}

	public abstract JIPersist<?,T> getRepo();
	
	
	/**
	 * fill in common info.  to execute 
	 * @param jMapper
	 * @param authorizer    generally its login user 
	 * @param jBaseModel
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private T proxyOnSave(JIPersist<?, T> repo, User authorizer, JBaseModel baseModel){
		baseModel.setCreateId(authorizer.getId());
		baseModel.setCreateTime(new Timestamp(new Date().getTime()));
		baseModel.setUpdateId(authorizer.getId());
		baseModel.setUpdateTime(new Timestamp(new Date().getTime()));
		baseModel.setVersion(1);
		baseModel.setDeleted("N");
		baseModel.setId(JUniqueUtils.unique());
		
		// give a chance to do something containing model intercepter
		new JDefaultModelInvocation(baseModel).proceed();
		
		repo.saveModel((T) baseModel);
		return (T) baseModel;
	}
	
	private T get(JIPersist<?, T> repo,String id){
		return repo.getModel(id);
	}
	
	/**
	 * fill in common info.
	 * also validate whether the version changes, then to execute 
	 * @param jMapper
	 * @param user
	 * @param jBaseModel
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private T proxyOnUpdate(JIPersist<?, T> repo, User authorizer, JBaseModel baseModel){
		baseModel.setUpdateId(authorizer.getId());
		baseModel.setUpdateTime(new Timestamp(new Date().getTime()));
		
		// give a chance to do something containing model intercepter
		new JDefaultModelInvocation(baseModel).proceed();
		
		JBaseModel dbModel=get(repo, baseModel.getId());
		if(dbModel.getVersion()!=baseModel.getVersion()){
			throw new JConcurrentException("version chaged , db verion is "+dbModel.getVersion()
					+" , but current version is  "+baseModel.getVersion());
		}
		baseModel.setCreateTime(dbModel.getCreateTime());
		baseModel.setCreateId(dbModel.getCreateId());
		baseModel.setVersion(baseModel.getVersion()+1);
		int affect=repo.updateModel((T) baseModel);
		if(affect==0) throw new JConcurrentException(
				"record conflict on "+baseModel.getId()+" of "+baseModel.getClass().getName());
		return (T) baseModel;
	}
	
	/**
	 * return the complete {@link JPage} instance.
	 * @param returnPage  THE {@link Page} instance returned from underlying ORM
	 * @param pageable the passing pagination parameters
	 * @return
	 */
	protected <M extends JModel> JPage<M> toJPage(Page<M> returnPage,JPageable pageable){
		JPageImpl<M> page=new JPageImpl<M>();
		page.setContent(returnPage.getContent());
		page.setTotalRecordNumber(returnPage.getTotalElements());
		page.setTotalPageNumber(returnPage.getTotalPages());
		JPageRequest pageRequest=(JPageRequest)pageable;
		pageRequest.setPageNumber(returnPage.getNumber());
		page.setPageable(pageable);
		return page;
	}
	
	protected SimplePageRequest toPageRequest(JPageable pageable){
		return new SimplePageRequest(pageable.getPageNumber(), pageable.getPageSize());
	}
	
	public <R> R executeOnSQLQuery(QueryMeta queryMeta){
		return (R) QueryBuilder.build(queryMeta).execute(queryMeta);
	}
	
	public <R> R executeOnSQLQuery(String jpql,Map<String, Object> params,Class<R> clazz){
		Query query=em.createQuery(jpql,clazz);
		setQueryParameters(query, params);
		return clazz.cast(query.getSingleResult());
	}
	
	public <R> R executeOnNativeSQLQueryWithSingle(String sql,Map<String, Object> params,Class<R> clazz){
		Query query=em.createNativeQuery(sql, clazz);
		setQueryParameters(query, params);
		return clazz.cast(query.getSingleResult());
	}
	
	@SuppressWarnings("unchecked")
	public <R> List<R> executeOnNativeSQLQuery(String sql,Map<String, Object> params,Class<R> clazz){
		Query query=em.createNativeQuery(sql, clazz);
		setQueryParameters(query, params);
		return query.getResultList();
	}
	
	public List<?> executeOnNativeSQLQuery(String sql,Map<String, Object> params){
		Query query=em.createNativeQuery(sql);
		setQueryParameters(query, params);
		return query.getResultList();
	}
	
	public <R> List<R> executeOnSQLQuery(String jpql,Map<String, Object> params){
		Query query=em.createQuery(jpql);
		setQueryParameters(query, params);
		return query.getResultList();
	}

	public <R> JPage<R> executePageableOnSQLQuery(String jpql,JPageable pageable,Map<String, Object> params){
		String countString=QueryUtils.createCountQueryFor(jpql);
		long count=executeOnSQLQuery(countString, params, Long.class);
		int pageNumber=pageable.getPageNumber();
		int pageSize=pageable.getPageSize();
		int tempTotalPageNumber=JPageImpl.caculateTotalPageNumber(count, pageSize);
		pageNumber=pageNumber>tempTotalPageNumber?tempTotalPageNumber:pageNumber;
		
		Query query=em.createQuery(jpql);
		setQueryParameters(query, params);
		query.setFirstResult(pageNumber*pageSize);
		query.setMaxResults(pageSize);
		List list= query.getResultList();
		JPageImpl page=new JPageImpl();
		page.setContent(list);
		page.setTotalRecordNumber(count);
		page.setTotalPageNumber(tempTotalPageNumber);
		JPageRequest pageRequest=(JPageRequest)pageable;
		pageRequest.setPageNumber(pageNumber);
		page.setPageable(pageable);
		return page;
	}
	
	private static boolean hasNamedQuery(EntityManager em, String queryName) {
		/*
		 * @see DATAJPA-617
		 * we have to use a dedicated em for the lookups to avoid a potential rollback of the running tx.
		 */
		EntityManager lookupEm = em.getEntityManagerFactory().createEntityManager();

		try {
			lookupEm.createNamedQuery(queryName);
			return true;
		} catch (IllegalArgumentException e) {
			LOGGER.debug("Did not find named query {}"+queryName);
			return false;
		} finally {
			lookupEm.close();
		}
	}
	
	public <R> JPage<R> executePageableOnNamedSQLQuery(String namedSql,String countNamedSql,JPageable pageable,Map<String, Object> params){
		PersistenceProvider persistenceProvider= PersistenceProvider.fromEntityManager(em);
		boolean namedCountQueryIsPresent=false;
		if(JStringUtils.isNotNullOrEmpty(countNamedSql)){
			namedCountQueryIsPresent=hasNamedQuery(em, countNamedSql);
			JAssert.state(namedCountQueryIsPresent, " count named SQL canot fround as persistence context : " +countNamedSql);
		}
		TypedQuery<Long> countQuery = null;
		if (namedCountQueryIsPresent) {
			countQuery = em.createNamedQuery(countNamedSql, Long.class);
		} else {
			Query query=em.createNamedQuery(namedSql);
			if(persistenceProvider.canExtractQuery()){
				String queryString = persistenceProvider.extractQueryString(query);
				countQuery = em.createQuery(QueryUtils.createCountQueryFor(queryString), Long.class);
			}
		}
		
		JAssert.state(countQuery!=null, "cannt found count sql for the named query : "+namedSql);
		
		long count=countQuery.getSingleResult();
		int pageNumber=pageable.getPageNumber();
		int pageSize=pageable.getPageSize();
		int tempTotalPageNumber=JPageImpl.caculateTotalPageNumber(count, pageSize);
		pageNumber=pageNumber>tempTotalPageNumber?tempTotalPageNumber:pageNumber;
		
		Query query=em.createNamedQuery(namedSql);
		setQueryParameters(query, params);
		query.setFirstResult(pageNumber*pageSize);
		query.setMaxResults(pageSize);
		List list= query.getResultList();
		JPageImpl page=new JPageImpl();
		page.setContent(list);
		page.setTotalRecordNumber(count);
		page.setTotalPageNumber(tempTotalPageNumber);
		JPageRequest pageRequest=(JPageRequest)pageable;
		pageRequest.setPageNumber(pageNumber);
		page.setPageable(pageable);
		return page;
	}
	
	
	
	private void setQueryParameters(Query query, Map<String, Object> params) {
		for (Map.Entry<String, Object> entry : params.entrySet()){
			if(JpaDateParam.class.isInstance(entry.getValue())){
				JpaDateParam jpaDateParam= (JpaDateParam) entry.getValue();
				query.setParameter(entry.getKey(), jpaDateParam.getDate(), jpaDateParam.getTemporalType());
			}
			else if(JpaCalendarParam.class.isInstance(entry.getValue())){
				JpaCalendarParam jpaCalendarParam= (JpaCalendarParam) entry.getValue();
				query.setParameter(entry.getKey(), jpaCalendarParam.getCalendar(), jpaCalendarParam.getTemporalType());
			}
			else{
				query.setParameter(entry.getKey(), entry.getValue());
			}
		}
	}
	
	public Query createNamedQuery(String name){
		return em.createNamedQuery(name);
	}
	
	protected EntityManager getEntityManager() {
		return em;
	}
}
