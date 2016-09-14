package com.youappcorp.project.alertmanager.model;

import j.jave.platform.jpa.springjpa.JJpaBaseModel;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;


@Entity
@Table(name="ALERT_ITEM_RECORD_AFFECT")
public class AlertItemRecordAffect extends JJpaBaseModel {

	private Timestamp recordTime;

	private String description;
	
	@Column(name="DESCRIPTION")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	@Column(name="RECORD_TIME")
	public Timestamp getRecordTime() {
		return recordTime;
	}

	public void setRecordTime(Timestamp recordTime) {
		this.recordTime = recordTime;
	}
	
}
