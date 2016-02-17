package j.jave.platform.basicwebcomp.login.service;

import j.jave.kernal.eventdriven.exception.JServiceException;
import j.jave.kernal.jave.persist.JIPersist;
import j.jave.kernal.jave.utils.JUniqueUtils;
import j.jave.platform.basicwebcomp.core.service.ServiceContext;
import j.jave.platform.basicwebcomp.core.service.ServiceSupport;
import j.jave.platform.basicwebcomp.login.model.UserGroup;
import j.jave.platform.basicwebcomp.login.repo.UserGroupRepo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service(value="userGroupServiceImpl.transation")
public class UserGroupServiceImpl extends ServiceSupport<UserGroup> implements UserGroupService {

	@Autowired
	private UserGroupRepo<?> userGroupMapper;
	
	@Override
	public JIPersist<?, UserGroup> getRepo() {
		return userGroupMapper;
	}
	@Override
	public List<UserGroup> getUserGroupsByUserId(ServiceContext serviceContext,
			String userId) {
		return userGroupMapper.getUserGroupsByUserId(userId);
	}
	
	@Override
	public void bingUserGroup(ServiceContext serviceContext, String userId,
			String groupId) throws JServiceException {
		if(isBing(serviceContext, userId, groupId)){
			throw new JServiceException("the user had already belong to the group.");
		}
		
		UserGroup userGroup=new UserGroup();
		userGroup.setUserId(userId);
		userGroup.setGroupId(groupId);
		userGroup.setId(JUniqueUtils.unique());
		saveOnly(serviceContext, userGroup);
	}
	
	@Override
	public void unbingUserGroup(ServiceContext serviceContext, String userId,
			String groupId) throws JServiceException {
		
		UserGroup userGroup=getUserGroupOnUserIdAndGroupId(serviceContext, userId, groupId);
		if(userGroup==null){
			throw new JServiceException("the user doesnot belong to the group.");
		}
		delete(serviceContext, userGroup.getId());
	}
	
	@Override
	public UserGroup getUserGroupOnUserIdAndGroupId(ServiceContext serviceContext,
			String userId, String groupId) {
		return userGroupMapper.getUserGroupOnUserIdAndGroupId(userId, groupId);
	}
	
	@Override
	public boolean isBing(ServiceContext serviceContext, String userId,
			String groupId) {
		int count=userGroupMapper.countOnUserIdAndGroupId(userId, groupId);
		return count>0;
	}
	
	
}
