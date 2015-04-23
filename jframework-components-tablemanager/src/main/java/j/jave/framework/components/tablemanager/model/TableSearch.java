package j.jave.framework.components.tablemanager.model;

import j.jave.framework.components.core.model.SearchCriteria;
import j.jave.framework.model.JPage;
import j.jave.framework.model.JPagination;

/**
 * search criteria model for table manager.
 * @author J
 *
 */
public class TableSearch implements SearchCriteria,JPagination {

	private JPage page;
	
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

	/* (non-Javadoc)
	 * @see j.jave.framework.model.JPagination#setPage(j.jave.framework.model.JPage)
	 */
	@Override
	public void setPage(JPage page) {
		this.page=page;
	}

	/* (non-Javadoc)
	 * @see j.jave.framework.model.JPagination#getPage()
	 */
	@Override
	public JPage getPage() {
		return this.page;
	}

	/* (non-Javadoc)
	 * @see j.jave.framework.model.JPagination#setTotalRecordNum(int)
	 */
	@Override
	public void setTotalRecordNum(int totalRecordNum) {
		this.page.setTotalPageNum(totalRecordNum);
	}

	
}
