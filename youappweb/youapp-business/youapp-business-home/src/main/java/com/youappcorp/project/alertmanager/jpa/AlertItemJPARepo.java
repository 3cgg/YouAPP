package com.youappcorp.project.alertmanager.jpa;

import j.jave.platform.jpa.springjpa.JSpringJpaRepository;

import org.springframework.stereotype.Repository;

import com.youappcorp.project.alertmanager.model.AlertItem;

@Repository(value="AlertItemJPARepo")
public interface AlertItemJPARepo extends JSpringJpaRepository<AlertItem,String>{
	
}
