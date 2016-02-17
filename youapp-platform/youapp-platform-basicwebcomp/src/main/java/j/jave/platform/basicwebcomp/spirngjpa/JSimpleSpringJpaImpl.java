package j.jave.platform.basicwebcomp.spirngjpa;

import j.jave.kernal.jave.model.JBaseModel;
import j.jave.kernal.jave.model.JPagination;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

public class JSimpleSpringJpaImpl<T extends JBaseModel, ID extends Serializable> 
	extends SimpleJpaRepository<T, Serializable> implements JSpringJpaRepository<T, ID> {

    private EntityManager em;
	
	// There are two constructors to choose from, either can be used.
	  public JSimpleSpringJpaImpl(Class<T> domainClass, EntityManager entityManager) {
	    super(domainClass, entityManager);
	    // This is the recommended method for accessing inherited class dependencies.
	    this.em = entityManager;
	  }
	  
	@Override
	public T save(T baseModel) {
		return super.save(baseModel);
	}

	@Override
	public int update(T baseModel) {
		super.save(baseModel);
		return 1;
	}

	@Override
	public T get(String id, String... entryName) {
		return (T) super.getOne(id);
	}

	@Override
	public void delete(T baseModel) {
		super.delete(baseModel);
	}

	@Override
	public void markDeleted(T baseModel) {
		baseModel.setDeleted("Y");
		this.update(baseModel);
	}
	
	@Override
	public JSpringJpaRepository<T, ID> getInstance() {
		return this;
	}

	@Override
	public void markDeleted(String id) {
		super.delete(id);
	}

	@Override
	public List<T> getsByPage(JPagination pagination) {
		// TODO Auto-generated method stub
		return null;
	}

}
