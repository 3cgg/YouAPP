package j.jave.kernal.jave.xml.xmldb;

import j.jave.kernal.jave.utils.JAssert;
import j.jave.kernal.jave.utils.JPropertiesUtils;
import j.jave.kernal.jave.utils.JStringUtils;

public class JSinglePropertiesXMLDataSource extends JXMLDataSource {

	private static JSinglePropertiesXMLDataSource singlePropertiesXMLDataSource;
	
	private static final String URL_KEY="youapp.xmldb.url";
	
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
