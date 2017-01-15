package me.bunny.kernel.container._resource;

import me.bunny.kernel.container.JExecutableURIUtil;
import me.bunny.kernel.container.JScheme;

public class JJarURIGetService implements JResourceURIGetService{

	@Override
	public String getGetRequestURI(String unique, String path) {
		return JExecutableURIUtil.getGetRequestURI(unique,path,JScheme.JAR);
	}

	@Override
	public String getPutRequestURI(String unique, String path) {
		return JExecutableURIUtil.getPutRequestURI(unique,path,JScheme.JAR);
	}

	@Override
	public String getDeleteRequestURI(String unique, String path) {
		return JExecutableURIUtil.getDeleteRequestURI(unique,path,JScheme.JAR);
	}

	@Override
	public String getExistRequestURI(String unique, String path) {
		return JExecutableURIUtil.getExistRequestURI(unique,path,JScheme.JAR);
	}

	@Override
	public String getExecuteRequestURI(String unique, String path) {
		return JExecutableURIUtil.getExecuteRequestURI(unique,path,JScheme.JAR);
	}

	
}
