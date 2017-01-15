package me.bunny.kernel._c.model.support.detect;

import java.lang.reflect.Field;
import java.sql.Timestamp;

import me.bunny.kernel._c.exception.JFormatException;
import me.bunny.kernel._c.logging.JLogger;
import me.bunny.kernel._c.logging.JLoggerFactory;
import me.bunny.kernel._c.model.support.JColumn;
import me.bunny.kernel._c.model.support.JSQLType;
import me.bunny.kernel._c.model.support.JTable;
import me.bunny.kernel._c.support._package.JFieldInfoProvider.JFieldInfoGen;
import me.bunny.kernel._c.utils.JDateUtils;
import me.bunny.kernel._c.utils.JStringUtils;

public class JColumnFieldInfo implements JFieldInfoGen<JColumnInfo> {

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
