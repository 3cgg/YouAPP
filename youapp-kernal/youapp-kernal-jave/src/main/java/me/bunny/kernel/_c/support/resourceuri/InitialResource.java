package me.bunny.kernel._c.support.resourceuri;

import me.bunny.kernel.JConfiguration;
import me.bunny.kernel._c.service.JService;

public interface InitialResource extends JService{
	public void initResource(JConfiguration configuration);
}
