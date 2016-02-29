package j.jave.platform.basicwebcomp.param.jpa;

import j.jave.kernal.jave.model.support.JModelRepo;
import j.jave.platform.basicwebcomp.param.model.Param;
import j.jave.platform.basicwebcomp.param.repo.ParamRepo;
import j.jave.platform.basicwebcomp.spirngjpa.JSpringJpaRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@JModelRepo(component="jpaParamRepo",name=Param.class)
@Repository(value="jpaParamRepo")
public interface ParamJPARepo extends JSpringJpaRepository<Param, String>,
ParamRepo<JSpringJpaRepository<Param, String>>{

//	@Query(value="select t from Param t")
//	public List<Param> getsByPage(JPagination pagination);
	
	public Param getParamByFunctionIdAndCode(String functionId,String code);
	
	
	@Query(value="from Param p left join User u on p.id=u.id where p.name=:name or 1=1 ")
	public Page<Param> getParamsByNameByPage(Pageable pagination,
			@org.springframework.data.repository.query.Param(value="name")String name);
}
