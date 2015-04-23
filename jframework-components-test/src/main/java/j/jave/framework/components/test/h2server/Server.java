package j.jave.framework.components.test.h2server;

import java.sql.SQLException;

public class Server {
	

	private String port = "9494";  
  
    private void startServer() {  
    	startServer(port);
    }
    
    public static void startServer(String port){
    	try {  
            System.out.println("正在启动h2...");  
            org.h2.tools.Server.createWebServer(new String[] { "-webPort", port }).start();
        } catch (SQLException e) {  
            System.out.println("启动h2出错：" + e.toString());  
            e.printStackTrace();  
            throw new RuntimeException(e);  
        }  
    }
  
    public static void main(String[] args) throws Exception {
    	Server server = new Server();  
    	server.startServer();  
        System.out.println("==END==");  
    }  

}
