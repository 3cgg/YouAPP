package j.jave.framework.commons.model.support.detect;

import j.jave.framework.commons.model.support.JColumn;
import j.jave.framework.commons.model.support.JSQLType;

import java.lang.reflect.Field;


public class JColumnInfo {
	
	
	
	private Field field;
	
	private JColumn column;
	
	/**
	 * column name , not field name 
	 */
	private String name;
	
	/**
	 * column length
	 */
	private int length;
	
	/**
	 * column type 
	 */
	private JSQLType type;
	
	/**
	 * whether or not the column is null
	 */
	private boolean nullable;
	
	private JTableInfo tableInfo;
	
	private Object defaultValue;
	
	public void setDefaultValue(Object defaultValue) {
		this.defaultValue = defaultValue;
	}
	
	public Object getDefaultValue() {
		return defaultValue;
	}

	public Field getField() {
		return field;
	}

	public void setField(Field field) {
		this.field = field;
	}

	public JColumn getColumn() {
		return column;
	}

	public void setColumn(JColumn column) {
		this.column = column;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	
	public JSQLType getType() {
		return type;
	}

	public void setType(JSQLType type) {
		this.type = type;
	}

	public boolean isNullable() {
		return nullable;
	}

	public void setNullable(boolean nullable) {
		this.nullable = nullable;
	}

	public JTableInfo getTableInfo() {
		return tableInfo;
	}

	public void setTableInfo(JTableInfo tableInfo) {
		this.tableInfo = tableInfo;
	}

	
	
}
