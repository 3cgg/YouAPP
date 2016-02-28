package j.jave.kernal.jave.model;

@SuppressWarnings("serial")
public class JPageRequest implements JPageable {

	private static final int DEFAULT_SIZE_PER_PAGE = 100;
	
	private int pageNumber;
	
	private int pageSize=DEFAULT_SIZE_PER_PAGE;
	
	private JOrder order;

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public JOrder getOrder() {
		return order;
	}

	public void setOrder(JOrder order) {
		this.order = order;
	}
}
