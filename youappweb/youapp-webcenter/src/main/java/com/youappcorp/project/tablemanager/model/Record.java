package com.youappcorp.project.tablemanager.model;

import java.util.ArrayList;
import java.util.List;

public class Record {

	private String tableName;
	
	/**
	 * full class name.
	 */
	private String modelName;
	
	private List<Cell> cells=new ArrayList<Cell>();

	public List<Cell> getCells() {
		return cells;
	}

	public void setCells(List<Cell> cells) {
		this.cells = cells;
	}

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
	
	
}
