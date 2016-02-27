package j.jave.platform.basicwebcomp.tablemanager.model;

import j.jave.platform.basicwebcomp.core.model.Criteria;
import j.jave.platform.basicwebcomp.core.model.SimplePageCriteria;

/**
 * search criteria model for table manager.
 * @author J
 *
 */
public class TableSearch extends SimplePageCriteria implements Criteria {
	
	/**
	 * full class name of including the package part. 
	 */
	private String modelName;
	
	/**
	 * table name
	 */
	private String tableName;
	
	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	
}
