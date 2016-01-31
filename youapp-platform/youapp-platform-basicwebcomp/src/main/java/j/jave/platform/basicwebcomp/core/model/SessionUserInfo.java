/**
 * 
 */
package j.jave.platform.basicwebcomp.core.model;

import j.jave.platform.basicwebcomp.login.model.User;
import j.jave.platform.basicwebcomp.login.subhub.SessionUser;

import java.util.List;

/**
 * used in holding session information of the end user . 
 * @author J
 */
public class SessionUserInfo  extends User implements SessionUser{

	private List<String> roleCodes;

	public List<String> getRoleCodes() {
		return roleCodes;
	}

	public void setRoleCodes(List<String> roleCodes) {
		this.roleCodes = roleCodes;
	}

}
