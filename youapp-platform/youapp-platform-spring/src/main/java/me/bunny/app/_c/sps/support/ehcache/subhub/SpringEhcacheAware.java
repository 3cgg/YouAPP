package me.bunny.app._c.sps.support.ehcache.subhub;

import net.sf.ehcache.Ehcache;

public interface SpringEhcacheAware {
	public void putEhcache(Ehcache cache);
}
