package j.jave.framework.components.authorize.resource.mapper;

import j.jave.framework.components.authorize.resource.model.ResourceAuthorized;
import j.jave.framework.mybatis.JMapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;


@Component(value="ResourceAuthorizedMapper")
public interface ResourceAuthorizedMapper extends JMapper{

	public List<ResourceAuthorized> getResourceAuthorizedByUserId(@Param(value="userId") 
	String userId);
	
}
