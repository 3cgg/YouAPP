package com.youappcorp.project.codetable.jpa;

import j.jave.kernal.jave.model.support.JModelRepo;
import j.jave.platform.jpa.springjpa.JSpringJpaRepository;

import org.springframework.stereotype.Repository;

import com.youappcorp.project.codetable.model.ParamCode;

@JModelRepo(component="jpaParamCodeRepo",name=ParamCode.class)
@Repository(value="jpaParamCodeRepo")
public interface ParamCodeJPARepo extends JSpringJpaRepository<ParamCode,String>{
	
}
