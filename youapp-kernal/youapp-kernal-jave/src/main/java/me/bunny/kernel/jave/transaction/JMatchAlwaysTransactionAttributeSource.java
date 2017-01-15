package me.bunny.kernel.jave.transaction;

import java.io.Serializable;
import java.lang.reflect.Method;

import me.bunny.kernel.jave.reflect.JClassUtils;
import me.bunny.kernel.jave.utils.JObjectUtils;

/**
 * Very simple implementation of TransactionAttributeSource which will always return
 * the same TransactionAttribute for all methods fed to it. The TransactionAttribute
 * may be specified, but will otherwise default to PROPAGATION_REQUIRED. This may be
 * used in the cases where you want to use the same transaction attribute with all
 * methods being handled by a transaction interceptor.
 *
 * @author Colin Sampaleanu
 * @since 15.10.2003
 * @see org.springframework.transaction.interceptor.TransactionProxyFactoryBean
 * @see org.springframework.aop.framework.autoproxy.BeanNameAutoProxyCreator
 */
@SuppressWarnings("serial")
public class JMatchAlwaysTransactionAttributeSource implements JTransactionAttributeSource, Serializable {

	private JTransactionAttribute transactionAttribute = new JDefaultTransactionAttribute();


	/**
	 * Allows a transaction attribute to be specified, using the String form, for
	 * example, "PROPAGATION_REQUIRED".
	 * @param transactionAttribute The String form of the transactionAttribute to use.
	 * @see org.springframework.transaction.interceptor.TransactionAttributeEditor
	 */
	public void setTransactionAttribute(JTransactionAttribute transactionAttribute) {
		this.transactionAttribute = transactionAttribute;
	}


	@Override
	public JTransactionAttribute getTransactionAttribute(Method method, Class<?> targetClass) {
		return (method == null || JClassUtils.isUserLevelMethod(method) ? this.transactionAttribute : null);
	}


	@Override
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof JMatchAlwaysTransactionAttributeSource)) {
			return false;
		}
		JMatchAlwaysTransactionAttributeSource otherTas = (JMatchAlwaysTransactionAttributeSource) other;
		return JObjectUtils.nullSafeEquals(this.transactionAttribute, otherTas.transactionAttribute);
	}

	@Override
	public int hashCode() {
		return JMatchAlwaysTransactionAttributeSource.class.hashCode();
	}

	@Override
	public String toString() {
		return getClass().getName() + ": " + this.transactionAttribute;
	}

}
