/**
 * 
 */
package com.youappcorp.project.usermanager.mapper;

import j.jave.platform.mybatis.JMapper;
import me.bunny.kernel._c.model.support.JModelRepo;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import com.youappcorp.project.usermanager.model.UserTracker;
import com.youappcorp.project.usermanager.repo.UserTrackerRepo;

/**
 * @author J
 */
@Component(value="userTrackerMapper.mapper")
@JModelRepo(component="userTrackerMapper.mapper",name=UserTracker.class)
public interface UserTrackerMapper extends JMapper<UserTracker,String>,UserTrackerRepo<JMapper<UserTracker,String>> {

	
	public List<UserTracker> getUserTrackerByName(
			@Param(value="userName")String userName);
	
	
}
