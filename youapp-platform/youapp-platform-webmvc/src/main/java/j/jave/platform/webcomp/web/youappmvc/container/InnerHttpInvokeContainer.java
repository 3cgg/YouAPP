package j.jave.platform.webcomp.web.youappmvc.container;

import j.jave.kernal.container.JContainer;
import j.jave.kernal.container.JContainerDelegate;
import j.jave.kernal.container.JExecutableURIUtil;
import j.jave.kernal.container.JExecutor;
import j.jave.kernal.container.JIdentifier;
import j.jave.kernal.container.JScheme;
import j.jave.kernal.jave.logging.JLogger;
import j.jave.kernal.jave.logging.JLoggerFactory;
import j.jave.platform.data.web.mapping.MappingMeta;
import j.jave.platform.sps.core.container.SpringCompMicroContainer;
import j.jave.platform.sps.core.container.SpringCompMicroContainerConfig;
import j.jave.platform.sps.core.container.SpringContainerConfig;
import j.jave.platform.sps.multiv.ComponentVersionApplication;
import j.jave.platform.webcomp.web.model.ResponseModel;
import j.jave.platform.webcomp.web.youappmvc.HttpContext;
import j.jave.platform.webcomp.web.youappmvc.controller.ControllerExecutor;

import java.net.URI;

public class InnerHttpInvokeContainer implements JExecutor,JIdentifier,JContainer {

	private static final JLogger LOGGER=JLoggerFactory.getLogger(InnerHttpInvokeContainer.class);
	
	private String unique;
	
	private String name;
	
	private SpringCompMicroContainer springCompMicroContainer;
	
	private ControllerMicroContainer controllerMicroContainer;
	
	protected final SpringContainerConfig springContainerConfig;
	
	private final ComponentVersionApplication componentVersionApplication;
	
	public InnerHttpInvokeContainer(SpringContainerConfig springContainerConfig,
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
		
			if(JScheme.CONTROLLER.getValue().equals(uri.getScheme())
					&&JExecutableURIUtil.Type.EXECUTE.getValue().equals(uri.getPath())){
				try{
					String path=JExecutableURIUtil.getPath(uri);
					String unique=JExecutableURIUtil.getUnique(uri);
					String controllerGetURI=controllerMicroContainer.getGetRequestURI(unique, path);
					MappingMeta mappingMeta= (MappingMeta) controllerMicroContainer.execute(new URI(controllerGetURI), object);
					if(mappingMeta==null){
						return ResponseModel.newError().setData("cannot find any controller for the path. "
								+ " check if turn on multiple component version infrastructure (immutable version)."
								+ " attempt to prefix /youappcomp/[appname]/[component]/[compversion]/...  you actual path.");
					}
					
					Object controllerObject=getControllerObject(unique, mappingMeta, object);
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

	/**
	 * override the method to provide the needable runtime object.
	 * @param unique
	 * @param mappingMeta
	 * @param object
	 * @return
	 * @throws Exception
	 */
	protected Object getControllerObject(String unique,MappingMeta mappingMeta,Object object) throws Exception{
		String beanGetURI=springCompMicroContainer.getGetRequestURI(unique, mappingMeta.getControllerName());
		Object controllerObject=springCompMicroContainer.execute(new URI(beanGetURI), object);
		return controllerObject;
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
	
	public String getControllerGetRequestURI(String unique, String path) {
		return controllerMicroContainer.getGetRequestURI(unique, path);
	}

	public String getControllerPutRequestURI(String unique, String path) {
		return controllerMicroContainer.getPutRequestURI(unique, path);
	}

	public String getControllerDeleteRequestURI(String unique, String path) {
		return controllerMicroContainer.getDeleteRequestURI(unique, path);
	}

	public String getControllerExistRequestURI(String unique, String path) {
		return controllerMicroContainer.getExistRequestURI(unique, path);
	}

	public String getExecuteRequestURI(String unique, String path) {
		return JExecutableURIUtil.getExecuteRequestURI(unique, path, JScheme.CONTROLLER);
	}
	
}
