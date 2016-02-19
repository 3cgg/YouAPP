package j.jave.platform.basicwebcomp.param.jpa;

import j.jave.kernal.jave.model.support.JModelRepo;
import j.jave.platform.basicwebcomp.param.model.Param;
import j.jave.platform.basicwebcomp.param.repo.ParamRepo;
import j.jave.platform.basicwebcomp.spirngjpa.JSpringJpaRepository;

import org.springframework.stereotype.Repository;

@JModelRepo(component="jpaParamRepo",name=Param.class)
@Repository(value="jpaParamRepo")
public interface ParamJPARepo extends JSpringJpaRepository<Param, String>,
ParamRepo<JSpringJpaRepository<Param, String>>{

//	@Query(value="select t from Param t")
//	public List<Param> getsByPage(JPagination pagination);
	
}
