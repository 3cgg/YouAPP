package com.youappcorp.project.sysparam.jpa;

import com.youappcorp.project.sysparam.model.SysParam;
import com.youappcorp.project.sysparam.repo.SysParamRepo;

import me.bunny.app._c.jpa.springjpa.JSpringJpaRepository;

public interface SysParamJPARepo extends SysParamRepo,
	JSpringJpaRepository<SysParam,String> {

}
