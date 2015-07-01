package j.jave.framework.cxf.rs;

import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.apache.cxf.jaxrs.provider.json.JSONProvider;

public class RSServer {

	 public static void main(String[] args) throws Exception {  
	        JAXRSServerFactoryBean factoryBean = new JAXRSServerFactoryBean();  
	        factoryBean.getInInterceptors().add(new LoggingInInterceptor());  
	        factoryBean.getOutInterceptors().add(new LoggingOutInterceptor());  
	        factoryBean.setResourceClasses(RSServiceImpl.class);  
	        factoryBean.setAddress("http://localhost:8111/ws/jaxrs");  
	        factoryBean.setProvider(new JSONProvider());
	        factoryBean.create();  
	        System.out.println("end..."); 
	    }  
	 
}
