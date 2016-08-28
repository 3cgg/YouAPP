/**
 * 
 */
package j.jave.kernal.memcached;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;

import net.spy.memcached.MemcachedClient;
import net.spy.memcached.OperationTimeoutException;

/**
 * 	delegate to {@link MemcachedClient} 
 * @author J
 *
 */
public class JMemcached {
	
	private MemcachedClient memcachedClient;
	
	private Object sync=new Object();
	
	public JMemcached(){}
	
	@SuppressWarnings("unused")
	private List<InetSocketAddress> addrs=null;
	
	public JMemcached(List<InetSocketAddress> addrs){
		try {
			this.memcachedClient = new MemcachedClient(addrs);
			this.addrs=addrs;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	public void init(List<InetSocketAddress> addrs){
		try {
			synchronized (sync) {
				if(memcachedClient==null) 
					memcachedClient = new MemcachedClient(addrs);
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	
	public Object set(String key , int expiry, Object value){
		
		Object pre=memcachedClient.get(key);
		memcachedClient.set(key, expiry, value);
		return pre;
	}
	
	public Object get(String key){
		try{
			return memcachedClient.get(key);
		}catch(OperationTimeoutException e){
			throw e;
		}
	}

	public void add(String key , int expiry, Object value){
		
		memcachedClient.add(key, expiry, value);
	}
	
	public void delete(String key){
		memcachedClient.delete(key);
	}
	
	public String getLocatorServer(String key){
		// TODO 
		return "";
	}
	
	
	
	public static void main(String[] args) throws Exception {
		
		List<InetSocketAddress> addrs=new ArrayList<InetSocketAddress>();
		addrs.add(new InetSocketAddress("127.0.0.1", 11220));
		addrs.add(new InetSocketAddress("127.0.0.1", 11221));
		MemcachedClient  MemcachedClient = new MemcachedClient(addrs);
		
		MemcachedClient.set("kl", 0, "from here");
		
		Object obj = MemcachedClient.get("zhongjin");
		
		System.out.println(obj);
		
		
	}
	
}
