package j.jave.framework.components.runtimeload.model;

import j.jave.framework.commons.model.JPage;
import j.jave.framework.commons.model.JPagination;
import j.jave.framework.components.web.model.SearchCriteria;

public class RuntimeLoadSearchCriteria extends RuntimeLoad implements SearchCriteria ,JPagination {

	private JPage page;
	
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

	@Override
	public void setPage(JPage page) {
		this.page=page;
	}

	@Override
	public JPage getPage() {
		return page;
	}

	@Override
	public void setTotalRecordNum(int totalRecordNum) {
		this.page.setTotalRecordNum(totalRecordNum);
	}
	
}
