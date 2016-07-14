package j.jave.kernal.container._resource;

import j.jave.kernal.container.JExecutableURIUtil;
import j.jave.kernal.container.JScheme;

public class JHttpURIGetService implements JResourceURIGetService{

	@Override
	public String getGetRequestURI(String unique, String path) {
		return JExecutableURIUtil.getGetRequestURI(unique,path,JScheme.HTTP);
	}

	@Override
	public String getPutRequestURI(String unique, String path) {
		return JExecutableURIUtil.getPutRequestURI(unique,path,JScheme.HTTP);
	}

	@Override
	public String getDeleteRequestURI(String unique, String path) {
		return JExecutableURIUtil.getDeleteRequestURI(unique,path,JScheme.HTTP);
	}

	@Override
	public String getExistRequestURI(String unique, String path) {
		return JExecutableURIUtil.getExistRequestURI(unique,path,JScheme.HTTP);
	}

	@Override
	public String getExecuteRequestURI(String unique, String path) {
		return JExecutableURIUtil.getExecuteRequestURI(unique,path,JScheme.HTTP);
	}

	
}
