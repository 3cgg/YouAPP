/**
 * 
 */
package j.jave.framework.components.login.mapper;

import java.util.List;

import j.jave.framework.components.login.model.User;
import j.jave.framework.components.login.model.UserTracker;
import j.jave.framework.mybatis.JMapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

/**
 * @author Administrator
 *
 */
@Component(value="UserTrackerMapper")
public interface UserTrackerMapper extends JMapper<User> {

	
	public List<UserTracker> getUserTrackerByName(
			@Param(value="userName")String userName);
	
	
}
