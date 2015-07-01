/**
 * 
 */
package j.jave.framework.components.runtimeload.service;

import j.jave.framework.commons.eventdriven.exception.JServiceException;
import j.jave.framework.components.core.service.ServiceContext;
import j.jave.framework.components.core.service.ServiceSupport;
import j.jave.framework.components.login.service.UserService;
import j.jave.framework.components.runtimeload.jnerface.Key;
import j.jave.framework.components.runtimeload.mapper.RuntimeLoadMapper;
import j.jave.framework.components.runtimeload.model.RuntimeLoad;
import j.jave.framework.mybatis.JMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Administrator
 *
 */
@Service(value="runtimeloadService.transation")
public class RuntimeLoadServiceImpl extends ServiceSupport<RuntimeLoad> implements RuntimeLoadService{

	@Autowired
	private RuntimeLoadMapper runtimeLoadMapper;
	
	public RuntimeLoadServiceImpl() {
		System.out.println(this.getClass());
	}
	
	@Autowired
	private UserService userService;
	
	
	@Override
	protected JMapper<RuntimeLoad> getMapper() {
		return this.runtimeLoadMapper;
	}
	
	@Override
	public void saveRuntimeLoad(ServiceContext context, RuntimeLoad bill)
			throws JServiceException {
		saveOnly(context, bill);
	}

	@Override
	public void updateRuntimeLoad(ServiceContext context, RuntimeLoad bill)
			throws JServiceException {
		updateOnly(context, bill);
	}

	@Override
	public RuntimeLoad getRuntimeLoadById(ServiceContext context, String id) {
		return getById(context, id);
	}
	
	private static int count=0;
	
	public String running() {
		return "I am Running of  version("+Key.get().getVersion()+")  count: "+(++count);
	};
}
