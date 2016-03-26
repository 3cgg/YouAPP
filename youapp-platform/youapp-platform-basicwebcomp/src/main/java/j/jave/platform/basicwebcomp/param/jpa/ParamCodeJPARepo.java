package j.jave.platform.basicwebcomp.param.jpa;

import java.util.List;

import j.jave.kernal.jave.model.support.JModelRepo;
import j.jave.platform.basicwebcomp.param.model.ParamCode;
import j.jave.platform.basicwebcomp.param.repo.ParamCodeRepo;
import j.jave.platform.basicwebcomp.spirngjpa.JSpringJpaRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@JModelRepo(component="jpaParamCodeRepo",name=ParamCode.class)
@Repository(value="jpaParamCodeRepo")
public interface ParamCodeJPARepo extends JSpringJpaRepository<ParamCode, String>,
ParamCodeRepo<JSpringJpaRepository<ParamCode, String>>{

//	@Query(value="select t from Param t")
//	public List<Param> getsByPage(JPagination pagination);
	
	public ParamCode getParamByTypeIdAndCode(String typeId,String code);
	
	
	public List<ParamCode> getParamCodeByTypeId(String typeId);
	
	
	@Query(value="from ParamCode p where p.name=:name or 1=1 ")
	public Page<ParamCode> getParamsByNameByPage(Pageable pagination,
			@org.springframework.data.repository.query.Param(value="name")String name);
}
