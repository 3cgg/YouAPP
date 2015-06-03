package j.jave.framework.android.resource;

import j.jave.framework.android.R;

public class GlobalBackground {

	private static int bg01=R.drawable.rabbit_1080;
	
	private static GlobalBackground background;
	
	private boolean used;
	
	public boolean isUsed() {
		return used;
	}

	public void setUsed(boolean used) {
		this.used = used;
	}

	public static GlobalBackground get(){
	
		if(background==null){
			synchronized (GlobalBackground.class) {
				if(background==null){
					background=new GlobalBackground();
				}
			}
		}
		return background;
	}
	
	
	private int dynamicBackGround=bg01;
	
	public int getDynamicBackGround() {
		return dynamicBackGround;
	}
	
	public void setDynamicBackGround(int dynamicBackGround) {
		this.dynamicBackGround = dynamicBackGround;
	}
	
}
