package j.jave.platform.webcomp.core.service;

import j.jave.platform.data.web.model.SimplePageRequest;
import j.jave.platform.jpa.springjpa.query.JCondition.Condition;
import me.bunny.kernel.jave.logging.JLogger;
import me.bunny.kernel.jave.logging.JLoggerFactory;
import me.bunny.kernel.jave.model.JModel;
import me.bunny.kernel.jave.model.JPage;
import me.bunny.kernel.jave.model.JPageable;
import me.bunny.kernel.jave.service.JService;
import j.jave.platform.jpa.springjpa.query.JQueryBuilder;

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
