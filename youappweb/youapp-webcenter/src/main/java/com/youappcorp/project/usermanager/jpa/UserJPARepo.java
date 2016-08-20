package com.youappcorp.project.usermanager.jpa;

import j.jave.platform.jpa.springjpa.JSpringJpaRepository;

import org.springframework.stereotype.Repository;

import com.youappcorp.project.usermanager.model.User;

@Repository(value="jpaUserRepo")
public interface UserJPARepo extends JSpringJpaRepository<User,String>{
	
}
