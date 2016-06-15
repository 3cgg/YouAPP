package j.jave.platform.mybatis.plugin.pageable;

import j.jave.kernal.jave.model.JPageImpl;
import j.jave.platform.basicwebcomp.core.model.SimplePageRequest;

import java.lang.reflect.Method;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.data.domain.Pageable;

public class QueryExecutorMethodInterceptor implements MethodInterceptor {

	private String countPrefix=PageableProperties.COUNT_FOR_PAGEABLE_METHOD_PREFIX;
	
	public void setCountPrefix(String countPrefix) {
		this.countPrefix = countPrefix;
	}
	
	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		Class<?>[] paramTypes= invocation.getMethod().getParameterTypes();
		boolean isPageable=false;
		if(paramTypes.length>0){
			String methodName=invocation.getMethod().getName();
			boolean isCountFor_=methodName.startsWith(countPrefix);
			for(Class<?> clazz:paramTypes){
				if(Pageable.class==clazz&&!isCountFor_){
					isPageable=true;
					break;
				}
			}
		}
		SimplePageRequest pageable=null;
		if(isPageable){
			String methodName=invocation.getMethod().getName();
			String countMethodName=countPrefix+methodName;
			
			Method countMethod=invocation.getThis().getClass().getMethod(countMethodName, invocation.getMethod().getParameterTypes());
			if(countMethod!=null){
				int count= (int) countMethod.invoke(invocation.getThis(), invocation.getArguments());
				
				Object[] objects= invocation.getArguments();
				Class<?>[] classes=invocation.getMethod().getParameterTypes();
				for(int i=0;i<classes.length;i++){
					Class<?> clazz=classes[i];
					if(Pageable.class==clazz){
						SimplePageRequest pageRequest= ((SimplePageRequest)objects[i]);
						int pageNumber=pageRequest.getPageNumber();
						int pageSize=pageRequest.getPageSize();
						int tempPageNumber=JPageImpl.caculateTotalPageNumber(count, pageSize);
						pageNumber=pageNumber>tempPageNumber?tempPageNumber:pageNumber;
						pageRequest=new SimplePageRequest(pageNumber, pageSize);
						pageRequest.setCount(count);
						pageable=pageRequest;
						objects[i]=pageable;
						break;
					}
				}
			}
		}
		Object obj= invocation.proceed();
		return obj;
	}

}
