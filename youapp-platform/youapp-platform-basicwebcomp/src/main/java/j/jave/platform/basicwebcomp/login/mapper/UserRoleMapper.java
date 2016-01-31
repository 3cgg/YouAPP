/**
 * 
 */
package j.jave.platform.basicwebcomp.login.mapper;

import j.jave.kernal.jave.model.support.JModelMapper;
import j.jave.platform.basicwebcomp.login.model.UserRole;
import j.jave.platform.mybatis.JMapper;

import java.util.List;

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
