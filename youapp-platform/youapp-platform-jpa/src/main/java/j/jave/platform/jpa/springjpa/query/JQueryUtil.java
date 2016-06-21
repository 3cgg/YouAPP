package j.jave.platform.jpa.springjpa.query;

import javax.persistence.EntityManager;

public class JQueryUtil {

	
	public static EntityManager getEntityManager(JQuery<?> query){
		return query.em;
	}
	
	
}
