package me.bunny.kernel._c.support.resourceuri;

import me.bunny.kernel._c.utils.JAssert;

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
