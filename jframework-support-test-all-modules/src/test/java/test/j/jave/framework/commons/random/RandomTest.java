package test.j.jave.framework.commons.random;

import j.jave.framework.commons.json.JJSON;
import j.jave.framework.commons.random.JObjectPopulate;

import java.util.Date;

import junit.framework.TestCase;

public class RandomTest extends TestCase {

	private static class A{
		private String name;
		
		private int age;
		
		private Date time;
		
		private double money= 10.01;

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

		public Date getTime() {
			return time;
		}

		public void setTime(Date time) {
			this.time = time;
		}

		public double getMoney() {
			return money;
		}

		public void setMoney(double money) {
			this.money = money;
		}
		
	}
	
	
	public void testRandom() throws Exception{
		A a=new A();
		JObjectPopulate objectPopulate=new JObjectPopulate(a);
		objectPopulate.populate();
		
		System.out.println("------------------"+JJSON.get().formatObject(a));
	}
	
	
}
