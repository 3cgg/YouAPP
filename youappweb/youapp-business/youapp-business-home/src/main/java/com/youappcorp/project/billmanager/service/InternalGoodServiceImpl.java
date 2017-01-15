package com.youappcorp.project.billmanager.service;

import j.jave.platform.webcomp.core.service.InternalServiceSupport;
import me.bunny.kernel.jave.persist.JIPersist;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.youappcorp.project.billmanager.jpa.GoodJPARepo;
import com.youappcorp.project.billmanager.model.Good;

@Service(value="internalGoodServiceImpl.transation.jpa")
public class InternalGoodServiceImpl extends InternalServiceSupport<Good>{

	@Autowired
	private GoodJPARepo goodJPARepo;
	
	@Override
	public JIPersist<?, Good, String> getRepo() {
		return goodJPARepo;
	}

}
