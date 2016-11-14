package j.jave.kernal.jave.s_deprecated;

import java.io.InputStream;
import java.io.OutputStream;

import j.jave.kernal.jave.json.JJSON;

public class JSONFieldSerializer<T> extends JFieldSerializer<T> {

	private JJSON json;
	
	public JSONFieldSerializer(JJSON json) {
		this.json=json;
	}
	
	@Override
	public void write(JSO jso, OutputStream output, Object object) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public T read(JSO jso, InputStream input, Class<T> type) {
		// TODO Auto-generated method stub
		return null;
	}

}
