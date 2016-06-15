package j.jave.platform.webcomp.spirngjpa;

import j.jave.kernal.jave.model.JBaseModel;
import j.jave.kernal.jave.persist.JIPersist;

import java.io.Serializable;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;

@NoRepositoryBean
public interface JSpringJpaRepository<T extends JBaseModel,ID extends Serializable> 
	extends PagingAndSortingRepository<T, ID>,
		JIPersist<JSpringJpaRepository<T,ID>, T,ID> {
	
//	@Override
//	@Query(value="select t from #{#entityName} t")
//	public List<T> getModelsByPage(JPagination pagination);
	
}
