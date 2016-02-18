package j.jave.platform.basicwebcomp.spirngjpa;

import j.jave.kernal.jave.model.JBaseModel;
import j.jave.kernal.jave.model.JPagination;
import j.jave.kernal.jave.persist.JIPersist;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;

@NoRepositoryBean
public interface JSpringJpaRepository<T extends JBaseModel,ID extends Serializable> 
	extends PagingAndSortingRepository<T, Serializable>,
		JIPersist<JSpringJpaRepository<T,ID>, T> {
	
	@Override
	@Query(value="select t from #{#entityName} t")
	public List<T> getModelsByPage(JPagination pagination);
	
}
