package com.youappcorp.project.usermanager.service;

import j.jave.kernal.jave.model.JPage;
import j.jave.kernal.jave.model.JPageable;
import j.jave.kernal.jave.persist.JIPersist;
import j.jave.kernal.jave.utils.JStringUtils;
import j.jave.platform.basicwebcomp.core.service.InternalServiceSupport;
import j.jave.platform.basicwebcomp.core.service.ServiceContext;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.youappcorp.project.BusinessException;
import com.youappcorp.project.BusinessExceptionUtil;
import com.youappcorp.project.usermanager.model.Role;
import com.youappcorp.project.usermanager.repo.RoleRepo;

@Service(value="roleServiceImpl.transation.jpa")
public class RoleServiceImpl extends InternalServiceSupport<Role> implements RoleService {

	@Autowired
	private RoleRepo<?> roleMapper;
	
	@Autowired
	private RoleGroupService roleGroupService;
	
	
	@Override
	public JIPersist<?, Role> getRepo() {
		return roleMapper;
	}
	
	@Override
	public Role getRoleByRoleCode(ServiceContext serviceContext, String roleCode) {
		return roleMapper.getRoleByRoleCode(roleCode);
	}
	
	@Override
	public Role getAdminRole(ServiceContext serviceContext) {
		return getRoleByRoleCode(serviceContext, ADMIN_CODE);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Role getDefaultRole(ServiceContext serviceContext) {
		return getRoleByRoleCode(serviceContext, DEFAULT_CODE);
	}
	
	@Override
	public JPage<Role> getRoleByRoleNameByPage(ServiceContext serviceContext,
			JPageable pagination) {
		Page<Role> returnPage=roleMapper.getRoleByRoleNameByPage(
				toPageRequest(pagination),pagination);
		return toJPage(returnPage, pagination);
	}
	
	@Override
	public List<Role> getAllRoles(ServiceContext serviceContext) {
		return roleMapper.getAllRoles();
	}
	
	
	private void validateRoleCode(Role role) throws BusinessException{
		
		String code=role.getRoleCode();
		
		if(JStringUtils.isNullOrEmpty( code)){
			throw new IllegalArgumentException("role code  is null"); 
		}
		
		code=code.trim();
		
		if(ADMIN_CODE.equalsIgnoreCase( code)){
			throw new BusinessException("role code ["+ADMIN_CODE+"] is initialized by system.please change...");
		}
		
		if(DEFAULT_CODE.equalsIgnoreCase( code)){
			throw new BusinessException("role code ["+DEFAULT_CODE+"] is initialized by system.please change...");
		}
	}
	
	@Override
	public void saveRole(ServiceContext context, Role role)
			throws BusinessException {
		
		try{

			validateRoleCode(role);
			if(exists(context, role)){
				throw new BusinessException("role code ["+role.getRoleCode()+"] already has exist.");
			}
			saveOnly(context, role);
			
		}catch(Exception e){
			BusinessExceptionUtil.throwException(e);
		}
		
	}
	
	@Override
	public boolean exists(ServiceContext context, Role role)
			throws BusinessException {
		if(role==null){
			throw new IllegalArgumentException("role argument is null");
		}
		boolean exists=false;
		Role dbRole=getRoleByRoleCode(context, role.getRoleCode());
		// new created.
		if(JStringUtils.isNullOrEmpty(role.getId())){
			exists= dbRole!=null;
		}
		else{
			// updated status.
			if(dbRole!=null){
				// if it's self
				exists=!role.getId().equals(dbRole.getId());
			}
			else{
				exists=false;
			}
		}
		return exists;
	}
	
	@Override
	public void updateRole(ServiceContext context, Role role)
			throws BusinessException {
		try{
			validateRoleCode(role);
			
			if(JStringUtils.isNullOrEmpty(role.getId())){
				throw new IllegalArgumentException("the primary property id of role is null.");
			}
			
			if(exists(context, role)){
				throw new BusinessException("role code ["+role.getRoleCode()+"] already has exist.");
			}
			updateOnly(context, role);
			
		}catch(Exception e){
			BusinessExceptionUtil.throwException(e);
		}
		
	}
	
	@Override
	public void deleteRole(ServiceContext context, Role role)
			throws BusinessException {
		try{
			if(JStringUtils.isNullOrEmpty(role.getId())){
				throw new IllegalArgumentException("the primary property id of role is null.");
			}
			
			if(JStringUtils.isNullOrEmpty(role.getRoleCode())){
				role.setRoleCode(getById(context, role.getId()).getRoleCode());
			}
			validateRoleCode(role);
			delete(context, role.getId());
		}catch(Exception e){
			BusinessExceptionUtil.throwException(e);
		}
		
	}
	
}
