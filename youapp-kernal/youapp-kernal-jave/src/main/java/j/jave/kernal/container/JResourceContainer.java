package j.jave.kernal.container;

import j.jave.kernal.container.JExecutableURIUtil.Type;
import j.jave.kernal.container.JResourceContainer.JInnerResourceURIParserService;
import j.jave.kernal.container._resource.JResourceURIParserService;
import j.jave.kernal.eventdriven.servicehub.JServiceHubDelegate;
import j.jave.kernal.jave.support._resource.JResourceNotSupportedException;

import java.net.URI;

public class JResourceContainer implements JExecutor, JContainer,JResourceURIParserGetter<JInnerResourceURIParserService> {

	private JResourceContainerConfig resourceContainerConfig;
	
	private JResourceMicroContainer resourceMicroContainer;
	
	private JInnerResourceURIParserService resourceURIParserService
	=new JInnerResourceURIParserService();
	
	public JResourceContainer(JResourceContainerConfig resourceContainerConfig) {
		this.resourceContainerConfig = resourceContainerConfig;
	}

	@Override
	public void initialize() {
		JResourceMicroContainerConfig resourceMicroContainerConfig
		=new JResourceMicroContainerConfig();
		resourceMicroContainerConfig.setName(name());
		resourceMicroContainerConfig.setUnique(unique());
		this.resourceMicroContainer=new JResourceMicroContainer(resourceMicroContainerConfig);
		
		resourceMicroContainer.initialize();
		
		JContainerDelegate.get().register(this);
	}

	@Override
	public void destroy() {
		resourceMicroContainer.destroy();
	}

	@Override
	public void restart() {
		resourceMicroContainer.restart();
	}

	@Override
	public String unique() {
		return resourceContainerConfig.unique();
	}

	@Override
	public String name() {
		return resourceContainerConfig.name();
	}

	@Override
	public boolean accept(URI uri) {
		return true;
	}

	@Override
	public Object execute(URI uri, Object object) {
		URI executableURI=uri;
		if(!JExecutableURIUtil.isWrapped(uri)){
			String tempURIStr=uri.toString();
			try{
				if(tempURIStr.startsWith(JResourceURIParserService.CLASS_PATH_PREFIX)){
					executableURI=resourceURIParser().parse4ClassPath(tempURIStr);
				}
				else{
					executableURI=resourceURIParser().parse(new URI(tempURIStr));
				}
			}catch(Exception e){
				throw new JResourceNotSupportedException(e);
			}
		}
		return resourceMicroContainer.execute(executableURI, object);
	}

	@Override
	public JInnerResourceURIParserService resourceURIParser() {
		return resourceURIParserService;
	}

	public class JInnerResourceURIParserService implements JResourceURIParser{
		
		private final JResourceURIParserService
		resourceURIParserService=JServiceHubDelegate.get().getService(this, JResourceURIParserService.class);
		
		public URI parse(URI uri,Type type) throws Exception{
			return resourceURIParserService.parse(uri, JResourceContainer.this.unique(), type);
		}
		
		/**
		 * convenience to {@link #parse(URI, String, Type.GET)}
		 * @param uri
		 * @param container
		 * @return
		 * @throws Exception
		 */
		public URI parse(URI uri) throws Exception{
			return parse(uri, Type.GET);
		}
		
		public URI parse4Bean(String uri) throws Exception{
			return parse4Bean(uri,Type.GET);
		}
		
		public URI parse4Bean(String uri,Type type) throws Exception{
			return resourceURIParserService.parse4Bean(uri, JResourceContainer.this.unique(), Type.GET);
		}
		
		public URI parse4ClassPath(String uri) throws Exception{
			return parse4ClassPath(uri, Type.GET);
		}
		
		public URI parse4ClassPath(String uri,Type type) throws Exception{
			return resourceURIParserService.parse4ClassPath(uri, JResourceContainer.this.unique(), Type.GET);
		}
		
		public URI parse4Controller(String uri) throws Exception{
			return parse4Controller(uri, Type.GET);
		}
		
		public URI parse4Controller(String uri,Type type) throws Exception{
			return resourceURIParserService.parse4Controller(uri, JResourceContainer.this.unique(), Type.GET);
		}
		
		public URI parse4RemoteHttp(String uri) throws Exception{
			return parse4RemoteHttp(uri, Type.GET);
		}
		
		public URI parse4RemoteHttp(String uri,Type type) throws Exception{
			return resourceURIParserService.parse4RemoteHttp(uri, JResourceContainer.this.unique(), Type.GET);
			
		}
	}
	
	
}
