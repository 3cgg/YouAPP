package j.jave.framework.components.runtimeload.jnerface;

import j.jave.framework.components.multi.version.jnterface.JKey;

public class Key extends JKey {

	private static Key Key=new Key();
	
	private Key(){}
	
	public static Key get(){
		return Key;
	}
	
	@Override
	public String getKey() {
		return unique();
	}

}
