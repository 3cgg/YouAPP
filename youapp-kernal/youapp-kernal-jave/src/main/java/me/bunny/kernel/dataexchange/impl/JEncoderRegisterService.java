package me.bunny.kernel.dataexchange.impl;

import java.util.concurrent.ConcurrentHashMap;

import me.bunny.kernel._c.json.JJSON;
import me.bunny.kernel._c.service.JService;
import me.bunny.kernel._c.utils.JObjectSerializableUtils;
import me.bunny.kernel.eventdriven.servicehub.JServiceFactorySupport;

public class JEncoderRegisterService
extends JServiceFactorySupport<JEncoderRegisterService>
implements JService
{
	public static final String JSON="JSON";
	
	public static final String OBJECT_BYTES="OBJECT_BYTES";
	
	private static final JObjectEncoder JSON_ENCODER
	=new JObjectEncoder() {
		@Override
		public byte[] encode(Object data) throws Exception {
			return JJSON.get().formatObject(data).getBytes("utf-8");
		}
	};
	
	private static final JObjectEncoder OBJECT_BYTES_ENCODER
	=new JObjectEncoder() {
		@Override
		public byte[] encode(Object data) throws Exception {
			return JObjectSerializableUtils.serializeObject(data);
		}
	};
	
	private ConcurrentHashMap<String, JObjectEncoder> objectEncoders
	=new ConcurrentHashMap<String, JObjectEncoder>();
	
	public JObjectEncoder getObjectEncoder(String encoderName){
		switch (encoderName) {
		case JSON:
			return JSON_ENCODER;
		case OBJECT_BYTES:
			return OBJECT_BYTES_ENCODER;
		default:
			JObjectEncoder encoder= objectEncoders.get(encoderName);
			encoder=encoder==null?JSON_ENCODER:encoder;
			return encoder;
		}
	}
	
	@Override
	protected JEncoderRegisterService doGetService() {
		return this;
	}
	
	
	
	
	
	

	
	
	
}
