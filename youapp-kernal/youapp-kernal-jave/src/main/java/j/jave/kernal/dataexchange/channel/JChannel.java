package j.jave.kernal.dataexchange.channel;

import j.jave.kernal.jave.model.JModel;

public interface JChannel<T extends JModel> {

	JResponseFuture write(T msg) throws Exception;
	
	Object read() throws Exception;
	
}
