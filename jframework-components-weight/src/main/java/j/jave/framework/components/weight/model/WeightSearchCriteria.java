package j.jave.framework.components.weight.model;

import j.jave.framework.commons.model.JPage;
import j.jave.framework.commons.model.JPagination;
import j.jave.framework.components.web.model.SearchCriteria;

public class WeightSearchCriteria extends Weight implements SearchCriteria , JPagination {

	private JPage page;
	
	@Override
	public void setPage(JPage page) {
		this.page=page;
	}

	@Override
	public JPage getPage() {
		return this.page;
	}

	@Override
	public void setTotalRecordNum(int totalRecordNum) {
		this.page.setTotalRecordNum(totalRecordNum);
	}

	
}
