package me.bunny.kernel.jave.transaction;


public class JDefaultTransactionAttribute extends JDefaultTransactionDefinition implements JTransactionAttribute {

	private String qualifier;


	/**
	 * Create a new DefaultTransactionAttribute, with default settings.
	 * Can be modified through bean property setters.
	 * @see #setPropagationBehavior
	 * @see #setIsolationLevel
	 * @see #setTimeout
	 * @see #setReadOnly
	 * @see #setName
	 */
	public JDefaultTransactionAttribute() {
		super();
	}

	/**
	 * Copy constructor. Definition can be modified through bean property setters.
	 * @see #setPropagationBehavior
	 * @see #setIsolationLevel
	 * @see #setTimeout
	 * @see #setReadOnly
	 * @see #setName
	 */
	public JDefaultTransactionAttribute(JTransactionAttribute other) {
		super(other);
	}

	/**
	 * Create a new DefaultTransactionAttribute with the the given
	 * propagation behavior. Can be modified through bean property setters.
	 * @param propagationBehavior one of the propagation constants in the
	 * TransactionDefinition interface
	 * @see #setIsolationLevel
	 * @see #setTimeout
	 * @see #setReadOnly
	 */
	public JDefaultTransactionAttribute(int propagationBehavior) {
		super(propagationBehavior);
	}


	/**
	 * Associate a qualifier value with this transaction attribute.
	 * <p>This may be used for choosing a corresponding transaction manager
	 * to process this specific transaction.
	 */
	public void setQualifier(String qualifier) {
		this.qualifier = qualifier;
	}

	/**
	 * Return a qualifier value associated with this transaction attribute.
	 */
	@Override
	public String getQualifier() {
		return this.qualifier;
	}

	/**
	 * The default behavior is as with EJB: rollback on unchecked exception.
	 * Additionally attempt to rollback on Error.
	 * <p>This is consistent with TransactionTemplate's default behavior.
	 */
	@Override
	public boolean rollbackOn(Throwable ex) {
		return (ex instanceof RuntimeException || ex instanceof Error);
	}


	/**
	 * Return an identifying description for this transaction attribute.
	 * <p>Available to subclasses, for inclusion in their {@code toString()} result.
	 */
	protected final StringBuilder getAttributeDescription() {
		StringBuilder result = getDefinitionDescription();
		if (this.qualifier != null) {
			result.append("; '").append(this.qualifier).append("'");
		}
		return result;
	}

}
