package j.jave.framework.components.bill.model;

import j.jave.framework.components.core.model.SearchCriteria;

public class BillSearchCriteria extends Bill implements SearchCriteria {

	/**
	 * 最近几个月
	 */
	private int latestMonth;

	public int getLatestMonth() {
		return latestMonth;
	}

	public void setLatestMonth(int latestMonth) {
		this.latestMonth = latestMonth;
	}
	
}
