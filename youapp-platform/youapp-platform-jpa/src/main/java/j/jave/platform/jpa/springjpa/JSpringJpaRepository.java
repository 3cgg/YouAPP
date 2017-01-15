package j.jave.platform.jpa.springjpa;

import java.io.Serializable;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;

import me.bunny.kernel._c.model.JBaseModel;
import me.bunny.kernel._c.persist.JIPersist;

@NoRepositoryBean
public interface JSpringJpaRepository<T extends JBaseModel,ID extends Serializable> 
	extends PagingAndSortingRepository<T, ID>,
		JIPersist<JSpringJpaRepository<T,ID>, T,ID> {
	
//	@Override
//	@Query(value="select t from #{#entityName} t")
//	public List<T> getModelsByPage(JPagination pagination);
	
}
