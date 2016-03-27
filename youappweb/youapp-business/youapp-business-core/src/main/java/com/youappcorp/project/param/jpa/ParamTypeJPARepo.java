package com.youappcorp.project.param.jpa;

import j.jave.kernal.jave.model.support.JModelRepo;
import j.jave.platform.basicwebcomp.spirngjpa.JSpringJpaRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.youappcorp.project.param.model.ParamCode;
import com.youappcorp.project.param.model.ParamType;

@JModelRepo(component="jpaParamTypeRepo",name=ParamCode.class)
@Repository(value="jpaParamTypeRepo")
public interface ParamTypeJPARepo extends JSpringJpaRepository<ParamType, String>
{
	
	@Query(value="from ParamType p where p.name=:name or 1=1 ")
	public Page<ParamType> getParamsByNameByPage(Pageable pagination,
			@org.springframework.data.repository.query.Param(value="name")String name);
	
	@Query(value="select count(1) from ParamType p where p.code = :code and p.deleted='N'")
	public long getCountByCode(@org.springframework.data.repository.query.Param(value="code")String code);
	
}
