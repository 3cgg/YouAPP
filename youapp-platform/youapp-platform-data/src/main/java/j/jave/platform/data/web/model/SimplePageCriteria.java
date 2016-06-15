package j.jave.platform.data.web.model;

import j.jave.kernal.jave.model.JPageRequest;

public class SimplePageCriteria extends JPageRequest implements Criteria{

	/**
	 * order by [column asc/desc]
	 * if the multiple orders are indicated, the semicolon splits them.
	 */
	private String columnDirection;

	public String getColumnDirection() {
		return columnDirection;
	}
	
	public void setColumnDirection(String columnDirection) {
		this.columnDirection = columnDirection;
	}
}
