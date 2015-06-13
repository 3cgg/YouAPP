package j.jave.framework.cxf;

import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.jaxws.JaxWsServerFactoryBean;

public class Server {

	private Server() throws Exception {  
        // START SNIPPET: publish  
		HelloWorldImpl implementor = new HelloWorldImpl();
		JaxWsServerFactoryBean svrFactory = new JaxWsServerFactoryBean();
		svrFactory.setServiceClass(HelloWorld.class);
		svrFactory.setAddress("http://localhost:8111/helloWorld");
		svrFactory.setServiceBean(implementor);
		svrFactory.getInInterceptors().add(new LoggingInInterceptor());
		svrFactory.getOutInterceptors().add(new LoggingOutInterceptor());
		svrFactory.create();
        // END SNIPPET: publish  
    }  
      
    public static void main(String[] args) throws Exception {  
        new Server();  
        System.out.println("Server ready...");  
        
        HelloUser helloUser=new HelloUser();
        
        System.out.println(helloUser.getName());  
        
        System.out.println("hello");
    }  
}
