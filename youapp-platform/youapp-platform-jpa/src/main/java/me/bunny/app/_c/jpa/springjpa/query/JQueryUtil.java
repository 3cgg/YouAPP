package me.bunny.app._c.jpa.springjpa.query;

import javax.persistence.EntityManager;

public class JQueryUtil {

	
	public static EntityManager getEntityManager(JQuery<?> query){
		return query.em;
	}
	
	
}
