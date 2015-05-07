package j.jave.framework.components.login.service;

import j.jave.framework.components.core.service.ServiceContext;
import j.jave.framework.components.core.service.ServiceSupport;
import j.jave.framework.components.login.mapper.GroupMapper;
import j.jave.framework.components.login.model.Group;
import j.jave.framework.model.JPagination;
import j.jave.framework.mybatis.JMapper;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service(value="groupServiceImpl.transation")
public class GroupServiceImpl extends ServiceSupport<Group> implements GroupService {

	@Autowired
	private GroupMapper groupMapper;
	
	@Override
	protected JMapper<Group> getMapper() {
		return this.groupMapper;
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
	public List<Group> getGroupByGroupNameByPage(ServiceContext serviceContext,
			JPagination pagination) {
		return groupMapper.getGroupByGroupNameByPage(pagination);
	}
	
	@Override
	public List<Group> getAllGroups(ServiceContext serviceContext) {
		return groupMapper.getAllGroups();
	}
	
	
	
}
