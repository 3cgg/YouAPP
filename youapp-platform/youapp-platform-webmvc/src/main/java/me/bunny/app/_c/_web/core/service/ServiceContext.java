/**
 * 
 */
package me.bunny.app._c._web.core.service;

import me.bunny.kernel._c.model.JModel;



/**
 * @author J
 *
 */
public class ServiceContext implements JModel {
	
	private String ticket;
	
	private String userName;
	
	private String userId;

	public String getTicket() {
		return ticket;
	}

	public void setTicket(String ticket) {
		this.ticket = ticket;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

}
