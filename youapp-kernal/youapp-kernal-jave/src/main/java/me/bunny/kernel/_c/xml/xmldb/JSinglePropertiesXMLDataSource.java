package me.bunny.kernel._c.xml.xmldb;

import me.bunny.kernel.JConfiguration;
import me.bunny.kernel.JProperties;
import me.bunny.kernel._c.utils.JAssert;
import me.bunny.kernel._c.utils.JStringUtils;

public class JSinglePropertiesXMLDataSource extends JXMLDataSource {

	private static JSinglePropertiesXMLDataSource singlePropertiesXMLDataSource;
	
	private static final String URL_KEY=JProperties.XMLDB_URL;
	
	private JSinglePropertiesXMLDataSource(){}
	
	public static JSinglePropertiesXMLDataSource get(){
		
		if(singlePropertiesXMLDataSource==null){
			synchronized (JSinglePropertiesXMLDataSource.class) {
				if(singlePropertiesXMLDataSource==null){
					JSinglePropertiesXMLDataSource singlePropertiesXMLDataSource=new JSinglePropertiesXMLDataSource();
					String url=JConfiguration.get().getString(URL_KEY,"file:/"+System.getProperty("user.dir"));
					JAssert.state(JStringUtils.isNotNullOrEmpty(url), "url must be set while using xml db, check whether the file commons-xmldb.properties exists in the class path.");
					singlePropertiesXMLDataSource.uri=url;
					JSinglePropertiesXMLDataSource.singlePropertiesXMLDataSource=singlePropertiesXMLDataSource;
					singlePropertiesXMLDataSource.init();
				}
			}
		}
		return singlePropertiesXMLDataSource;
	}
	
}
