package j.jave.platform.basicwebcomp.core.service;

import j.jave.kernal.eventdriven.exception.JServiceException;
import j.jave.kernal.jave.model.JBaseModel;
import j.jave.kernal.jave.model.JPagination;
import j.jave.platform.mybatis.JMapper;

import java.util.List;

/**
 * delegate service operation of a certain table, 
 * <p>include insert, update, delete(default set "DELETE" as "Y" ), get(one record according)
 * <p>sub-class should implements method of {@code getMapper()} .
 * @author J
 *
 * @param <T>
 */
@SuppressWarnings("deprecation")
public abstract class ServiceSupport<T extends JBaseModel> extends AbstractBaseService implements Service<T>{
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void saveOnly(ServiceContext context, T object)
			throws JServiceException {
		proxyOnSave(getMapper(), context.getUser(), object);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void updateOnly(ServiceContext context, T object)
			throws JServiceException {
		proxyOnUpdate(getMapper(), context.getUser(), object);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void delete(ServiceContext context, String id) {
		getMapper().markDeleted(id);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public T getById(ServiceContext context, String id) {
		return getMapper().get(id);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<T> getsByPage(ServiceContext context, JPagination pagination) {
		return getMapper().getsByPage(pagination);
	}

	protected abstract JMapper<T> getMapper();
	
}
