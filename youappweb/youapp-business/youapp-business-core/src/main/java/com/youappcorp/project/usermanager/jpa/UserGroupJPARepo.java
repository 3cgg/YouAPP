package com.youappcorp.project.usermanager.jpa;

import j.jave.platform.jpa.springjpa.JSpringJpaRepository;

import org.springframework.stereotype.Repository;

import com.youappcorp.project.usermanager.model.UserGroup;

@Repository(value="jpaUserGroupRepo")
public interface UserGroupJPARepo extends JSpringJpaRepository<UserGroup,String>{
	
}
