package com.youappcorp.project.sysparam.jpa;

import j.jave.platform.jpa.springjpa.JSpringJpaRepository;

import com.youappcorp.project.sysparam.model.SysParam;
import com.youappcorp.project.sysparam.repo.SysParamRepo;

public interface SysParamJPARepo extends SysParamRepo,
	JSpringJpaRepository<SysParam,String> {

}
