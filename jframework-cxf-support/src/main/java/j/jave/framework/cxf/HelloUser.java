package j.jave.framework.cxf;

import j.jave.framework.model.JModel;

public class HelloUser implements JModel{

	private String id;  
    private String name;
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
