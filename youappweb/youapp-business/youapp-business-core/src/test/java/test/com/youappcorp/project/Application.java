package test.com.youappcorp.project;

import j.jave.platform.basicwebcomp.web.youappmvc.filter.AuthenticationFilter;
import j.jave.platform.basicwebcomp.web.youappmvc.filter.MemoryHTMLFilter;
import j.jave.platform.basicwebcomp.web.youappmvc.filter.ResourceAccessFilter;
import j.jave.platform.basicwebcomp.web.youappmvc.filter.ValidPathFilter;
import j.jave.platform.basicwebcomp.web.youappmvc.listener.MvcClassPathListener;
import j.jave.platform.basicwebcomp.web.youappmvc.listener.ResourceLoaderListener;
import j.jave.platform.basicwebcomp.web.youappmvc.servlet.MvcServiceServlet;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.boot.context.embedded.ServletListenerRegistrationBean;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource("classpath:spring-context.xml")
public class Application extends SpringBootServletInitializer implements EmbeddedServletContainerCustomizer {

    public static void main(String[] args) throws Exception {
    	
    	SpringApplication springApplication=new SpringApplication(Application.class);
    	springApplication.addListeners(new ResourceLoaderListener());
        ApplicationContext ctx = springApplication.run();

        System.out.println("Let's inspect the beans provided by Spring Boot:");

        String[] beanNames = ctx.getBeanDefinitionNames();
        Arrays.sort(beanNames);
        for (String beanName : beanNames) {
            System.out.println(beanName);
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
    static class Restbucks extends SpringBootServletInitializer {

        protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
            return builder.sources(Restbucks.class);
        }

        @Bean
        public MvcServiceServlet dispatcherServlet() {
            return new MvcServiceServlet();
        }

        @Bean(name = "mvcservice-servlet-regist-bean")
        public ServletRegistrationBean dispatcherServletRegistration() {
            ServletRegistrationBean registration = new ServletRegistrationBean(dispatcherServlet());
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
        
        @Bean(name = "mvcclasspath-listener-regist-bean")
        public ServletListenerRegistrationBean<MvcClassPathListener> mvcClassPathListenerRegistration() {
        	ServletListenerRegistrationBean<MvcClassPathListener> registration 
        	= new ServletListenerRegistrationBean<MvcClassPathListener>(mvcClassPathListener());
        	registration.setOrder(ServletListenerRegistrationBean.HIGHEST_PRECEDENCE);
        	return registration;
        }
        
        @Bean
        public AuthenticationFilter authenticationFilter(){
        	return new AuthenticationFilter();
        };
        
        @Bean(name = "authentication-filter-regist-bean")
        public FilterRegistrationBean authenticationFilterRegistration() {
        	FilterRegistrationBean registration 
        	= new FilterRegistrationBean();
        	registration.setFilter(authenticationFilter());
        	Set<String> urlPatterns=new HashSet<String>();
        	urlPatterns.add("/*");
        	registration.setUrlPatterns(urlPatterns);
        	registration.setOrder(FilterRegistrationBean.HIGHEST_PRECEDENCE);
        	return registration;
        }
        
        @Bean
        public ValidPathFilter validPathFilter(){
        	return new ValidPathFilter();
        };
        
        @Bean(name = "valid-path-filter-regist-bean")
        public FilterRegistrationBean validPathFilterRegistration() {
        	FilterRegistrationBean registration 
        	= new FilterRegistrationBean();
        	registration.setFilter(validPathFilter());
        	Set<String> urlPatterns=new HashSet<String>();
        	urlPatterns.add("/*");
        	registration.setUrlPatterns(urlPatterns);
        	registration.setOrder(FilterRegistrationBean.HIGHEST_PRECEDENCE-1);
        	return registration;
        }
        
        @Bean
        public ResourceAccessFilter resourceAccessFilter(){
        	return new ResourceAccessFilter();
        };
        
        @Bean(name = "resourc-access-filter-regist-bean")
        public FilterRegistrationBean resourceAccessFilterRegistration() {
        	FilterRegistrationBean registration 
        	= new FilterRegistrationBean();
        	registration.setFilter(resourceAccessFilter());
        	Set<String> urlPatterns=new HashSet<String>();
        	urlPatterns.add("/*");
        	registration.setUrlPatterns(urlPatterns);
        	registration.setOrder(FilterRegistrationBean.HIGHEST_PRECEDENCE-2);
        	return registration;
        }
        
        
        @Bean
        public MemoryHTMLFilter memoryHTMLFilter(){
        	return new MemoryHTMLFilter();
        };
        
        @Bean(name = "memory-html-filter-regist-bean")
        public FilterRegistrationBean memoryHTMLFilterRegistration() {
        	FilterRegistrationBean registration 
        	= new FilterRegistrationBean();
        	registration.setFilter(memoryHTMLFilter());
        	Set<String> urlPatterns=new HashSet<String>();
        	urlPatterns.add("/*");
        	registration.setUrlPatterns(urlPatterns);
        	registration.setOrder(FilterRegistrationBean.HIGHEST_PRECEDENCE-3);
        	return registration;
        }
        
        
        
        
        
        
        
    }

}
