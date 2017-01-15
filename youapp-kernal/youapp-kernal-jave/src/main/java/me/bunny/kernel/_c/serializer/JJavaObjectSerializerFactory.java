package me.bunny.kernel._c.serializer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import me.bunny.kernel._c.utils.JIOUtils;
import me.bunny.kernel._c.utils.JObjectSerializableUtils;

public class JJavaObjectSerializerFactory extends JSerializerFactory{

	private static final JSerializer SERIALIZER=new JSerializer() {
		
		@Override
		public void write(OutputStream output, Object object) {
			
			try {
				byte[] bytes=JObjectSerializableUtils.serializeObject(object);
				output.write(bytes);
				output.flush();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
			
		}
		
		@Override
		public <T> T read(InputStream input, Class<T> type) {
			byte[] bytes=JIOUtils.getBytes(input);
			return JObjectSerializableUtils.deserialize(bytes, type);
		}
	};
	
	@Override
	public JSerializer newSerializer(Class<?> clazz) {
		return SERIALIZER;
	}

}
