package me.bunny.kernel.jave.model.support;

import java.sql.Timestamp;
import java.util.Date;

import me.bunny.kernel.jave.exception.JFormatException;
import me.bunny.kernel.jave.utils.JDateUtils;
import me.bunny.kernel.jave.utils.JStringUtils;

/**
 * 
 * @author J
 */
public class JTIMESTAMP extends JAbstractType<Date> {

	public JTIMESTAMP(JSQLType sqlType) {
		super(sqlType);
	}

	@Override
	public String name() {
		return sqlType.name();
	}

	@Override
	public boolean defaultValidate(Date object) {
		return true;
	}


	@Override
	public Timestamp convert(String string) {
		if(JStringUtils.isNotNullOrEmpty(string)){
			return parseTimestamp(string);
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
