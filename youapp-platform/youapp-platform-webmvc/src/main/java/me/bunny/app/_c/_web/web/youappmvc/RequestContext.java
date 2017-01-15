package me.bunny.app._c._web.web.youappmvc;

public interface RequestContext {
	
	public String getParameter(String name);
	
	public String[] getParameterValues(String name);

	/**
	 * may be {@code ServletRequest}
	 * @return
	 */
	public Object getSource();
	
}
