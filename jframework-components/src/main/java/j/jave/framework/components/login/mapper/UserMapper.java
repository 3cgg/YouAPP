/**
 * 
 */
package j.jave.framework.components.login.mapper;

import java.util.List;

import j.jave.framework.components.login.model.User;
import j.jave.framework.mybatis.JMapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

/**
 * @author Administrator
 *
 */
@Component(value="UserMapper")
public interface UserMapper extends JMapper<User> {

	public User getUserByNameAndPassword(
			@Param(value="userName")String userName,
			@Param(value="password")String password);
	
	public User getUserByName(
			@Param(value="userName")String userName);
	
	public List<User> getUsersByPage(User user) ;
	
}
