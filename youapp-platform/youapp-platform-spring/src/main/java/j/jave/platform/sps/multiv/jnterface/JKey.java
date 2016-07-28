package j.jave.platform.sps.multiv.jnterface;

import j.jave.kernal.jave.exception.JInitializationException;
import j.jave.kernal.jave.io.JInputStreamWrapperSource;
import j.jave.kernal.jave.utils.JPropertiesUtils;
import j.jave.platform.sps.multiv.ComponentMetaNames;
import j.jave.platform.sps.multiv.ComponentVersionSpringApplicationSupport;
import j.jave.platform.sps.multiv.ComponentVersionSpringApplicationSupport.ComponentProperties;

import java.io.InputStream;
import java.util.Properties;

/**
 * Any unique version.
 * @author J
 *
 */
public abstract class JKey {

	protected String version;
	
	protected String component;
	
	protected String app;
	
	protected final String unique(){
		return ComponentVersionSpringApplicationSupport.unique(app, component, version);
	}
	
	/**
	 * generally, call {@link #unique()} directly.
	 * @return
	 */
	public abstract String getKey();
	
	public JKey(){
		findComponentInfo();
	}
	// get all info from component-version.properties.
	private void findComponentInfo() {
		InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(ComponentProperties.PROPERTY_LOCATION);
		try {
			JInputStreamWrapperSource inputStreamWrapperSource=new JInputStreamWrapperSource(inputStream);
			Properties properties= JPropertiesUtils.loadProperties(inputStreamWrapperSource);
			app = JPropertiesUtils.getKey(ComponentMetaNames.APP_NAME,
					properties);
			component = JPropertiesUtils.getKey(
					ComponentMetaNames.COMPONENT_NAME, properties);
			version = JPropertiesUtils.getKey(
					ComponentMetaNames.COMPONENT_VERSION, properties);
		} catch (Exception e) {
			throw new JInitializationException(e);
		}
	}
	
	public String getVersion() {
		return version;
	}

	public String getComponent() {
		return component;
	}

	public String getApp() {
		return app;
	}
	
	
}
