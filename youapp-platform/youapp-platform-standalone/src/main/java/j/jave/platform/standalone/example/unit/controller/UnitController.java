package j.jave.platform.standalone.example.unit.controller;

import j.jave.platform.standalone.server.controller.ControllerService;
import j.jave.platform.standalone.server.controller.ControllerSupport;
import j.jave.platform.standalone.server.controller.JRequestMapping;

@JRequestMapping(path="/unit")
public class UnitController extends ControllerSupport<UnitController> {

	@JRequestMapping(path="/getUnitName")
	public Object getUnitName(String name){
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
