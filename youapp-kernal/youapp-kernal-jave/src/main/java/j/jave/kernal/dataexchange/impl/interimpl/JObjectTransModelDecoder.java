package j.jave.kernal.dataexchange.impl.interimpl;

import j.jave.kernal.dataexchange.impl.JEncoderRegisterService;
import j.jave.kernal.dataexchange.impl.JMessageMetaDecoder;
import j.jave.kernal.dataexchange.model.MessageMeta;
import j.jave.kernal.jave.base64.JBase64;
import j.jave.kernal.jave.base64.JBase64FactoryProvider;
import j.jave.kernal.jave.json.JJSON;
import j.jave.kernal.jave.utils.JObjectSerializableUtils;

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
