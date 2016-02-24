package j.jave.platform.basicwebcomp.web.youappmvc.plugins.pageable;

import j.jave.kernal.jave.model.JPage;
import j.jave.platform.basicsupportcomp.core.servicehub.SpringServiceFactorySupport;
import j.jave.platform.basicwebcomp.web.youappmvc.HttpContext;
import j.jave.platform.basicwebcomp.web.youappmvc.service.JPageableService;

import org.springframework.stereotype.Service;

@Service(value="queryDataTablePageService")
public class QueryDataTablePageService extends SpringServiceFactorySupport<JPageableService> implements JPageableService {

	@Override
	public JPageableService getService() {
		return getBeanByName("queryDataTablePageService");
	}
	
	@Override
	public String getName() {
		return "pageable powered by jquery table : "+this.getClass().getName();
	}

	@Override
	public JPage parse(HttpContext httpContext) {
		return QueryDataTablePage.parse(httpContext);
	}
	
}
