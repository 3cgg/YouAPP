/**
 * 
 */
package j.jave.platform.basicwebcomp.login.service;

import j.jave.kernal.eventdriven.exception.JServiceException;
import j.jave.kernal.jave.utils.JStringUtils;
import j.jave.platform.basicwebcomp.core.service.ServiceContext;
import j.jave.platform.basicwebcomp.core.service.ServiceSupport;
import j.jave.platform.basicwebcomp.login.mapper.UserExtendMapper;
import j.jave.platform.basicwebcomp.login.model.UserExtend;
import j.jave.platform.mybatis.JMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author J
 *
 */
@Service(value="UserExtendServiceImpl")
public class UserExtendServiceImpl extends ServiceSupport<UserExtend> implements
		UserExtendService {
	
	@Autowired
	private UserExtendMapper userExtendMapper;
	
	@Override
	protected JMapper<UserExtend> getMapper() {
		return userExtendMapper;
	}

	@Override
	public void saveUserExtend(ServiceContext context, UserExtend userExtend)
			throws JServiceException {
		
		if(JStringUtils.isNullOrEmpty(userExtend.getUserId())){
			throw new JServiceException("user id is missing , when inserting user extenstion.");
		}
		
		if(JStringUtils.isNullOrEmpty(userExtend.getUserName())){
			throw new JServiceException("user name is missing , when inserting user extenstion.");
		}
		
		String natureName=userExtend.getNatureName();
		if(JStringUtils.isNotNullOrEmpty(natureName)){
			UserExtend dbUserExtend=getUserExtendByNatureName(context, userExtend.getNatureName());
			if(dbUserExtend!=null){
				throw new JServiceException("nature name already exists, please change others.");
			}
		}
		
		saveOnly(context, userExtend);
	}

	@Override
	public void updateUserExtend(ServiceContext context, UserExtend userExtend)
			throws JServiceException {
		
		if(JStringUtils.isNullOrEmpty(userExtend.getUserId())){
			throw new JServiceException("user id is missing , when inserting user extenstion.");
		}
		
		if(JStringUtils.isNullOrEmpty(userExtend.getUserName())){
			throw new JServiceException("user name is missing , when inserting user extenstion.");
		}
		
		String natureName=userExtend.getNatureName();
		if(JStringUtils.isNotNullOrEmpty(natureName)){
			UserExtend dbUserExtend=getUserExtendByNatureName(context, userExtend.getNatureName());
			if(dbUserExtend!=null&&!userExtend.getId().equals(dbUserExtend.getId())){
				throw new JServiceException("nature name already exists, please change others.");
			}
		}
		
		updateOnly(context, userExtend);
	}

	@Override
	public UserExtend getUserExtendByUserId(ServiceContext context,
			String userId) {
		return userExtendMapper.getUserExtendByUserId(userId);
	}

	@Override
	public UserExtend getUserExtendByNatureName(ServiceContext context,
			String natureName) {
		return userExtendMapper.getUserExtendByNatureName(natureName);
	}


	
}