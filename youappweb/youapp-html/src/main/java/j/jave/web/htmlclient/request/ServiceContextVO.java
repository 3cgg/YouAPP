/**
 * 
 */
package j.jave.web.htmlclient.request;

import me.bunny.kernel.jave.model.JModel;

/**
 * @author J
 *
 */
public class ServiceContextVO implements JModel {
	
	private String ticket;

	public String getTicket() {
		return ticket;
	}

	public void setTicket(String ticket) {
		this.ticket = ticket;
	}
	
}
