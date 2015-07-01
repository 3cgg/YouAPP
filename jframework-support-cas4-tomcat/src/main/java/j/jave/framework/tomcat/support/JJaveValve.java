package j.jave.framework.tomcat.support;

import java.io.IOException;

import javax.servlet.ServletException;

import org.apache.catalina.connector.Request;
import org.apache.catalina.connector.Response;
import org.apache.catalina.valves.ValveBase;

public class JJaveValve extends ValveBase {

	@Override
	public void invoke(Request request, Response response) throws IOException,
			ServletException {
		
		System.out.println(JJaveValve.class.getName());
		
		System.out.println("OK");
		
		getNext().invoke(request, response);
		
		
	}

}
