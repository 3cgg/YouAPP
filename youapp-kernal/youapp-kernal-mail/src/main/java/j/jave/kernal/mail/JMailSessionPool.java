package j.jave.kernal.mail;

import j.jave.kernal.jave.logging.JLogger;
import j.jave.kernal.jave.logging.JLoggerFactory;
import j.jave.kernal.jave.reflect.JClassUtils;
import j.jave.kernal.jave.security.JMD5Cipher;
import j.jave.kernal.jave.service.JCacheService;
import j.jave.kernal.jave.support.JDefaultHashCacheService;
import j.jave.kernal.jave.utils.JAssert;
import j.jave.kernal.jave.utils.JCollectionUtils;
import j.jave.kernal.jave.utils.JPropertiesUtils;
import j.jave.kernal.jave.utils.JStringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.locks.ReentrantLock;

import javax.mail.Session;

/**
 * mail session pool
 * @author J
 *
 */
public class JMailSessionPool {
	
	private static JLogger logger=JLoggerFactory.getLogger(JMailSessionPool.class);
	
	private JCacheService cacheService;
	
	private List<String> keys=new ArrayList<String>();

	private ReentrantLock lock=new ReentrantLock();
	
	private static JMailSessionPool mailSessionPool;
	
	private JMailSessionPool() {
	} 
	
	/**
	 * inner hash table cache
	 * @author J
	 *
	 */
	static class HashCache extends JDefaultHashCacheService {

		private static HashMap<String, JMailSession> sessions=new HashMap<String, JMailSession>();
		
		@Override
		public JMailSession putNeverExpired(String key, Object object) {
			return sessions.put(key, (JMailSession) object);
		}

		@Override
		public JMailSession get(String key) {
			return sessions.get(key);
		}

		@Override
		public JMailSession remove(String key) {
			JMailSession session =get(key);
			sessions.remove(key);
			return session;
		}
		
		@Override
		public boolean contains(String key) {
			return sessions.containsKey(key);
		}
		
	}
	
	public static JMailSessionPool get(){
		if(mailSessionPool==null){
			synchronized (JMailSessionPool.class) {
				if(mailSessionPool==null){
					mailSessionPool=new JMailSessionPool();
					try{
						String cacheClass=JPropertiesUtils.getKey("j.jave.framework.commons.mail.JMailSessionPool.cache", "commons-mail.properties");
						if(JStringUtils.isNotNullOrEmpty(cacheClass)){
							logger.info("load mail session cache factory : "+cacheClass);
							JMailSessionCacheFactory mailSessionCacheFactory=(JMailSessionCacheFactory) JClassUtils.load(cacheClass).newInstance();	
							mailSessionPool.cacheService=mailSessionCacheFactory.getCacheService();
						}
						else{
							logger.warn("no custom session mail cache,to use the default hashtable cache.");
							mailSessionPool.cacheService=new HashCache();
						}
					}catch(Exception e){
						logger.warn("some exception occurs while loading custom mail session cache factory, to use the default hashtable cache.");
						mailSessionPool.cacheService=new HashCache();
					}
				}
			}
		}
		return mailSessionPool;
	}
	
	
	/**
	 * return the JMailSession immediately
	 * @param authenticator
	 * @param properties
	 * @param immediateReturn  only true supported
	 * @return
	 * @throws Exception 
	 */
	public JMailSession connect(JMailAuthenticator authenticator, Properties properties,boolean immediateReturn) throws Exception{
		JAssert.isTrue(immediateReturn, "boolean argument must be true");
		String unique=connect(authenticator, properties);
		return getSession(unique);
	}
	
	/**
	 * return an unique identification reference to the internal JMailSession
	 * @param authenticator
	 * @param properties
	 * @return
	 * @throws Exception 
	 */
	public String connect(JMailAuthenticator authenticator, Properties properties) throws Exception{
		String unique=getMD5(authenticator, properties);
		JMailSession mailSession=null;
		if(!cacheService.contains(unique)){
			try{
				lock.lock();
				if(!cacheService.contains(unique)){
					Session session=Session.getInstance(properties, authenticator);
					mailSession=new JMailSession(unique, session);
					cacheService.putNeverExpired(unique, mailSession);
					keys.add(unique);
				}
			}finally{
				lock.unlock();
			}
		}
		return unique;
	}

	private String getMD5(JMailAuthenticator authenticator, Properties properties) throws Exception {
		try {
			final LinkedHashMap<String, Object> elems= JPropertiesUtils.sortKey(properties,true);
			// we get the MD5 value 
			final StringBuffer data=new StringBuffer();
			final String split="~~~~~~~~~~";
			JCollectionUtils.each(elems, new JCollectionUtils.EntryCallback<String, Object>() {
				@Override
				public void process(String key, Object value) throws Exception {
					
					if(logger.isDebugEnabled()){
						logger.debug(key+"--->"+JStringUtils.toString(value));
					}
					data.append(key+JStringUtils.toString(value)+split);
				}
			});
			String info= authenticator.info();
			data.append(info);
			
			if(logger.isDebugEnabled()){
				logger.debug("---------text---------->"+data.toString());
			}
			String md5= JMD5Cipher.encrypt(data.toString());
			if(logger.isDebugEnabled()){
				logger.debug("---------md5---------->"+md5);
			}
			
			return md5;
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			throw e;
		}
	}
	
	/**
	 * get mail session from pool
	 * @param unique
	 * @return
	 */
	public JMailSession getSession(String unique){
		return (JMailSession) cacheService.get(unique);
	}
	
	/**
	 * return the latest mail session, the 
	 * @return
	 */
	public JMailSession getLatestMailSession() {
		return (JMailSession) cacheService.get(keys.get(keys.size()-1));
	}
}
