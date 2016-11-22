package j.jave.kernal.streaming.netty.controller;

import java.io.ByteArrayInputStream;

import j.jave.kernal.jave.serializer.JSerializerFactory;
import j.jave.kernal.streaming.kryo.KryoSerializerFactory;

public class DefaultMethodParamParser implements MethodParamParser {
	
	private static JSerializerFactory factory=new KryoSerializerFactory();

	@Override
	public final Object[] parse(ControllerService controllerService,
			MappingMeta mappingMeta,FastMessageMeta fastMessageMeta) throws Exception {
		MethodParamMeta[] methodParamMetas= mappingMeta.getMethodParams();
		Object[] params=new Object[methodParamMetas.length];
		if(fastMessageMeta.bytes()!=null&&fastMessageMeta.bytes().length>0){
			params= factory.newSerializer(Object.class).read(new ByteArrayInputStream(
					fastMessageMeta.bytes()),
					Object[].class);
		}
		return params;
	}
	

}
