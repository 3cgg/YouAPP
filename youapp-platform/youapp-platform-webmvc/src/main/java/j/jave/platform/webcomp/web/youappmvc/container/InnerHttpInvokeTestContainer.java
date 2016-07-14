package j.jave.platform.webcomp.web.youappmvc.container;

import j.jave.kernal.container.JContainer;
import j.jave.kernal.container.JContainerDelegate;
import j.jave.kernal.container.JExecutableURIUtil;
import j.jave.kernal.container.JExecutor;
import j.jave.kernal.container.JIdentifier;
import j.jave.kernal.container.JTargetContainer;
import j.jave.kernal.container.JVirtual;
import j.jave.kernal.container.scheme.JScheme;
import j.jave.kernal.jave.json.JJSON;
import j.jave.kernal.jave.logging.JLogger;
import j.jave.kernal.jave.logging.JLoggerFactory;
import j.jave.platform.data.web.mapping.MappingMeta;
import j.jave.platform.sps.multiv.ComponentVersionApplication;
import j.jave.platform.webcomp.web.model.ResponseModel;
import j.jave.platform.webcomp.web.youappmvc.HttpContext;
import j.jave.platform.webcomp.web.youappmvc.controller.ControllerExecutor;

import java.net.URI;

/**
 * the container is only for testing.
 * @author J
 *
 */
@JVirtual
class InnerHttpInvokeTestContainer implements JExecutor,JIdentifier,JContainer,JTargetContainer {
	
	private static final JLogger LOGGER=JLoggerFactory.getLogger(InnerHttpInvokeTestContainer.class);
	
	private final InnerHttpInvokeContainer innerHttpInvokeContainer;
	
	private String unique;
	
	private String name;
	
	protected final InnerHttpInvokeTestContainerConfig testContainerConfig;
	
	private final ComponentVersionApplication componentVersionApplication;
	
	public InnerHttpInvokeTestContainer(
			InnerHttpInvokeTestContainerConfig testContainerConfig,
			ComponentVersionApplication componentVersionApplication,
			InnerHttpInvokeContainer innerHttpInvokeContainer ) {
		this.testContainerConfig=testContainerConfig;
		this.componentVersionApplication=componentVersionApplication;
		testContainerConfig.setName(componentVersionApplication.name());
		testContainerConfig.setUnique(componentVersionApplication.unique());
		this.name=testContainerConfig.getName();
		this.unique=testContainerConfig.getUnique();
		this.controllerObjectGetter=testContainerConfig.getControllerObjectGetter();
		
		this.innerHttpInvokeContainer=innerHttpInvokeContainer;
	}

	private ControllerObjectGetter controllerObjectGetter=null;
	
	protected Object getControllerObject(String unique,
			MappingMeta mappingMeta, Object object) throws Exception {
		if(LOGGER.isDebugEnabled()){
			LOGGER.debug("------------GET TEST OBJECT--------->"+JJSON.get().formatObject(mappingMeta));
		}
		return controllerObjectGetter.getObjet(mappingMeta);
	}

	public String getUnique() {
		return unique;
	}

	public void setUnique(String unique) {
		this.unique = unique;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public void initialize() {
		JContainerDelegate.get().register(this);
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void restart() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String unique() {
		return unique;
	}

	@Override
	public String name() {
		return name;
	}

	@Override
	public boolean accept(URI uri) {
		return innerHttpInvokeContainer.accept(uri);
	}

	@Override
	public Object execute(URI uri, Object object) {

		
		if(JScheme.CONTROLLER.getValue().equals(uri.getScheme())
				&&JExecutableURIUtil.Type.EXECUTE.getValue().equals(uri.getPath())){
			try{
				String path=JExecutableURIUtil.getPath(uri);
				String unique=JExecutableURIUtil.getUnique(uri);
				String controllerGetURI=innerHttpInvokeContainer.getControllerGetRequestURI(unique, path);
				MappingMeta mappingMeta= (MappingMeta) innerHttpInvokeContainer.execute(new URI(controllerGetURI), object);
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
		
		return innerHttpInvokeContainer.execute(uri, object);
	}
	
	@Override
	public JContainer getTargetContainer() {
		return innerHttpInvokeContainer;
	}
	
	
	
	
	
	
	
	
	
	
	
}
