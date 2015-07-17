import java.util.HashMap;

import j.jave.framework.commons.json.JJSON;
import j.jave.framework.commons.json.JJSONObject;


public class A implements JJSONObject {

	private String name="name";
	
	private int age=20;
	
	public static void main(String[] args) {
		
		String forA=JJSON.get().format(new A(),true);
		
		System.out.println(forA);
		
		System.out.println(JJSON.get().formatObject(null));
		
		System.out.println(JJSON.get().formatJSONObject(new A()));
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public HashMap serializableJSONObject() {
		HashMap map=new HashMap();
		map.put("aaaa", "mmmmmmmmm");
		return map;
	}
	
}
