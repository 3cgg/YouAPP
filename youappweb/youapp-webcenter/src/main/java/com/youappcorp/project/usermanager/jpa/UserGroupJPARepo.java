package com.youappcorp.project.usermanager.jpa;

import org.springframework.stereotype.Repository;

import com.youappcorp.project.usermanager.model.UserGroup;

import me.bunny.app._c.jpa.springjpa.JSpringJpaRepository;

@Repository(value="jpaUserGroupRepo")
public interface UserGroupJPARepo extends JSpringJpaRepository<UserGroup,String>{
	
}
