package me.bunny.kernel.container.rhttp;

import java.util.Map;

public interface JHttpTransEntry {

	/**
	 * for http get.
	 * @return
	 */
	Map<String, Object> parameters();
	
	/**
	 * for http post.
	 * @return
	 */
	byte[] entry();
	
}
