/**
 * 
 */
package j.jave.platform.basicwebcomp.login.mapper;

import j.jave.kernal.jave.model.support.JModelRepo;
import j.jave.platform.basicwebcomp.login.model.UserExtend;
import j.jave.platform.basicwebcomp.login.repo.UserExtendRepo;
import j.jave.platform.mybatis.JMapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

/**
 * @author J
 */
@Component(value="userExtendMapper.mapper")
@JModelRepo(name=UserExtend.class,component="userExtendMapper.mapper")
public interface UserExtendMapper extends JMapper<UserExtend >,UserExtendRepo<JMapper<UserExtend >> {
	
	public UserExtend getUserExtendByUserId(@Param(value="userId")String userId);

	public UserExtend getUserExtendByNatureName(@Param(value="natureName")String natureName);
	
}
