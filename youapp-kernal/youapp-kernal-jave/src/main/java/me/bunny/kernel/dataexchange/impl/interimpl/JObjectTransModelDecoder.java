package me.bunny.kernel.dataexchange.impl.interimpl;

import me.bunny.kernel.dataexchange.impl.JEncoderRegisterService;
import me.bunny.kernel.dataexchange.impl.JMessageMetaDecoder;
import me.bunny.kernel.dataexchange.model.MessageMeta;
import me.bunny.kernel.jave.base64.JBase64;
import me.bunny.kernel.jave.base64.JBase64FactoryProvider;
import me.bunny.kernel.jave.json.JJSON;
import me.bunny.kernel.jave.utils.JObjectSerializableUtils;

public class JObjectTransModelDecoder implements JMessageMetaDecoder<JObjectTransModel> {

	private JBase64 base64Service=JBase64FactoryProvider.getBase64Factory().getBase64();
	
	@Override
	public JObjectTransModel encode(MessageMeta messageMeta) throws Exception {
		String dataEncoder=messageMeta.dataEncoder();
		byte[] bytes=base64Service.decodeBase64(messageMeta.data());
		
		if(JEncoderRegisterService.JSON.equals(dataEncoder)){
			return JJSON.get().parse(new String(bytes,"utf-8"), JObjectTransModel.class); 
		}
		if(JEncoderRegisterService.OBJECT_BYTES.equals(dataEncoder)){
			return JObjectSerializableUtils.deserialize(new String(bytes,"utf-8"), JObjectTransModel.class);
		}
		return null;
	}
}
