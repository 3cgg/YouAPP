package j.jave.kernal.jave.s_deprecated;

import j.jave.kernal.jave.json.JJSON;

public class D_JSONSerializerFactory implements D_SerializerFactory {
	
	private D_JSONFieldSerializer jsonFieldSerializer; 
	
	@Override
	public D_Serializer makeSerializer(D_SO jso, Class<?> type) {
		if(jsonFieldSerializer==null){
			JJSON json=JJSON.getJSON(jso.getJsonConfig());
			jsonFieldSerializer=new D_JSONFieldSerializer<>(json);
		}
		return jsonFieldSerializer;
	}

}
