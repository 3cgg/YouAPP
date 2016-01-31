package j.jave.kernal.jave.model.support;

import j.jave.kernal.jave.exception.JFormatException;
import j.jave.kernal.jave.utils.JDateUtils;
import j.jave.kernal.jave.utils.JStringUtils;

import java.sql.Timestamp;
import java.util.Date;

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
