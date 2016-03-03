package j.jave.platform.basicwebcomp.core.service;

import j.jave.kernal.jave.model.JPageImpl;
import j.jave.kernal.jave.model.JPageRequest;
import j.jave.kernal.jave.model.JPageable;
import j.jave.platform.basicwebcomp.core.model.JpaCalendarParam;
import j.jave.platform.basicwebcomp.core.model.JpaDateParam;

import java.util.List;
import java.util.Map;

import javax.persistence.NoResultException;
import javax.persistence.Query;

public abstract class QueryExecution {

	public Object execute(QueryMeta queryMeta){
		Object result=null;
		try {
			result = doExecute(queryMeta);
		} catch (NoResultException e) {
			return null;
		}
		return result;
	}
	
	
	protected abstract Object doExecute(QueryMeta queryMeta);
	
	
	public static void setQueryParameters(Query query, Map<String, Object> params) {
		for (Map.Entry<String, Object> entry : params.entrySet()){
			if(JpaDateParam.class.isInstance(entry.getValue())){
				JpaDateParam jpaDateParam= (JpaDateParam) entry.getValue();
				query.setParameter(entry.getKey(), jpaDateParam.getDate(), jpaDateParam.getTemporalType());
			}
			else if(JpaCalendarParam.class.isInstance(entry.getValue())){
				JpaCalendarParam jpaCalendarParam= (JpaCalendarParam) entry.getValue();
				query.setParameter(entry.getKey(), jpaCalendarParam.getCalendar(), jpaCalendarParam.getTemporalType());
			}
			else{
				query.setParameter(entry.getKey(), entry.getValue());
			}
		}
	}
	
	static class SingleExecution extends QueryExecution{
		@Override
		protected Object doExecute(QueryMeta queryMeta) {
			Object object= queryMeta.getQuery().getSingleResult();
			return queryMeta.getResult().cast(object);
		}
	}
	
	static class ListExecution extends QueryExecution{
		@Override
		protected Object doExecute(QueryMeta queryMeta) {
			return queryMeta.getQuery().getResultList();
		}
	}
	
	
	static class PagedExecution extends QueryExecution {
		private JPageable pageable;
		public PagedExecution(JPageable pageable) {
			this.pageable=pageable;
		}
		
		@Override
		protected Object doExecute(QueryMeta queryMeta) {
			Query countQuery=queryMeta.getCountQuery();
			QueryExecution.setQueryParameters(countQuery, queryMeta.getParams());
			long count=(long) countQuery.getSingleResult();
			int pageNumber=pageable.getPageNumber();
			int pageSize=pageable.getPageSize();
			int tempTotalPageNumber=JPageImpl.caculateTotalPageNumber(count, pageSize);
			pageNumber=pageNumber>tempTotalPageNumber?tempTotalPageNumber:pageNumber;
			
			Query query=queryMeta.getQuery();
			QueryExecution.setQueryParameters(query, queryMeta.getParams());
			query.setFirstResult(pageNumber*pageSize);
			query.setMaxResults(pageSize);
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
