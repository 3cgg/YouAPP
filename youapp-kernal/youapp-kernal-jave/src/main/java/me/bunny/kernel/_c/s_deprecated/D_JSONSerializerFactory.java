package me.bunny.kernel._c.s_deprecated;

import me.bunny.kernel._c.json.JJSON;

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
