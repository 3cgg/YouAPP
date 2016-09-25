package com.youappcorp.project.alertmanager.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import j.jave.platform.jpa.springjpa.JJpaBaseModel;


@Entity
@Table(name="ALERT_RECORD")
public class AlertRecord extends JJpaBaseModel {

	private String alertItemId;
	
	private Timestamp recordTime;

	/**
	 * JSON FORMAT
	 * {@link AlertItem#getMeta()}
	 */
	private String data;
	
	private String description;
	
	/**
	 * Y / N 
	 */
	private String primary;
	
	@Column(name="ALERT_ITEM_ID")
	public String getAlertItemId() {
		return alertItemId;
	}

	public void setAlertItemId(String alertItemId) {
		this.alertItemId = alertItemId;
	}

	@Column(name="DESCRIPTION")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	@Column(name="_DATA")
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

	@Column(name="_PRIMARY")
	public String getPrimary() {
		return primary;
	}

	public void setPrimary(String primary) {
		this.primary = primary;
	}
	
}
