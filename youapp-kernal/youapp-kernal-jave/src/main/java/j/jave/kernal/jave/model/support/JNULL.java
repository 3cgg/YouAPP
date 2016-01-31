package j.jave.kernal.jave.model.support;

import j.jave.kernal.jave.exception.JOperationNotSupportedException;


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
