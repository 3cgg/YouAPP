package j.jave.framework.components.tablemanager.service;

import j.jave.framework.components.core.service.ServiceContext;
import j.jave.framework.components.tablemanager.model.Column;
import j.jave.framework.components.tablemanager.model.Record;
import j.jave.framework.components.tablemanager.model.Table;
import j.jave.framework.model.JBaseModel;
import j.jave.framework.model.JModel;

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
	 * query one record .
	 * @param searchCreiteria
	 * @return
	 */
	public Record getRecord(ServiceContext serviceContext, JBaseModel model);
	
	/**
	 * query records.
	 * @param searchCreiteria
	 * @return
	 */
	public List<Record> getRecords(ServiceContext serviceContext, JBaseModel model);
	
}
