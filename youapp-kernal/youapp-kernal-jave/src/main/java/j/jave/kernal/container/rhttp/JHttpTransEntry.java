package j.jave.kernal.container.rhttp;

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
