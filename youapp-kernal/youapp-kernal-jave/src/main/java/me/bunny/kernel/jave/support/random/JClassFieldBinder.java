package me.bunny.kernel.jave.support.random;

import me.bunny.kernel.jave.support._package.JAbstractFieldFinder.JFieldFilter;

public interface JClassFieldBinder extends JRandomBinder{
	
	public void setFieldFilter(JFieldFilter fieldFilter);
	
}
