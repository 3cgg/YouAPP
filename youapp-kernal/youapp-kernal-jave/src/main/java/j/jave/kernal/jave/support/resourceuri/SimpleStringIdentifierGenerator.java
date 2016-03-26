package j.jave.kernal.jave.support.resourceuri;

import j.jave.kernal.jave.utils.JAssert;

public class SimpleStringIdentifierGenerator implements IdentifierGenerator {

	@Override
	public String key(Object object) {
		JAssert.isNotNull(object);
		return String.valueOf(object);
	}

}
