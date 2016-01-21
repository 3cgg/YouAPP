package j.jave.framework.commons.xmldb;

import j.jave.framework.commons.utils.JAssert;
import j.jave.framework.commons.utils.JPropertiesUtils;
import j.jave.framework.commons.utils.JStringUtils;

public class JSinglePropertiesXMLDataSource extends JXMLDataSource {

	private static JSinglePropertiesXMLDataSource singlePropertiesXMLDataSource;
	
	private static final String URL_KEY="j.jave.framework.commons.xmldb.url";
	
	private JSinglePropertiesXMLDataSource(){}
	
	public static JSinglePropertiesXMLDataSource get(){
		
		if(singlePropertiesXMLDataSource==null){
			synchronized (JSinglePropertiesXMLDataSource.class) {
				if(singlePropertiesXMLDataSource==null){
					JSinglePropertiesXMLDataSource singlePropertiesXMLDataSource=new JSinglePropertiesXMLDataSource();
					String url=JPropertiesUtils.getKey(URL_KEY, "commons-xmldb.properties");
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
