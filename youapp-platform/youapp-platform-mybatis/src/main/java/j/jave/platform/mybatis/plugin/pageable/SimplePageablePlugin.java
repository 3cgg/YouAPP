package j.jave.platform.mybatis.plugin.pageable;

import me.bunny.app._c.data.web.model.SimplePageRequest;
import me.bunny.kernel._c.logging.JLogger;
import me.bunny.kernel._c.logging.JLoggerFactory;
import me.bunny.kernel._c.utils.JAssert;
import me.bunny.kernel._c.utils.JCollectionUtils;
import me.bunny.kernel._c.utils.JStringUtils;
import me.bunny.kernel.eventdriven.servicehub.JServiceHubDelegate;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.resultset.ResultSetHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.apache.ibatis.scripting.defaults.DefaultParameterHandler;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

@Intercepts({
		@Signature(type = StatementHandler.class, method = "prepare", args = { Connection.class }),
		@Signature(type = ResultSetHandler.class, method = "handleResultSets", args = { Statement.class }) })
public class SimplePageablePlugin implements Interceptor {

	private static final JLogger LOGGER = JLoggerFactory
			.getLogger(SimplePageablePlugin.class);

	private String dialect = "";
	
	private boolean countSelf=true;
	
	private String countMethodPrefix=PageableProperties.COUNT_FOR_PAGEABLE_METHOD_PREFIX;
	
	private PageableSupportService pageableSupportService=JServiceHubDelegate.get().getService(this, PageableSupportService.class);;
	
	public PageableSupportService getPageableSupportService() {
		return pageableSupportService;
	}
	public void setCountMethodPrefix(String countMethodPrefix) {
		this.countMethodPrefix = countMethodPrefix;
	}
	
	public void setCountSelf(String countSelf) {
		if(JStringUtils.isNotNullOrEmpty(countSelf)){
			this.countSelf = Boolean.valueOf(countSelf);
		}
	}

	public void setDialect(String dialect) {
		this.dialect = dialect;
	}
	
	PageableMeta getPageableMeta(SimplePageRequest pageRequest,String sql){
		if(dialect.equals(Dialect.H2.name())){
			H2PageableMeta pageableMeta=new H2PageableMeta();
			pageableMeta.setPageNumber(pageRequest.getPageNumber());
			pageableMeta.setPageSize(pageRequest.getPageSize());
			pageableMeta.setCount(pageRequest.getCount());
			pageableMeta.setSql(sql);
			return pageableMeta;
		}
		return null;
	}

	private static ThreadLocal<SimplePageRequest> threadLocal=new ThreadLocal<SimplePageRequest>();
	
