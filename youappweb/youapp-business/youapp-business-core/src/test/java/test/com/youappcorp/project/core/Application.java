package test.com.youappcorp.project.core;

import j.jave.platform.basicwebcomp.web.youappmvc.servlet.MvcServiceServlet;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
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
        ApplicationContext ctx = SpringApplication.run(Application.class, args);

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

    }

}
