package j.jave.platform.sps.support.ehcache.subhub;

import net.sf.ehcache.Ehcache;

public interface SpringEhcacheAware {
	public void putEhcache(Ehcache cache);
}
