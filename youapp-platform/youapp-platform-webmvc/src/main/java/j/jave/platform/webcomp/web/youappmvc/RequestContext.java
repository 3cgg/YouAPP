package j.jave.platform.webcomp.web.youappmvc;

public interface RequestContext {
	
	public String getParameter(String name);
	
	public String[] getParameterValues(String name);

	/**
	 * may be {@code ServletRequest}
	 * @return
	 */
	public Object getSource();
	
}
