/**
 * 
 */
package j.jave.framework.servicehub.memcached;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;

import net.spy.memcached.MemcachedClient;

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
	
	
	public void set(String key , int expiry, Object value){
		memcachedClient.set(key, expiry, value);
	}
	
	public Object get(String key){
		return memcachedClient.get(key);
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