	public Object intercept(Invocation invocation) throws Throwable {
		boolean isPageable = false;
		PageImpl pageImpl =null;
		SimplePageRequest pageable=null;
		long count=-1;
		if (invocation.getTarget() instanceof StatementHandler) {
			StatementHandler statementHandler = (StatementHandler) invocation
					.getTarget();
			MetaObject metaStatementHandler = SystemMetaObject
					.forObject(statementHandler);
			while (metaStatementHandler.hasGetter("h")) {
				Object object = metaStatementHandler.getValue("h");
				metaStatementHandler = SystemMetaObject.forObject(object);
			}
			while (metaStatementHandler.hasGetter("target")) {
				Object object = metaStatementHandler.getValue("target");
				metaStatementHandler = SystemMetaObject.forObject(object);
			}
			MappedStatement mappedStatement = (MappedStatement) metaStatementHandler
					.getValue("delegate.mappedStatement");
			String mappedId= mappedStatement.getId();
			BoundSql boundSql = (BoundSql) metaStatementHandler
					.getValue("delegate.boundSql");
			MappedMeta mappedMeta= getPageableSupportService().getMappedMeta(mappedId);
			if(mappedMeta!=null){
				isPageable=mappedMeta.isPageable();
			}
			else{
				Object params=boundSql.getParameterObject();
				if(SimplePageRequest.class.isInstance(params)){
					isPageable=true;
					pageable=(SimplePageRequest) params;
				}
				else if(Map.class.isInstance(params)){
					Map<String, Object> runtimeParams = (Map<String, Object>) params;
					if(JCollectionUtils.hasInMap(runtimeParams)){
						for (Object obj : runtimeParams.values()) {
							if (SimplePageRequest.class.isInstance(obj)) {
								isPageable = true;
								pageable=(SimplePageRequest) obj;
								break;
							}
						}
					}
				}
				
				isPageable=isPageable&&mappedId.indexOf("."+countMethodPrefix)==-1;
				mappedMeta=new MappedMeta();
				mappedMeta.setPageable(isPageable);
				mappedMeta.setMappedId(mappedId);
				getPageableSupportService().putMappedMeta(mappedMeta);
			}
			
			if (isPageable) {
				count=pageable.getCount();
				String sql = boundSql.getSql();
				if(countSelf&&!pageable.counted()){
					Connection connection = (Connection) invocation.getArgs()[0];  
					count=count(sql, connection, mappedStatement, boundSql, pageable);
					pageable.setCount(count);
				}
				PageableMeta pageableMeta= getPageableMeta(pageable,sql);
				String pageSql = pageableMeta.getPageableSql();
				metaStatementHandler.setValue("delegate.boundSql.sql", pageSql);
			}
			threadLocal.set(pageable);
			return invocation.proceed();
		}else if (invocation.getTarget() instanceof ResultSetHandler) {
            Object obj = invocation.proceed();
            MetaObject metaStatementHandler = SystemMetaObject
					.forObject(invocation.getTarget());
            MappedStatement mappedStatement = (MappedStatement) metaStatementHandler
					.getValue("mappedStatement");
            MappedMeta mappedMeta= getPageableSupportService().getMappedMeta(mappedStatement.getId());
            isPageable=mappedMeta.isPageable();
            List list = (List) obj;
    		List result = new ArrayList();
    		if (isPageable) {
    			pageImpl = new PageImpl(list,threadLocal.get(),threadLocal.get().getCount());
    			threadLocal.remove();
    			result.add(pageImpl);
    		}
    		return result.size() == 0 ? obj : result;
        }
		return null;
	}

	private long count(String sql, Connection connection,
			MappedStatement mappedStatement, BoundSql boundSql, Pageable page) {
		String countSql = "select count(0) from (" + sql + ")";
		PreparedStatement countStmt = null;
		ResultSet rs = null;
		try {
			countStmt = connection.prepareStatement(countSql);
			BoundSql countBoundSql = new BoundSql(mappedStatement.getConfiguration(),
					countSql, boundSql.getParameterMappings(),
					boundSql.getParameterObject());
			ParameterHandler parameterHandler = new DefaultParameterHandler(
					mappedStatement, countBoundSql.getParameterObject(), countBoundSql);
			parameterHandler.setParameters(countStmt);
			rs = countStmt.executeQuery();
			long totalCount = 0;
			if (rs.next()) {
				totalCount = rs.getLong(1);
			}
			return totalCount;
		} catch (SQLException e) {
			LOGGER.error("Ignore this exception", e);
			throw new RuntimeException(e);
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
			} catch (SQLException e) {
				LOGGER.error("Ignore this exception", e);
			}
			try {
				countStmt.close();
			} catch (SQLException e) {
				LOGGER.error("Ignore this exception", e);
			}
		}
	}

	public Object plugin(Object arg0) {
		return Plugin.wrap(arg0, this);
	}

	public void setProperties(Properties p) {
		dialect = p.getProperty("dialect");
		JAssert.state(JStringUtils.isNotNullOrEmpty(dialect),"dialect is not found.");
		
		setCountSelf(p.getProperty("countSelf"));
		
		String countMethodPrefix=p.getProperty("countMethodPrefix");
		if(countMethodPrefix!=null){
			JAssert.isNotEmpty(countMethodPrefix,"The count method prefix found as empty, it should be same as one configured in "+QueryExecutorMethodInterceptor.class);
			this.countMethodPrefix=countMethodPrefix;
		}
		
	}

}
