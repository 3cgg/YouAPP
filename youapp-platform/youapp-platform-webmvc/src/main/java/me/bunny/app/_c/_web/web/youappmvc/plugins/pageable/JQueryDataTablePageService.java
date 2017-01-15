package me.bunny.app._c._web.web.youappmvc.plugins.pageable;

import me.bunny.app._c._web.web.youappmvc.HttpContext;
import me.bunny.app._c._web.web.youappmvc.service.PageableService;
import me.bunny.app._c._web.web.youappmvc.service.PageableServiceFactory;
import me.bunny.kernel._c.model.JPageable;

import org.springframework.stereotype.Service;

@Service(value="JQueryDataTablePageService")
public class JQueryDataTablePageService extends PageableServiceFactory<PageableService> implements PageableService {

	@Override
	public String getName() {
		return "pageable powered by jquery table : "+this.getClass().getName();
	}

	@Override
	public JPageable parse(HttpContext httpContext) {
//		return JQueryDataTablePage.parse(httpContext);
		return null;
	}
	
}
