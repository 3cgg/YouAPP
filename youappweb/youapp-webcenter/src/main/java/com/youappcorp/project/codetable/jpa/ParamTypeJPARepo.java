package com.youappcorp.project.codetable.jpa;

import j.jave.platform.jpa.springjpa.JSpringJpaRepository;
import me.bunny.kernel._c.model.support.JModelRepo;

import org.springframework.stereotype.Repository;

import com.youappcorp.project.codetable.model.ParamCode;
import com.youappcorp.project.codetable.model.ParamType;

@JModelRepo(component="jpaParamTypeRepo",name=ParamCode.class)
@Repository(value="jpaParamTypeRepo")
public interface ParamTypeJPARepo extends JSpringJpaRepository<ParamType,String>{

}
