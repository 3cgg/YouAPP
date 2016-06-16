package j.jave.platform.jpa.springjpa;

import j.jave.kernal.jave.model.JBaseModel;
import j.jave.kernal.jave.model.JPageable;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

public class JSimpleSpringJpaImpl<T extends JBaseModel,ID extends Serializable> 
	extends SimpleJpaRepository<T, ID> implements JSpringJpaRepository<T,ID> {

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
	public T getModel(ID id, String... entryName) {
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
	public void markModelDeleted(ID id) {
		T t=getModel(id);
		t.setDeleted("Y");
		updateModel(t);
	}

	@Override
	public List<T> getModelsByPage(JPageable pageable) {
		Pageable springPageable=new PageRequest(pageable.getPageNumber(), 
				pageable.getPageSize());
		Page<T> dbPage=findAll(springPageable);
		return dbPage.getContent();
	}
	
	@Override
	public Page<T> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return super.findAll(pageable);
	}
	
	@Override
	public Page<T> findAll(Specification<T> spec, Pageable pageable) {
		// TODO Auto-generated method stub
		return super.findAll(spec, pageable);
	}
	
	@Override
	public List<T> getAllModels() {
		return findAll();
	}

}
