package j.jave.kernal.jave.transaction;

import j.jave.kernal.jave.utils.JAssert;

import java.io.Serializable;

/**
 * {@link TransactionDefinition} implementation that delegates all calls to a given target
 * {@link TransactionDefinition} instance. Abstract because it is meant to be subclassed,
 * with subclasses overriding specific methods that are not supposed to simply delegate
 * to the target instance.
 *
 * @author Juergen Hoeller
 * @since 3.0
 */
public class JDelegatingTransactionDefinition implements JTransactionDefinition, Serializable {

	private final JTransactionDefinition targetDefinition;


	/**
	 * Create a DelegatingTransactionAttribute for the given target attribute.
	 * @param targetDefinition the target TransactionAttribute to delegate to
	 */
	public JDelegatingTransactionDefinition(JTransactionDefinition targetDefinition) {
		JAssert.notNull(targetDefinition, "Target definition must not be null");
		this.targetDefinition = targetDefinition;
	}


	@Override
	public int getPropagationBehavior() {
		return this.targetDefinition.getPropagationBehavior();
	}

	@Override
	public int getIsolationLevel() {
		return this.targetDefinition.getIsolationLevel();
	}

	@Override
	public int getTimeout() {
		return this.targetDefinition.getTimeout();
	}

	@Override
	public boolean isReadOnly() {
		return this.targetDefinition.isReadOnly();
	}

	@Override
	public String getName() {
		return this.targetDefinition.getName();
	}


	@Override
	public boolean equals(Object obj) {
		return this.targetDefinition.equals(obj);
	}

	@Override
	public int hashCode() {
		return this.targetDefinition.hashCode();
	}

	@Override
	public String toString() {
		return this.targetDefinition.toString();
	}

}
