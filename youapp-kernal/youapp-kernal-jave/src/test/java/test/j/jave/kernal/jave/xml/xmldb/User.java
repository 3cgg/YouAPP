/**
 * 
 */
package test.j.jave.kernal.jave.xml.xmldb;

import me.bunny.kernel._c.model.JBaseModel;
import me.bunny.kernel._c.model.support.JColumn;
import me.bunny.kernel._c.model.support.JSQLType;
import me.bunny.kernel._c.model.support.JTable;

/**
 * @author Administrator
 *
 */
@JTable(name="USER")
public class User extends JBaseModel{
	
	@JColumn(name="USERNAME",type=JSQLType.VARCHAR,length=32)
	private String userName;
	
	@JColumn(name="PASSWORD",type=JSQLType.VARCHAR,length=64)
	private String password;

	private String nannnn ; 
	
	
	
	public String getNannnn() {
		return nannnn;
	}

	public void setNannnn(String nannnn) {
		this.nannnn = nannnn;
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
