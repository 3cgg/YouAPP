package j.jave.framework.commons.sqlloader.dml;

import j.jave.framework.commons.logging.JLogger;
import j.jave.framework.commons.logging.JLoggerFactory;
import j.jave.framework.commons.sqlloader.JPropertiesDBConfiguration;
import j.jave.framework.commons.sqlloader.JPropertiesDBConfigure;
import j.jave.framework.commons.sqlloader.JSQLLoaderException;
import j.jave.framework.commons.utils.JStringUtils;

import java.io.InputStream;

/**
 * create concrete <code>JSQLDMLCreate</code> object from properties.
 * see {@link JPropertiesDBConfigure} to know some property-value. 
 * @author J
 * @see JH2DBSQLDMLCreate
 * @see JPropertiesDBConfigure
 */
public class JPropertiesSQLDMLCreateFactory extends JAbstractSQLDMLCreateFactory implements JPropertiesDBConfigure{

	private static final JLogger LOGGER=JLoggerFactory.getLogger(JPropertiesSQLDMLCreateFactory.class);
	
	@Override
	public JSQLDMLCreate getObject() {
		try {
			JPropertiesDBConfiguration configuration= parse(null);
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
	public JPropertiesDBConfiguration parse(InputStream inputStream) {
		return new JPropertiesDBConfiguration().parse(inputStream);
	}
	
}
