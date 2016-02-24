/**
 * 
 */
package j.jave.platform.basicwebcomp.web.youappmvc.plugins.pageable;

/**
 * @author J
 */
public class TH {

	/**
	 * column lable
	 */
	private String lable;
	
	/**
	 * sorting column
	 */
	private String column;
	
	/**
	 * sort type of "desc" or "asc"
	 */
	private String sortType;
	
	/**
	 * column index , start 0 
	 */
	private int index;
	
	

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public String getLable() {
		return lable;
	}

	public void setLable(String lable) {
		this.lable = lable;
	}

	public String getColumn() {
		return column;
	}

	public void setColumn(String column) {
		this.column = column;
	}

	public String getSortType() {
		return sortType;
	}

	public void setSortType(String sortType) {
		this.sortType = sortType;
	}
	
	
}
