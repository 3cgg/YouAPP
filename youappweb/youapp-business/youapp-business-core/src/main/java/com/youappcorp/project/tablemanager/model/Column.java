package com.youappcorp.project.tablemanager.model;

/**
 * 
 * @author J
 *
 */
public class Column {

	/**
	 * column name
	 */
	private String columnName;
	
	/**
	 * property name of a model mapping to column related to table. 
	 */
	private String propertyName;

	/**
	 * column type
	 */
	private String sqlType;
	
	/**
	 * property type.
	 */
	private String propertyTypeName;
	
	
	private Class<?> propertyType;

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public String getPropertyName() {
		return propertyName;
	}

	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}

	public String getSqlType() {
		return sqlType;
	}

	public void setSqlType(String sqlType) {
		this.sqlType = sqlType;
	}

	public String getPropertyTypeName() {
		return propertyTypeName;
	}

	public void setPropertyTypeName(String propertyTypeName) {
		this.propertyTypeName = propertyTypeName;
	}

	public Class<?> getPropertyType() {
		return propertyType;
	}

	public void setPropertyType(Class<?> propertyType) {
		this.propertyType = propertyType;
	}

}
