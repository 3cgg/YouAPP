package com.youappcorp.project.alertmanager.jpa;

import j.jave.platform.jpa.springjpa.JSpringJpaRepository;

import org.springframework.stereotype.Repository;

import com.youappcorp.project.alertmanager.model.AlertRecord;

@Repository(value="AlertRecordJPARepo")
public interface AlertRecordJPARepo extends JSpringJpaRepository<AlertRecord,String>{
	
}
