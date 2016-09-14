package com.youappcorp.project.alertmanager.model;

import j.jave.platform.jpa.springjpa.JJpaBaseModel;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;


@Entity
@Table(name="ALERT_ITEM_RECORD")
public class AlertItemRecord extends JJpaBaseModel {

	private Timestamp recordTime;

	/**
	 * JSON FORMAT
	 * {@link AlertItem#getMeta()}
	 */
	private String data;
	
	private String description;
	
	@Column(name="DESCRIPTION")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	@Column(name="DATA")
	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	@Column(name="RECORD_TIME")
	public Timestamp getRecordTime() {
		return recordTime;
	}

	public void setRecordTime(Timestamp recordTime) {
		this.recordTime = recordTime;
	}
	
}
