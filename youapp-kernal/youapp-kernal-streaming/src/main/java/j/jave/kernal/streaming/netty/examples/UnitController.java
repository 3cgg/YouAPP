package j.jave.kernal.streaming.netty.examples;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.util.HashMap;
import java.util.Map;

import j.jave.kernal.jave.json.JJSON;
import j.jave.kernal.streaming.netty.controller.ControllerService;
import j.jave.kernal.streaming.netty.controller.ControllerSupport;
import j.jave.kernal.streaming.netty.controller.JRequestMapping;

@JRequestMapping(path="/unit")
public class UnitController extends ControllerSupport<UnitController> 
	implements IUnitController {

	@Override
	public String superName(String name) {
		return "you get it . super name ("+name+")";
	}
	
	@Override
	public Object name(String name){
		return "you get it.("+name+")";
	}

	@Override
	public Object jvmVersion(){
		RuntimeMXBean runtimeMXBean= ManagementFactory.getRuntimeMXBean();
		return "JVM-VERSION.("+runtimeMXBean.getVmVersion()+")";
	}
	
	@Override
	public Object jvm(){
		RuntimeMXBean runtimeMXBean= ManagementFactory.getRuntimeMXBean();
		Map<String, String> info=new HashMap<>();
		info.put("JVM-VERSION", runtimeMXBean.getVmVersion());
		info.put("Spec-Vendor", runtimeMXBean.getSpecVendor());
		info.put("Spec-Name", runtimeMXBean.getSpecName());
		info.put("VM-Vendor", runtimeMXBean.getVmVendor());
		info.put("ID(PID/HOSTNAME)", runtimeMXBean.getName());
		return JJSON.get().formatObject(info);
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
