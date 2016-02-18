package j.jave.platform.basicwebcomp.param.jpa;

import j.jave.kernal.jave.model.JPagination;
import j.jave.platform.basicwebcomp.param.model.Param;
import j.jave.platform.basicwebcomp.param.repo.ParamRepo;
import j.jave.platform.basicwebcomp.spirngjpa.JSpringJpaRepository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ParamJPARepo extends JSpringJpaRepository<Param, String>,
ParamRepo<JSpringJpaRepository<Param, String>>{

//	@Query(value="select t from Param t")
//	public List<Param> getsByPage(JPagination pagination);
	
}
