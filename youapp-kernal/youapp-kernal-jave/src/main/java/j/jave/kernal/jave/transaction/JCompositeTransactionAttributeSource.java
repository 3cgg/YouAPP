package j.jave.kernal.jave.transaction;

import j.jave.kernal.jave.utils.JAssert;

import java.io.Serializable;
import java.lang.reflect.Method;

public class JCompositeTransactionAttributeSource implements JTransactionAttributeSource, Serializable {

	private final JTransactionAttributeSource[] transactionAttributeSources;


	/**
	 * Create a new CompositeTransactionAttributeSource for the given sources.
	 * @param transactionAttributeSources the TransactionAttributeSource instances to combine
	 */
	public JCompositeTransactionAttributeSource(JTransactionAttributeSource[] transactionAttributeSources) {
		JAssert.notNull(transactionAttributeSources, "TransactionAttributeSource array must not be null");
		this.transactionAttributeSources = transactionAttributeSources;
	}

	/**
	 * Return the TransactionAttributeSource instances that this
	 * CompositeTransactionAttributeSource combines.
	 */
	public final JTransactionAttributeSource[] getTransactionAttributeSources() {
		return this.transactionAttributeSources;
	}


	@Override
	public JTransactionAttribute getTransactionAttribute(Method method, Class<?> targetClass) {
		for (JTransactionAttributeSource tas : this.transactionAttributeSources) {
			JTransactionAttribute ta = tas.getTransactionAttribute(method, targetClass);
			if (ta != null) {
				return ta;
			}
		}
		return null;
	}

}