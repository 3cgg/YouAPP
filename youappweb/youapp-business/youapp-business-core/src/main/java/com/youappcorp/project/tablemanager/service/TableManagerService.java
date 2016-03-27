package com.youappcorp.project.tablemanager.service;

import j.jave.kernal.eventdriven.exception.JServiceException;
import j.jave.kernal.jave.model.JBaseModel;
import j.jave.platform.basicwebcomp.core.model.Criteria;
import j.jave.platform.basicwebcomp.core.service.ServiceContext;
import com.youappcorp.project.tablemanager.model.Column;
import com.youappcorp.project.tablemanager.model.Record;
import com.youappcorp.project.tablemanager.model.Table;

import java.util.List;

/**
 * 
 * @author J
 */
public interface TableManagerService {
	
	/**
	 * get table
	 * @return
	 */
	public List<Table> getTables();

	/**
	 * get all columns names of a table
	 * @param serviceContext
	 * @param tableName
	 * @return
	 */
	public List<Column> getColumnsByTable(ServiceContext serviceContext, String tableName);
	
	/**
	 * get all columns names of a table
	 * @param serviceContext
	 * @param modelName
	 * @return
	 */
	public List<Column> getColumnsByModelName(ServiceContext serviceContext, String modelName);
	
	/**
	 * query one record .
	 * @param serviceContext
	 * @param model
	 * @return
	 */
	public Record getRecord(ServiceContext serviceContext, JBaseModel model);
	
	/**
	 * query one record .
	 * @param serviceContext
	 * @param modelName
	 * @param id
	 * @return
	 */
	public Record getRecord(ServiceContext serviceContext, String modelName,String id);
	
	/**
	 * query records.
	 * @param searchCreiteria
	 * @return
	 */
	public List<Record> getRecords(ServiceContext serviceContext, Criteria model);
	
	/**
	 * update record. 
	 * @param serviceContext
	 * @param record
	 */
	public void updateRecord(ServiceContext serviceContext,Record record) throws JServiceException;
	
	
	
}
