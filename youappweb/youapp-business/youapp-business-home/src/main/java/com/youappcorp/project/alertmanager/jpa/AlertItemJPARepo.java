package com.youappcorp.project.alertmanager.jpa;

import org.springframework.stereotype.Repository;

import com.youappcorp.project.alertmanager.model.AlertItem;

import me.bunny.app._c.jpa.springjpa.JSpringJpaRepository;

@Repository(value="AlertItemJPARepo")
public interface AlertItemJPARepo extends JSpringJpaRepository<AlertItem,String>{
	
}
