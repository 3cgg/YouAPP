package test.j.jave.kernal.eventdriven;

import org.h2.jdbcx.JdbcConnectionPool;

import j.jave.kernal.eventdriven.servicehub.JServiceFactorySupport;
import j.jave.kernal.jave.service.JService;
import j.jave.kernal.jave.transaction.JDataSourceTransactionManager;
import j.jave.kernal.jave.transaction.eventlistener.JTransactionManagerGetEvent;
import j.jave.kernal.jave.transaction.eventlistener.JTransactionManagerGetListener;

public class TestDataSourceTransactionManagerFactory 
extends JServiceFactorySupport<TestDataSourceTransactionManagerFactory>
implements JTransactionManagerGetListener ,JService{

	@Override
	public Object trigger(JTransactionManagerGetEvent event) {
		JDataSourceTransactionManager dataSourceTransactionManager=new JDataSourceTransactionManager();
		JdbcConnectionPool connectionPool=JdbcConnectionPool.create(
	             "jdbc:h2:d:/h2db/test", "sa", "sa");
		dataSourceTransactionManager.setDataSource(connectionPool);
		return dataSourceTransactionManager;
	}
	
	@Override
	protected TestDataSourceTransactionManagerFactory doGetService() {
		return this;
	}
}
