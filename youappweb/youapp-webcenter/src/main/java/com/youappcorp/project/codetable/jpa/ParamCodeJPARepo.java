package com.youappcorp.project.codetable.jpa;

import j.jave.platform.jpa.springjpa.JSpringJpaRepository;
import me.bunny.kernel.jave.model.support.JModelRepo;

import org.springframework.stereotype.Repository;

import com.youappcorp.project.codetable.model.ParamCode;

@JModelRepo(component="jpaParamCodeRepo",name=ParamCode.class)
@Repository(value="jpaParamCodeRepo")
public interface ParamCodeJPARepo extends JSpringJpaRepository<ParamCode,String>{
	
}
