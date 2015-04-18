package j.jave.framework.model.support;


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

	

}
