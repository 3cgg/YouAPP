package j.jave.platform.sps.core.container;

import j.jave.kernal.container.JExecutableURIGenerator;
import j.jave.kernal.container.JExecutableURIUtil;
import j.jave.kernal.container.JRunner;
import j.jave.kernal.container.JScheme;
import j.jave.kernal.jave.exception.JOperationNotSupportedException;
import j.jave.platform.sps.multiv.ComponentVersionApplication;

import java.net.URI;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;

public class SpringCompRunner implements JRunner,JExecutableURIGenerator {

	private String unique;
	
	private String name;
	
	private final ApplicationContext applicationContext;
	
	private final ComponentVersionApplication componentVersionApplication;
	
	private final SpringCompMicroContainerConfig springCompMicroContainerConfig;
	
	public SpringCompRunner(ApplicationContext applicationContext,ComponentVersionApplication componentVersionApplication,SpringCompMicroContainerConfig springCompMicroContainerConfig) {
		this.applicationContext=applicationContext;
		this.componentVersionApplication=componentVersionApplication;
		this.springCompMicroContainerConfig=springCompMicroContainerConfig;
		this.unique=this.springCompMicroContainerConfig.unique();
		this.name=this.springCompMicroContainerConfig.name();
	}
	
	@Override
	public String unique() {
		return unique;
	}
	
	@Override
	public String name() {
		return name;
	}
	
	public ComponentVersionApplication getComponentVersionApplication() {
		return componentVersionApplication;
	}

	
	@Override
	public boolean accept(URI uri) {
		boolean accept= JScheme.BEAN.getValue().equals(uri.getScheme());
		return accept;
	}

	@Override
	public Object execute(URI uri,Object object) {
		String type=uri.getPath();
		if(JExecutableURIUtil.Type.GET.getValue().equals(type)){
			return getBean(uri, object);
		}
		if(JExecutableURIUtil.Type.EXIST.getValue().equals(type)){
			return existBean(uri, object);
		}
		throw new JOperationNotSupportedException(" the uri ["+uri.toString()+"] not supported."); 
	}
	
	private boolean existBean(URI uri,Object object){
		String beanName=getBeanName(uri);
		try{
			applicationContext.getBean(beanName);
			return true;
		}catch(NoSuchBeanDefinitionException e){
			return false;
		}catch(BeansException e){
			return false;
		}
	}
	
	private Object getBean(URI uri,Object object){
		String beanName=getBeanName(uri);
		return applicationContext.getBean(beanName);
	}

	private String getBeanName(URI uri) {
		return JExecutableURIUtil.getPath(uri);
	}
	
	public ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	@Override
	public String getGetRequestURI(String unique, String path) {
		return JExecutableURIUtil.getGetRequestURI(unique, path, JScheme.BEAN);
	}

	@Override
	public String getPutRequestURI(String unique, String path) {
		return JExecutableURIUtil.getPutRequestURI(unique, path, JScheme.BEAN);
	}

	@Override
	public String getDeleteRequestURI(String unique, String path) {
		return JExecutableURIUtil.getDeleteRequestURI(unique, path, JScheme.BEAN);
	}

	@Override
	public String getExistRequestURI(String unique, String path) {
		return JExecutableURIUtil.getExistRequestURI(unique, path, JScheme.BEAN);
	}
	
}
