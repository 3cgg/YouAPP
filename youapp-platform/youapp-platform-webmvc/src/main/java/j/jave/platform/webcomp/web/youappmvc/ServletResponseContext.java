package j.jave.platform.webcomp.web.youappmvc;

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
