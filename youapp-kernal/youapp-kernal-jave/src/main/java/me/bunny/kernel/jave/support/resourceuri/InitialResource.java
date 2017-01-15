package me.bunny.kernel.jave.support.resourceuri;

import me.bunny.kernel.JConfiguration;
import me.bunny.kernel.jave.service.JService;

public interface InitialResource extends JService{
	public void initResource(JConfiguration configuration);
}
