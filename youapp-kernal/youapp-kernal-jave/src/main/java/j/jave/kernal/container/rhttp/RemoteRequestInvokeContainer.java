package j.jave.kernal.container.rhttp;

import j.jave.kernal.container.JContainer;
import j.jave.kernal.container.JContainerDelegate;
import j.jave.kernal.container.JExecutor;
import j.jave.kernal.container.JIdentifier;
import j.jave.kernal.container.Scheme;
import j.jave.kernal.jave.logging.JLogger;
import j.jave.kernal.jave.logging.JLoggerFactory;
import j.jave.platform.basicsupportcomp.core.container.MappingMeta;
import j.jave.platform.basicsupportcomp.core.container.SpringCompMicroContainer;
import j.jave.platform.basicsupportcomp.core.container.SpringCompMicroContainerConfig;
import j.jave.platform.basicsupportcomp.core.container.SpringContainerConfig;
import j.jave.platform.basicwebcomp.web.model.ResponseModel;
import j.jave.platform.basicwebcomp.web.youappmvc.HttpContext;
import j.jave.platform.basicwebcomp.web.youappmvc.controller.ControllerExecutor;
import j.jave.platform.multiversioncompsupportcomp.ComponentVersionApplication;

import java.net.URI;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class RemoteRequestInvokeContainer implements JExecutor,JIdentifier,JContainer {

	private static final JLogger LOGGER=JLoggerFactory.getLogger(RemoteRequestInvokeContainer.class);
	
	private String unique;
	
	private String name;
	
	private JRemoteHttpMicroContainer remoteHttpMicroContainer;
	
	protected final JRemoteHttpContainerConfig remoteHttpContainerConfig;
	
	public RemoteRequestInvokeContainer(JRemoteHttpContainerConfig remoteHttpContainerConfig) {
		this.remoteHttpContainerConfig=remoteHttpContainerConfig;
		this.name=remoteHttpContainerConfig.name();
		this.unique=remoteHttpContainerConfig.unique();
	}
	
	@Override
	public String unique() {
		return this.unique;
	}

	@Override
	public String name() {
		return this.name;
	}

	@Override
	public boolean accept(URI uri) {
		return remoteHttpMicroContainer.accept(uri);
	}

	@Override
	public Object execute(URI uri, Object object) {
		
			if(Scheme.CONTROLLER.getValue().equals(uri.getScheme())
					&&Type.EXECUTE.value.equals(uri.getPath())){
				try{
					String query= uri.getQuery();
					Pattern pattern=Pattern.compile(REGX);
					Matcher matcher=pattern.matcher(query);
					String path=null;
					String unique=null;
					if(matcher.matches()){
						unique=matcher.group(1);
						path=matcher.group(2);
					}
					
					return null;
					
				}catch(Exception e){
					LOGGER.error(e.getMessage(), e);
					throw new RuntimeException(e);
				}
			}
		return null;
	}

	@Override
	public void destroy() {
		remoteHttpMicroContainer.destroy();
		remoteHttpMicroContainer=null;
	}

	private void initializeRemoteHttpMicroContainer(){
		JRemoteHttpMicroContainerConfig remoteHttpMicroContainerConfig=
				new JRemoteHttpMicroContainerConfig();
		remoteHttpMicroContainerConfig.setRemoteHttpContainerConfig(remoteHttpContainerConfig);
		remoteHttpMicroContainerConfig.setUnique(remoteHttpMicroContainerConfig.unique());
		remoteHttpMicroContainerConfig.setName(remoteHttpMicroContainerConfig.name());
		
		remoteHttpMicroContainer=new JRemoteHttpMicroContainer(remoteHttpContainerConfig, remoteHttpMicroContainerConfig);
		remoteHttpMicroContainer.initialize();
	}
	
	
	@Override
	public void initialize() {
		initializeRemoteHttpMicroContainer();
		JContainerDelegate.get().register(this);
	}

	@Override
	public void restart() {
		remoteHttpMicroContainer.restart();
	}
	
	public static enum Type{
		
		EXECUTE("/execute");
		
		private String value;
		
		Type(String value){
			this.value=value;
		}
		public String getValue() {
			return value;
		}
	}

	private static final String UNIQUE="unique";
	
	private static final String PATH="path";
	
	/**
	 * controller://get?unique=%s&path=%s
	 */
	private static final String EXECUTE=Scheme.CONTROLLER.getValue()+"://localhost%s?"+UNIQUE+"=%s&"+PATH+"=%s";
	
	/**
	 * ^unique=([a-zA-Z:0-9_]+)&path=([a-zA-Z:0-9_]+)$
	 */
	private static final String REGX=ControllerRunner.REGX;
	
	public static class URIUtil{
		public static final String getControllerRequestGetURI(String unique,String path){
			return ControllerMicroContainer.getGetRequest(unique, path);
		}
		
		public static final String getControllerRequestExistURI(String unique,String path){
			return ControllerMicroContainer.getExistRequest(unique, path);
		}
		
		public static final String getControllerRequestPutURI(String unique,String path){
			return ControllerMicroContainer.getPutRequest(unique, path);
		}

		public static final String getBeanRequestGetURI(String unique,String beanName){
			return SpringCompMicroContainer.getGetRequest(unique, beanName);
		}
		
		public static String getRequestExecuteURI(String unique,String path){
			return String.format(EXECUTE,Type.EXECUTE.value,unique,path);
		}
	}
}
