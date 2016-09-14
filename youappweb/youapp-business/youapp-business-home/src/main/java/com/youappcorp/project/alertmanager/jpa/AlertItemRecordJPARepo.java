package com.youappcorp.project.alertmanager.jpa;

import j.jave.platform.jpa.springjpa.JSpringJpaRepository;

import org.springframework.stereotype.Repository;

import com.youappcorp.project.alertmanager.model.AlertItemRecord;

@Repository(value="alertItemRecordJPARepo")
public interface AlertItemRecordJPARepo extends JSpringJpaRepository<AlertItemRecord,String>{
	
}
