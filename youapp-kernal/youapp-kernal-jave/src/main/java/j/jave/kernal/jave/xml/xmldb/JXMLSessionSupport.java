package j.jave.kernal.jave.xml.xmldb;

import j.jave.kernal.jave.proxy.JAtomicResourceSessionHolder;

public class JXMLSessionSupport {
	
	protected JXMLSession getXmlSession() throws Exception{
		JXMLSession session= (JXMLSession) JAtomicResourceSessionHolder.getAtomicResourceSession(true);
		if(session==null){
			session=new JXMLSession();
		}
		return session;
	}
	
}
