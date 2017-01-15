/**
 * 
 */
package com.youappcorp.project.containermanager.jpa;

import com.youappcorp.project.containermanager.model.AppMeta;

import me.bunny.app._c.jpa.springjpa.JSpringJpaRepository;

/**
 * @author J
 *
 */
public interface AppMetaJPARepo extends JSpringJpaRepository<AppMeta,String> {
	
}
