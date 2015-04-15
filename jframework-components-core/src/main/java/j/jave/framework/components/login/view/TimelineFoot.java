package j.jave.framework.components.login.view;

import java.io.Serializable;

public class TimelineFoot implements Serializable {

	private String viewAction;
	
	private String deleteAction;

	public String getViewAction() {
		return viewAction;
	}

	public void setViewAction(String viewAction) {
		this.viewAction = viewAction;
	}

	public String getDeleteAction() {
		return deleteAction;
	}

	public void setDeleteAction(String deleteAction) {
		this.deleteAction = deleteAction;
	}
	
	
	
}
