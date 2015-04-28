/**
 * 
 */
package j.jave.framework.support.sqlloader;

/**
 * @author Administrator
 *
 */
public abstract class JDefaultSQLConfigure implements JSQLConfigure {
	
	protected String packageName="j";
	
	protected String jarName="jframework-components";
	
	/* (non-Javadoc)
	 * @see j.jave.framework.sqlloader.JSQLDDLConfigure#setPackageName(java.lang.String)
	 */
	@Override
	public void setPackageName(String packageName) {
		if(packageName!=null){
			this.packageName=packageName;
		}
	}

	/* (non-Javadoc)
	 * @see j.jave.framework.sqlloader.JSQLDDLConfigure#setJarName(java.lang.String)
	 */
	@Override
	public void setJarName(String jarName) {
		if(jarName!=null){
			this.jarName=jarName;
		}
	}
	
}
