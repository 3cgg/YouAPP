/**
 * 
 */
package com.youappcorp.project.usermanager.mapper;

import j.jave.kernal.jave.model.JPageable;
import j.jave.kernal.jave.model.support.JModelRepo;
import j.jave.platform.mybatis.JMapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import com.youappcorp.project.usermanager.model.Group;
import com.youappcorp.project.usermanager.repo.GroupRepo;

/**
 * @author J
 *
 */
@Component(value="groupMapper.mapper")
@JModelRepo(component="groupMapper.mapper",name=Group.class)
public interface GroupMapper extends JMapper<Group>,GroupRepo<JMapper<Group>> {

	Group getGroupByGroupCode(@Param(value="groupCode")String roleCode);
	
	/**
	 * GET ALL GROUPS ACCORDING TO 'ROLE NAME'
	 * @param pagination
	 * @return
	 */
	List<Group> getGroupByGroupNameByPage(JPageable pagination);
	
	/**
	 * GET ALL GROUPS.
	 * @return
	 */
	List<Group> getAllGroups();
}
