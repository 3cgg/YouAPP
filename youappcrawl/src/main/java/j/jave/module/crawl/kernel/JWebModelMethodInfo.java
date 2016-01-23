package j.jave.module.crawl.kernel;

import java.lang.reflect.Method;

public class JWebModelMethodInfo {

	private String key;
	
	private Method getMethod;
	
	private Method setMethod;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public Method getGetMethod() {
		return getMethod;
	}

	public void setGetMethod(Method getMethod) {
		this.getMethod = getMethod;
	}

	public Method getSetMethod() {
		return setMethod;
	}

	public void setSetMethod(Method setMethod) {
		this.setMethod = setMethod;
	}
	
}
