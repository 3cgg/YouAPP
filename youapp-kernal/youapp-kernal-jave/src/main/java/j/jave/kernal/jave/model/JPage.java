/**
 * 
 */
package j.jave.kernal.jave.model;

/**
 * @author J
 */
public class JPage {
	
	private int DEFAULT_NUM_PER_PAGE = Integer.MAX_VALUE/10;
	
	/**
	 * 总记录数
	 */
	private int totalRecordNum=Integer.MAX_VALUE;
	
	/**
	 * 总页数
	 */
	private int totalPageNum;
	
	/**
	 * 每页记录数
	 */
	private int pageSize = DEFAULT_NUM_PER_PAGE;		
	
	/**
	 * 当前页
	 */
	private int currentPageNum  = 1;
	
	/**
	 * sort column 
	 */
	private String sortColumn;
	
	/**
	 * sort type .
	 */
	private String sortType;
	
	public String getSortColumn() {
		return sortColumn;
	}
	public void setSortColumn(String sortColumn) {
		this.sortColumn = sortColumn;
	}
	public String getSortType() {
		return sortType;
	}
	public void setSortType(String sortType) {
		this.sortType = sortType;
	}
	public int getTotalRecordNum() {
		return totalRecordNum;
	}
	public void setTotalRecordNum(int totalRecordNum) {
		this.totalRecordNum = totalRecordNum;
	}
	public int getTotalPageNum() {
		return totalPageNum;
	}
	public void setTotalPageNum(int totalPageNum) {
		this.totalPageNum = totalPageNum;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {//
		this.pageSize = pageSize;
	}
	public int getCurrentPageNum() {
		return currentPageNum;
	}
	public void setCurrentPageNum(int currentPageNum) {//
		this.currentPageNum=currentPageNum;
	}
	/**
	 * 根据总记录数和每页记录数，计算页码
	 * @param page
	 */
	public void processPageBean() {
		//计算当前页和总页数
		if(this.getTotalRecordNum() < this.getPageSize()) {
			this.setTotalPageNum(1);
		} else {
			if ((this.getTotalRecordNum() % this.getPageSize()) > 0) {
				this.setTotalPageNum(this.getTotalRecordNum()/this.getPageSize()+1);
			} else {
				this.setTotalPageNum(this.getTotalRecordNum()/this.getPageSize());
			}
		}
		
		if (this.getCurrentPageNum() < 1) {
			this.setCurrentPageNum(1);
		}
		if (this.getCurrentPageNum() > this.getTotalPageNum()) {
			this.setCurrentPageNum(this.getTotalPageNum());
		}
	}

	
}
