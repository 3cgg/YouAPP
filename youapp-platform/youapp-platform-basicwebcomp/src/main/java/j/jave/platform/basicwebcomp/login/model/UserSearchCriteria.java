/**
 * 
 */
package j.jave.platform.basicwebcomp.login.model;

import j.jave.kernal.jave.model.JPage;
import j.jave.kernal.jave.model.JPagination;
import j.jave.platform.basicwebcomp.core.model.SearchCriteria;

/**
 * @author J
 */
public class UserSearchCriteria extends User implements SearchCriteria , JPagination{

	private static final long serialVersionUID = 6216852161796720737L;

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
