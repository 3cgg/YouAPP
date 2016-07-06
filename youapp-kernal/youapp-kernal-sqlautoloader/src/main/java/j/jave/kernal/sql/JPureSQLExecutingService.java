package j.jave.kernal.sql;

import j.jave.kernal.eventdriven.servicehub.JServiceFactorySupport;

import java.sql.Connection;
import java.util.Map;

public class JPureSQLExecutingService
extends JServiceFactorySupport<JPureSQLExecutingService>
implements JSQLExecutingService {

	private Connection connection;
	
	public void prepare(Connection connection){
		this.connection=connection;
	}
	
	@Override
	public void execute(String sql, Map<String, Object> params) {
		
	}

	@Override
	public void execute(String sql) {
		// TODO Auto-generated method stub

	}
	
	@Override
	public JPureSQLExecutingService getService() {
		return new JPureSQLExecutingService();
	}
	
	

}
