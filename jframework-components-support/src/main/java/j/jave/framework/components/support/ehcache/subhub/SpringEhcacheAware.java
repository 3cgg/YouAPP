package j.jave.framework.components.support.ehcache.subhub;

import net.sf.ehcache.Ehcache;

public interface SpringEhcacheAware {
	public void putEhcache(Ehcache cache);
}
