package j.jave.framework.components.login.service;

import j.jave.framework.components.core.service.ServiceSupport;
import j.jave.framework.components.login.mapper.UserGroupMapper;
import j.jave.framework.components.login.model.UserGroup;
import j.jave.framework.mybatis.JMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service(value="userGroupServiceImpl")
public class UserGroupServiceImpl extends ServiceSupport<UserGroup> implements UserGroupService {

	@Autowired
	private UserGroupMapper userGroupMapper;
	
	@Override
	protected JMapper<UserGroup> getMapper() {
		return this.userGroupMapper;
	}
	
	
	
}
