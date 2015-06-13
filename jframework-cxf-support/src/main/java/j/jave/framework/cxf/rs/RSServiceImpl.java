package j.jave.framework.cxf.rs;

public class RSServiceImpl implements RSService {

	@Override
	public RSDemo saveRsDemo(RSDemo rsDemo) {
		System.out.println("saving... "+rsDemo.getId());
		rsDemo.setName(rsDemo.getName()+"  --- from server");
		return rsDemo;
	}

	@Override
	public RSDemo getRsDemoJSON(String id, String name) {
		return new RSDemo(id, name) ;
	}
	
	@Override
	public RSDemo getRsDemo(String id, String name) {
		return new RSDemo(id, name) ;
	}
}
