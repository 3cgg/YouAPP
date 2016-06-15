/**
 * 
 */
package com.youappcorp.project.containermanager.repo;

import j.jave.platform.webcomp.spirngjpa.JSpringJpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.youappcorp.project.containermanager.model.AppMeta;

/**
 * @author J
 *
 */
public interface AppMetaRepo extends JSpringJpaRepository<AppMeta,String> {

	@Query(value="from AppMeta where appName= :appName and appCompName= :appCompName and appVersion= :appVersion and deleted=1 ")
	AppMeta getAppMetaByConfig(
			@Param(value="appName")String appName,
			@Param(value="appCompName")String appCompName,
			@Param(value="appVersion")String appVersion);
	
	@Query(value="from AppMeta where unique= :unique and deleted=1")
	AppMeta getAppMetaByUnique(@Param(value="unique")String unique);
	
	@Query(value="select count(*)  from AppMeta where appName= :appName and appCompName= :appCompName and appVersion= :appVersion and deleted=1 ")
	long getCountByConfig(
			@Param(value="appName")String appName,
			@Param(value="appCompName")String appCompName,
			@Param(value="appVersion")String appVersion);
	
}
