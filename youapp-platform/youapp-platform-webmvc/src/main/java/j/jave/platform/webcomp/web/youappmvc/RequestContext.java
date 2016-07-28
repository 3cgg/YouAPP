package j.jave.platform.webcomp.web.youappmvc;


public interface RequestContext {
	
	public String getParameter(String name);
	
	public String[] getParameterValues(String name);
	
	
	
}
