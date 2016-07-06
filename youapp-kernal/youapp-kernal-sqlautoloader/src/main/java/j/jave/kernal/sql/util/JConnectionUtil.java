package j.jave.kernal.sql.util;

import java.sql.Connection;
import java.sql.DriverManager;

public abstract class JConnectionUtil {

	public static Connection newConnection(String className,String url, String user, String password,boolean... autoCommit) throws Exception{
		Class.forName(className);  
		Connection conn = DriverManager.getConnection(url,  
                user, password);
		if(autoCommit.length>0){
			conn.setAutoCommit(autoCommit[0]);
		}
		return conn;
	}
}
