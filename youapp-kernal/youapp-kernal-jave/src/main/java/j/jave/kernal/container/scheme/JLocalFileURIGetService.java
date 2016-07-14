package j.jave.kernal.container.scheme;

import j.jave.kernal.container.JExecutableURIUtil;

public class JLocalFileURIGetService implements JSchemeURIGetService{

	@Override
	public String getGetRequestURI(String unique, String path) {
		return JExecutableURIUtil.getGetRequestURI(unique,path,JScheme.FILE);
	}

	@Override
	public String getPutRequestURI(String unique, String path) {
		return JExecutableURIUtil.getPutRequestURI(unique,path,JScheme.FILE);
	}

	@Override
	public String getDeleteRequestURI(String unique, String path) {
		return JExecutableURIUtil.getDeleteRequestURI(unique,path,JScheme.FILE);
	}

	@Override
	public String getExistRequestURI(String unique, String path) {
		return JExecutableURIUtil.getExistRequestURI(unique,path,JScheme.FILE);
	}

	@Override
	public String getExecuteRequestURI(String unique, String path) {
		return JExecutableURIUtil.getExecuteRequestURI(unique,path,JScheme.FILE);
	}

	
}
