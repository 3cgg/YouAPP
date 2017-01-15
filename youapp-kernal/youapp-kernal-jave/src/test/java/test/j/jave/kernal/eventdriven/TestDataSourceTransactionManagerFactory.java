package test.j.jave.kernal.eventdriven;

import org.h2.jdbcx.JdbcConnectionPool;

import me.bunny.kernel.eventdriven.servicehub.JServiceFactorySupport;
import me.bunny.kernel.jave.service.JService;
import me.bunny.kernel.jave.transaction.JDataSourceTransactionManager;
import me.bunny.kernel.jave.transaction.eventlistener.JTransactionManagerGetEvent;
import me.bunny.kernel.jave.transaction.eventlistener.JTransactionManagerGetListener;

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
