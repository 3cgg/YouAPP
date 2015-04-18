package j.jave.framework.model.support;


/**
 * INT Delegate 
 * @author J
 */
public class JINT extends JAbstractType<String> {
	
	public JINT(JSQLType sqlType) {
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
