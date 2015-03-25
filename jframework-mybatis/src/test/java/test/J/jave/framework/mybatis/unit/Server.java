package test.J.jave.framework.mybatis.unit;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Server {

	
	private org.h2.tools.Server server;  
    private String port = "9094";  
    private String dbDir = "./db/h2/h2db/JDatabase";  
    private String user = "J";  
    private String password = "J";  
  
    public void startServer() {  
        try {  
            System.out.println("正在启动h2...");  
            
            org.h2.tools.Server.createWebServer(new String[] { "-webPort", port }).start();

        } catch (SQLException e) {  
            System.out.println("启动h2出错：" + e.toString());  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
            throw new RuntimeException(e);  
        }  
    }  
  
    public void stopServer() {  
        if (server != null) {  
            System.out.println("正在关闭h2...");  
            server.stop();  
            System.out.println("关闭成功.");  
        }  
    }  
  
    public void useH2() {  
        try {  
            Class.forName("org.h2.Driver");  
            Connection conn = DriverManager.getConnection("jdbc:h2:" + dbDir,  
                    user, password);  
            Statement stat = conn.createStatement();  
            // insert data  
//            stat.execute("CREATE TABLE PERSON(ID VARCHAR"
//            		+ ",AGE INTEGER"
//            		+ ",FIRSTNAME VARCHAR"
//            		+ ",LASTNAME VARCHAR"
//            		+ ",CREATEID VARCHAR"
//            		+ ",UPDATEID VARCHAR"
//            		+ ",CREATETIME TIMESTAMP "
//            		+ ",UPDATETIME TIMESTAMP "
//            		+ ",DELETED VARCHAR "
//            		+ ",VERSION INTEGER "
//            		+ ")");  
            
            stat.execute("CREATE TABLE usersS ("+
  "id varchar(32),"+
  "userName varchar(20) DEFAULT NULL,"+
  "password varchar(60) DEFAULT NULL,"+
  "createId varchar(32),"+
  "createTime timestamp  ,"+
  "updateId varchar(32) , "+
  "updateTime timestamp,"+
   "deleted varchar(1) DEFAULT NULL,"+
  "version int, "+
  "PRIMARY KEY (id)"+
            	");");
            
            stat.close();  
            conn.close();  
        } catch (Exception e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        }  
    }  
  
    public static void main(String[] args) {  
    	Server server = new Server();  
    	server.startServer();  
    	//server.useH2();  
       // h2.stopServer();  
        System.out.println("==END==");  
    }  
	
	

}
