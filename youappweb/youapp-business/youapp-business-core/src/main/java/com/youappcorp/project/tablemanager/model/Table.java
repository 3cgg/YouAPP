package com.youappcorp.project.tablemanager.model;

/**
 * 
 * @author J
 *
 */
public class Table {

	private String tableName;
	
	/**
	 * full class name of mapping model. 
	 */
	private String modelName;
	
	private String owner;

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}
	
}
