package com.youappcorp.project.usermanager.service;

import j.jave.kernal.jave.model.JPage;
import j.jave.kernal.jave.model.JPageImpl;
import j.jave.kernal.jave.model.JPageable;
import j.jave.kernal.jave.persist.JIPersist;
import j.jave.kernal.jave.utils.JStringUtils;
import j.jave.platform.basicwebcomp.core.service.InternalServiceSupport;
import j.jave.platform.basicwebcomp.core.service.ServiceContext;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.youappcorp.project.BusinessException;
import com.youappcorp.project.BusinessExceptionUtil;
import com.youappcorp.project.usermanager.model.Group;
import com.youappcorp.project.usermanager.repo.GroupRepo;

@Service(value="groupServiceImpl.transation.jpa")
public class GroupServiceImpl extends InternalServiceSupport<Group> implements GroupService {

	@Autowired
	private GroupRepo<?> groupMapper;
	
	@Override
	public JIPersist<?, Group, String> getRepo() {
		return groupMapper;
	}
	
	@Override
	public Group getGroupByGroupCode(ServiceContext serviceContext, String roleCode) {
		return groupMapper.getGroupByGroupCode(roleCode);
	}
	
	@Override
	public Group getAdminGroup(ServiceContext serviceContext) {
		return getGroupByGroupCode(serviceContext, ADMIN_CODE);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Group getDefaultGroup(ServiceContext serviceContext) {
		return getGroupByGroupCode(serviceContext, DEFAULT_CODE);
	}
	
	@Override
	public JPage<Group> getGroupByGroupNameByPage(ServiceContext serviceContext,
			JPageable pagination) {
		JPageImpl<Group> page=new JPageImpl<Group>();
		List<Group> groups=groupMapper.getGroupByGroupNameByPage(pagination);
		page.setContent(groups);
		return page;
	}
	
	@Override
	public List<Group> getAllGroups(ServiceContext serviceContext) {
		return groupMapper.getAllGroups();
	}
	
	
	@Override
	public void saveGroup(ServiceContext context, Group group)
			throws BusinessException {
		try{
			validateGroupCode(group);
			
			if(exists(context, group)){
				throw new BusinessException("group code ["+group.getGroupCode()+"] already has exist.");
			}
			saveOnly(context, group);
		}catch(Exception e){
			BusinessExceptionUtil.throwException(e);
		}
		
	}
	
	@Override
	public boolean exists(ServiceContext context, Group group)
			throws BusinessException {
		boolean exists=false;
		try{
			if(group==null){
				throw new IllegalArgumentException("role argument is null");
			}
			
			Group dbGroup=getGroupByGroupCode(context, group.getGroupCode());
			// new created.
			if(JStringUtils.isNullOrEmpty(group.getId())){
				exists= dbGroup!=null;
			}
			else{
				// updated status.
				if(dbGroup!=null){
					// if it's self
					exists=!group.getId().equals(dbGroup.getId());
				}
				else{
					exists=false;
				}
			}
		}catch(Exception e){
			BusinessExceptionUtil.throwException(e);
		}
		return exists;
	}
	
	private void validateGroupCode(Group group) throws BusinessException{
		
		String code=group.getGroupCode();
		
		if(JStringUtils.isNullOrEmpty(code)){
			throw new IllegalArgumentException("group code  is null"); 
		}
		code=code.trim();
		
		if(ADMIN_CODE.equalsIgnoreCase(code)){
			throw new BusinessException("group code ["+ADMIN_CODE+"] is initialized by system.please change...");
		}
		
		if(DEFAULT_CODE.equalsIgnoreCase(code)){
			throw new BusinessException("group code ["+DEFAULT_CODE+"] is initialized by system.please change...");
		}
	}
	
	@Override
	public void updateGroup(ServiceContext context, Group group)
			throws BusinessException {
		try{
			validateGroupCode(group);
			
			if(JStringUtils.isNullOrEmpty(group.getId())){
				throw new IllegalArgumentException("the primary property id of group is null.");
			}
			
			if(exists(context, group)){
				throw new BusinessException("group code ["+group.getGroupCode()+"] already has exist.");
			}
			updateOnly(context, group);
		}catch(Exception e){
			BusinessExceptionUtil.throwException(e);
		}
	}
	
	@Override
	public void deleteGroup(ServiceContext context, Group group)
			throws BusinessException {
		try{
			if(JStringUtils.isNullOrEmpty(group.getId())){
				throw new IllegalArgumentException("the primary property id of group is null.");
			}
			
			if(JStringUtils.isNullOrEmpty(group.getGroupCode())){
				group.setGroupCode(getById(context, group.getId()).getGroupCode());
			}
			
			validateGroupCode(group);
			delete(context, group.getId());
		}catch(Exception e){
			BusinessExceptionUtil.throwException(e);
		}
	}
	
	
}
