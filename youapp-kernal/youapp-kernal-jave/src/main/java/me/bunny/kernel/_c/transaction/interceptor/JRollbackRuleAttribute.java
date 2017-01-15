package me.bunny.kernel._c.transaction.interceptor;

import java.io.Serializable;

import me.bunny.kernel._c.utils.JAssert;


/**
 * Rule determining whether or not a given exception (and any subclasses)
 * should cause a rollback.
 *
 * <p>Multiple such rules can be applied to determine whether a transaction
 * should commit or rollback after an exception has been thrown.
 *
 * @author Rod Johnson
 * @since 09.04.2003
 * @see NoRollbackRuleAttribute
 */
@SuppressWarnings("serial")
public class JRollbackRuleAttribute implements Serializable{

	/**
	 * The {@link JRollbackRuleAttribute rollback rule} for
	 * {@link RuntimeException RuntimeExceptions}.
	 */
	public static final JRollbackRuleAttribute ROLLBACK_ON_RUNTIME_EXCEPTIONS =
			new JRollbackRuleAttribute(RuntimeException.class);


	/**
	 * Could hold exception, resolving class name but would always require FQN.
	 * This way does multiple string comparisons, but how often do we decide
	 * whether to roll back a transaction following an exception?
	 */
	private final String exceptionName;


	/**
	 * Create a new instance of the {@code JRollbackRuleAttribute} class.
	 * <p>This is the preferred way to construct a rollback rule that matches
	 * the supplied {@link Exception} class (and subclasses).
	 * @param clazz throwable class; must be {@link Throwable} or a subclass
	 * of {@code Throwable}
	 * @throws IllegalArgumentException if the supplied {@code clazz} is
	 * not a {@code Throwable} type or is {@code null}
	 */
	public JRollbackRuleAttribute(Class<?> clazz) {
		JAssert.notNull(clazz, "'clazz' cannot be null");
		if (!Throwable.class.isAssignableFrom(clazz)) {
			throw new IllegalArgumentException(
					"Cannot construct rollback rule from [" + clazz.getName() + "]: it's not a Throwable");
		}
		this.exceptionName = clazz.getName();
	}

	/**
	 * Create a new instance of the {@code JRollbackRuleAttribute} class
	 * for the given {@code exceptionName}.
	 * <p>This can be a substring, with no wildcard support at present. A value
	 * of "ServletException" would match
	 * {@code javax.servlet.ServletException} and subclasses, for example.
	 * <p><b>NB:</b> Consider carefully how specific the pattern is, and
	 * whether to include package information (which is not mandatory). For
	 * example, "Exception" will match nearly anything, and will probably hide
	 * other rules. "java.lang.Exception" would be correct if "Exception" was
	 * meant to define a rule for all checked exceptions. With more unusual
	 * exception names such as "BaseBusinessException" there's no need to use a
	 * fully package-qualified name.
	 * @param exceptionName the exception name pattern; can also be a fully
	 * package-qualified class name
	 * @throws IllegalArgumentException if the supplied
	 * {@code exceptionName} is {@code null} or empty
	 */
	public JRollbackRuleAttribute(String exceptionName) {
		JAssert.hasText(exceptionName, "'exceptionName' cannot be null or empty");
		this.exceptionName = exceptionName;
	}


	/**
	 * Return the pattern for the exception name.
	 */
	public String getExceptionName() {
		return exceptionName;
	}

	/**
	 * Return the depth of the superclass matching.
	 * <p>{@code 0} means {@code ex} matches exactly. Returns
	 * {@code -1} if there is no match. Otherwise, returns depth with the
	 * lowest depth winning.
	 */
	public int getDepth(Throwable ex) {
		return getDepth(ex.getClass(), 0);
	}


	private int getDepth(Class<?> exceptionClass, int depth) {
		if (exceptionClass.getName().contains(this.exceptionName)) {
			// Found it!
			return depth;
		}
		// If we've gone as far as we can go and haven't found it...
		if (exceptionClass == Throwable.class) {
			return -1;
		}
		return getDepth(exceptionClass.getSuperclass(), depth + 1);
	}


	@Override
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof JRollbackRuleAttribute)) {
			return false;
		}
		JRollbackRuleAttribute rhs = (JRollbackRuleAttribute) other;
		return this.exceptionName.equals(rhs.exceptionName);
	}

	@Override
	public int hashCode() {
		return this.exceptionName.hashCode();
	}

	@Override
	public String toString() {
		return "JRollbackRuleAttribute with pattern [" + this.exceptionName + "]";
	}

}
