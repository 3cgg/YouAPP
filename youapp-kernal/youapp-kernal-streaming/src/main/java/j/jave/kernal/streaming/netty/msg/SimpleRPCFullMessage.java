package j.jave.kernal.streaming.netty.msg;


import java.util.ArrayList;
import java.util.List;

import j.jave.kernal.jave.json.JJSON;
import j.jave.kernal.jave.serializer.JSerializerFactory;
import j.jave.kernal.jave.serializer.SerializerUtils;
import j.jave.kernal.jave.utils.JStringUtils;
import j.jave.kernal.streaming.kryo._KryoSerializerFactoryGetter;

public abstract class SimpleRPCFullMessage extends SimpleFullMessage implements RPCFullMessage{
	
	
	
	protected class KryoResponse implements RPCFullResponse{

		private KryoEncoder encoder=new KryoEncoder();
		
		private List<Object> objects=new ArrayList<>();
		
		@Override
		public KryoEncoder encoder() {
			return encoder ;
		}
		
		private class KryoEncoder implements RPCMsgEncoder{
			
			private JSerializerFactory factory=_KryoSerializerFactoryGetter.get();
			
			@Override
			public Object encode(Object object) {
				return SerializerUtils.serialize(factory, object);
			}
		}

		@Override
		public RPCFullResponse offer(Object object) {
			objects.add(object);
			return this;
		}

		@Override
		public Object get() {
			return encoder().encode(objects.toArray());
		}
		
	
	}
	
	
	protected class JSONResponse implements RPCFullResponse{
		
		private JSONEncoder encoder=new JSONEncoder();
		
		private List<Object> objects=new ArrayList<>();
		
		@Override
		public JSONEncoder encoder() {
			return encoder ;
		}
		
		private class JSONEncoder implements RPCMsgEncoder{
			@Override
			public Object encode(Object object) {
				return JJSON.get().formatObject(object);
			}
		}

		@Override
		public RPCFullResponse offer(Object object) {
			objects.add(object);
			return this;
		}

		@Override
		public Object get() {
			return JStringUtils.utf8((String) encoder().encode(objects.toArray()));
		}
		
	}
	
	
	
}
