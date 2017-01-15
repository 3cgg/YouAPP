package me.bunny.app._c._web.core.service;

import me.bunny.app._c.data.web.model.SimplePageRequest;
import me.bunny.app._c.jpa.springjpa.query.JQueryBuilder;
import me.bunny.app._c.jpa.springjpa.query.JCondition.Condition;
import me.bunny.kernel._c.logging.JLogger;
import me.bunny.kernel._c.logging.JLoggerFactory;
import me.bunny.kernel._c.model.JModel;
import me.bunny.kernel._c.model.JPage;
import me.bunny.kernel._c.model.JPageable;
import me.bunny.kernel._c.service.JService;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.data.domain.Page;

/**
 * @author J
 * @param <T>
 */
public abstract class ServiceSupport implements JService{
	
	protected final JLogger logger=JLoggerFactory.getLogger(getClass());
	
	@PersistenceContext
	private EntityManager em;
	
	/**
	 * return the complete {@link JPage} instance.
	 * @param returnPage  THE {@link Page} instance returned from underlying ORM
	 * @param pageable the passing pagination parameters
	 * @return
	 */
	protected <M extends JModel> JPage<M> toJPage(Page<M> returnPage,JPageable pageable){
		return ServiceSupportUtil.toJPage(returnPage, pageable);
	}
	
	protected SimplePageRequest toPageRequest(JPageable pageable){
		return ServiceSupportUtil.toPageRequest(pageable);
	}
	
	protected JQueryBuilder queryBuilder(){
		return JQueryBuilder.get(em);
	}
	
	protected Map<String, Object> params(Map<String, Condition> params){
		Map<String, Object> realParams=new HashMap<String, Object>();
		for(Entry<String, Condition> entry:params.entrySet() ){
			realParams.put(entry.getKey(), entry.getValue().getValue());
		}
		return realParams;
	}
}
