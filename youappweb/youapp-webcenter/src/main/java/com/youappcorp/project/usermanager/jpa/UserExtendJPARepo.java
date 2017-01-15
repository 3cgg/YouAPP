package com.youappcorp.project.usermanager.jpa;

import org.springframework.stereotype.Repository;

import com.youappcorp.project.usermanager.model.UserExtend;

import me.bunny.app._c.jpa.springjpa.JSpringJpaRepository;

@Repository(value="jpaUserExtendRepo")
public interface UserExtendJPARepo extends JSpringJpaRepository<UserExtend,String>{
	
}
