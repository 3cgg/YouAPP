package me.bunny.kernel._c.utils;

public class JConstantException extends IllegalArgumentException {

	/**
	 * Thrown when an invalid constant name is requested.
	 * @param className name of the class containing the constant definitions
	 * @param field invalid constant name
	 * @param message description of the problem
	 */
	public JConstantException(String className, String field, String message) {
		super("Field '" + field + "' " + message + " in class [" + className + "]");
	}

	/**
	 * Thrown when an invalid constant value is looked up.
	 * @param className name of the class containing the constant definitions
	 * @param namePrefix prefix of the searched constant names
	 * @param value the looked up constant value
	 */
	public JConstantException(String className, String namePrefix, Object value) {
		super("No '" + namePrefix + "' field with value '" + value + "' found in class [" + className + "]");
	}

}
