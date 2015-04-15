/**
 * 
 */
package j.jave.framework.sqlloader;

/**
 * @author Administrator
 *
 */
public abstract class JDefaultSQLDDLConfigure implements JSQLDDLConfigure {
	
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
