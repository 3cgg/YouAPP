/**
 * 
 */
package com.youappcorp.project.resource.mapper;

import j.jave.kernal.jave.model.support.JModelRepo;

import com.youappcorp.project.resource.model.Resource;
import com.youappcorp.project.resource.repo.ResourceRepo;

import j.jave.platform.mybatis.JMapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

/**
 * @author J
 *
 */
@Component(value="resourceMapper.mapper")
@JModelRepo(component="resourceMapper.mapper",name=Resource.class)
public interface ResourceMapper extends JMapper<Resource,String> ,ResourceRepo<JMapper<Resource,String>> {
	
	List<Resource> getResources();
	
	Resource getResourceByURL(@Param(value="url")String url);
	
}
