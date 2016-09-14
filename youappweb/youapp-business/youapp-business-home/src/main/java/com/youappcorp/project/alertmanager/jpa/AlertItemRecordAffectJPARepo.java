package com.youappcorp.project.alertmanager.jpa;

import j.jave.platform.jpa.springjpa.JSpringJpaRepository;

import org.springframework.stereotype.Repository;

import com.youappcorp.project.alertmanager.model.AlertItemRecordAffect;

@Repository(value="alertItemRecordAffectJPARepo")
public interface AlertItemRecordAffectJPARepo extends JSpringJpaRepository<AlertItemRecordAffect,String>{
	
}
