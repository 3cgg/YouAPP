package me.bunny.kernel._c.model.support;

import me.bunny.kernel._c.support.validate.JValidator;

/**
 * 
 * @author J
 *
 * @param <T>
 */
public interface JFieldValidator<T> extends JValidator<T> {
	
	/**
	 * if return false from validate , the message can make the caller know the detail informaiton.
	 * @return
	 */
	String invalidMessage();
	
}
