/**
 * 
 */
package j.jave.framework.me;

import j.jave.framework.components.h2server.server.Server;


/**
 * @author Administrator
 *
 */
public class H2Server {

    private static final String port = "9999";  
    private static final  String dbDir = "D:/java_/server-directory/db/h2/h2db/youapp;AUTOCOMMIT=OFF";  
    private static final  String user = "J";  
    private static final  String password = "J";  
	
	public static void main(String[] args) {
		
		Server.startServer(port); 
		Server.connect(dbDir, user, password);
//       // h2.stopServer();  
        System.out.println("==END==");  
		
	}
	
}
