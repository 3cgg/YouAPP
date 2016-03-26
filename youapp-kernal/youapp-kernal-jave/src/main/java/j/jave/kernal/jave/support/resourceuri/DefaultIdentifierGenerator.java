package j.jave.kernal.jave.support.resourceuri;

import j.jave.kernal.jave.utils.JAssert;

public class DefaultIdentifierGenerator extends AbstractIdentifierGenerator implements IdentifierGenerator {

	@Override
	protected String getKey(Object object) {
		JAssert.isNotNull(object);
		return String.valueOf(object.hashCode());
	}
	
	private static final String namesapce="/hashcode/";
	
	@Override
	public String namespace() {
		return namesapce;
	}

}
