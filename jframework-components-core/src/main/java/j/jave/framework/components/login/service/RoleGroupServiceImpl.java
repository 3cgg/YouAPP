package j.jave.framework.components.login.service;

import j.jave.framework.components.core.service.ServiceContext;
import j.jave.framework.components.core.service.ServiceSupport;
import j.jave.framework.components.login.mapper.RoleGroupMapper;
import j.jave.framework.components.login.model.RoleGroup;
import j.jave.framework.mybatis.JMapper;
import j.jave.framework.servicehub.exception.JServiceException;
import j.jave.framework.utils.JUniqueUtils;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service(value="roleGroupServiceImpl.transation")
public class RoleGroupServiceImpl extends ServiceSupport<RoleGroup> implements RoleGroupService {

	@Autowired
	private RoleGroupMapper  roleGroupMapper;
	
	@Override
	protected JMapper<RoleGroup> getMapper() {
		return this.roleGroupMapper;
	}
	
	@Override
	public void bingRoleGroup(ServiceContext serviceContext,String roleId,String groupId) throws JServiceException {
		if(isBing(serviceContext, roleId, groupId)){
			throw new JServiceException("the role had already belong to the group.");
		}
		
		RoleGroup userGroup=new RoleGroup();
		userGroup.setRoleId(roleId);
		userGroup.setGroupId(groupId);
		userGroup.setId(JUniqueUtils.unique());
		saveOnly(serviceContext, userGroup);
	}
	
	@Override
	public void unbingRoleGroup(ServiceContext serviceContext,String roleId,String groupId) throws JServiceException {
		RoleGroup userGroup=getRoleGroupOnRoleIdAndGroupId(serviceContext, roleId, groupId);
		if(userGroup==null){
			throw new JServiceException("the user doesnot belong to the group.");
		}
		delete(serviceContext, userGroup.getId());
	}

	@Override
	public RoleGroup getRoleGroupOnRoleIdAndGroupId(
			ServiceContext serviceContext, String roleId, String groupId)
			throws JServiceException {
		return roleGroupMapper.getRoleGroupOnRoleIdAndGroupId(roleId, groupId);
	}
	
	@Override
	public boolean isBing(ServiceContext serviceContext,String roleId,String groupId) {
		int count=roleGroupMapper.countOnRoleIdAndGroupId(roleId, groupId);
		return count>0;
	}

	@Override
	public List<RoleGroup> getRoleGroupsByRoleId(ServiceContext serviceContext,
			String roleId) {
		return roleGroupMapper.getRoleGroupsOnRoleIdOrGroupId(roleId, null);
	}

	@Override
	public List<RoleGroup> getRoleGroupsByGroupId(
			ServiceContext serviceContext, String groupId) {
		return roleGroupMapper.getRoleGroupsOnRoleIdOrGroupId(null, groupId);
	}
	
}
