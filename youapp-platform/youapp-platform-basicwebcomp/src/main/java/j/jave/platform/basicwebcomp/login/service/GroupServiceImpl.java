package j.jave.platform.basicwebcomp.login.service;

import j.jave.kernal.eventdriven.exception.JServiceException;
import j.jave.kernal.jave.model.JPage;
import j.jave.kernal.jave.model.JPageImpl;
import j.jave.kernal.jave.model.JPageable;
import j.jave.kernal.jave.persist.JIPersist;
import j.jave.kernal.jave.utils.JStringUtils;
import j.jave.platform.basicwebcomp.core.service.ServiceContext;
import j.jave.platform.basicwebcomp.core.service.ServiceSupport;
import j.jave.platform.basicwebcomp.login.model.Group;
import j.jave.platform.basicwebcomp.login.repo.GroupRepo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service(value="groupServiceImpl.transation")
public class GroupServiceImpl extends ServiceSupport<Group> implements GroupService {

	@Autowired
	private GroupRepo<?> groupMapper;
	
	@Override
	public JIPersist<?, Group> getRepo() {
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
			throws JServiceException {
		
		validateGroupCode(group);
		
		if(exists(context, group)){
			throw new JServiceException("group code ["+group.getGroupCode()+"] already has exist.");
		}
		saveOnly(context, group);
	}
	
	@Override
	public boolean exists(ServiceContext context, Group group)
			throws JServiceException {
		if(group==null){
			throw new IllegalArgumentException("role argument is null");
		}
		boolean exists=false;
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
		return exists;
	}
	
	private void validateGroupCode(Group group) throws JServiceException{
		
		String code=group.getGroupCode();
		
		if(JStringUtils.isNullOrEmpty(code)){
			throw new IllegalArgumentException("group code  is null"); 
		}
		code=code.trim();
		
		if(ADMIN_CODE.equalsIgnoreCase(code)){
			throw new JServiceException("group code ["+ADMIN_CODE+"] is initialized by system.please change...");
		}
		
		if(DEFAULT_CODE.equalsIgnoreCase(code)){
			throw new JServiceException("group code ["+DEFAULT_CODE+"] is initialized by system.please change...");
		}
	}
	
	@Override
	public void updateGroup(ServiceContext context, Group group)
			throws JServiceException {
		
		validateGroupCode(group);
		
		if(JStringUtils.isNullOrEmpty(group.getId())){
			throw new IllegalArgumentException("the primary property id of group is null.");
		}
		
		if(exists(context, group)){
			throw new JServiceException("group code ["+group.getGroupCode()+"] already has exist.");
		}
		updateOnly(context, group);
	}
	
	@Override
	public void deleteGroup(ServiceContext context, Group group)
			throws JServiceException {
		
		if(JStringUtils.isNullOrEmpty(group.getId())){
			throw new IllegalArgumentException("the primary property id of group is null.");
		}
		
		if(JStringUtils.isNullOrEmpty(group.getGroupCode())){
			group.setGroupCode(getById(context, group.getId()).getGroupCode());
		}
		
		validateGroupCode(group);
		delete(context, group.getId());
	}
	
	
}
