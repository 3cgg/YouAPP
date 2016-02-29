package j.jave.platform.mybatis.plugin.pageable;

import j.jave.kernal.jave.model.JPageImpl;

public class PageableMeta {

	protected long count;
	
	protected long pageNumber;
	
	protected int pageSize;
	
	protected int start;
	
	protected int end;
	
	protected String sql;
	
	protected String pageableSql;

	public long getCount() {
		return count;
	}

	public void setCount(long count) {
		this.count = count;
	}

	public long getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(long pageNumber) {
		this.pageNumber = pageNumber;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getStart() {
		return start;
	}

	public int getEnd() {
		return end;
	}

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}
	
	protected PageableMeta prepare(){
		if(count>0){
			int tempPageNumber=JPageImpl.caculateTotalPageNumber(count, pageSize);
			pageNumber=pageNumber>tempPageNumber?tempPageNumber:pageNumber;
			start = (int) (pageSize*pageNumber);
			end = (int) (pageSize *(pageNumber + 1));
		}
		return this;
	}
	
	public final String getPageableSql() {
		prepare();
		return doGetPageableSql();
	}
	
	protected String doGetPageableSql(){
		return this.sql;
	}
}
