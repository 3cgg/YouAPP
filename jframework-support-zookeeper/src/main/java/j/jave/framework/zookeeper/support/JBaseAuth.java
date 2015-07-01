package j.jave.framework.zookeeper.support;


public abstract class JBaseAuth implements JAuth {

	protected String schema;
	
	protected String username;
	
	protected String password;

	public String getSchema() {
		return schema;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	/**
	 * return ( {@link #username}+":"+{@link #password} ) form like "username:password"
	 */
	protected String consistof() {
		return username+":"+password;
	}
	
	public String authorizingIdSHA1(){
		return consistof() ;
	}
}
