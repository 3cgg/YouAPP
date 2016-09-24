package test.com.youappcorp.template.ftl.testmanager.service;

import j.jave.kernal.jave.persist.JIPersist;
import j.jave.platform.webcomp.core.service.InternalServiceSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import test.com.youappcorp.template.ftl.testmanager.jpa.ParamCodeJPARepo;
import test.com.youappcorp.template.ftl.testmanager.model.ParamCode;

@Service(value="InternalParamCodeServiceImpl.transation.jpa")
public class InternalParamCodeServiceImpl extends InternalServiceSupport<ParamCode>{

	@Autowired
	private ParamCodeJPARepo repo;
	
	@Override
	public JIPersist<?, ParamCode, String> getRepo() {
		return repo;
	}

}
