package j.jave.platform.basicwebcomp.core.service;

public class QueryBuilder {
	
	public static QueryExecution build(QueryMeta queryMeta){
		QueryExecution queryExecution=null;
		if(queryMeta.isPageable()){
			queryExecution=new QueryExecution.PagedExecution(queryMeta.getPageable());
		}
		else if(queryMeta.isSingle()){
			queryExecution=new QueryExecution.SingleExecution();
		}
		else{
			queryExecution=new QueryExecution.ListExecution();
		}
		return queryExecution;
	}
	
	
	
}
