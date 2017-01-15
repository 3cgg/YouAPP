package me.bunny.kernel.jave.s_deprecated;

import java.io.InputStream;
import java.io.OutputStream;

import me.bunny.kernel.jave.json.JJSON;

public class D_JSONFieldSerializer<T> extends D_FieldSerializer<T> {

	private JJSON json;
	
	public D_JSONFieldSerializer(JJSON json) {
		this.json=json;
	}
	
	@Override
	public void write(D_SO jso, OutputStream output, Object object) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public T read(D_SO jso, InputStream input, Class<T> type) {
		// TODO Auto-generated method stub
		return null;
	}

}
