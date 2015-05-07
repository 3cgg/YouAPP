package j.jave.framework.components.tablemanager.service;

import j.jave.framework.components.core.model.SearchCriteria;
import j.jave.framework.components.core.service.ServiceContext;
import j.jave.framework.components.tablemanager.model.Column;
import j.jave.framework.components.tablemanager.model.Record;
import j.jave.framework.components.tablemanager.model.Table;
import j.jave.framework.model.JBaseModel;
import j.jave.framework.servicehub.exception.JServiceException;

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
	public List<Record> getRecords(ServiceContext serviceContext, SearchCriteria model);
	
	/**
	 * update record. 
	 * @param serviceContext
	 * @param record
	 */
	public void updateRecord(ServiceContext serviceContext,Record record) throws JServiceException;
	
	
	
}
