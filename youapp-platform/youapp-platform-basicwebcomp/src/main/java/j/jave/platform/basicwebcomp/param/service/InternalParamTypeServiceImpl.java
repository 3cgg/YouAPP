package j.jave.platform.basicwebcomp.param.service;

import j.jave.kernal.jave.persist.JIPersist;
import j.jave.platform.basicwebcomp.core.service.ServiceSupport;
import j.jave.platform.basicwebcomp.param.jpa.ParamTypeJPARepo;
import j.jave.platform.basicwebcomp.param.model.ParamType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service(value="internalparamTypeService.transation.jpa")
public class InternalParamTypeServiceImpl extends ServiceSupport<ParamType>{

	@Autowired
	private ParamTypeJPARepo paramTypeJPARepo;
	
	@Override
	public JIPersist<?, ParamType> getRepo() {
		return paramTypeJPARepo;
	}
	
}
