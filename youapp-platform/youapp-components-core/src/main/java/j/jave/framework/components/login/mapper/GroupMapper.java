/**
 * 
 */
package j.jave.framework.components.login.mapper;

import j.jave.framework.commons.model.JPagination;
import j.jave.framework.commons.model.support.JModelMapper;
import j.jave.framework.components.login.model.Group;
import j.jave.framework.mybatis.JMapper;

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
