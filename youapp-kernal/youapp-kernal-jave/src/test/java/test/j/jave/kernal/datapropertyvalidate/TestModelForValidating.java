package test.j.jave.kernal.datapropertyvalidate;

import j.jave.kernal.jave.model.JModel;
import j.jave.kernal.jave.support.validate.annotationvalidator.annotation.JDate;
import j.jave.kernal.jave.support.validate.annotationvalidator.annotation.JDouble;
import j.jave.kernal.jave.support.validate.annotationvalidator.annotation.JFloat;
import j.jave.kernal.jave.support.validate.annotationvalidator.annotation.JInt;
import j.jave.kernal.jave.support.validate.annotationvalidator.annotation.JLength;
import j.jave.kernal.jave.support.validate.annotationvalidator.annotation.JNotEmpty;
import j.jave.kernal.jave.support.validate.annotationvalidator.annotation.JNotNull;


public class TestModelForValidating implements JModel {

	@JLength(min=3,max=20)
	private String name="Jia-zhong-jin";
	
	@JInt(min=3,max=100)
	private int age=100;
	
	@JDouble(min=0.4d,max=12.9d)
	private double mon=12.3d;
	
	@JFloat(min=0.4f,max=12.2f)
	private float price=3.4f;
	
	@JNotEmpty
	private String leng="a";
	
	@JDate
	private String date="2016-04-Jd";
	
	@JNotNull
	private Object object="";

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

	public double getMon() {
		return mon;
	}

	public void setMon(double mon) {
		this.mon = mon;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public String getLeng() {
		return leng;
	}

	public void setLeng(String leng) {
		this.leng = leng;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public Object getObject() {
		return object;
	}

	public void setObject(Object object) {
		this.object = object;
	}
	
}
