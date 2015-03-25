package j.jave.framework.hibernate;

import java.sql.Timestamp;

import j.jave.framework.model.JBaseModel;
import j.jave.framework.persist.JIPersist;

import org.springframework.orm.hibernate4.support.HibernateDaoSupport;

/**
 * the class used to for the basic db operation
 * @author Administrator
 *
 * @param <T>
 */
public class JPersist<T extends JBaseModel> extends HibernateDaoSupport  implements JIPersist<JPersist<T>,T> {
	
	@Override
	public void save(JBaseModel baseModel) {
		baseModel.setCreateTime(new Timestamp(System.currentTimeMillis()));
		
		this.getHibernateTemplate().save(baseModel);
	}

	@Override
	public int update(JBaseModel baseModel) {
		baseModel.setUpdateTime(new Timestamp(System.currentTimeMillis()));
		this.getHibernateTemplate().update(baseModel);
		return 1;
	}

	@Override
	public T get(String id,String... entryName){
		return (T) this.getHibernateTemplate().get(entryName[0], id);
	}

	@Override
	public void delete(JBaseModel baseModel) {
		this.getHibernateTemplate().delete(baseModel);
	}

	@Override
	public void markDeleted(JBaseModel baseModel) {
		this.getHibernateTemplate().update(baseModel);
	}
	
	/* (non-Javadoc)
	 * @see j.jave.framework.persist.JIPersist#getInstance()
	 */
	@Override
	public JPersist<T> getInstance() {
		return this;
	}
	
}
