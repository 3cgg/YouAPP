/**
 * 
 */
package test.j.jave.kernal.jave.xml.xmldb;

import me.bunny.kernel.jave.model.JBaseModel;
import me.bunny.kernel.jave.model.support.JColumn;
import me.bunny.kernel.jave.model.support.JSQLType;
import me.bunny.kernel.jave.model.support.JTable;

/**
 * @author J
 */
@JTable(name="USERS_EXTEND")	
public class UserExtend extends JBaseModel{
	
	@JColumn(name="USERID",type=JSQLType.VARCHAR,length=32)
	private String userId;
	
	@JColumn(name="USERNAME",type=JSQLType.VARCHAR,length=32)
	private String userName;
	
	@JColumn(name="USERIMAGE",type=JSQLType.VARCHAR,length=256)
	private String userImage;
	
	@JColumn(name="NATURENAME",type=JSQLType.VARCHAR,length=64)
	private String natureName;
	
	private User user;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserImage() {
		return userImage;
	}

	public void setUserImage(String userImage) {
		this.userImage = userImage;
	}

	public String getNatureName() {
		return natureName;
	}

	public void setNatureName(String natureName) {
		this.natureName = natureName;
	}
	
	
	
}
