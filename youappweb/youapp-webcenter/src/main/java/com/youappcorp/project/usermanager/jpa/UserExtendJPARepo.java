package com.youappcorp.project.usermanager.jpa;

import j.jave.platform.jpa.springjpa.JSpringJpaRepository;

import org.springframework.stereotype.Repository;

import com.youappcorp.project.usermanager.model.UserExtend;

@Repository(value="jpaUserExtendRepo")
public interface UserExtendJPARepo extends JSpringJpaRepository<UserExtend,String>{
	
}
