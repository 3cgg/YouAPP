package j.jave.platform.mybatis.plugin;

import j.jave.kernal.jave.model.JPage;
import j.jave.kernal.jave.model.JPagination;
import j.jave.kernal.jave.utils.JAssert;
import j.jave.kernal.jave.utils.JStringUtils;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.util.Properties;

import org.apache.ibatis.executor.statement.BaseStatementHandler;
import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;

@Intercepts({@Signature(type=StatementHandler.class,method="prepare",args={Connection.class})})  
public class JPagePlugin implements Interceptor {

	/**
	 * 数据库方言
	 */
	private String dialect = "";
	
	/**
	 * mapper.xml中需要拦截的ID(正则匹配)
	 */
	private String pageSqlId = "";

	public void setDialect(String dialect) {
		this.dialect = dialect;
	}
	public void setPageSqlId(String pageSqlId) {
		this.pageSqlId = pageSqlId;
	}


	public Object intercept(Invocation ivk) throws Throwable {
		if (ivk.getTarget() instanceof RoutingStatementHandler) {
			RoutingStatementHandler statementHandler = (RoutingStatementHandler) ivk.getTarget();
			BaseStatementHandler delegate = (BaseStatementHandler) getValueByFieldName(statementHandler, "delegate");
			MappedStatement mappedStatement = (MappedStatement) getValueByFieldName(delegate, "mappedStatement");

			 // 拦截需要分页的SQL
			if (mappedStatement.getId().matches(pageSqlId)) {
				BoundSql boundSql = delegate.getBoundSql();
				
				// 分页SQL<select>中parameterType属性对应的实体参数，即Mapper接口中执行分页方法的参数,该参数不得为空
				Object parameterObject = boundSql.getParameterObject();
				
				if (parameterObject == null) {
					throw new NullPointerException("parameter not initialed.");
				} else {
					JPage page=null;
					if (parameterObject instanceof JPagination) { // 参数就是Page实体
						JPagination pagination =(JPagination) parameterObject;
						page =pagination.getPage();
						
						//set default value . 
						if(page==null){
							page=new JPage();
							pagination.setPage(page);
						}
						page.processPageBean();
						
					} else {
						throw new NoSuchFieldException("parameter ["+parameterObject.getClass().getName()+"] should implements JPagination");
					}
					String pageSql = generatePageSql(boundSql.getSql(), page);
					setValueByFieldName(boundSql, "sql", pageSql); // 将分页sql语句反射回BoundSql.
				}
			}
		}
		return ivk.proceed();
	}


	/**
	 * 根据数据库方言，生成特定的分页SQL
	 * @param sql
	 * @param page
	 * @return
	 */
	private String generatePageSql(String sql, JPage page) {
		if (page != null && JStringUtils.isNotNullOrEmpty(dialect)) {
			StringBuffer pageSql = new StringBuffer();
			if("mysql".equalsIgnoreCase(dialect)) {
				pageSql.append(sql);
				pageSql.append(" limit " + handleOffset(page) + "," + page.getPageSize());
			} else if("oracle".equalsIgnoreCase(dialect)) {
				pageSql.append(" select * from ( select tmp_tb.*,ROWNUM row_id from ( ");
				pageSql.append(sql);
				pageSql.append(" ) as tmp_tb where ROWNUM < ");
				pageSql.append( handleOffset(page) +  page.getPageSize());
				pageSql.append(" ) where row_id >= ");
				pageSql.append( handleOffset(page) );
			} else if("mssql".equalsIgnoreCase(dialect)) {
				pageSql.append(getLimitString(sql, handleOffset(page), page.getPageSize()));
			}
			else if("h2".equalsIgnoreCase(dialect)){
				pageSql.append(getH2LimitString(sql, handleOffset(page), page.getPageSize()));
			}
			return pageSql.toString();
		} else {
			return sql;
		}
	}

	
	private String getH2LimitString(String sql, int offset, int limit ){
    	String result = "";
    	String prefix = "SELECT B.* FROM ( "
    			+"SELECT ROWNUM() RN, A.* FROM (";
    	int toNum = offset + limit;
    	String suffix = ") A ) B WHERE B.RN >= "+offset+" AND B.RN< "+toNum;
    	sql = sql.trim();
    	result=prefix+sql+suffix;
    	
    	return result;
    }
	
    private String getLimitString(String sql, int offset, int limit ){
    	String result = "";
    	String prefix = "select * from(select a.*,row_number()over(order by (select 0)) rn from(";
    	int toNum = offset + limit;
    	String suffix = ")a)b where b.rn >= "+offset+" and b.rn < "+toNum;
    	sql = sql.trim();
    	int firstIndexOfSelect = sql.toLowerCase().indexOf("select");
    	if(firstIndexOfSelect != -1){
    		result = prefix + "select top " + toNum +"  "+ sql.substring(firstIndexOfSelect+6) + suffix+"  ";
    	}
    	return result;
    }
	
	private int handleOffset(JPage page) {
		return (page.getCurrentPageNum()-1) * page.getPageSize() + 1;
	}

	public Object plugin(Object arg0) {
		return Plugin.wrap(arg0, this);
	}

	public void setProperties(Properties p) {
		dialect = p.getProperty("dialect");
		JAssert.state(JStringUtils.isNotNullOrEmpty(dialect), "dialect property is not found,mybatis pagination plugin not correctly configured.");
		pageSqlId = p.getProperty("pageSqlId");
		JAssert.state(JStringUtils.isNotNullOrEmpty(pageSqlId), "pageSqlId property is not found,mybatis pagination plugin not correctly configured.");		
	}
	
	/** 
     * 获取obj对象fieldName的Field 
     * @param obj 
     * @param fieldName 
     * @return 
     */  
    public Field getFieldByFieldName(Object obj, String fieldName) {  
        for (Class<?> superClass = obj.getClass(); superClass != Object.class; superClass = superClass  
                .getSuperclass()) {  
            try {  
                return superClass.getDeclaredField(fieldName);  
            } catch (NoSuchFieldException e) {  
            }  
        }  
        return null;  
    }  
  
    /** 
     * 获取obj对象fieldName的属性值 
     * @param obj 
     * @param fieldName 
     * @return 
     * @throws SecurityException 
     * @throws NoSuchFieldException 
     * @throws IllegalArgumentException 
     * @throws IllegalAccessException 
     */  
    public Object getValueByFieldName(Object obj, String fieldName)  
            throws SecurityException, NoSuchFieldException,  
            IllegalArgumentException, IllegalAccessException {  
        Field field = getFieldByFieldName(obj, fieldName);  
        Object value = null;  
        if(field!=null){  
            if (field.isAccessible()) {  
                value = field.get(obj);  
            } else {  
                field.setAccessible(true);  
                value = field.get(obj);  
                field.setAccessible(false);  
            }  
        }  
        return value;  
    }  
  
    /** 
     * 设置obj对象fieldName的属性值 
     * @param obj 
     * @param fieldName 
     * @param value 
     * @throws SecurityException 
     * @throws NoSuchFieldException 
     * @throws IllegalArgumentException 
     * @throws IllegalAccessException 
     */  
    public void setValueByFieldName(Object obj, String fieldName,  
            Object value) throws SecurityException, NoSuchFieldException,  
            IllegalArgumentException, IllegalAccessException {  
        Field field = obj.getClass().getDeclaredField(fieldName);  
        if (field.isAccessible()) {  
            field.set(obj, value);  
        } else {  
            field.setAccessible(true);  
            field.set(obj, value);  
            field.setAccessible(false);  
        }  
    }  
	
	
}
