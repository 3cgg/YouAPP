package com.youappcorp.project.containermanager.service;

import j.jave.kernal.jave.persist.JIPersist;
import j.jave.platform.webcomp.core.service.InternalServiceSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.youappcorp.project.containermanager.model.URLMappingMeta;
import com.youappcorp.project.containermanager.repo.URLMappingMetaRepo;

@Service(value="internalURLMappingMetaServiceImpl.transation.jpa")
public class InternalURLMappingMetaServiceImpl extends InternalServiceSupport<URLMappingMeta>{

	@Autowired
	private URLMappingMetaRepo urlMappingMetaRepo;
	
	@Override
	public JIPersist<?, URLMappingMeta, String> getRepo() {
		return urlMappingMetaRepo;
	}
	
}
