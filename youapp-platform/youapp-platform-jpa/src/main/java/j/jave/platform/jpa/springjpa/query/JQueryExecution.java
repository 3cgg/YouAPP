package j.jave.platform.jpa.springjpa.query;

import j.jave.kernal.jave.json.JJSON;
import j.jave.kernal.jave.logging.JLogger;
import j.jave.kernal.jave.logging.JLoggerFactory;
import j.jave.kernal.jave.model.JPageImpl;
import j.jave.kernal.jave.model.JPageRequest;
import j.jave.kernal.jave.model.JPageable;
import j.jave.kernal.jave.utils.JAssert;
import j.jave.kernal.jave.utils.JCollectionUtils;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;

import javax.persistence.NoResultException;
import javax.persistence.Parameter;
import javax.persistence.Query;

abstract class JQueryExecution {
	
	protected JLogger logger=JLoggerFactory.getLogger(getClass());
	
	protected JQuery<?> queryMeta;
	
	public JQueryExecution(JQuery<?> queryMeta) {
		this.queryMeta = queryMeta;
	}


	public <T> T execute(){
		T result=null;
		try {
			result = doExecute();
		} catch (NoResultException e) {
			return null;
		}
		return result;
	}
	
	
	protected abstract <T> T doExecute();
	
	protected void setQueryParameters(Query query, Map<?, Object> params){
		boolean set=setSpecifiedQueryParameterAsPossible(query, params);
		if(!set){
			set=setUnspecifiedQueryParameterAsPossible(query, params);
		}
	}
	
	/**
	 * call {@link Query#getParameters()} to inspect how many parameters.
	 * @param query
	 * @param params
	 * @return if already set the parameters successfully.
	 * @see Query
	 */
	private boolean setSpecifiedQueryParameterAsPossible(Query query, Map<?, Object> params) {
		
		Set<Parameter<?>> sqlParams=null;
		try{
			sqlParams=query.getParameters();
		}catch(IllegalStateException e ){
			if(logger.isDebugEnabled()){
				logger.debug("cannot get parameters of the sql before setting parameters.");
			}
			return false;
		}
		
		for (Parameter<?> param : sqlParams){
			String paramName=param.getName();
			Object value=params.get(paramName);
			
			if(JJpaDateParam.class.isInstance(value)){
				JJpaDateParam jpaDateParam= (JJpaDateParam) value;
//				if(String.class.isInstance(paramName)){
				query.setParameter(paramName, jpaDateParam.getDate(), jpaDateParam.getTemporalType());
//				}
//				else if(Integer.class.isInstance(paramName)){
//					query.setParameter((Integer)paramName, jpaDateParam.getDate(), jpaDateParam.getTemporalType());
//				}
			}
			else if(JJpaCalendarParam.class.isInstance(value)){
				JJpaCalendarParam jpaCalendarParam= (JJpaCalendarParam) value;
//				if(String.class.isInstance(paramName)){
				query.setParameter(paramName, jpaCalendarParam.getCalendar(), jpaCalendarParam.getTemporalType());
//				}
//				else if(Integer.class.isInstance(paramName)){
//					query.setParameter((Integer)paramName, jpaCalendarParam.getCalendar(), jpaCalendarParam.getTemporalType());
//				}
			}
			else{
//				if(String.class.isInstance(paramName)){
				query.setParameter(paramName, value);
//				}
//				else if(Integer.class.isInstance(paramName)){
//					query.setParameter((Integer)paramName, value);
//				}
			}
		}
		return true;
	}
	
	
	/**
	 * directly iterate the parameters map to set parameters.
	 * @param query
	 * @param params
	 * @return  if already set the parameters successfully.
	 */
	private boolean setUnspecifiedQueryParameterAsPossible(Query query, Map<?, Object> params) {
		for (Map.Entry<?, Object> entry : params.entrySet()){
			if(JJpaDateParam.class.isInstance(entry.getValue())){
				JJpaDateParam jpaDateParam= (JJpaDateParam) entry.getValue();
				if(String.class.isInstance(entry.getKey())){
					query.setParameter((String)entry.getKey(), jpaDateParam.getDate(), jpaDateParam.getTemporalType());
				}
				else if(Integer.class.isInstance(entry.getKey())){
					query.setParameter((Integer)entry.getKey(), jpaDateParam.getDate(), jpaDateParam.getTemporalType());
				}
			}
			else if(JJpaCalendarParam.class.isInstance(entry.getValue())){
				JJpaCalendarParam jpaCalendarParam= (JJpaCalendarParam) entry.getValue();
				if(String.class.isInstance(entry.getKey())){
					query.setParameter((String)entry.getKey(), jpaCalendarParam.getCalendar(), jpaCalendarParam.getTemporalType());
					
				}
				else if(Integer.class.isInstance(entry.getKey())){
					query.setParameter((Integer)entry.getKey(), jpaCalendarParam.getCalendar(), jpaCalendarParam.getTemporalType());
				}
			}
			else{
				if(String.class.isInstance(entry.getKey())){
					query.setParameter((String)entry.getKey(), entry.getValue());
					
				}
				else if(Integer.class.isInstance(entry.getKey())){
					query.setParameter((Integer)entry.getKey(), entry.getValue());
				}
			}
		}
		return true;
	}
	
