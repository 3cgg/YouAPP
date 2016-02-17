package j.jave.platform.basicwebcomp.spirngjpa;

import j.jave.kernal.jave.model.JBaseModel;
import j.jave.kernal.jave.model.JPagination;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public class JSimpleSpringJPAImpl<T, ID extends Serializable> 
	extends SimpleJpaRepository<T, Serializable> implements JSpringJPARepository<T, ID> {

    private EntityManager em;
	
	// There are two constructors to choose from, either can be used.
	  public JSimpleSpringJPAImpl(Class<T> domainClass, EntityManager entityManager) {
	    super(domainClass, entityManager);
	    // This is the recommended method for accessing inherited class dependencies.
	    this.em = entityManager;
	  }
	  
	@Override
	public void save(JBaseModel baseModel) {
		super.save((T)baseModel);
	}

	@Override
	public int update(JBaseModel baseModel) {
		super.save((T)baseModel);
		return 1;
	}

	@Override
	public JBaseModel get(String id, String... entryName) {
		return (JBaseModel) super.getOne(id);
	}

	@Override
	public void delete(JBaseModel baseModel) {
		super.delete(baseModel);
	}

	@Override
	public void markDeleted(JBaseModel baseModel) {
		baseModel.setDeleted("Y");
		this.update(baseModel);
	}
	
	@Override
	public JSpringJPARepository<T, ID> getInstance() {
		return this;
	}

	@Override
	public void markDeleted(String id) {
		super.delete(id);
	}

	@Override
	public List<JBaseModel> getsByPage(JPagination pagination) {
		// TODO Auto-generated method stub
		return null;
	}

}
