package j.jave.platform.basicwebcomp.spirngjpa;

import j.jave.kernal.jave.model.JBaseModel;
import j.jave.kernal.jave.persist.JIPersist;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;

@NoRepositoryBean
public interface JSpringJpaRepository<T extends JBaseModel> 
	extends PagingAndSortingRepository<T, String>,
		JIPersist<JSpringJpaRepository<T>, T> {
	
//	@Override
//	@Query(value="select t from #{#entityName} t")
//	public List<T> getModelsByPage(JPagination pagination);
	
}
