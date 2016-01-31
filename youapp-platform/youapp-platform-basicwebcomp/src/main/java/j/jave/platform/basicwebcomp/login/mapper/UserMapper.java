/**
 * 
 */
package j.jave.platform.basicwebcomp.login.mapper;

import j.jave.kernal.jave.model.JPagination;
import j.jave.kernal.jave.model.support.JModelMapper;
import j.jave.platform.basicwebcomp.login.model.User;
import j.jave.platform.mybatis.JMapper;

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
