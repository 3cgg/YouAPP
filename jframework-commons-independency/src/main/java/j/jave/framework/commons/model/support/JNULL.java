package j.jave.framework.commons.model.support;

import j.jave.framework.commons.exception.JOperationNotSupportedException;


/**
 * support the case of SQL TYPE not found  
 * @author J
 */
public class JNULL extends JAbstractType<String> {
	
	
	public JNULL(JSQLType sqlType) {
		super(sqlType);
	}
	
	@Override
	public String name() {
		return sqlType.name();
	}

	@Override
	public boolean defaultValidate(String object) {
		return true;
	}

	
	@Override
	public Object convert(String string) {
		throw new JOperationNotSupportedException(JNULL.class.getName()+" doesnot support conver.");
	}
	

}
