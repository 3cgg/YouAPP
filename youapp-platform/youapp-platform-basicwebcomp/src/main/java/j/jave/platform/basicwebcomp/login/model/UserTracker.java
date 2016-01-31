package j.jave.platform.basicwebcomp.login.model;

import j.jave.kernal.jave.model.JBaseModel;
import j.jave.kernal.jave.model.support.JColumn;
import j.jave.kernal.jave.model.support.JSQLType;
import j.jave.kernal.jave.model.support.JTable;

import java.sql.Timestamp;

/**
 * 
 * @author J
 *
 */
@JTable(name="USER_TRACKER")
public class UserTracker extends JBaseModel {

	/**
	 * User ID
	 */
	@JColumn(name="USERID",type=JSQLType.VARCHAR,length=32)
	private String userId;
	
	/**
	 * ip from the client 
	 */
	@JColumn(name="IP",type=JSQLType.VARCHAR,length=32)
	private String ip;
	
	/**
	 * user name 
	 */
	@JColumn(name="USERNAME",type=JSQLType.VARCHAR,length=32)
	private String userName;
	
	/**
	 * login on the time . 
	 */
	@JColumn(name="LOGIN_TIME",type=JSQLType.TIMESTAMP)
	private Timestamp loginTime;
	
	/**
	 * the client that login from
	 */
	@JColumn(name="LOGIN_CLIENT",type=JSQLType.VARCHAR,length=256)
	private String loginClient;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Timestamp getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(Timestamp loginTime) {
		this.loginTime = loginTime;
	}

	public String getLoginClient() {
		return loginClient;
	}

	public void setLoginClient(String loginClient) {
		this.loginClient = loginClient;
	}
	
}
