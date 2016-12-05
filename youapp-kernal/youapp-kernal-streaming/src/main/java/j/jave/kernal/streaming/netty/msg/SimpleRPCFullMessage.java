package j.jave.kernal.streaming.netty.msg;


import java.util.ArrayList;
import java.util.List;

import j.jave.kernal.jave.json.JJSON;
import j.jave.kernal.jave.serializer.JSerializerFactory;
import j.jave.kernal.jave.serializer.SerializerUtils;
import j.jave.kernal.jave.utils.JStringUtils;
import j.jave.kernal.streaming.Util;
import j.jave.kernal.streaming.kryo._KryoSerializerFactoryGetter;
import j.jave.kernal.streaming.netty.server.ErrorCode;
import j.jave.kernal.streaming.netty.server.ServerExecuteException;

public abstract class SimpleRPCFullMessage extends SimpleFullMessage implements RPCFullMessage{
	
	
	
	protected class KryoResponse implements RPCFullResponseWriter{

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
		public RPCFullResponseWriter offer(Object object) {
			if((object instanceof Throwable)
					&&ServerExecuteException.class!=object.getClass()){
				objects.add(new ServerExecuteException(ErrorCode.E0001, (Throwable) object));
			}else{
				objects.add(object);
			}
			return this;
		}

		@Override
		public Object get() {
			return encoder().encode(objects.toArray());
		}
		
	
	}
	
	
	protected class JSONResponse implements RPCFullResponseWriter{
		
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
		public RPCFullResponseWriter offer(Object object) {
			objects.add(object);
			return this;
		}

		@Override
		public Object get() {
			try{
				return JStringUtils.utf8((String) encoder().encode(objects.toArray()));
			}catch (Exception e) {
				return JStringUtils.utf8((String) encoder().encode(Util.getMsg(e)));
			}
			
		}
		
	}
	
	
	
}
