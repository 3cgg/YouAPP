package j.jave.kernal.jave.transaction;

import j.jave.kernal.jave.aop.JYouAPPProxy;


/**
 * A marker interface for manually created transactional proxies.
 *
 * <p>{@link TransactionAttributeSourcePointcut} will ignore such existing
 * transactional proxies during AOP auto-proxying and therefore avoid
 * re-processing transaction metadata on them.
 *
 * @author Juergen Hoeller
 * @since 4.1.7
 */
public interface JTransactionalProxy extends JYouAPPProxy {

}
