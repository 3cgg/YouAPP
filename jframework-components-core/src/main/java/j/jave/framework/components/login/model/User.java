/**
 * 
 */
package j.jave.framework.components.login.model;

import j.jave.framework.model.JBaseModel;
import j.jave.framework.model.support.JColumn;
import j.jave.framework.model.support.JSQLType;
import j.jave.framework.model.support.JTable;

/**
 * @author Administrator
 *
 */
@JTable(name="USERS")
public class User extends JBaseModel{
	
	@JColumn(name="USERNAME",type=JSQLType.VARCHAR,length=32)
	private String userName;
	
	@JColumn(name="PASSWORD",type=JSQLType.VARCHAR,length=64)
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
