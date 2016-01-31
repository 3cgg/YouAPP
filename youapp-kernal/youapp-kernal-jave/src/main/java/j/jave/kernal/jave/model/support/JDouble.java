package j.jave.kernal.jave.model.support;

import j.jave.kernal.jave.utils.JStringUtils;


/**
 * Double Delegate 
 * @author J
 */
public class JDouble extends JAbstractType<Double> {
	
	public JDouble(JSQLType sqlType) {
		super(sqlType);
	}
	
	@Override
	public String name() {
		return sqlType.name();
	}

	@Override
	public boolean defaultValidate(Double object) {
		return true;
	}

	@Override
	public Double convert(String string) {
		if(JStringUtils.isNotNullOrEmpty(string)){
			return Double.parseDouble(string);
		}
		return null;
	}

}
