package me.bunny.kernel._c.sync;

import me.bunny.kernel._c.utils.JUniqueUtils;

public class JDefaultSyncMonitor implements JSyncMonitor{

	private String unique=JUniqueUtils.unique();
	
	@Override
	public String unique() {
		return unique;
	}

	@Override
	public String name() {
		return "DEFAULT-SYNC-OBJ";
	}

	
}
