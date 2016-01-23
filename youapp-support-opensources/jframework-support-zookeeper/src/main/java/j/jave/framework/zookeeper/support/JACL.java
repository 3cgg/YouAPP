package j.jave.framework.zookeeper.support;

public class JACL {

	private final JBaseAuth baseAuth;
	
	/**
	 *  get from {@link JPerms}
	 */
	private int perm=JPerms.READ;

	public JACL(JBaseAuth baseAuth){
		this.baseAuth=baseAuth;
	}
	
	public JBaseAuth getBaseAuth() {
		return baseAuth;
	}

	public int getPerm() {
		return perm;
	}

	public void setPerm(int perm) {
		this.perm = perm;
	}
	
}
