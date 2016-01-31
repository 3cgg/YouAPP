/**
 * 
 */
package j.jave.platform.basicwebcomp.login.mapper;

import j.jave.kernal.jave.model.JPagination;
import j.jave.kernal.jave.model.support.JModelMapper;
import j.jave.platform.basicwebcomp.login.model.Group;
import j.jave.platform.mybatis.JMapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

/**
 * @author J
 *
 */
@Component(value="GroupMapper")
@JModelMapper(component="GroupMapper",name=Group.class)
public interface GroupMapper extends JMapper<Group> {

	Group getGroupByGroupCode(@Param(value="groupCode")String roleCode);
	
	/**
	 * GET ALL GROUPS ACCORDING TO 'ROLE NAME'
	 * @param pagination
	 * @return
	 */
	List<Group> getGroupByGroupNameByPage(JPagination pagination);
	
	/**
	 * GET ALL GROUPS.
	 * @return
	 */
	List<Group> getAllGroups();
}
