package com.youappcorp.project.alertmanager.jpa;

import org.springframework.stereotype.Repository;

import com.youappcorp.project.alertmanager.model.AlertRecord;

import me.bunny.app._c.jpa.springjpa.JSpringJpaRepository;

@Repository(value="AlertRecordJPARepo")
public interface AlertRecordJPARepo extends JSpringJpaRepository<AlertRecord,String>{
	
}
