package j.jave.platform.webcomp.web.youappmvc.container;

import me.bunny.kernel.container.JContainer;
import me.bunny.kernel.container.JContainerDelegate;
import me.bunny.kernel.container.rhttp.JRemoteHttpInvokeContainer;

/**
 * the basic http url exposer for {@link InnerHttpInvokeContainer} OR {@link JRemoteHttpInvokeContainer}
 * @author JIAZJ
 * @see InnerHttpInvokeContainer
 * @see JRemoteHttpInvokeContainer
 */
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
	
	private HttpURLExpose get(){
		if(JRemoteHttpInvokeContainer.class.isInstance(container)){
			return new RemoteRequestInvokeContainerExpose(container);
		}
		else if(InnerHttpInvokeContainer.class.isInstance(container)){
			return new RequestInvokeContainerExpose(container);
		}
		else{
			return new EmptyHttpURLExpose();
		}
	}
	
	@Override
	public String getExistRequestURI() {
		return get().getExistRequestURI();
	}

	@Override
	public String getExecuteRequestURI() {
		return get().getExecuteRequestURI();
	}
	
	@Override
	public String getGetRequestURI() {
		return get().getGetRequestURI();
	}
	
	@Override
	public String getPutRequestURI() {
		return get().getPutRequestURI();
	}
	
	@Override
	public String getDeleteRequestURI() {
		return get().getDeleteRequestURI();
	}
	
	public class EmptyHttpURLExpose implements HttpURLExpose{

		@Override
		public String getExistRequestURI() {
			return null;
		}

		@Override
		public String getExecuteRequestURI() {
			return null;
		}

		@Override
		public String getPutRequestURI() {
			return null;
		}

		@Override
		public String getGetRequestURI() {
			return null;
		}

		@Override
		public String getDeleteRequestURI() {
			return null;
		}
		
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
		
		@Override
		public String getGetRequestURI() {
			return remoteRequestInvokeContainer.getURLGetRequestURI(unique, path);
		}
		
		@Override
		public String getPutRequestURI() {
			return remoteRequestInvokeContainer.getURLPutRequestURI(unique, path);
		}
		
		@Override
		public String getDeleteRequestURI() {
			return remoteRequestInvokeContainer.getURLDeleteRequestURI(unique, path);
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
		
		@Override
		public String getGetRequestURI() {
			return requestInvokeContainer.getControllerGetRequestURI(unique, path);
		}
		
		@Override
		public String getPutRequestURI() {
			return requestInvokeContainer.getControllerPutRequestURI(unique, path);
		}
		
		@Override
		public String getDeleteRequestURI() {
			return requestInvokeContainer.getControllerDeleteRequestURI(unique, path);
		}
		
	}
	

}
