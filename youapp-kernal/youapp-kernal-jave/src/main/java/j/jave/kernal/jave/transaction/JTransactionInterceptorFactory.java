package j.jave.kernal.jave.transaction;

import j.jave.kernal.eventdriven.servicehub.JServiceFactorySupport;
import j.jave.kernal.eventdriven.servicehub.aop.JServiceMethodInterceptorFactory;

public class JTransactionInterceptorFactory 
extends JServiceFactorySupport<JTransactionInterceptorFactory>
implements JServiceMethodInterceptorFactory<JTransactionInterceptor,JTransactionInterceptorFactory>
{
	private JTransactionInterceptor transactionInterceptor=null;
	{
		transactionInterceptor=new JTransactionInterceptor();
//		JMatchAlwaysTransactionAttributeSource attributeSource=new JMatchAlwaysTransactionAttributeSource();
		
		JNameMatchTransactionAttributeSource attributeSource=new JNameMatchTransactionAttributeSource();
		attributeSource.addTransactionalMethod("*", new JDefaultTransactionAttribute());
		
		transactionInterceptor.setTransactionAttributeSource(attributeSource);
	}
	@Override
	public JTransactionInterceptor getInterceptor() {
		return this.transactionInterceptor;
	}

	@Override
	public boolean accept(Class<?> targetClass) {
		return targetClass.getAnnotation(JTransactional.class)!=null;
	}
	
	@Override
	protected JTransactionInterceptorFactory doGetService() {
		return this;
	}

	@Override
	public Class<?> getServiceClass() {
		return JTransactionInterceptor.class;
	}
	
	@Override
	public Class<?> getServiceImplClass() {
		return JTransactionInterceptor.class;
	}
	
	
}
