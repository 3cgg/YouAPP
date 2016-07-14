package j.jave.kernal.container.scheme;

import j.jave.kernal.container.JExecutableURIUtil.Type;
import j.jave.kernal.eventdriven.servicehub.JServiceFactorySupport;
import j.jave.kernal.jave.exception.JOperationNotSupportedException;
import j.jave.kernal.jave.support.parser.JParser;

import java.net.URI;

public class JSchemeURIParserService
extends JServiceFactorySupport<JSchemeURIParserService>
implements JParser {
	
	private JBeanURIGetService beanURIGetService=new JBeanURIGetService();
	
	private JControllerURIGetService controllerURIGetService=new JControllerURIGetService();
	
	private JJarURIGetService jarURIGetService=new JJarURIGetService();
	
	private JRHttpURIGetService remoteHttpURIGetService=new JRHttpURIGetService();
	
	private JHttpURIGetService httpURIGetService=new JHttpURIGetService();
	
	private JHttpsURIGetService httpsURIGetService=new JHttpsURIGetService();
	
	private JLocalFileURIGetService localFileURIGetService=new JLocalFileURIGetService();
	
	private JFtpURIGetService ftpURIGetService=new JFtpURIGetService();
	
	private JClassPathURIGetService classPathURIGetService=new JClassPathURIGetService();
	
	@Override
	protected JSchemeURIParserService doGetService() {
		return this;
	}
	
	private String doGetURI(String path,String container,Type type,JSchemeURIGetService uriGetService){
		String uri=null;
		switch (type) {
		case GET:
			uri=uriGetService.getGetRequestURI(container, path);
			break;
		case PUT:
			uri=uriGetService.getPutRequestURI(container, path);
			break;
		case DELETE:
			uri=uriGetService.getDeleteRequestURI(container, path);
			break;
		case EXIST:
			uri=uriGetService.getExistRequestURI(container, path);
			break;
		case EXECUTE:
			uri=uriGetService.getExecuteRequestURI(container, path);
			break;
		default:
			break;
		}
		return uri;
	}
	

	public URI parse(URI uri,String container,Type type) throws Exception{
		String scheme=uri.getScheme();
		if("jar".equals(scheme)){
			return new URI(doGetURI(uri.toURL().toString(), container, type, jarURIGetService));
		}
		if("file".equals(scheme)){
			return new URI(doGetURI(uri.toURL().toString(), container, type, localFileURIGetService));
		}
		if("http".equals(scheme)){
			return new URI(doGetURI(uri.toURL().toString(), container, type, httpURIGetService));
		}
		if("https".equals(scheme)){
			return new URI(doGetURI(uri.toURL().toString(), container, type, httpsURIGetService));
		}
		if("ftp".equals(scheme)){
			return new URI(doGetURI(uri.toURL().toString(), container, type, ftpURIGetService));
		}
		throw new JOperationNotSupportedException("cannot parser the uri : "+uri.toURL().toString());
	}
	
	/**
	 * convenience to {@link #parse(URI, String, Type.GET)}
	 * @param uri
	 * @param container
	 * @return
	 * @throws Exception
	 */
	public URI parse(URI uri,String container) throws Exception{
		return parse(uri, container, Type.GET);
	}
	
	public URI parse4Bean(String uri,String container) throws Exception{
		return parse4Bean(uri, container, Type.GET);
	}
	
	public URI parse4Bean(String uri,String container,Type type) throws Exception{
		return new URI(doGetURI(uri, container, type, beanURIGetService));
	}
	
	public URI parse4ClassPath(String uri,String container) throws Exception{
		return parse4ClassPath(uri, container, Type.GET);
	}
	
	public URI parse4ClassPath(String uri,String container,Type type) throws Exception{
		return new URI(doGetURI(uri, container, type, classPathURIGetService));
	}
	
	public URI parse4Controller(String uri,String container) throws Exception{
		return parse4Controller(uri, container, Type.GET);
	}
	
	public URI parse4Controller(String uri,String container,Type type) throws Exception{
		return new URI(doGetURI(uri, container, type, controllerURIGetService));
	}
	
	public URI parse4RemoteHttp(String uri,String container) throws Exception{
		return parse4RemoteHttp(uri, container, Type.GET);
	}
	
	public URI parse4RemoteHttp(String uri,String container,Type type) throws Exception{
		return new URI(doGetURI(uri, container, type, remoteHttpURIGetService));
	}
	
}
