package j.jave.framework.cxf.rs;

import j.jave.framework.utils.JUniqueUtils;

import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
public class RSDemo {

	public RSDemo(String id, String name){
		this.id=id;
		this.name=name;
	}
	
	public RSDemo() {
	}
	
	private String id=JUniqueUtils.unique();
	
	private String name=this.getClass().getName();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
	
	
}
