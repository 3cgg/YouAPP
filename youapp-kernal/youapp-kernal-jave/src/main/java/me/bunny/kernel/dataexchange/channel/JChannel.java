package me.bunny.kernel.dataexchange.channel;

import me.bunny.kernel._c.model.JModel;

public interface JChannel<T extends JModel> {

	JResponseFuture write(T msg) throws Exception;
	
	Object read() throws Exception;
	
}
