package j.jave.framework.sqlloader;

import j.jave.framework._package.JFileClassPathDefaultScan;
import j.jave.framework._package.JJARDefaultScan;
import j.jave.framework._package.JPackageResolve;
import j.jave.framework.utils.JClassPathUtils;
import j.jave.framework.utils.JUtils;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * basic class to create DDL SQL. 
 * extract common method. 
 * @author J
 *
 */
public abstract class JAbstractSQLDDLCreate extends JDefaultSQLDDLConfigure implements JSQLDDLCreate {

	protected final Logger LOGGER=LoggerFactory.getLogger(getClass());
	
	private final String className;
	private final String url;
	private final String user;
	private final String password;
	
	public JAbstractSQLDDLCreate(String className,String url, String user, String password) {
		this.className=className;
		this.url=url;
		this.user=user;
		this.password=password;
	}
	
	@Override
	public void create() {
		Connection conn=null;
		Statement stat =null;
		try {  
            Class.forName(className);  
            conn = DriverManager.getConnection(url,  
                    user, password);  
            stat = conn.createStatement();  
            
            // existing tables . 
            List<String> tables=existTables(conn);
            LOGGER.info("ALL EXISTING TABLES : ");
            for (Iterator<String> iterator = tables.iterator(); iterator.hasNext();) {
				String string =  iterator.next();
				LOGGER.info(string);
			}
            
            // scanning JSQLDDLLoader implementation. 
            ClassLoader classLoader=Thread.currentThread().getContextClassLoader();
			Set<Class<?>> classes=new HashSet<Class<?>>();
			
			Set<File> files= JClassPathUtils.getClassPathFilesFromSystem();
			for (Iterator<File> iterator = files.iterator(); iterator.hasNext();) {
				File classPathFile =  iterator.next();
				if(classPathFile.exists()){
					String fileName=classPathFile.getName();
					
					
					if(fileName.endsWith(".jar")){
						JJARDefaultScan defaultScan=new JJARDefaultScan(classPathFile);
						defaultScan.setClassLoader(classLoader);
						if(this.packageName!=null){
							defaultScan.setIncludePackages(new String[]{this.packageName});
						}
						classes.addAll(JPackageResolve.get().getImplements(defaultScan, JSQLDDLLoader.class));
					}
					else{
						JFileClassPathDefaultScan defaultScan=new JFileClassPathDefaultScan(classPathFile);
						defaultScan.setClassLoader(classLoader);
						if(this.packageName!=null){
							defaultScan.setIncludePackages(new String[]{this.packageName});
						}
						classes.addAll(JPackageResolve.get().getImplements(defaultScan, JSQLDDLLoader.class));
					}
				}
			}
			
            if(classes!=null){
            	for (Iterator<Class<?>> iterator = classes.iterator(); iterator.hasNext();) {
					Class<?> clazz =iterator.next();
					JSQLDDLLoader sqlddlLoader= (JSQLDDLLoader) clazz.newInstance();
					List<String> sqls=sqlddlLoader.load();
					execute(stat, tables, sqls);
				}
            }
            
            LOGGER.info("load sql completely! ");
        } catch (Exception e) {  
        	LOGGER.error(" load sql: "+e.getMessage(), e);
        }finally{
        	try {
        		if(stat!=null){
        			stat.close();
        		}
			} catch (SQLException e) {
				LOGGER.error("close statement ", e);
			}  
            try {
            	if(conn!=null){
            		conn.close();
            	}
			} catch (SQLException e) {
				LOGGER.error("close connection ", e);
			}  
        }

	}
	
	private void execute(Statement stat, List<String> tables,
			List<String> ddls) throws SQLException {
		for (Iterator<String> iterator = ddls.iterator(); iterator.hasNext();) {
			String sql =  iterator.next();
			if(JUtils.isNullOrEmpty(sql)) continue;
			boolean done=false;
			for (Iterator<String> iterator2 = tables.iterator(); iterator2.hasNext();) {
				String string =  iterator2.next();
				String sqlName=sql.substring(0,sql.indexOf("("));
				String[] sqls=sqlName.split(" ");
				String tableName=sqls[sqls.length-1].trim();
				if(tableName.equalsIgnoreCase(string))  {
					done=true;
					break;
				}
			}
			
			if(!done){
				LOGGER.info("processed : "+sql);
				stat.execute(sql);
			}
		}
	}
	
	/**
	 * return existing tables that had been created .
	 * important must not close the connection . 
	 * @return
	 */
	protected abstract List<String> existTables(Connection connection);

	/**
	 * check whether the jar file matches. 
	 * @param jarFile
	 * @return
	 */
	protected boolean matches(File jarFile){
		boolean valid=jarFile.getName().contains(jarName);
		return valid;
	}
	
	
}
