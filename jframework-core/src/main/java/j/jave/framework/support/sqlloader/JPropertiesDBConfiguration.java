/**
 * 
 */
package j.jave.framework.support.sqlloader;

/**
 * DB configuration in the properties. 
 * @author J
 */
public interface JPropertiesDBConfiguration {

	String URL="jframework.database.url";
	String DRIVER="jframework.database.driver";
	String USER_NAME="jframework.database.username";
	String PASSWORD="jframework.database.password";
	String DDL_AOTU="jframework.database.ddl.auto";
	String H2="org.h2.Driver";
	String AUTO="true";
	
}
