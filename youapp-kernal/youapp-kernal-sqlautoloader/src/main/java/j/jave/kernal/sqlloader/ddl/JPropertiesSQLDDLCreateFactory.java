package j.jave.kernal.sqlloader.ddl;

import j.jave.kernal.jave.logging.JLogger;
import j.jave.kernal.jave.logging.JLoggerFactory;
import j.jave.kernal.jave.utils.JStringUtils;
import j.jave.kernal.sqlloader.JPropertiesDBConfiguration;
import j.jave.kernal.sqlloader.JPropertiesDBConfigure;
import j.jave.kernal.sqlloader.JSQLLoaderException;

import java.io.InputStream;


/**
 * create concrete <code>JSQLDDLCreate</code> object from properties.
 * see {@link JPropertiesDBConfigure} to know some property-value. 
 * @author J
 * @see JH2DBSQLDDLCreate
 * @see JPropertiesDBConfigure
 */
public class JPropertiesSQLDDLCreateFactory extends JAbstractSQLDDLCreateFactory implements JPropertiesDBConfigure{

	private static final JLogger LOGGER=JLoggerFactory.getLogger(JPropertiesSQLDDLCreateFactory.class);
	
	@Override
	public JSQLDDLCreate getObject() {
		try {
			
			JPropertiesDBConfiguration configuration= parse(null);
			String driver=configuration.getDriver();
			String url=configuration.getUrl();
			String userName=configuration.getUserName();
			String password=configuration.getPassword();
			boolean auto=configuration.isAuto();
			String dialect=configuration.getDialect();
			
			if(!auto){
				return new JEmptySQLDDLCreate(driver, url, userName, password);
			}
			
			if(dialect!=null&&H2.equals(dialect.trim())){
				// h2
				JH2DBSQLDDLCreate h2=new JH2DBSQLDDLCreate(driver, url, userName, password);
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
	public JPropertiesDBConfiguration parse(InputStream inputStream) {
		return new JPropertiesDBConfiguration().parse(inputStream);
	}

}
