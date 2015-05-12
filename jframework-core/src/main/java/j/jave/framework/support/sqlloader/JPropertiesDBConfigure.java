/**
 * 
 */
package j.jave.framework.support.sqlloader;

import java.io.InputStream;

/**
 * DB configuration in the properties. 
 * <p><table BORDER CELLPADDING=3 CELLSPACING=1>
 * <tr>
 * <td>jframework.database.url</td><td>jdbc:h2:D:/java_/server-directory/db/h2/h2db/youapp;AUTOCOMMIT=OFF</td>
 * </tr>
 * <tr>
 *	<td>jframework.database.driver</td><td>org.h2.Driver</td>
 *	</tr>
 * <tr>
 *	<td>jframework.database.username</td><td>J</td>
 * </tr>
 * <tr>
 *	<td>jframework.database.password</td><td>J</td>
 * </tr>
 * <tr>
 *	<td>jframework.database.sql.auto</td><td>true|false</td>
 * </tr>
 * <tr>
 *	<td>jframework.java.sql.dialect</td><td>h2</td>
 * </tr>
 * </table>
 * @author J
 */
public interface JPropertiesDBConfigure {

	String URL="jframework.database.url";
	String DRIVER="jframework.database.driver";
	String USER_NAME="jframework.database.username";
	String PASSWORD="jframework.database.password";
	
	String SQL_AUTO="jframework.database.sql.auto";
	String DIALECT="jframework.java.sql.dialect";
	
	String H2="h2";
	
	public JPropertiesDBConfiguration parse(InputStream inputStream);
	
	
}
