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
	public void saveModel(T baseModel) {
		super.save(baseModel);
	}

	@Override
	public int updateModel(T baseModel) {
		super.save(baseModel);
		return 1;
	}

	@Override
	public T getModel(String id, String... entryName) {
		return super.getOne(id);
	}

	@Override
	public void deleteModel(T baseModel) {
		super.delete(baseModel);
	}

	@Override
	public void markModelDeleted(T baseModel) {
		baseModel.setDeleted("Y");
		this.updateModel(baseModel);
	}
	
	@Override
	public JSpringJpaRepository<T, ID> getInstance() {
		return this;
	}

	@Override
	public void markModelDeleted(String id) {
		T t=getModel(id);
		t.setDeleted("Y");
		updateModel(t);
	}

	@Override
	public List<T> getModelsByPage(JPagination pagination) {
		return findAll();
	}

}
