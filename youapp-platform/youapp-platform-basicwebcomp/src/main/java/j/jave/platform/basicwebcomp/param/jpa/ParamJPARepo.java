package j.jave.platform.basicwebcomp.param.jpa;

import org.springframework.stereotype.Repository;

import j.jave.platform.basicwebcomp.param.model.Param;
import j.jave.platform.basicwebcomp.param.repo.ParamRepo;
import j.jave.platform.basicwebcomp.spirngjpa.JSpringJpaRepository;

@Repository
public interface ParamJPARepo extends JSpringJpaRepository<Param, String>,
ParamRepo<JSpringJpaRepository<Param, String>>{

}
