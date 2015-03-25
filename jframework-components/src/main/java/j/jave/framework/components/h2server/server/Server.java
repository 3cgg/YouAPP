package j.jave.framework.components.h2server.server;

import j.jave.framework.components.bill.sql.BillSQLLoader;
import j.jave.framework.components.h2server.sql.SQLLoader;
import j.jave.framework.components.login.sql.LoginSQLLoader;
import j.jave.framework.components.param.sql.ParamSQLLoader;
import j.jave.framework.components.weight.sql.WeightSQLLoader;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Server {

	
	private org.h2.tools.Server server;  
    private String port = "9494";  
    private String dbDir = "./db/h2/h2db/components";  
    private String user = "J";  
    private String password = "J";  
  
    private void startServer() {  
    	startServer(port);
    }
    
    public static void startServer(String port){
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
  
    public static void connect(String dbDir,String user,String password){
    	try {  
            Class.forName("org.h2.Driver");  
            Connection conn = DriverManager.getConnection("jdbc:h2:" + dbDir,  
                    user, password);  
            Statement stat = conn.createStatement();  
            
            ResultSet resultSet=stat.executeQuery(" select * from  INFORMATION_SCHEMA.TABLES where TABLE_TYPE='TABLE' AND TABLE_SCHEMA='PUBLIC' ");
            List<String> tables=new ArrayList<String>();
            while (resultSet.next()) {
				String tableName=resultSet.getString("TABLE_NAME");
				tables.add(tableName);
			}
            //login 
            SQLLoader sqlLoader=new LoginSQLLoader();
            List<String> loginSqls=sqlLoader.load();
            execute(stat, tables, loginSqls);

            //weight 
            sqlLoader=new WeightSQLLoader();
            loginSqls=sqlLoader.load();
            execute(stat, tables, loginSqls);
            
            //param 
            sqlLoader=new ParamSQLLoader();
            loginSqls=sqlLoader.load();
            execute(stat, tables, loginSqls);
            
            //bill 
            sqlLoader=new BillSQLLoader();
            loginSqls=sqlLoader.load();
            execute(stat, tables, loginSqls);
            
            
            System.out.println("ALL TABLES : ");
            
            for (Iterator iterator = tables.iterator(); iterator.hasNext();) {
				String string = (String) iterator.next();
				System.out.println(string);
			}
            
            
            stat.close();  
            conn.close();  
        } catch (Exception e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        }
    }

	private static void execute(Statement stat, List<String> tables,
			List<String> loginSqls) throws SQLException {
		for (Iterator<String> iterator = loginSqls.iterator(); iterator.hasNext();) {
			String sql =  iterator.next();
			boolean done=false;
			for (Iterator<String> iterator2 = tables.iterator(); iterator2.hasNext();) {
				String string =  iterator2.next();
				if(sql.indexOf(string)!=-1)  {
					done=true;
					break;
				}
			}
			
			if(!done){
				System.out.println("processed : "+sql);
				stat.execute(sql);
			}
		}
	}
    
    
    private void connect() {  
    	connect(dbDir, user, password);
    }  
  
    public static void main(String[] args) {  
    	Server server = new Server();  
    	server.startServer();  
    	server.connect();  
//       // h2.stopServer();  
        System.out.println("==END==");  
    }  
	
    
    
    public List<String> load(){
    	List<String> sqls=new ArrayList<String>();
    	Package[] packages=Package.getPackages();

    	for (int i = 0; i < packages.length; i++) {
    		Package pack=packages[i];
    		
    		System.out.println(pack.getName());
		}
    	
    	return sqls;
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
	

}
