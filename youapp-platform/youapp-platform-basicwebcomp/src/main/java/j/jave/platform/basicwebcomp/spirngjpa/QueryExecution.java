package j.jave.platform.basicwebcomp.spirngjpa;

import j.jave.kernal.jave.model.JPageImpl;
import j.jave.kernal.jave.model.JPageRequest;
import j.jave.kernal.jave.model.JPageable;
import j.jave.kernal.jave.utils.JAssert;
import j.jave.platform.basicwebcomp.core.model.JpaCalendarParam;
import j.jave.platform.basicwebcomp.core.model.JpaDateParam;

import java.util.List;
import java.util.Map;

import javax.persistence.NoResultException;
import javax.persistence.Query;

public abstract class QueryExecution {
	
	protected QueryMeta queryMeta;
	
	public QueryExecution(QueryMeta queryMeta) {
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
	
	
	public static void setQueryParameters(Query query, Map<?, Object> params) {
		for (Map.Entry<?, Object> entry : params.entrySet()){
			if(JpaDateParam.class.isInstance(entry.getValue())){
				JpaDateParam jpaDateParam= (JpaDateParam) entry.getValue();
				if(String.class.isInstance(entry.getKey())){
					query.setParameter((String)entry.getKey(), jpaDateParam.getDate(), jpaDateParam.getTemporalType());
				}
				else if(Integer.class.isInstance(entry.getKey())){
					query.setParameter((Integer)entry.getKey(), jpaDateParam.getDate(), jpaDateParam.getTemporalType());
				}
			}
			else if(JpaCalendarParam.class.isInstance(entry.getValue())){
				JpaCalendarParam jpaCalendarParam= (JpaCalendarParam) entry.getValue();
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
	}
	
	static class SingleExecution extends QueryExecution{
		public SingleExecution(QueryMeta queryMeta) {
			super(queryMeta);
		}

		@Override
		protected Object doExecute() {
			Query query=queryMeta.getQuery();
			QueryExecution.setQueryParameters(query, queryMeta.getParams());
			Object object= query.getSingleResult();
			Class<?> result=queryMeta.getResult();
			return result==null?object:result.cast(object);
		}
	}
	
	static class ListExecution extends QueryExecution{
		public ListExecution(QueryMeta queryMeta) {
			super(queryMeta);
		}

		@Override
		protected List<?> doExecute() {
			Query query=queryMeta.getQuery();
			QueryExecution.setQueryParameters(query, queryMeta.getParams());
			return query.getResultList();
		}
	}
	
	
	static class PagedExecution extends QueryExecution {
		
		public PagedExecution(QueryMeta queryMeta) {
			super(queryMeta);
		}

		@Override
		protected JPageImpl doExecute() {
			JPageable pageable=queryMeta.getPageable();
			Query countQuery=queryMeta.getCountQuery();
			JAssert.isNotNull(countQuery, "count query not found.");
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
