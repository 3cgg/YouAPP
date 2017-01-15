package me.bunny.app._c._web.web.youappmvc;

import javax.servlet.http.HttpServletResponse;

public class ServletResponseContext implements ResponseContext{

	private transient HttpServletResponse response;
	
	public ServletResponseContext(HttpServletResponse response) {
		this.response = response;
	}

	@Override
	public Object getSource() {
		return response;
	}
	
}
