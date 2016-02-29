
package j.jave.platform.mybatis.plugin.pageable;

public class H2PageableMeta extends PageableMeta {
	
	@Override
	protected String doGetPageableSql() {
		
		StringBuilder pageSql = new StringBuilder(200);
		
//		pageSql.append("SELECT B.* FROM ( " + "SELECT ROWNUM() RN, A.* FROM (");
//		pageSql.append(sql);
//		pageSql.append(") A ) B WHERE B.RN >= " );
//		pageSql.append(start);
//		pageSql.append(" AND B.RN< ");
//		pageSql.append(end);
		
//		String prefix = "select * from(select a.*,row_number()over(order by (select 0)) rn from(";
//		int toNum = offset + limit;
//		String suffix = ")a)b where b.rn >= " + offset + " and b.rn < " + toNum;
		
        pageSql.append("select * from ( select temp.*, rownum row_id from ( ");  
        pageSql.append(sql);  
        pageSql.append(" ) temp where rownum <= ").append(end);  
        pageSql.append(") where row_id > ").append(start);  
        return pageSql.toString();  
	}
	
}
