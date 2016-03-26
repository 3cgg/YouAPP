package j.jave.platform.basicwebcomp.param.service;

import j.jave.kernal.jave.persist.JIPersist;
import j.jave.platform.basicwebcomp.core.service.ServiceSupport;
import j.jave.platform.basicwebcomp.param.model.ParamCode;
import j.jave.platform.basicwebcomp.param.repo.ParamCodeRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service(value="internalparamCodeService.transation.jpa")
public class InternalParamCodeServiceImpl extends ServiceSupport<ParamCode>{

	@Autowired
	private ParamCodeRepo<?> paramCodeRepo;
	
	@Override
	public JIPersist<?, ParamCode> getRepo() {
		return paramCodeRepo;
	}

}
