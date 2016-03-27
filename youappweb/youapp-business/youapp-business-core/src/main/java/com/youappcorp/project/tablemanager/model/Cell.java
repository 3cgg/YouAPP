package com.youappcorp.project.tablemanager.model;

/**
 * 
 * @author J
 *
 */
public class Cell {

	private Column column;
	
	private Object object;
	
	/**
	 * format the value. 
	 * @return
	 */
	public String getValue(){
		return String.valueOf(object);
	}

	public Column getColumn() {
		return column;
	}

	public void setColumn(Column column) {
		this.column = column;
	}

	public Object getObject() {
		return object;
	}

	public void setObject(Object object) {
		this.object = object;
	}
	
	
}
