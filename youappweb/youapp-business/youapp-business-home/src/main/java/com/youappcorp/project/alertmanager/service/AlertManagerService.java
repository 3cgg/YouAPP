package com.youappcorp.project.alertmanager.service;

import com.youappcorp.project.alertmanager.model.AlertItem;
import com.youappcorp.project.alertmanager.model.AlertItemRecord;
import com.youappcorp.project.alertmanager.vo.AlertItemCriteria;
import com.youappcorp.project.alertmanager.model.AlertRecord;
import com.youappcorp.project.alertmanager.model.AlertRecordRecord;
import com.youappcorp.project.alertmanager.vo.AlertRecordCriteria;

import me.bunny.kernel._c.model.JPage;
import me.bunny.kernel._c.model.JSimplePageable;



public interface AlertManagerService {

	/**
	 * save
	 */
	void saveAlertItem (AlertItemRecord alertItemRecord);
	
	/**
	 * update
	 */
	void updateAlertItem (AlertItemRecord alertItemRecord);
	
	/**
	 * delete
	 */
	void deleteAlertItem (AlertItemRecord alertItemRecord);
	
	/**
	 * delete
	 */
	void deleteAlertItemById (String id);
	
	/**
	 * get
	 */
	AlertItemRecord getAlertItemById (String id);
	
	/**
	 * page...
	 */
	JPage<AlertItemRecord> getAlertItemsByPage(AlertItemCriteria alertItemCriteria, JSimplePageable simplePageable);

	/**
	 * save
	 */
	void saveAlertRecord (AlertRecordRecord alertRecordRecord);
	
	/**
	 * update
	 */
	void updateAlertRecord (AlertRecordRecord alertRecordRecord);
	
	/**
	 * delete
	 */
	void deleteAlertRecord (AlertRecordRecord alertRecordRecord);
	
	/**
	 * delete
	 */
	void deleteAlertRecordById (String id);
	
	/**
	 * get
	 */
	AlertRecordRecord getAlertRecordById (String id);
	
	/**
	 * page...
	 */
	JPage<AlertRecordRecord> getAlertRecordsByPage(AlertRecordCriteria alertRecordCriteria, JSimplePageable simplePageable);

	
}
