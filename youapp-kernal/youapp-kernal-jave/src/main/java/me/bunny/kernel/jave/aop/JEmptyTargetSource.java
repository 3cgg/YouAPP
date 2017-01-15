package me.bunny.kernel.jave.aop;

import java.io.Serializable;

import me.bunny.kernel.jave.utils.JLangUtils;

/**
 * Canonical {@code TargetSource} when there is no target
 * (or just the target class known), and behavior is supplied
 * by interfaces and advisors only.
 *
 * @author Rod Johnson
 * @author Juergen Hoeller
 */
public class JEmptyTargetSource implements JTargetSource, Serializable {


	//---------------------------------------------------------------------
	// Static factory methods
	//---------------------------------------------------------------------

	/**
	 * The canonical (Singleton) instance of this {@link JEmptyTargetSource}.
	 */
	public static final JEmptyTargetSource INSTANCE = new JEmptyTargetSource(null, true);


	/**
	 * Return an JEmptyTargetSource for the given target Class.
	 * @param targetClass the target Class (may be {@code null})
	 * @see #getTargetClass()
	 */
	public static JEmptyTargetSource forClass(Class<?> targetClass) {
		return forClass(targetClass, true);
	}

	/**
	 * Return an JEmptyTargetSource for the given target Class.
	 * @param targetClass the target Class (may be {@code null})
	 * @param isStatic whether the TargetSource should be marked as static
	 * @see #getTargetClass()
	 */
	public static JEmptyTargetSource forClass(Class<?> targetClass, boolean isStatic) {
		return (targetClass == null && isStatic ? INSTANCE : new JEmptyTargetSource(targetClass, isStatic));
	}


	//---------------------------------------------------------------------
	// Instance implementation
	//---------------------------------------------------------------------

	private final Class<?> targetClass;

	private final boolean isStatic;


	/**
	 * Create a new instance of the {@link JEmptyTargetSource} class.
	 * <p>This constructor is {@code private} to enforce the
	 * Singleton pattern / factory method pattern.
	 * @param targetClass the target class to expose (may be {@code null})
	 * @param isStatic whether the TargetSource is marked as static
	 */
	private JEmptyTargetSource(Class<?> targetClass, boolean isStatic) {
		this.targetClass = targetClass;
		this.isStatic = isStatic;
	}

	/**
	 * Always returns the specified target Class, or {@code null} if none.
	 */
	@Override
	public Class<?> getTargetClass() {
		return this.targetClass;
	}

	/**
	 * Always returns {@code true}.
	 */
	@Override
	public boolean isStatic() {
		return this.isStatic;
	}

	/**
	 * Always returns {@code null}.
	 */
	@Override
	public Object getTarget() {
		return null;
	}

	/**
	 * Nothing to release.
	 */
	@Override
	public void releaseTarget(Object target) {
	}


	/**
	 * Returns the canonical instance on deserialization in case
	 * of no target class, thus protecting the Singleton pattern.
	 */
	private Object readResolve() {
		return (this.targetClass == null && this.isStatic ? INSTANCE : this);
	}

	@Override
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof JEmptyTargetSource)) {
			return false;
		}
		JEmptyTargetSource otherTs = (JEmptyTargetSource) other;
		return (JLangUtils.equals(this.targetClass, otherTs.targetClass) && this.isStatic == otherTs.isStatic);
	}

	@Override
	public int hashCode() {
		return JEmptyTargetSource.class.hashCode() * 13 + JLangUtils.nullSafeHashCode(this.targetClass);
	}

	@Override
	public String toString() {
		return "JEmptyTargetSource: " +
				(this.targetClass != null ? "target class [" + this.targetClass.getName() + "]" : "no target class") +
				", " + (this.isStatic ? "static" : "dynamic");
	}

}