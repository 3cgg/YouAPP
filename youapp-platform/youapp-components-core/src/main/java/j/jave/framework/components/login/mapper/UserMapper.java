/**
 * 
 */
package j.jave.framework.components.login.mapper;

import j.jave.framework.commons.model.JPagination;
import j.jave.framework.commons.model.support.JModelMapper;
import j.jave.framework.components.login.model.User;
import j.jave.framework.mybatis.JMapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

/**
 * @author Administrator
 *
 */
@Component(value="UserMapper")
@JModelMapper(name=User.class,component="UserMapper")
public interface UserMapper extends JMapper<User> {

	public User getUserByNameAndPassword(
			@Param(value="userName")String userName,
			@Param(value="password")String password);
	
	public User getUserByName(
			@Param(value="userName")String userName);
	
	public List<User> getUsersByPage(JPagination pagination) ;
	
	public List<User> getUsers();
	
}
