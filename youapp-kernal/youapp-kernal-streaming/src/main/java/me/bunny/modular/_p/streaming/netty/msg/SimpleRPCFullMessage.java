package me.bunny.modular._p.streaming.netty.msg;


import java.util.ArrayList;
import java.util.List;

import me.bunny.kernel._c.json.JJSON;
import me.bunny.kernel._c.serializer.JSerializerFactory;
import me.bunny.kernel._c.serializer.SerializerUtils;
import me.bunny.kernel._c.utils.JStringUtils;
import me.bunny.modular._p.streaming.Util;
import me.bunny.modular._p.streaming.kryo._KryoSerializerFactoryGetter;
import me.bunny.modular._p.streaming.netty.server.ErrorCode;
import me.bunny.modular._p.streaming.netty.server.ServerExecuteException;

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
