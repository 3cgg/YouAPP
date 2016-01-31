package j.jave.platform.basicwebcomp.login.service;

import j.jave.kernal.eventdriven.exception.JServiceException;
import j.jave.kernal.jave.model.JPagination;
import j.jave.kernal.jave.utils.JStringUtils;
import j.jave.platform.basicwebcomp.core.service.ServiceContext;
import j.jave.platform.basicwebcomp.core.service.ServiceSupport;
import j.jave.platform.basicwebcomp.login.mapper.RoleMapper;
import j.jave.platform.basicwebcomp.login.model.Role;
import j.jave.platform.mybatis.JMapper;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service(value="roleServiceImpl.transation")
public class RoleServiceImpl extends ServiceSupport<Role> implements RoleService {

	@Autowired
	private RoleMapper roleMapper;
	
	@Autowired
	private RoleGroupService roleGroupService;
	
	
	@Override
	protected JMapper<Role> getMapper() {
		return this.roleMapper;
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
	public List<Role> getRoleByRoleNameByPage(ServiceContext serviceContext,
			JPagination pagination) {
		return roleMapper.getRoleByRoleNameByPage(pagination);
	}
	
	@Override
	public List<Role> getAllRoles(ServiceContext serviceContext) {
		return roleMapper.getAllRoles();
	}
	
	
	private void validateRoleCode(Role role) throws JServiceException{
		
		String code=role.getRoleCode();
		
		if(JStringUtils.isNullOrEmpty( code)){
			throw new IllegalArgumentException("role code  is null"); 
		}
		
		code=code.trim();
		
		if(ADMIN_CODE.equalsIgnoreCase( code)){
			throw new JServiceException("role code ["+ADMIN_CODE+"] is initialized by system.please change...");
		}
		
		if(DEFAULT_CODE.equalsIgnoreCase( code)){
			throw new JServiceException("role code ["+DEFAULT_CODE+"] is initialized by system.please change...");
		}
	}
	
	@Override
	public void saveRole(ServiceContext context, Role role)
			throws JServiceException {
		validateRoleCode(role);
		if(exists(context, role)){
			throw new JServiceException("role code ["+role.getRoleCode()+"] already has exist.");
		}
		saveOnly(context, role);
	}
	
	@Override
	public boolean exists(ServiceContext context, Role role)
			throws JServiceException {
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
			throws JServiceException {
		
		validateRoleCode(role);
		
		if(JStringUtils.isNullOrEmpty(role.getId())){
			throw new IllegalArgumentException("the primary property id of role is null.");
		}
		
		if(exists(context, role)){
			throw new JServiceException("role code ["+role.getRoleCode()+"] already has exist.");
		}
		updateOnly(context, role);
	}
	
	@Override
	public void deleteRole(ServiceContext context, Role role)
			throws JServiceException {
		if(JStringUtils.isNullOrEmpty(role.getId())){
			throw new IllegalArgumentException("the primary property id of role is null.");
		}
		
		if(JStringUtils.isNullOrEmpty(role.getRoleCode())){
			role.setRoleCode(getById(context, role.getId()).getRoleCode());
		}
		validateRoleCode(role);
		delete(context, role.getId());
	}
	
}
