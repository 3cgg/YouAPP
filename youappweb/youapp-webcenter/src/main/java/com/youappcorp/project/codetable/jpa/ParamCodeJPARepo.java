package com.youappcorp.project.codetable.jpa;

import me.bunny.app._c.jpa.springjpa.JSpringJpaRepository;
import me.bunny.kernel._c.model.support.JModelRepo;

import org.springframework.stereotype.Repository;

import com.youappcorp.project.codetable.model.ParamCode;

@JModelRepo(component="jpaParamCodeRepo",name=ParamCode.class)
@Repository(value="jpaParamCodeRepo")
public interface ParamCodeJPARepo extends JSpringJpaRepository<ParamCode,String>{
	
}
