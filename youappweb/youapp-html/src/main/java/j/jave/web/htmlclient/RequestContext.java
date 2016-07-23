package j.jave.web.htmlclient;


public interface RequestContext {
	
	public String getParameter(String name);
	
	public String[] getParameterValues(String name);
	
	
	
}
