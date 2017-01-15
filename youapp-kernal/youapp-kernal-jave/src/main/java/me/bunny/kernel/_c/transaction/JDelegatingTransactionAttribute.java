package me.bunny.kernel._c.transaction;

import java.io.Serializable;

/**
 * {@link TransactionAttribute} implementation that delegates all calls to a given target
 * {@link TransactionAttribute} instance. Abstract because it is meant to be subclassed,
 * with subclasses overriding specific methods that are not supposed to simply delegate
 * to the target instance.
 *
 * @author Juergen Hoeller
 * @since 1.2
 */
@SuppressWarnings("serial")
public abstract class JDelegatingTransactionAttribute extends JDelegatingTransactionDefinition
		implements JTransactionAttribute, Serializable {

	private final JTransactionAttribute targetAttribute;


	/**
	 * Create a DelegatingTransactionAttribute for the given target attribute.
	 * @param targetAttribute the target TransactionAttribute to delegate to
	 */
	public JDelegatingTransactionAttribute(JTransactionAttribute targetAttribute) {
		super(targetAttribute);
		this.targetAttribute = targetAttribute;
	}


	@Override
	public String getQualifier() {
		return this.targetAttribute.getQualifier();
	}

	@Override
	public boolean rollbackOn(Throwable ex) {
		return this.targetAttribute.rollbackOn(ex);
	}

}
