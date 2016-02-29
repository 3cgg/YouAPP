package j.jave.platform.basicwebcomp.core.service;

import j.jave.kernal.eventdriven.exception.JServiceException;
import j.jave.kernal.jave.exception.JConcurrentException;
import j.jave.kernal.jave.model.JBaseModel;
import j.jave.kernal.jave.model.JModel;
import j.jave.kernal.jave.model.JPage;
import j.jave.kernal.jave.model.JPageImpl;
import j.jave.kernal.jave.model.JPageRequest;
import j.jave.kernal.jave.model.JPageable;
import j.jave.kernal.jave.model.support.interceptor.JDefaultModelInvocation;
import j.jave.kernal.jave.persist.JIPersist;
import j.jave.kernal.jave.utils.JUniqueUtils;
import j.jave.platform.basicwebcomp.core.model.SimplePageRequest;
import j.jave.platform.basicwebcomp.login.model.User;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;

/**
 * delegate service operation of a certain table, 
 * <p>include insert, update, delete(default set "DELETE" as "Y" ), get(one record according)
 * <p>sub-class should implements method of {@code getRepo()} .
 * @author J
 *
 * @param <T>
 */
public abstract class ServiceSupport<T extends JBaseModel> implements Service<T>{
	
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
	
}
