package j.jave.platform.standalone.server.controller;

@JRequestMapping(path="example")
public class DefaultControllerSupport extends ControllerSupport<DefaultControllerSupport> {

	@JRequestMapping(path="saveCar")
	public void saveCar(){
		System.out.println("iiiiiiiiiiiiiiiiiii");
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
