package test.com.youappcorp.project;

import j.jave.platform.basicwebcomp.web.listener.SimpleServiceRegisterContextListener;
import j.jave.platform.basicwebcomp.web.youappmvc.listener.MvcClassPathListener;
import j.jave.platform.basicwebcomp.web.youappmvc.servlet.MvcServiceServlet;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.ServletListenerRegistrationBean;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource("classpath:spring-context.xml")
public class Application extends SpringBootServletInitializer implements EmbeddedServletContainerCustomizer {

    public static void main(String[] args) throws Exception {
    	
    	SpringApplication springApplication=new SpringApplication(Application.class);
        ApplicationContext ctx = springApplication.run();

        System.out.println("Let's inspect the beans provided by Spring Boot:");

        String[] beanNames = ctx.getBeanDefinitionNames();
        Arrays.sort(beanNames);
        for (String beanName : beanNames) {
            System.out.println(beanName+"->"+ctx.getBean(beanName).getClass().getName());
        }
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(Application.class);
    }

    @Override
    public void customize(ConfigurableEmbeddedServletContainer container) {
    	container.setContextPath("/youapp");
        container.setPort(8689);
    }
    
    
    @Configuration
    static class Restbucks extends SpringBootServletInitializer implements ApplicationContextAware {

    	ApplicationContext ctx;
    	
    	@Override
    	public void setApplicationContext(ApplicationContext applicationContext)
    			throws BeansException {
    		this.ctx=applicationContext;
    	}
    	
        protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
            return builder.sources(Restbucks.class);
        }

        /*
         *  use "dispatcherServlet" to replace the default spring mvc , if not set the bean name, 
         *  the method name is used as the bean name.
         */
        @Bean(name={"mvcServiceServlet"}) 
        public MvcServiceServlet mvcServiceServlet() {
            return new MvcServiceServlet();
        }

        @Bean(name = "mvcservice-servlet-regist-bean")
        public ServletRegistrationBean dispatcherServletRegistration() {
            ServletRegistrationBean registration = new ServletRegistrationBean(mvcServiceServlet());
            registration.addUrlMappings("/extapi/*");
            Map<String,String> params = new HashMap<String,String>();
            params.put("org.atmosphere.servlet","org.springframework.web.servlet.DispatcherServlet");
            params.put("contextClass","org.springframework.web.context.support.AnnotationConfigWebApplicationContext");
            params.put("contextConfigLocation","net.org.selector.animals.config.ComponentConfiguration");
            registration.setInitParameters(params);
            return registration;
        }
       
        @Bean
        public MvcClassPathListener mvcClassPathListener(){
            return new MvcClassPathListener();
        }
        
        @Bean(name = "mvcclasspath-listener-register-bean")
        public ServletListenerRegistrationBean<MvcClassPathListener> mvcClassPathListenerRegistration() {
        	ServletListenerRegistrationBean<MvcClassPathListener> registration 
        	= new ServletListenerRegistrationBean<MvcClassPathListener>(mvcClassPathListener());
        	registration.setOrder(ServletListenerRegistrationBean.HIGHEST_PRECEDENCE);
        	return registration;
        }
        
        @Bean
        public SimpleServiceRegisterContextListener simpleServiceRegisterContextListener(){
            return new SimpleServiceRegisterContextListener();
        }
        
        @Bean(name = "serviceregister-listener-register-bean")
        public ServletListenerRegistrationBean<SimpleServiceRegisterContextListener> simpleServiceRegisterContextListenerRegistration() {
        	ServletListenerRegistrationBean<SimpleServiceRegisterContextListener> registration 
        	= new ServletListenerRegistrationBean<SimpleServiceRegisterContextListener>(simpleServiceRegisterContextListener());
        	registration.setOrder(ServletListenerRegistrationBean.HIGHEST_PRECEDENCE-1);
        	return registration;
        }
        
        
    }

}
