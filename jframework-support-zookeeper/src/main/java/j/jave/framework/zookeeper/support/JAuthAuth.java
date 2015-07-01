package j.jave.framework.zookeeper.support;

public class JAuthAuth extends JBaseAuth {

	public JAuthAuth(){
		this.schema="auth";
	}
	
	@Override
	public String authorizingId() {
		return super.consistof();
	}
	
}
