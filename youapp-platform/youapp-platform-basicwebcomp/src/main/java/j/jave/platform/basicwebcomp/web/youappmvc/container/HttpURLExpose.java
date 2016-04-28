package j.jave.platform.basicwebcomp.web.youappmvc.container;


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
	
}
