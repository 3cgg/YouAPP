/**
 * 
 */
package j.jave.platform.basicwebcomp.login.model;

import j.jave.kernal.jave.model.JOrder;
import j.jave.kernal.jave.model.JPage;
import j.jave.kernal.jave.model.JPageAware;
import j.jave.kernal.jave.model.JPageable;
import j.jave.platform.basicwebcomp.core.model.Criteria;

/**
 * @author J
 */
public class RoleSearchCriteria extends Role implements Criteria , JPageable,JPageAware{

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
	public int getPageNumber() {
		return page.getPageable().getPageNumber();
	}

	@Override
	public int getPageSize() {
		return page.getPageable().getPageSize();
	}

	@Override
	public JOrder getOrder() {
		return page.getPageable().getOrder();
	}
	
}
