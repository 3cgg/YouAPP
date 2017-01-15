package me.bunny.kernel.jave.support.resourceuri;

import me.bunny.kernel.jave.utils.JAssert;

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
