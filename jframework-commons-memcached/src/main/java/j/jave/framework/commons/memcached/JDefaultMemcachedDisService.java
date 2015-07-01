/**
 * 
 */
package j.jave.framework.commons.memcached;

import j.jave.framework.commons.logging.JLogger;
import j.jave.framework.commons.logging.JLoggerFactory;
import j.jave.framework.commons.memcached.eventdriven.JMemcachedDisAddEvent;
import j.jave.framework.commons.memcached.eventdriven.JMemcachedDisDeleteEvent;
import j.jave.framework.commons.memcached.eventdriven.JMemcachedDisGetEvent;
import j.jave.framework.commons.memcached.eventdriven.JMemcachedDisSetEvent;
import j.jave.framework.commons.support.JObjectLoop;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;


/**
 * 
 * 默认的存储机制，有一个后备的备份存储
 * @author J
 *
 */
public class JDefaultMemcachedDisService implements JMemcachedDisService{
	
	private static final JLogger LOGGER=JLoggerFactory.getLogger(JDefaultMemcachedDisService.class);
	
	private JObjectLoop<Integer, JMemcached> store=new JObjectLoop<Integer, JMemcached>();

	private JObjectLoop<Integer, JMemcached> backupStore=new JObjectLoop<Integer, JMemcached>();
	
	private Object sync=new Object();
	
	/**
	 * constructor 
	 * @param storeAddes  
	 * @param backupAddes
	 */
	public JDefaultMemcachedDisService(Map<String, List<String>> storeAddes,Map<String, List<String>> backupAddes){
		try {
			synchronized (sync) {
				if(storeAddes!=null){
					produce(store, storeAddes);
				}
				if(backupAddes!=null){
					produce(backupStore, storeAddes);
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * return first element. 
	 * @param store
	 * @param storeAddes
	 */
	private void produce(JObjectLoop<Integer, JMemcached> store,Map<String, List<String>> storeAddes) {
		for (Iterator<Entry<String, List<String>>> iterator = storeAddes.entrySet().iterator(); iterator
				.hasNext();) {
			Entry<String, List<String>> entry = iterator.next();
			String keyString="";
			List<InetSocketAddress> inetSocketAddresses =new ArrayList<InetSocketAddress>();
			List<String> addes=entry.getValue();
			for(String add:addes){
				keyString=keyString+","+add;
				String[] adds=add.split(":");
				inetSocketAddresses.add(new InetSocketAddress(adds[0], Integer.valueOf(adds[1])));
			}
			keyString=keyString.replaceFirst(",", "");
			LOGGER.info(" one group("+entry.getKey()+") contains "+keyString);
			store.put(keyString.hashCode(), new JMemcached(inetSocketAddresses));
		}
	}
	
	public Object set(String key , int expiry, Object value){
		Object object=null;
		JMemcached jMemcached=null;
		jMemcached = getStoreJMemcached(key);
		if(jMemcached!=null)
			object=jMemcached.set(key, expiry, value);
		
		jMemcached = getBackupJMemcached(key);
		if(jMemcached!=null)
			jMemcached.set(key, expiry, value);
		return object;
	}

	
	private JMemcached getStoreJMemcached(String value) {
		return store.get(value.hashCode());
	}
	
	private JMemcached getBackupJMemcached(String value) {
		return backupStore.get(value.hashCode());
	}
	
	public Object get(String key){
		
		JMemcached jMemcached=null;
		jMemcached = getStoreJMemcached(key);
		Object obj=null;
		if(jMemcached!=null)
			obj=jMemcached.get(key);
		if(obj==null){
			jMemcached = getBackupJMemcached(key);
			if(jMemcached!=null)
				obj=jMemcached.get(key);
			
			if(obj!=null){
				jMemcached = getStoreJMemcached(key);
				if(jMemcached!=null)
					jMemcached.set(key, 0, obj);
			}
		}
		return obj;
	}

	public void add(String key , int expiry, Object value){
		JMemcached jMemcached=null;
		jMemcached = getStoreJMemcached(key);
		if(jMemcached!=null)
		jMemcached.add(key, expiry, value);
		
		jMemcached = getBackupJMemcached(key);
		if(jMemcached!=null)
		jMemcached.add(key, expiry, value);
	}
	
	public void delete(String key){
		JMemcached jMemcached=null;
		jMemcached = getStoreJMemcached(key);
		if(jMemcached!=null)
		jMemcached.delete(key);
		
		jMemcached = getBackupJMemcached(key);
		if(jMemcached!=null)
		jMemcached.delete(key);
	}

	@Override
	public Object trigger(JMemcachedDisGetEvent event) {
		return get(event.getKey());
	}

	@Override
	public Object trigger(JMemcachedDisSetEvent event) {
		return set(event.getKey(), event.getExpiry(), event.getValue());
	}

	@Override
	public Object trigger(JMemcachedDisDeleteEvent event) {
		delete(event.getKey());
		return true;
	}

	@Override
	public Object trigger(JMemcachedDisAddEvent event) {
		add(event.getKey(), event.getExpiry(), event.getValue());
		return true;
	}

	
	
	
}
