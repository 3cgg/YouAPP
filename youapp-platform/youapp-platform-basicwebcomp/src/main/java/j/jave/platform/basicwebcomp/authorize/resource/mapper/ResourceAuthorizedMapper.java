package j.jave.platform.basicwebcomp.authorize.resource.mapper;

import j.jave.platform.basicwebcomp.authorize.resource.model.ResourceAuthorized;
import j.jave.platform.basicwebcomp.authorize.resource.repo.ResourceAuthorizedRepo;
import j.jave.platform.mybatis.JMapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;


@Component(value="ResourceAuthorizedMapper")
public interface ResourceAuthorizedMapper extends JMapper,ResourceAuthorizedRepo{

	public List<ResourceAuthorized> getResourceAuthorizedByUserId(@Param(value="userId") 
	String userId);
	
}
