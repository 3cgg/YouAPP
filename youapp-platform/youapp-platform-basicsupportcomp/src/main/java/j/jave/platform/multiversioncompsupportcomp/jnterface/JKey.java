package j.jave.platform.multiversioncompsupportcomp.jnterface;

import j.jave.kernal.jave.exception.JInitializationException;
import j.jave.kernal.jave.io.JInputStreamWrapperSource;
import j.jave.kernal.jave.utils.JPropertiesUtils;
import j.jave.platform.multiversioncompsupportcomp.ComponentMetaNames;
import j.jave.platform.multiversioncompsupportcomp.ComponentVersionSpringApplicationSupport;
import j.jave.platform.multiversioncompsupportcomp.ComponentVersionSpringApplicationSupport.ComponentProperties;

import java.io.InputStream;
import java.util.Properties;

/**
 * Any unique version.
 * @author J
 *
 */
public abstract class JKey {

	protected int version;
	
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
			version = Integer.parseInt(JPropertiesUtils.getKey(
					ComponentMetaNames.COMPONENT_VERSION, properties));
		} catch (Exception e) {
			throw new JInitializationException(e);
		}
	}

	public int getVersion() {
		return version;
	}

	public String getComponent() {
		return component;
	}

	public String getApp() {
		return app;
	}
	
	
}
