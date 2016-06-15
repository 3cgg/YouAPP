/**
 * 
 */
package com.youappcorp.project.containermanager.repo;

import j.jave.platform.webcomp.spirngjpa.JSpringJpaRepository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.youappcorp.project.containermanager.model.URLMappingMeta;

/**
 * @author J
 *
 */
public interface URLMappingMetaRepo extends JSpringJpaRepository<URLMappingMeta,String> {

	@Query(value="from URLMappingMeta where id= :id and deleted=1")
	URLMappingMeta getURLMappingMetaById(@Param(value="id")String id);
	
	@Query(value="from URLMappingMeta where appId= :appId and deleted=1")
	List<URLMappingMeta> getAllURLMappingMetasByAppId(@Param(value="appId")String appId);
	
}
