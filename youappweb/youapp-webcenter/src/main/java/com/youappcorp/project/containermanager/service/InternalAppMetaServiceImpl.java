package com.youappcorp.project.containermanager.service;

import j.jave.platform.webcomp.core.service.InternalServiceSupport;
import me.bunny.kernel.jave.persist.JIPersist;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.youappcorp.project.containermanager.jpa.AppMetaJPARepo;
import com.youappcorp.project.containermanager.model.AppMeta;

@Service(value="internalAppMetaServiceImpl.transation.jpa")
public class InternalAppMetaServiceImpl extends InternalServiceSupport<AppMeta>{

	@Autowired
	private AppMetaJPARepo appMetaRepo;
	
	@Override
	public JIPersist<?, AppMeta, String> getRepo() {
		return appMetaRepo;
	}
	
}
