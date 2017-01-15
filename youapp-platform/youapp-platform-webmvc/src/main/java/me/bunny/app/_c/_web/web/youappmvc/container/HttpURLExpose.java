package me.bunny.app._c._web.web.youappmvc.container;


/**
 * the interface that expose all reachable URLs that may exist in the internal http processor(Controller)
 * or remote http processor (redirect to other web app.)
 * @author JIAZJ
 *
 */
public interface HttpURLExpose {
	
	
	/**
	 * the URI that can check if the container contains the special path.
	 * @return
	 */
	String getExistRequestURI();
	
	/**
	 * the URI that the container can execute the special path.
	 * @return
	 */
	String getExecuteRequestURI();
	
	/**
	 * the URI that the container can put the special path in itself .
	 * @return
	 */
	String getPutRequestURI();
	
	/**
	 * the URI that the container expose special information about the path.
	 * @return
	 */
	String getGetRequestURI();
	
	/**
	 * the URI that the container remove the special path from itself .
	 * @return
	 */
	String getDeleteRequestURI();
}
