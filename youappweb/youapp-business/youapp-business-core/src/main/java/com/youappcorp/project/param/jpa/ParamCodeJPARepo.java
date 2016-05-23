package com.youappcorp.project.param.jpa;

import j.jave.kernal.jave.model.support.JModelRepo;
import j.jave.platform.basicwebcomp.spirngjpa.JSpringJpaRepository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.youappcorp.project.param.model.ParamCode;

@JModelRepo(component="jpaParamCodeRepo",name=ParamCode.class)
@Repository(value="jpaParamCodeRepo")
public interface ParamCodeJPARepo extends JSpringJpaRepository<ParamCode,String>{

//	@Query(value="select t from Param t")
//	public List<Param> getsByPage(JPagination pagination);
	
	public ParamCode getParamByTypeIdAndCode(String typeId,String code);
	
	
	public List<ParamCode> getParamCodeByTypeId(String typeId);
	
	
	@Query(value="from ParamCode p where p.name=:name or 1=1 ")
	public Page<ParamCode> getParamsByNameByPage(Pageable pagination,
			@org.springframework.data.repository.query.Param(value="name")String name);
	
	@Query(value="select count(1) from ParamType p , ParamCode pc "
			+ " where p.id=pc.typeId and p.code= :type and p.code = :code and pc.deleted='N'")
	public long getCountByTypeAndCode(
			@org.springframework.data.repository.query.Param(value="type")String type, 
			@org.springframework.data.repository.query.Param(value="code")String code);
	
	@Query(value="select count(1) from ParamCode pc "
			+ " where pc.typeId=:typeId and pc.code=:code and pc.deleted='N'")
	public long getCountByTypeIdAndCode(
			@org.springframework.data.repository.query.Param(value="typeId")String typeId, 
			@org.springframework.data.repository.query.Param(value="code")String code);
	
}
