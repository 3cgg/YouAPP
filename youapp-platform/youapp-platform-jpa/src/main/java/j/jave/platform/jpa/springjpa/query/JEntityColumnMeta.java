package j.jave.platform.jpa.springjpa.query;

import j.jave.kernal.jave.support.detect.JDefaultFieldMeta;

public class JEntityColumnMeta extends JDefaultFieldMeta {
	
	private String property;
	
	private String column;

	public String getProperty() {
		return property;
	}

	public void setProperty(String property) {
		this.property = property;
	}

	public String getColumn() {
		return column;
	}

	public void setColumn(String column) {
		this.column = column;
	}
	

	
}
