package j.jave.platform.basicwebcomp.param.jpa;

import j.jave.kernal.jave.model.support.JModelRepo;
import j.jave.platform.basicwebcomp.param.model.ParamCode;
import j.jave.platform.basicwebcomp.param.model.ParamType;
import j.jave.platform.basicwebcomp.spirngjpa.JSpringJpaRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@JModelRepo(component="jpaParamTypeRepo",name=ParamCode.class)
@Repository(value="jpaParamTypeRepo")
public interface ParamTypeJPARepo extends JSpringJpaRepository<ParamType, String>
{
	
	@Query(value="from ParamType p where p.name=:name or 1=1 ")
	public Page<ParamType> getParamsByNameByPage(Pageable pagination,
			@org.springframework.data.repository.query.Param(value="name")String name);
}
