package com.youappcorp.project.menumanager.service;

import j.jave.kernal.jave.persist.JIPersist;
import j.jave.platform.webcomp.core.service.InternalServiceSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.youappcorp.project.menumanager.jpa.MenuJPARepo;
import com.youappcorp.project.menumanager.model.Menu;

@Service(value="internalMenuServiceImpl.transation.jpa")
public class InternalMenuServiceImpl extends InternalServiceSupport<Menu> {
	@Autowired
	private MenuJPARepo menuRepo;
	
	@Override
	public JIPersist<?, Menu, String> getRepo() {
		return menuRepo;
	}
}
