package j.jave.platform.basicwebcomp.web.youappmvc.plugins.pageable;

import j.jave.kernal.jave.model.JPage;
import j.jave.platform.basicwebcomp.web.youappmvc.HttpContext;
import j.jave.platform.basicwebcomp.web.youappmvc.service.PageableService;
import j.jave.platform.basicwebcomp.web.youappmvc.service.PageableServiceFactory;

import org.springframework.stereotype.Service;

@Service(value="jqueryDataTablePageService")
public class JQueryDataTablePageService extends PageableServiceFactory<PageableService> implements PageableService {

	@Override
	public PageableService getService() {
		return getBeanByName("jqueryDataTablePageService");
	}
	
	@Override
	public String getName() {
		return "pageable powered by jquery table : "+this.getClass().getName();
	}

	@Override
	public JPage parse(HttpContext httpContext) {
		return JQueryDataTablePage.parse(httpContext);
	}
	
}
