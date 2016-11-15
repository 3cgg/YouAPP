package j.jave.kernal.jave.serializer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;

import j.jave.kernal.jave.json.JJSON;
import j.jave.kernal.jave.json.JJSONConfig;
import j.jave.kernal.jave.utils.JIOUtils;

public class JJSONSerializer implements JSerializer {

	private JJSON JSON;
	
	private Class<?> clazz;
	
	public JJSONSerializer(JJSONConfig config,Class<?> clazz) {
		this.JSON=JJSON.getJSON(config);
		this.clazz=clazz;
	}
	
	@Override
	public void write(OutputStream output, Object object) {
		String string=JSON.formatObject(object);
		try {
			output.write(string.getBytes(Charset.forName("utf-8")));
			output.flush();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public <T> T read(InputStream input, Class<T> type) {
		
		try {
			byte[] bytes=JIOUtils.getBytes(input);
			String string=new String(bytes, Charset.forName("utf-8"));
			T object= JSON.parse(string, type);
			if(Collection.class.isAssignableFrom(type)){
				Collection<Object> objects=(Collection<Object>) object;
				List list=new ArrayList<>();
			}
			return object;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
