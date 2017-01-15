package test.j.jave.kernal.common;

import java.util.Map;

import junit.framework.TestCase;
import me.bunny.kernel.jave.json.JJSON;

public class TestCommon extends TestCase {

	public static class A{
		private String name="N";
		
		private String desc="D";
		
		private String code="C";

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getDesc() {
			return desc;
		}

		public void setDesc(String desc) {
			this.desc = desc;
		}

		public String getCode() {
			return code;
		}

		public void setCode(String code) {
			this.code = code;
		}
		
		
	}
	
	
	public static class B{
		private String name;
		
		private String desc;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getDesc() {
			return desc;
		}

		public void setDesc(String desc) {
			this.desc = desc;
		}
		
		
	}
	
	public void testJSON(){
		try{

			String json=JJSON.get().formatObject(new A());
			System.out.println(json);
			
			Map<String, Object> map= JJSON.get().parse(json);
			System.out.println(map);
			
			B b=JJSON.get().parse(json,B.class);
			System.out.println(b);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	public void testJSON2(){
		try{

			String json=JJSON.get().formatObject(new B());
			System.out.println(json);
			
			Map<String, Object> map= JJSON.get().parse(json);
			System.out.println(map);
			
			A b=JJSON.get().parse(json,A.class);
			System.out.println(b);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
}
