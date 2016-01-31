package j.jave.kernal.jave.proxy;

/**
 * the sub-implementation must contain public constructor.
 * @author J
 *
 */
public interface JAtomicResourceSessionProvide {

	/**
	 * create new atomic resource session, generally the session will be cached in the thread local scope.
	 * @return
	 */
	JAtomicResourceSession newInstance();
	
}
