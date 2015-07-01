/**
 * 
 */
package j.jave.framework.commons.sqlloader;

/**
 * default SQL configuration.
 * @author J
 */
public abstract class JDefaultSQLConfigure implements JSQLConfigure {
	
	protected String packageName="j";
	
	protected String jarName="jframework-components";
	
	@Override
	public void setPackageName(String packageName) {
		if(packageName!=null){
			this.packageName=packageName;
		}
	}

	@Override
	public void setJarName(String jarName) {
		if(jarName!=null){
			this.jarName=jarName;
		}
	}
	
}
