package com.youappcorp.project.runtimeurl.vo;

import me.bunny.app._c.data.web.model.JOutputModel;

public class MethodInfoVO implements JOutputModel {
	
	private String url;
	
	private String methodName;
	
	private ParamInfoVO[] paramInfos;
	
	private String returnType;

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public String getReturnType() {
		return returnType;
	}

	public void setReturnType(String returnType) {
		this.returnType = returnType;
	}

	public ParamInfoVO[] getParamInfos() {
		return paramInfos;
	}

	public void setParamInfos(ParamInfoVO[] paramInfos) {
		this.paramInfos = paramInfos;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
