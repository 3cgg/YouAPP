package j.jave.platform.standalone.server.controller;

@JRequestMapping(path="/example")
public class DefaultControllerSupport extends ControllerSupport<DefaultControllerSupport> {

	@JRequestMapping(path="/getCar")
	public Object getCar(String name){
		return "you get it.("+name+")";
	}
	
	
	@Override
	public String getControllerServiceName() {
		return "defaultControllerSupport";
	}
	
	@Override
	public ControllerService getControllerService() {
		return this;
	}
	
	

}
