package me.bunny.kernel.container;

public interface JLifecycle {

	void initialize();
	
	void destroy();

	void restart();
	
	
}
