package j.jave.framework.commons.model.support;

import j.jave.framework.commons.utils.JStringUtils;


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
