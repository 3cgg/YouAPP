package j.jave.kernal.jave.support.resourceuri;

public abstract class AbstractIdentifierGenerator implements IdentifierGenerator {
	
	@Override
	public final String key(Object object) {
		return namespace()+getKey(object);
	}
	
	protected abstract String getKey(Object object);
	
}
