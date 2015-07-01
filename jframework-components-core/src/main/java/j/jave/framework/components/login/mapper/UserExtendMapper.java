/**
 * 
 */
package j.jave.framework.components.login.mapper;

import j.jave.framework.commons.model.support.JModelMapper;
import j.jave.framework.components.login.model.UserExtend;
import j.jave.framework.mybatis.JMapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

/**
 * @author J
 */
@Component(value="UserExtendMapper")
@JModelMapper(name=UserExtend.class,component="UserExtendMapper")
public interface UserExtendMapper extends JMapper<UserExtend > {
	
	public UserExtend getUserExtendByUserId(@Param(value="userId")String userId);

	public UserExtend getUserExtendByNatureName(@Param(value="natureName")String natureName);
	
}
