package j.jave.kernal.zookeeper;

public class JAuthAuth extends JBaseAuth {

	public JAuthAuth(){
		this.schema="auth";
	}
	
	@Override
	public String authorizingId() {
		return super.consistof();
	}
	
}
