package j.jave.platform.basicwebcomp.param.model;

import j.jave.kernal.jave.model.JPage;
import j.jave.kernal.jave.model.JPagination;
import j.jave.platform.basicwebcomp.core.model.SearchCriteria;

public class ParamSearchCriteria extends Param implements SearchCriteria ,JPagination {

	private JPage page=new JPage();

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
