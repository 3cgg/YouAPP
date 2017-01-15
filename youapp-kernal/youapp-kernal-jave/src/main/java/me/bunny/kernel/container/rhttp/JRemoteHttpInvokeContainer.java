package me.bunny.kernel.container.rhttp;

import java.net.URI;

import me.bunny.kernel._c.logging.JLogger;
import me.bunny.kernel._c.logging.JLoggerFactory;
import me.bunny.kernel.container.JContainer;
import me.bunny.kernel.container.JContainerDelegate;
import me.bunny.kernel.container.JExecutableURIUtil;
import me.bunny.kernel.container.JExecutor;
import me.bunny.kernel.container.JIdentifier;
import me.bunny.kernel.container.JScheme;
import me.bunny.kernel.container.JURIExecuteException;
import me.bunny.kernel.http.JHttpFactoryProvider;
import me.bunny.kernel.http.JHttpType;

public class JRemoteHttpInvokeContainer implements JExecutor,JIdentifier,JContainer {

	private static final JLogger LOGGER=JLoggerFactory.getLogger(JRemoteHttpInvokeContainer.class);
	
	private String unique;
	
	private String name;
	
	private JRemoteHttpMicroContainer remoteHttpMicroContainer;
	
	protected final JRemoteHttpContainerConfig remoteHttpContainerConfig;
	
	public JRemoteHttpInvokeContainer(JRemoteHttpContainerConfig remoteHttpContainerConfig) {
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
		
			if(JScheme.REMOTE_HTTP.getValue().equals(uri.getScheme())
					&&JExecutableURIUtil.Type.EXECUTE.getValue().equals(uri.getPath())){
				try{
					
					String path=JExecutableURIUtil.getPath(uri);
					String unique=JExecutableURIUtil.getUnique(uri);
					
					if(!this.unique.equals(unique)){
						throw new JURIExecuteException("the container ["+this.unique
								+"] cannot execute this reqeust uri :"+uri.toString());
					}
					String getURI=this.remoteHttpMicroContainer.getGetRequestURI(unique, path);
					JRemoteURIInfo remoteURIInfo=(JRemoteURIInfo) remoteHttpMicroContainer.execute(new URI(getURI), object);
					
					String remoteURL=this.remoteHttpContainerConfig.getHost()+remoteURIInfo.getPath();
					
					JHttpTransEntry httpTransEntry=(JHttpTransEntry) object;
					
					if(remoteURIInfo.getHttpType()==JHttpType.GET){
						return JHttpFactoryProvider.getHttpFactory().getHttpGet()
						.setUrl(remoteURL)
						.putParams(httpTransEntry.parameters())
						.execute();
					}
					else if(remoteURIInfo.getHttpType()==JHttpType.POST){
						return JHttpFactoryProvider.getHttpFactory().getHttpPost()
						.setUrl(remoteURL)
						.setEntry(httpTransEntry.entry())
						.execute();
					}
					throw new JURIExecuteException("the remote uri ["+remoteURL+"] cannoe reach.");
				}catch(Exception e){
					LOGGER.error(e.getMessage(), e);
					if(JURIExecuteException.class.isInstance(e)){
						throw (JURIExecuteException)e;
					}
					throw new JURIExecuteException(e);
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

	public String getURLGetRequestURI(String unique, String path) {
		return remoteHttpMicroContainer.getGetRequestURI(unique, path);
	}

	public String getURLPutRequestURI(String unique, String path) {
		return remoteHttpMicroContainer.getPutRequestURI(unique, path);
	}

	public String getURLDeleteRequestURI(String unique, String path) {
		return remoteHttpMicroContainer.getDeleteRequestURI(unique, path);
	}

	public String getURLExistRequestURI(String unique, String path) {
		return remoteHttpMicroContainer.getExistRequestURI(unique, path);
	}
	
	public String getExecuteRequestURI(String unique, String path) {
		return JExecutableURIUtil.getExecuteRequestURI(unique, path, JScheme.REMOTE_HTTP);
	}
	
}
