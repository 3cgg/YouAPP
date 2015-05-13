/**
 * 
 */
package j.jave.framework.components.web.model;

import java.util.ArrayList;
import java.util.List;

import j.jave.framework.model.JPage;

/**
 * @author J
 */
public class Table {

	/**
	 * PAGINATION INFORMATION.
	 */
	private JPage page;
	
	/**
	 * basic column 
	 */
	private List<TH> columns=new ArrayList<TH>();
	
	/**
	 * basic record.
	 */
	private List<TR> records=new ArrayList<TR>();

	public JPage getPage() {
		return page;
	}

	public void setPage(JPage page) {
		this.page = page;
	}

	public List<TH> getColumns() {
		return columns;
	}

	public void setColumns(List<TH> columns) {
		this.columns = columns;
	}

	public List<TR> getRecords() {
		return records;
	}

	public void setRecords(List<TR> records) {
		this.records = records;
	}
	
}
