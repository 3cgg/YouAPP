package me.bunny.kernel._c.support.resourceuri;

public interface IdentifierGenerator {
	public String key(Object object);
	
	/**
	 * the namespace, under which all keys are.
	 * starts with slash and end with slash. i.e "/hashcode/"
	 * @return
	 */
	public String namespace();
}
