/**
 * 
 */
package j.jave.framework.components.login.model;

import j.jave.framework.components.core.model.SearchCriteria;
import j.jave.framework.model.JPage;
import j.jave.framework.model.JPagination;

/**
 * @author Administrator
 *
 */
public class UserSearchCriteria extends User implements SearchCriteria , JPagination{

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
