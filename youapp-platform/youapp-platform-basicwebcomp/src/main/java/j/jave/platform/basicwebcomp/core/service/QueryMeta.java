package j.jave.platform.basicwebcomp.core.service;

import j.jave.kernal.jave.model.JPageable;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;

public abstract class QueryMeta {

	protected Map<String, Object> params=new HashMap<String, Object>();
	
	protected final EntityManager em;
	
	protected JPageable pageable;
	
	protected Class<?> result;
	
	protected boolean single;

	public QueryMeta(EntityManager em) {
		super();
		this.em = em;
	}
	
	public JPageable getPageable() {
		return pageable;
	}
	public void setPageable(JPageable pageable) {
		this.pageable = pageable;
	}
	public boolean isPageable(){
		return pageable!=null;
	}
	
	public Map<String, Object> getParams() {
		return params;
	}
	
	public void setParams(Map<String, Object> params) {
		this.params = params;
	}
	
	public abstract Query getQuery();
	
	public abstract String getQueryString();
	
	public abstract String getCountQueryString();
	
	public abstract Query getCountQuery();

	public Class<?> getResult() {
		return result;
	}

	public void setResult(Class<?> result) {
		this.result = result;
	}

	public boolean isSingle() {
		return single;
	}

	public void setSingle(boolean single) {
		this.single = single;
	}
	
	
}
