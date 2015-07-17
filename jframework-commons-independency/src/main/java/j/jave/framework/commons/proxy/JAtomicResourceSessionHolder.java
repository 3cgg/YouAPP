package j.jave.framework.commons.proxy;

import j.jave.framework.commons.exception.JInitializationException;
import j.jave.framework.commons.logging.JLogger;
import j.jave.framework.commons.logging.JLoggerFactory;
import j.jave.framework.commons.reflect.JClassUtils;
import j.jave.framework.commons.support._package.JDefaultClassesScanner;
import j.jave.framework.commons.utils.JAssert;
import j.jave.framework.commons.utils.JCollectionUtils;
import j.jave.framework.commons.utils.JCollectionUtils.Callback;
import j.jave.framework.commons.utils.JPropertiesUtils;
import j.jave.framework.commons.utils.JStringUtils;

import java.util.Set;


/**
 * the atomic resource session holder, that first search "commons-independency.properties" file to find session provider,
 * then search all classes under the class path. Note the whole system should only have an unique session provider.
 * @author J
 * @see JAtomicResourceSessionProvide
 */
public class JAtomicResourceSessionHolder {

	private static ThreadLocal<JAtomicResourceSession> threadLocal=new ThreadLocal<JAtomicResourceSession>();
	
	private static final JLogger logger =JLoggerFactory.getLogger(JAtomicResourceSessionHolder.class);
	
	private static final String ATOMIC_RESOURCE_SESSION_PROVIDER="j.jave.framework.commons.proxy.JAtomicResourceSessionProvide";
	
	public static JAtomicResourceSession getAtomicResourceSession() throws Exception{
		
		if(threadLocal.get()==null){
			JAtomicResourceSessionProvide atomicResourceSessionProvided=null;
			String sessionProviderClass=null;
			
			//get from the commons-independency.properties under the class path.
			String sessionProvider=getConfigAtomicResourceSessionProvider();
			if(JStringUtils.isNotNullOrEmpty(sessionProvider)){
				sessionProviderClass=sessionProvider;
			}
			
			if(JStringUtils.isNullOrEmpty(sessionProviderClass)){
				JDefaultClassesScanner classesScanner=new JDefaultClassesScanner(JAtomicResourceSessionProvide.class);
				Set<Class<?>> classes= classesScanner.scan();
				
				if(!JCollectionUtils.hasInCollect(classes)){
					throw new JInitializationException("must provide an atomic resource session provider.");
				}
				
				if(JCollectionUtils.hasInCollect(classes)&&classes.size()>1){
					JCollectionUtils.each(classes, new Callback<Class<?>,Class<?>>() {
						@Override
						public void process(Class<?> key, Class<?> value)
								throws Exception {
							logger.info(value.getClass().getName());
						}
					});
					logger.info("obscure atomic resource sssion provide implementaion, only one supported in the whole system");
					throw new JInitializationException("obscure atomic resource sssion provide implementaion, only one supported in the whole system");
				}
				atomicResourceSessionProvided=(JAtomicResourceSessionProvide) classes.iterator().next().newInstance();
			}
			else{
				Class<?> sessionClazz= JClassUtils.load(sessionProviderClass, Thread.currentThread().getContextClassLoader());
				atomicResourceSessionProvided=(JAtomicResourceSessionProvide) sessionClazz.newInstance();
			}
			threadLocal.set(atomicResourceSessionProvided.newInstance());
		}
		return threadLocal.get();
	}
	
	
	private static String getConfigAtomicResourceSessionProvider(){
		return JPropertiesUtils.getKey(ATOMIC_RESOURCE_SESSION_PROVIDER, "commons-independency.properties");
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
