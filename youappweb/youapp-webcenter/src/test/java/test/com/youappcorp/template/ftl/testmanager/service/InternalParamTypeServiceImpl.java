package test.com.youappcorp.template.ftl.testmanager.service;

import j.jave.kernal.jave.persist.JIPersist;
import j.jave.platform.webcomp.core.service.InternalServiceSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import test.com.youappcorp.template.ftl.testmanager.jpa.ParamTypeJPARepo;
import test.com.youappcorp.template.ftl.testmanager.model.ParamType;

@Service(value="InternalParamTypeServiceImpl.transation.jpa")
public class InternalParamTypeServiceImpl extends InternalServiceSupport<ParamType>{

	@Autowired
	private ParamTypeJPARepo repo;
	
	@Override
	public JIPersist<?, ParamType, String> getRepo() {
		return repo;
	}

}
