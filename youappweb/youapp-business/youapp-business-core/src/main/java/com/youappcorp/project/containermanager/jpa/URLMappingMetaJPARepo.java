/**
 * 
 */
package com.youappcorp.project.containermanager.jpa;

import j.jave.platform.jpa.springjpa.JSpringJpaRepository;

import com.youappcorp.project.containermanager.model.URLMappingMeta;

/**
 * @author J
 *
 */
public interface URLMappingMetaJPARepo extends JSpringJpaRepository<URLMappingMeta,String> {
	
}
