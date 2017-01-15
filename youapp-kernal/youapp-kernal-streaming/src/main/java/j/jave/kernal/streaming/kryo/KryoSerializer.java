package j.jave.kernal.streaming.kryo;

import java.io.InputStream;
import java.io.OutputStream;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.ByteBufferInput;
import com.esotericsoftware.kryo.io.ByteBufferOutput;

import me.bunny.kernel._c.serializer.JSerializer;

public class KryoSerializer implements JSerializer {

	private Kryo kryo;
	
	public KryoSerializer(Kryo kryo) {
		this.kryo = kryo;
	}

	@Override
	public void write(OutputStream output, Object object) {
		ByteBufferOutput bufferOutput=new ByteBufferOutput(output);
		kryo.writeObject(bufferOutput, object);
		bufferOutput.close();
	}

	@Override
	public <T> T read(InputStream input, Class<T> type) {
		ByteBufferInput bufferInput=new ByteBufferInput(input);
		return kryo.readObject(bufferInput, type);
	}

}
