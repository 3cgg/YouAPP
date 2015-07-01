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
public class RoleSearchCriteria extends Role implements SearchCriteria , JPagination{

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
