package j.jave.kernal.jave.model.support;

import j.jave.kernal.jave.utils.JStringUtils;


/**
 * INT Delegate 
 * @author J
 */
public class JINT extends JAbstractType<Integer> {
	
	public JINT(JSQLType sqlType) {
		super(sqlType);
	}
	
	@Override
	public String name() {
		return sqlType.name();
	}

	@Override
	public boolean defaultValidate(Integer object) {
		return true;
	}

	@Override
	public Integer convert(String string) {
		if(JStringUtils.isNotNullOrEmpty(string)){
			return Integer.parseInt(string);
		}
		return null;
	}
	

}
