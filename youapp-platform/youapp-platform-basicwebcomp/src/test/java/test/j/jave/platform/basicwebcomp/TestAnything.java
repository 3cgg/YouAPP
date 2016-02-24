package test.j.jave.platform.basicwebcomp;

import j.jave.kernal.jave.json.JJSON;
import j.jave.platform.basicwebcomp.web.model.ResponseModel;
import junit.framework.TestCase;

public class TestAnything extends TestCase{

	public void testEnum(){
		ResponseModel responseModel=ResponseModel.newSuccess();
		responseModel.setData("success...");
		System.out.println(JJSON.get().formatObject(responseModel));
	}
}
