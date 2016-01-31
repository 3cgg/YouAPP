/**
 * 
 */
package j.jave.platform.basicwebcomp.login.mapper;

import j.jave.kernal.jave.model.support.JModelMapper;
import j.jave.platform.basicwebcomp.login.model.UserExtend;
import j.jave.platform.mybatis.JMapper;

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
