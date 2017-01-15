package me.bunny.kernel.jave.model;

@SuppressWarnings("serial")
public class JSimplePageable implements JPageable {

	private static final int DEFAULT_SIZE_PER_PAGE = 100;
	
	private int pageSize=DEFAULT_SIZE_PER_PAGE;
	
	private int pageNumber;
	
	private JOrder[] orders;

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

	public JOrder[] getOrders() {
		return orders;
	}

	public void setOrders(JOrder[] orders) {
		this.orders = orders;
	}

	@Override
	public String getOrder() {
		String orderby="";
		if(orders!=null){
			for(JOrder order:orders){
				orderby=orderby+","+order.getColumn()+" "+order.getType();
			}
		}
		return orderby;
	}
	
}
