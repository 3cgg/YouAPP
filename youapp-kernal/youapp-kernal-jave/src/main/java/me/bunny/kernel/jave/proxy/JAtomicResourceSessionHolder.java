package me.bunny.kernel.jave.proxy;

import java.util.Set;

import me.bunny.kernel.JConfiguration;
import me.bunny.kernel.JProperties;
import me.bunny.kernel.jave.exception.JInitializationException;
import me.bunny.kernel.jave.logging.JLogger;
import me.bunny.kernel.jave.logging.JLoggerFactory;
import me.bunny.kernel.jave.reflect.JClassUtils;
import me.bunny.kernel.jave.support._package.JDefaultClassesScanner;
import me.bunny.kernel.jave.utils.JAssert;
import me.bunny.kernel.jave.utils.JCollectionUtils;
import me.bunny.kernel.jave.utils.JStringUtils;
import me.bunny.kernel.jave.xml.xmldb.JXMLSessionProvider;


/**
 * the atomic resource session holder, that first search "commons-jave.properties" file to find session provider,
 * then search all classes under the class path. Note the whole system should only have an unique session provider.
 * @author J
 * @see JAtomicResourceSessionProvider
 */
public class JAtomicResourceSessionHolder {

	private static ThreadLocal<JAtomicResourceSession> threadLocal=new ThreadLocal<JAtomicResourceSession>();
	
	private static final JLogger logger =JLoggerFactory.getLogger(JAtomicResourceSessionHolder.class);
	
	private static final String ATOMIC_RESOURCE_SESSION_PROVIDER=JProperties.ATOMIC_RESOURCE_SESSION_PROVIDER;
	
	public static JAtomicResourceSession getAtomicResourceSession() throws Exception{
		
		if(threadLocal.get()==null){
			JAtomicResourceSessionProvider atomicResourceSessionProvided=null;
			String sessionProviderClass=null;
			
			//get from the commons-jave.properties under the class path.
			String sessionProvider=getConfigAtomicResourceSessionProvider();
			if(JStringUtils.isNotNullOrEmpty(sessionProvider)){
				sessionProviderClass=sessionProvider;
			}
			
			if(JStringUtils.isNullOrEmpty(sessionProviderClass)){
				JDefaultClassesScanner classesScanner=new JDefaultClassesScanner(JAtomicResourceSessionProvider.class);
				Set<Class<?>> classes= classesScanner.scan();
				
				if(!JCollectionUtils.hasInCollect(classes)){
					throw new JInitializationException("must provide an atomic resource session provider.");
				}
				
				if(JCollectionUtils.hasInCollect(classes)&&classes.size()>1){
					JCollectionUtils.each(classes, new JCollectionUtils.CollectionCallback<Class<?>>() {
						@Override
						public void process(Class<?> key)
								throws Exception {
							logger.info(key.getClass().getName());
						}
					});
					logger.info("obscure atomic resource sssion provide implementaion, only one supported in the whole system");
					throw new JInitializationException("obscure atomic resource sssion provide implementaion, only one supported in the whole system");
				}
				atomicResourceSessionProvided=(JAtomicResourceSessionProvider) classes.iterator().next().newInstance();
			}
			else{
				Class<?> sessionClazz= JClassUtils.load(sessionProviderClass, Thread.currentThread().getContextClassLoader());
				atomicResourceSessionProvided=(JAtomicResourceSessionProvider) sessionClazz.newInstance();
			}
			threadLocal.set(atomicResourceSessionProvided.newInstance());
		}
		return threadLocal.get();
	}
	
	
	private static String getConfigAtomicResourceSessionProvider(){
		return JConfiguration.get().getString(ATOMIC_RESOURCE_SESSION_PROVIDER,
				JXMLSessionProvider.class.getName());
	}
	
	
	public static void release(){
		threadLocal.remove();
	}
	
	/**
	 * get session from thread local , the method is used by the DAO layer.  ONLY true accepted.
	 * @param already
	 * @return
	 * @throws Exception
	 */
	public static JAtomicResourceSession getAtomicResourceSession(boolean already) throws Exception{
		JAssert.isTrue(already, "argument is invalid, only true supported");
		return threadLocal.get();
	}
	
}
