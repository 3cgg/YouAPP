package j.jave.framework.components.resource.service;

import java.util.List;

import j.jave.framework.components.core.service.Service;
import j.jave.framework.components.resource.model.Resource;

public interface ResourceService extends Service<Resource> {

	/**
	 * get all resources from <code>RESOURCE</code> table. not any filter.
	 * @return
	 */
	List<Resource> getResources();
}
