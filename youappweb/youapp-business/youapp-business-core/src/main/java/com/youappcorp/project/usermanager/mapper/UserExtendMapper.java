/**
 * 
 */
package com.youappcorp.project.usermanager.mapper;

import j.jave.kernal.jave.model.support.JModelRepo;
import j.jave.platform.mybatis.JMapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import com.youappcorp.project.usermanager.model.UserExtend;
import com.youappcorp.project.usermanager.repo.UserExtendRepo;

/**
 * @author J
 */
@Component(value="userExtendMapper.mapper")
@JModelRepo(name=UserExtend.class,component="userExtendMapper.mapper")
public interface UserExtendMapper extends JMapper<UserExtend,String >,UserExtendRepo<JMapper<UserExtend ,String>> {
	
	public UserExtend getUserExtendByUserId(@Param(value="userId")String userId);

	public UserExtend getUserExtendByNatureName(@Param(value="natureName")String natureName);
	
}
