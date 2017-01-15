package j.jave.platform.standalone.client.netty.http;

import java.net.URI;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

import me.bunny.kernel.jave.logging.JLogger;
import me.bunny.kernel.jave.logging.JLoggerFactory;

class ConnectionPoolService {

	private static JLogger logger=JLoggerFactory.getLogger(ConnectionPoolService.class);
	
	private static ConcurrentHashMap<String, ConnectionService> 
	connectionServices=new ConcurrentHashMap<String, ConnectionService>();
	
	private static final ReentrantLock initializeConnectionLock=new ReentrantLock();
	
	public static ConnectionService get(String url) throws Exception{
		URI uri=new URI(url);
		String unique=DefaultConnectionService.getScheme(uri)+"//"+
				DefaultConnectionService.getHost(uri)+":"+
				DefaultConnectionService.getPort(uri);
		
		if(connectionServices.containsKey(unique)){
			if(logger.isDebugEnabled()){
				logger.debug("got keep-alive connection from pool : "+url);
			}
			ConnectionService connectionService= connectionServices.get(unique);
			if(connectionService.isActive()){
				return connectionService;
			}
		}
		initializeConnectionLock.lockInterruptibly();
		try{
			if(connectionServices.containsKey(unique)){
				ConnectionService connectionService= connectionServices.get(unique);
				if(connectionService.isActive()){
					return connectionService;
				}
			}
			connectionServices.remove(unique);
			ConnectionService connectionService=  new KeepAliveConnectionService(url);
			connectionService.connect(url);
			connectionService.await();
			connectionServices.put(unique, connectionService);
			return connectionService;
		}finally{
			if(initializeConnectionLock.isHeldByCurrentThread()){
				initializeConnectionLock.unlock();
			}
		}
	}
    
}