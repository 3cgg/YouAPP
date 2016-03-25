/**
 * 
 */
package j.jave.kernal.sqlloader;

import java.io.InputStream;

/**
 * DB configuration in the properties. 
 * <p><table BORDER CELLPADDING=3 CELLSPACING=1>
 * <tr>
 * <td>youapp.database.url</td><td>jdbc:h2:D:/java_/server-directory/db/h2/h2db/youapp;AUTOCOMMIT=OFF</td>
 * </tr>
 * <tr>
 *	<td>youapp.database.driver</td><td>org.h2.Driver</td>
 *	</tr>
 * <tr>
 *	<td>youapp.database.username</td><td>J</td>
 * </tr>
 * <tr>
 *	<td>youapp.database.password</td><td>J</td>
 * </tr>
 * <tr>
 *	<td>youapp.database.sql.auto</td><td>true|false</td>
 * </tr>
 * <tr>
 *	<td>youapp.java.sql.dialect</td><td>h2</td>
 * </tr>
 * </table>
 * @author J
 */
public interface JPropertiesDBConfigure {

	String URL="youapp.database.url";
	String DRIVER="youapp.database.driver";
	String USER_NAME="youapp.database.username";
	String PASSWORD="youapp.database.password";
	
	String SQL_AUTO="youapp.database.sql.auto";
	String DIALECT="youapp.java.sql.dialect";
	
	String H2="H2";
	
	public JPropertiesDBConfiguration parse(InputStream inputStream);
	
	
}
