/**
 * 
 */
package com.youappcorp.project.containermanager.jpa;

import j.jave.platform.jpa.springjpa.JSpringJpaRepository;

import com.youappcorp.project.containermanager.model.AppMeta;

/**
 * @author J
 *
 */
public interface AppMetaJPARepo extends JSpringJpaRepository<AppMeta,String> {
	
}
