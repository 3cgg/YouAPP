package j.jave.platform.webcomp.web.youappmvc.plugins.pageable;

import j.jave.platform.webcomp.web.youappmvc.HttpContext;
import j.jave.platform.webcomp.web.youappmvc.service.PageableService;
import j.jave.platform.webcomp.web.youappmvc.service.PageableServiceFactory;
import me.bunny.kernel.jave.model.JPageable;

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
