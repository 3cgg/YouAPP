package j.jave.platform.jpa.springjpa.query;

import j.jave.kernal.jave.model.JPageable;
import j.jave.kernal.jave.utils.JAssert;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;

public abstract class JQuery<T extends JQuery<T>> {

	protected Map<?, Object> params=new HashMap<Object, Object>();
	
	/**
	 * Parameters for count computing
	 */
	protected Map<?, Object> countParams=new HashMap<Object, Object>();
	
	
	protected final EntityManager em;
	
    /**
     * If pageable
     */
	protected JPageable pageable;
	
    /**
     * Expected Result Type 
     */
	protected Class<?> result;
	
    /**
     * The result set is only one element or many.
     */
	protected boolean single;
	
    /**
     * Result Set Mapping Defined in the orm.xml or on the Entity Class
     */
	protected String resultSetMapping;
	
	/**
	 * set true if the sql is update of insert 
	 * like 'update table set ... ; insert table ...'
	 */
	protected boolean update=false;

	public JQuery(EntityManager em) {
		super();
		this.em = em;
	}
	
	public JPageable getPageable() {
		return pageable;
	}
	
	public T setPageable(JPageable pageable) {
		this.pageable = pageable;
		return (T) this;
	}
	public boolean isPageable(){
		return pageable!=null;
	}
	
	public Map<?, Object> getParams() {
		return params;
	}
	
	public T setParams(Map<?, Object> params) {
		this.params = params;
		return (T) this;
	}
	
	public Map<?, Object> getCountParams() {
		return countParams;
	}

	public T setCountParams(Map<?, Object> countParams) {
		this.countParams = countParams;
		return (T) this;
	}

	abstract Query getQuery();
	
	public abstract String getQueryString();
	
	public abstract String getCountQueryString();
	
	abstract Query getCountQuery();

	public Class<?> getResult() {
		return result;
	}

	public T setResult(Class<?> result) {
		this.result = result;
		return (T) this;
	}

	public boolean isSingle() {
		return single;
	}

	public T setSingle(boolean single) {
		this.single = single;
		return (T) this;
	}
	
	public T setResultSetMapping(String resultSetMapping) {
		this.resultSetMapping = resultSetMapping;
		return (T) this;
	}

	/**
	 * set true if the sql is update of insert 
	 * like 'update table set ... ; insert table ...'
	 * @param update
	 */
	public T setUpdate(boolean update) {
		this.update = update;
		return (T) this;
	}
	
	public boolean isUpdate() {
		return update;
	}
	
	
	public <M> M execute(){
		return ready().execute();
	}
	
	private JQueryExecution ready(){
		JAssert.isNotNull(em, "entity manager must be provided.");
		JQueryExecution queryExecution=null;
		if(isPageable()){
			queryExecution=new JQueryExecution.PagedExecution(this);
		}
		else if(isSingle()){
			queryExecution=new JQueryExecution.SingleExecution(this);
		}
		else if(isUpdate()){
			queryExecution=new JQueryExecution.UpdateExecution(this);
		}
		else{
			queryExecution=new JQueryExecution.ListExecution(this);
		}
		return queryExecution;
	}
	
	
}
