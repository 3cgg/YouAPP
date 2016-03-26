package j.jave.kernal.jave.support.resourceuri;

import j.jave.kernal.jave.utils.JAssert;

public class SimpleStringIdentifierGenerator  extends AbstractIdentifierGenerator implements IdentifierGenerator {

	private static final String namesapce="/string/";
	
	@Override
	public String namespace() {
		return namesapce;
	}
	
	@Override
	public String getKey(Object object) {
		JAssert.isNotNull(object);
		return String.valueOf(object);
	}

}
