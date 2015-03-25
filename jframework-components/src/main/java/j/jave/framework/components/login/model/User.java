/**
 * 
 */
package j.jave.framework.components.login.model;

import j.jave.framework.model.JBaseModel;

/**
 * @author Administrator
 *
 */
public class User extends JBaseModel {

	
	private String userName;
	
	private String password;
	
	private String retypePassword;

	public String getRetypePassword() {
		return retypePassword;
	}

	public void setRetypePassword(String retypePassword) {
		this.retypePassword = retypePassword;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	
	
	
}
