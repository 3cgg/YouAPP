package j.jave.kernal.sqlloader.dml;

import j.jave.kernal.jave.logging.JLogger;
import j.jave.kernal.jave.logging.JLoggerFactory;
import j.jave.kernal.jave.utils.JStringUtils;
import j.jave.kernal.sqlloader.JDBConfig;
import j.jave.kernal.sqlloader.JDBConfigNames;
import j.jave.kernal.sqlloader.JSQLLoaderException;

import java.io.InputStream;

/**
 * create concrete <code>JSQLDMLCreate</code> object from properties.
 * see {@link JDBConfigNames} to know some property-value. 
 * @author J
 * @see JH2DBSQLDMLCreate
 * @see JDBConfigNames
 */
public class JPropertiesSQLDMLCreateFactory extends JAbstractSQLDMLCreateFactory implements JDBConfigNames{

	private static final JLogger LOGGER=JLoggerFactory.getLogger(JPropertiesSQLDMLCreateFactory.class);
	
	@Override
	public JSQLDMLCreate getObject() {
		try {
			JDBConfig configuration= parse(null);
			String driver=configuration.getDriver();
			String url=configuration.getUrl();
			String userName=configuration.getUserName();
			String password=configuration.getPassword();
			boolean auto=configuration.isAuto();
			String dialect=configuration.getDialect();
			
			if(!auto){
				return new JEmptySQLDMLCreate(driver, url, userName, password);
			}
			
			if(dialect!=null&&H2.equals(dialect.trim())){
				// h2
				JH2DBSQLDMLCreate h2=new JH2DBSQLDMLCreate(driver, url, userName, password);
				if(JStringUtils.isNotNullOrEmpty(jarName)){
					h2.setJarName(jarName);
				}
				if(JStringUtils.isNotNullOrEmpty(packageName)){
					h2.setPackageName(packageName);
				}
				return h2;
			}
			
		} catch (Exception e) {
			LOGGER.error("in create ddl sql creating factory", e);
			throw new JSQLLoaderException(e);
		}
		return null;
	}

	
	@Override
	public JDBConfig parse(InputStream inputStream) {
		return new JDBConfig().parse(inputStream);
	}
	
}
