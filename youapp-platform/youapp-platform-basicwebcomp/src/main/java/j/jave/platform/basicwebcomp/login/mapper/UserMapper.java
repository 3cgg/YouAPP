/**
 * 
 */
package j.jave.platform.basicwebcomp.login.mapper;

import j.jave.kernal.jave.model.JPageable;
import j.jave.kernal.jave.model.support.JModelRepo;
import j.jave.platform.basicwebcomp.login.model.User;
import j.jave.platform.basicwebcomp.login.repo.UserRepo;
import j.jave.platform.mybatis.JMapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

/**
 * @author Administrator
 *
 */
@Component(value="userMapper.mapper")
@JModelRepo(name=User.class,component="userMapper.mapper")
public interface UserMapper extends JMapper<User>,UserRepo<JMapper<User> > {

	public User getUserByNameAndPassword(
			@Param(value="userName")String userName,
			@Param(value="password")String password);
	
	public User getUserByName(
			@Param(value="userName")String userName);
	
	public List<User> getUsersByPage(JPageable pagination) ;
	
	public List<User> getUsers();
	
}
