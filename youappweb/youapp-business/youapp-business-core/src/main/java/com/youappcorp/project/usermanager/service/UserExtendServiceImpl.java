/**
 * 
 */
package com.youappcorp.project.usermanager.service;

import j.jave.kernal.jave.persist.JIPersist;
import j.jave.kernal.jave.utils.JStringUtils;
import j.jave.platform.basicwebcomp.core.service.InternalServiceSupport;
import j.jave.platform.basicwebcomp.core.service.ServiceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.youappcorp.project.BusinessException;
import com.youappcorp.project.BusinessExceptionUtil;
import com.youappcorp.project.usermanager.model.UserExtend;
import com.youappcorp.project.usermanager.repo.UserExtendRepo;

/**
 * @author J
 *
 */
@Service(value="UserExtendServiceImpl.transation.jpa")
public class UserExtendServiceImpl extends InternalServiceSupport<UserExtend> implements
		UserExtendService {
	
	@Autowired
	private UserExtendRepo<?> userExtendMapper;
	
	@Override
	public JIPersist<?, UserExtend> getRepo() {
		return userExtendMapper;
	}
	
	@Override
	public void saveUserExtend(ServiceContext context, UserExtend userExtend)
			throws BusinessException {
		
		try{
			

			if(JStringUtils.isNullOrEmpty(userExtend.getUserId())){
				throw new BusinessException("user id is missing , when inserting user extenstion.");
			}
			
			if(JStringUtils.isNullOrEmpty(userExtend.getUserName())){
				throw new BusinessException("user name is missing , when inserting user extenstion.");
			}
			
			String natureName=userExtend.getNatureName();
			if(JStringUtils.isNotNullOrEmpty(natureName)){
				UserExtend dbUserExtend=getUserExtendByNatureName(context, userExtend.getNatureName());
				if(dbUserExtend!=null){
					throw new BusinessException("nature name already exists, please change others.");
				}
			}
			
			saveOnly(context, userExtend);
		}catch(Exception e){
			BusinessExceptionUtil.throwException(e);
		}
		
	}

	@Override
	public void updateUserExtend(ServiceContext context, UserExtend userExtend)
			throws BusinessException {
		
		try{
			if(JStringUtils.isNullOrEmpty(userExtend.getUserId())){
				throw new BusinessException("user id is missing , when inserting user extenstion.");
			}
			
			if(JStringUtils.isNullOrEmpty(userExtend.getUserName())){
				throw new BusinessException("user name is missing , when inserting user extenstion.");
			}
			
			String natureName=userExtend.getNatureName();
			if(JStringUtils.isNotNullOrEmpty(natureName)){
				UserExtend dbUserExtend=getUserExtendByNatureName(context, userExtend.getNatureName());
				if(dbUserExtend!=null&&!userExtend.getId().equals(dbUserExtend.getId())){
					throw new BusinessException("nature name already exists, please change others.");
				}
			}
			
			updateOnly(context, userExtend);
			
		}catch(Exception e){
			BusinessExceptionUtil.throwException(e);
		}
		
		
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
