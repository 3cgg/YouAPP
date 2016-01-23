package j.jave.framework.cxf;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebParam.Mode;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;
import javax.jws.soap.SOAPBinding.Use;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.WebFault;

import org.apache.cxf.jaxrs.ext.Oneway;

@WebService(serviceName="HelloWorld_ServiceName",portName="HelloWorld_PortName"
,name="HelloWorld_Name",targetNamespace="http://j.jave.framework.cxf/")
@SOAPBinding(style=Style.RPC,use=Use.ENCODED)
@WebFault(name="local_name")
public interface HelloWorld {
	
	@WebMethod(operationName="sayHi_operationName",action="sayHi_action")
	@RequestWrapper(className="j.jave.framework.cxf.HelloWorld")
	@Oneway
	 @WebResult(name="hi_return",header=true) 
    String sayHi(
    		@WebParam(name="text",mode=Mode.IN)
    		String text);
	
	
	void run(String condition);
	
	
	
	
	
	
	
	
}
