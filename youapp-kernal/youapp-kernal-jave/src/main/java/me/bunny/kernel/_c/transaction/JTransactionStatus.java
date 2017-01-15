package me.bunny.kernel._c.transaction;


public interface JTransactionStatus {

	/**
	 * Return whether the present transaction is new (else participating
	 * in an existing transaction, or potentially not running in an
	 * actual transaction in the first place).
	 */
	boolean isNewTransaction();

	/**
	 * Return whether this transaction internally carries a savepoint,
	 * that is, has been created as nested transaction based on a savepoint.
	 * <p>This method is mainly here for diagnostic purposes, alongside
	 * {@link #isNewTransaction()}. For programmatic handling of custom
	 * savepoints, use SavepointManager's operations.
	 * @see #isNewTransaction()
	 * @see #createSavepoint
	 * @see #rollbackToSavepoint(Object)
	 * @see #releaseSavepoint(Object)
	 */
	boolean hasSavepoint();

	/**
	 * Set the transaction rollback-only. This instructs the transaction manager
	 * that the only possible outcome of the transaction may be a rollback, as
	 * alternative to throwing an exception which would in turn trigger a rollback.
	 * <p>This is mainly intended for transactions managed by
	 * {@link org.springframework.transaction.support.TransactionTemplate} or
	 * {@link org.springframework.transaction.interceptor.TransactionInterceptor},
	 * where the actual commit/rollback decision is made by the container.
	 * @see org.springframework.transaction.support.TransactionCallback#doInTransaction
	 * @see org.springframework.transaction.interceptor.TransactionAttribute#rollbackOn
	 */
	void setRollbackOnly();

	/**
	 * Return whether the transaction has been marked as rollback-only
	 * (either by the application or by the transaction infrastructure).
	 */
	boolean isRollbackOnly();

	/**
	 * Flush the underlying session to the datastore, if applicable:
	 * for example, all affected Hibernate/JPA sessions.
	 */
	void flush();

	/**
	 * Return whether this transaction is completed, that is,
	 * whether it has already been committed or rolled back.
	 * @see PlatformTransactionManager#commit
	 * @see PlatformTransactionManager#rollback
	 */
	boolean isCompleted();
	
}
