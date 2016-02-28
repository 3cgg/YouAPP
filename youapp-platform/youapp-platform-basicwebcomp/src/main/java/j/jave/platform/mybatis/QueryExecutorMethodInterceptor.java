package j.jave.platform.mybatis;

import j.jave.kernal.jave.model.JPageImpl;

import java.lang.reflect.Method;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

public class QueryExecutorMethodInterceptor implements MethodInterceptor {

	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		Class<?>[] paramTypes= invocation.getMethod().getParameterTypes();
		boolean isPageable=false;
		if(paramTypes.length>0){
			for(Class<?> clazz:paramTypes){
				if(Pageable.class==clazz){
					isPageable=true;
				}
			}
		}
		Pageable pageable=null;
		int count=0;
		if(isPageable){
			String countPrefix="countFor_";
			String methodName=invocation.getMethod().getName();
			String countMethodName=countPrefix+methodName;
			
			Method countMethod=invocation.getThis().getClass().getMethod(countMethodName, invocation.getMethod().getParameterTypes());
			count= (int) countMethod.invoke(invocation.getThis(), invocation.getArguments());
			
			Object[] objects= invocation.getArguments();
			Class<?>[] classes=invocation.getMethod().getParameterTypes();
			for(int i=0;i<classes.length;i++){
				Class<?> clazz=classes[i];
				if(Pageable.class==clazz){
					PageRequest pageRequest= ((PageRequest)objects[i]);
					int pageNumber=pageRequest.getPageNumber();
					int pageSize=pageRequest.getPageSize();
					int tempPageNumber=JPageImpl.caculateTotalPageNumber(count, pageSize);
					pageNumber=pageNumber>tempPageNumber?tempPageNumber:pageNumber;
					pageable=new PageRequest(pageNumber, pageSize);
					objects[i]=pageable;
				}
			}
		}
		Object obj= invocation.proceed();
		if(isPageable){
			obj=new PageImpl(((PageImpl) obj).getContent(),pageable,count);
		}
		return obj;
	}

}
