package me.bunny.kernel.jave.model.support;

import me.bunny.kernel.jave.support.validate.JValidator;

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
