package j.jave.framework.model.support;

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


}
