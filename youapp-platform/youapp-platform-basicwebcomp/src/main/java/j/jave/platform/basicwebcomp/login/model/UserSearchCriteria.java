/**
 * 
 */
package j.jave.platform.basicwebcomp.login.model;

import j.jave.kernal.jave.model.JOrder;
import j.jave.kernal.jave.model.JPage;
import j.jave.kernal.jave.model.JPageAware;
import j.jave.kernal.jave.model.JPageImpl;
import j.jave.kernal.jave.model.JPageable;
import j.jave.platform.basicwebcomp.core.model.Criteria;

/**
 * @author J
 */
public class UserSearchCriteria extends User implements Criteria , 
JPageable,JPageAware{

	private static final long serialVersionUID = 6216852161796720737L;

	private JPage page=new JPageImpl();
	
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
