/**
 * 
 */
package j.jave.framework.components.login.mapper;

import j.jave.framework.commons.model.support.JModelMapper;
import j.jave.framework.components.login.model.UserTracker;
import j.jave.framework.mybatis.JMapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

/**
 * @author J
 */
@Component(value="UserTrackerMapper")
@JModelMapper(component="UserTrackerMapper",name=UserTracker.class)
public interface UserTrackerMapper extends JMapper<UserTracker> {

	
	public List<UserTracker> getUserTrackerByName(
			@Param(value="userName")String userName);
	
	
}
