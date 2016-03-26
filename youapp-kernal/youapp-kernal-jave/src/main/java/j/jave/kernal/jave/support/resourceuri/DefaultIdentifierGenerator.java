package j.jave.kernal.jave.support.resourceuri;

import j.jave.kernal.jave.utils.JAssert;

public class DefaultIdentifierGenerator implements IdentifierGenerator {

	@Override
	public String key(Object object) {
		JAssert.isNotNull(object);
		return String.valueOf(object.hashCode());
	}

}
