package j.jave.platform.basicwebcomp.web.youappmvc.container;

import j.jave.kernal.container.JContainer;
import j.jave.kernal.container.JContainerDelegate;
import j.jave.kernal.container.rhttp.JRemoteHttpInvokeContainer;

public class BaseHttpURLExpose implements HttpURLExpose {

	private JContainerDelegate containerDelegate=JContainerDelegate.get();

	private JContainer container;
	
	private String unique;
	
	private String path;
	
	public BaseHttpURLExpose(String unique,String path) {
		this.unique=unique;
		this.path=path;
		this.container=containerDelegate.getContainer(unique);
	}
	
	@Override
	public String getExistRequestURI() {
		if(JRemoteHttpInvokeContainer.class.isInstance(container)){
			return new RemoteRequestInvokeContainerExpose(container).getExistRequestURI();
		}
		else if(InnerHttpInvokeContainer.class.isInstance(container)){
			return new RequestInvokeContainerExpose(container).getExistRequestURI();
		}
		return null;
	}

	@Override
	public String getExecuteRequestURI() {
		if(JRemoteHttpInvokeContainer.class.isInstance(container)){
			return new RemoteRequestInvokeContainerExpose(container).getExecuteRequestURI();
		}
		else if(InnerHttpInvokeContainer.class.isInstance(container)){
			return new RequestInvokeContainerExpose(container).getExecuteRequestURI();
		}
		return null;
	}
	
	public class RemoteRequestInvokeContainerExpose implements HttpURLExpose{
		
		private JRemoteHttpInvokeContainer remoteRequestInvokeContainer;
		
		public RemoteRequestInvokeContainerExpose(JContainer container) {
			this.remoteRequestInvokeContainer=(JRemoteHttpInvokeContainer)container;
		}
		
		public String getExistRequestURI(){
			return remoteRequestInvokeContainer.getURLExistRequestURI(unique, path);
		}
		
		public String getExecuteRequestURI(){
			return remoteRequestInvokeContainer.getExecuteRequestURI(unique, path);
		}
		
	}
	
	public class RequestInvokeContainerExpose implements HttpURLExpose{
		
		private InnerHttpInvokeContainer requestInvokeContainer;
		
		public RequestInvokeContainerExpose(JContainer container) {
			this.requestInvokeContainer=(InnerHttpInvokeContainer)container;
		}
		
		public String getExistRequestURI(){
			return requestInvokeContainer.getControllerExistRequestURI(unique, path);
		}
		
		public String getExecuteRequestURI(){
			return requestInvokeContainer.getExecuteRequestURI(unique, path);
		}
		
	}
	

}
