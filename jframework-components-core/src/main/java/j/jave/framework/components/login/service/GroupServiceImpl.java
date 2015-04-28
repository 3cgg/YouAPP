package j.jave.framework.components.login.service;

import j.jave.framework.components.core.service.ServiceSupport;
import j.jave.framework.components.login.mapper.GroupMapper;
import j.jave.framework.components.login.model.Group;
import j.jave.framework.mybatis.JMapper;

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
	
	
	
}
