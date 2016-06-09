package j.jave.kernal.jave.sync;

import j.jave.kernal.jave.utils.JUniqueUtils;

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
