package j.jave.framework.commons.model.support.detect;

import j.jave.framework.commons.exception.JFormatException;
import j.jave.framework.commons.logging.JLogger;
import j.jave.framework.commons.logging.JLoggerFactory;
import j.jave.framework.commons.model.support.JColumn;
import j.jave.framework.commons.model.support.JSQLType;
import j.jave.framework.commons.model.support.JTable;
import j.jave.framework.commons.support.detect.JFieldInfoProvider.JFieldInfo;
import j.jave.framework.commons.utils.JDateUtils;
import j.jave.framework.commons.utils.JStringUtils;

import java.lang.reflect.Field;
import java.sql.Timestamp;

public class JColumnFieldInfo implements JFieldInfo<JColumnInfo> {

	private static JLogger logger=JLoggerFactory.getLogger(JColumnFieldInfo.class);
	
	@Override
	public JColumnInfo getInfo(Field field, Class<?> classIncudeField) {
		JColumnInfo columnInfo=new JColumnInfo();
		JColumn column=field.getAnnotation(JColumn.class);
		columnInfo.setColumn(column);
		columnInfo.setName(column.name());
		columnInfo.setType(column.type());
		columnInfo.setLength(column.length());
		columnInfo.setNullable(column.nullable());
		
		JTable table=classIncudeField.getAnnotation(JTable.class);
		JTableInfo tableInfo=new JTableInfo();
		tableInfo.setTable(table);
		tableInfo.setName(table.name());
		tableInfo.setSchema(table.schema());
		columnInfo.setTableInfo(tableInfo); 
		
		columnInfo.setField(field);
		columnInfo.setDefaultValue(getDefaultValue(columnInfo));

		return columnInfo;
	}
	
	
	private Object getDefaultValue(JColumnInfo columnInfo){

		String defaultV=columnInfo.getColumn().defaultValue();
		JSQLType type=columnInfo.getColumn().type();
		
		switch(type){
			case VARCHAR: return defaultV;
			case TIMESTAMP: {
				if(JStringUtils.isNotNullOrEmpty(defaultV)){
					try{
						return parseTimestamp(defaultV);
					}catch(Exception e){
						logger.error(columnInfo.getTableInfo().getName()+"->"+columnInfo.getName(),e);
						return type.defaultValue();
					}
				}
				return type.defaultValue();
			}
			case INTEGER: {
				if(JStringUtils.isNotNullOrEmpty(defaultV)){
					try{
						return Integer.parseInt(defaultV);
					}catch(Exception e){
						logger.error(columnInfo.getTableInfo().getName()+"->"+columnInfo.getName(),e);
						return type.defaultValue();
					}
				}
				return type.defaultValue();
			}
			case DOUBLE: {
				if(JStringUtils.isNotNullOrEmpty(defaultV)){
					try{
						return Double.parseDouble(defaultV);
					}catch(Exception e){
						logger.error(columnInfo.getTableInfo().getName()+"->"+columnInfo.getName(),e);
						return type.defaultValue();
					}
				}
				return type.defaultValue();
			}
		
		}
		
		return null;
	}


	private Timestamp parseTimestamp(String defaultV) {
		Timestamp timestamp=null;
		try{
			timestamp=JDateUtils.parseTimestampWithSeconds(defaultV);
		}catch(Exception e){
			logger.info(e.getMessage(), e);
		}
		
		if(timestamp==null){
			try{
				timestamp=JDateUtils.parseTimestampWithSeconds(defaultV,JDateUtils.ddMMyyyyHHmmss);
			}catch(Exception e){
				logger.info(e.getMessage(), e);
			}
		}
		
		if(timestamp==null){
			throw new JFormatException("canot format timestamp via default strategy");
		}
		return timestamp;
	}

}