	static class UpdateExecution extends JQueryExecution{
		public UpdateExecution(JQuery<?> queryMeta) {
			super(queryMeta);
		}

		@SuppressWarnings("unchecked")
		@Override
		protected Object doExecute() {
			Query query=queryMeta.getQuery();
			setQueryParameters(query, queryMeta.getParams());
			return query.executeUpdate();
		}
	}
	
	static class SingleExecution extends JQueryExecution{
		public SingleExecution(JQuery<?> queryMeta) {
			super(queryMeta);
		}

		@SuppressWarnings("unchecked")
		@Override
		protected Object doExecute() {
			Query query=queryMeta.getQuery();
			setQueryParameters(query, queryMeta.getParams());
			Object object= query.getSingleResult();
			Class<?> result=queryMeta.getResult();
			return result==null?object:result.cast(object);
		}
	}
	
	static class ListExecution extends JQueryExecution{
		public ListExecution(JQuery<?> queryMeta) {
			super(queryMeta);
		}

		@SuppressWarnings("unchecked")
		@Override
		protected List<?> doExecute() {
			Query query=queryMeta.getQuery();
			setQueryParameters(query, queryMeta.getParams());
			return query.getResultList();
		}
	}
	
	
	static class PagedExecution extends JQueryExecution {
		
		public PagedExecution(JQuery<?> queryMeta) {
			super(queryMeta);
		}

		@Override
		protected JPageImpl doExecute() {
			JPageable pageable=queryMeta.getPageable();
			Query countQuery=queryMeta.getCountQuery();
			JAssert.isNotNull(countQuery, "count query not found.");
			
			if(JCollectionUtils.hasInMap(queryMeta.getCountParams())){
				//use count parameters if exists
				setQueryParameters(countQuery, queryMeta.getCountParams());
			}
			else{
				setQueryParameters(countQuery, queryMeta.getParams());
			}
			if(logger.isDebugEnabled()){
				Map<?, Object> debugParams=queryMeta.getCountParams();
				if(debugParams==null){
					debugParams=queryMeta.getParams();
				}
				logger.debug("ready to execute count computing (sql) : "
								+queryMeta.getCountQueryString()
								+"\n"+JJSON.get().formatObject(debugParams));
			}
			
			long count=0;
			Object obj=countQuery.getSingleResult();
			if(BigInteger.class.isInstance(obj)){
				((BigInteger)obj).longValue();
			}
			else{
				count=Long.valueOf(obj.toString());
			}
			int pageNumber=pageable.getPageNumber();
			int pageSize=pageable.getPageSize();
			int tempTotalPageNumber=JPageImpl.caculateTotalPageNumber(count, pageSize);
			pageNumber=pageNumber>tempTotalPageNumber?tempTotalPageNumber:pageNumber;
			
			Query query=queryMeta.getQuery();
			setQueryParameters(query, queryMeta.getParams());
			int startPosition=pageNumber*pageSize;
			query.setFirstResult(startPosition);
			query.setMaxResults(pageSize);
			
			if(logger.isDebugEnabled()){
				Map<Object, Object> debugParams=new WeakHashMap<Object, Object>(queryMeta.getParams());
				debugParams.put("debug-startPosition", startPosition);
				debugParams.put("debug-pageSize", pageSize);
				logger.debug("ready to execute resultset computing (sql) : "
								+queryMeta.getQueryString()
								+"\n"+JJSON.get().formatObject(debugParams));
			}
			
			List list= query.getResultList();
			JPageImpl page=new JPageImpl();
			page.setContent(list);
			page.setTotalRecordNumber(count);
			page.setTotalPageNumber(tempTotalPageNumber);
			JPageRequest pageRequest=(JPageRequest)pageable;
			pageRequest.setPageNumber(pageNumber);
			page.setPageable(pageable);
			return page;
		}
	} 
	
	
	
}
