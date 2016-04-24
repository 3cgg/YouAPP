package j.jave.platform.basicwebcomp.web.youappmvc.container;

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

class RequestInvokeContainer implements JExecutor,JIdentifier,JContainer {

	private static final JLogger LOGGER=JLoggerFactory.getLogger(RequestInvokeContainer.class);
	
	private String unique;
	
	private String name;
	
	private SpringCompMicroContainer springCompMicroContainer;
	
	private ControllerMicroContainer controllerMicroContainer;
	
	protected final SpringContainerConfig springContainerConfig;
	
	private final ComponentVersionApplication componentVersionApplication;
	
	public RequestInvokeContainer(SpringContainerConfig springContainerConfig,
			ComponentVersionApplication componentVersionApplication) {
		this.springContainerConfig=springContainerConfig;
		this.componentVersionApplication=componentVersionApplication;
		springContainerConfig.setName(componentVersionApplication.name());
		springContainerConfig.setUnique(componentVersionApplication.unique());
		this.name=springContainerConfig.getName();
		this.unique=springContainerConfig.getUnique();
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
		return controllerMicroContainer.accept(uri)||springCompMicroContainer.accept(uri);
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
					String controllerGetURI= URIUtil.getControllerRequestGetURI(unique, path);
					MappingMeta mappingMeta= (MappingMeta) controllerMicroContainer.execute(new URI(controllerGetURI), object);
					if(mappingMeta==null){
						return ResponseModel.newError().setData("cannot find any controller for the path. "
								+ " check if turn on multiple component version infrastructure (immutable version)."
								+ " attempt to prefix /youappcomp/[appname]/[component]/[compversion]/...  you actual path.");
					}
					
					String beanGetURI=URIUtil.getBeanRequestGetURI(unique, mappingMeta.getControllerName());
					Object controllerObject=springCompMicroContainer.execute(new URI(beanGetURI), object);
					if(controllerObject==null){
						return ResponseModel.newError().setData("cannot find any controller for the path. "
								+ " check if turn on multiple component version infrastructure (immutable version)."
								+ " attempt to prefix /youappcomp/[appname]/[component]/[compversion]/...  you actual path.");
					}
					return ControllerExecutor.newSingleExecutor().execute((HttpContext)object,
							mappingMeta, controllerObject);
				}catch(Exception e){
					LOGGER.error(e.getMessage(), e);
					throw new RuntimeException(e);
				}
			}
		
		if(controllerMicroContainer.accept(uri)){
			return controllerMicroContainer.execute(uri, object);
		}
		if(springCompMicroContainer.accept(uri)){
			return springCompMicroContainer.execute(uri, object);
		}
		return null;
	}

	@Override
	public void destroy() {
		springCompMicroContainer.destroy();
		controllerMicroContainer.destroy();
		springCompMicroContainer=null;
		controllerMicroContainer=null;
	}

	private void initializeSpringCompMicroContainer(){
		SpringCompMicroContainerConfig springCompMicroContainerConfig=
				new SpringCompMicroContainerConfig();
		springCompMicroContainerConfig.setUnique(componentVersionApplication.unique());
		springCompMicroContainerConfig.setName(componentVersionApplication.name());
		springCompMicroContainer=new SpringCompMicroContainer
				(springContainerConfig,springCompMicroContainerConfig,componentVersionApplication);
		springCompMicroContainer.initialize();
	}
	
	private void initializeControllerMicroContainer(){
		ControllerMicroContainerConfig controllerMicroContainerConfig=
				new ControllerMicroContainerConfig(springCompMicroContainer.getApplicationCotext());
		controllerMicroContainerConfig.setName(componentVersionApplication.name());
		controllerMicroContainerConfig.setUnique(componentVersionApplication.unique());
		controllerMicroContainer=new ControllerMicroContainer(springContainerConfig, controllerMicroContainerConfig,componentVersionApplication);
		controllerMicroContainer.initialize();
	}
	
	@Override
	public void initialize() {
		initializeSpringCompMicroContainer();
		initializeControllerMicroContainer();
		JContainerDelegate.get().register(this);
	}

	@Override
	public void restart() {
		springCompMicroContainer.restart();
		controllerMicroContainer.restart();
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
