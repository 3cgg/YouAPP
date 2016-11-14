package j.jave.kernal.jave.s_deprecated;

import j.jave.kernal.jave.json.JJSON;

public class JJSONSerializerFactory implements JSerializerFactory {
	
	private JSONFieldSerializer jsonFieldSerializer; 
	
	@Override
	public JSerializer makeSerializer(JSO jso, Class<?> type) {
		if(jsonFieldSerializer==null){
			JJSON json=JJSON.getJSON(jso.getJsonConfig());
			jsonFieldSerializer=new JSONFieldSerializer<>(json);
		}
		return jsonFieldSerializer;
	}

}
