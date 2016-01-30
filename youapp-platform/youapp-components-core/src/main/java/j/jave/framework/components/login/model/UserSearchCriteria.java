/**
 * 
 */
package j.jave.framework.components.login.model;

import j.jave.framework.commons.model.JPage;
import j.jave.framework.commons.model.JPagination;
import j.jave.framework.components.web.model.SearchCriteria;

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
