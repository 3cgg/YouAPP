/**
 * 
 */
package j.jave.framework.components.login.mapper;

import java.util.List;

import j.jave.framework.components.login.model.UserRole;
import j.jave.framework.model.support.JModelMapper;
import j.jave.framework.mybatis.JMapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

/**
 * @author J
 *
 */
@Component(value="UserRoleMapper")
@JModelMapper(component="UserRoleMapper",name=UserRole.class)
public interface UserRoleMapper extends JMapper<UserRole> {

	List<UserRole> getUserRolesByUserId(@Param(value="userId")String userId);
	
	int countOnUserIdAndRoleId(@Param(value="userId")String userId,@Param(value="roleId")String roleId);
	
	UserRole getUserRoleOnUserIdAndRoleId(@Param(value="userId")String userId,@Param(value="roleId")String roleId);
}
