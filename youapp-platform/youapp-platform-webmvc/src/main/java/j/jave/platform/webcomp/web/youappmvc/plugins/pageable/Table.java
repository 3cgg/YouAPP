/**
 * 
 */
package j.jave.platform.webcomp.web.youappmvc.plugins.pageable;

import java.util.ArrayList;
import java.util.List;

import me.bunny.kernel._c.model.JPageImpl;

/**
 * @author J
 */
public class Table {

	/**
	 * PAGINATION INFORMATION.
	 */
	private JPageImpl page;
	
	/**
	 * basic column 
	 */
	private List<TH> columns=new ArrayList<TH>();
	
	/**
	 * basic record.
	 */
	private List<TR> records=new ArrayList<TR>();

	public JPageImpl getPage() {
		return page;
	}

	public void setPage(JPageImpl page) {
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
