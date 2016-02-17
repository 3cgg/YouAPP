/**
 * 
 */
package j.jave.platform.basicwebcomp.login.mapper;

import j.jave.kernal.jave.model.support.JModelMapper;
import j.jave.platform.basicwebcomp.login.model.UserGroup;
import j.jave.platform.basicwebcomp.login.repo.UserGroupRepo;
import j.jave.platform.mybatis.JMapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

/**
 * @author J
 *
 */
@Component(value="UserGroupMapper")
@JModelMapper(component="UserGroupMapper",name=UserGroup.class)
public interface UserGroupMapper extends JMapper<UserGroup>,UserGroupRepo<JMapper<UserGroup>> {
	
	List<UserGroup> getUserGroupsByUserId(@Param(value="userId")String userId);
	
	int countOnUserIdAndGroupId(@Param(value="userId")String userId,@Param(value="groupId")String groupId);
	
	UserGroup getUserGroupOnUserIdAndGroupId(@Param(value="userId")String userId,@Param(value="groupId")String groupId);
	
}
