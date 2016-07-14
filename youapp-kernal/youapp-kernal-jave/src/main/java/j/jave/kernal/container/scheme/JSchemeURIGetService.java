package j.jave.kernal.container.scheme;

import j.jave.kernal.jave.service.JService;


public interface JSchemeURIGetService extends JService {

	public String getGetRequestURI(String unique,String path);
	
	public String getPutRequestURI(String unique,String path);
	
	public String getDeleteRequestURI(String unique,String path);
	
	public String getExistRequestURI(String unique,String path);
	
	public String getExecuteRequestURI(String unique,String path);
	
}
