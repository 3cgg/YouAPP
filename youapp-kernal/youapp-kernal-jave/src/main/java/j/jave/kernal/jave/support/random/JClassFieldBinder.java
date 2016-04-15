package j.jave.kernal.jave.support.random;

import j.jave.kernal.jave.support.detect.JFieldDetector.JFieldFilter;

public interface JClassFieldBinder extends JRandomBinder{
	
	public void setFieldFilter(JFieldFilter fieldFilter);
	
}
