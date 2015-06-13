package j.jave.framework.temp.test.service;

import java.util.ArrayList;
import java.util.List;

import j.jave.framework.json.JJSON;
import j.jave.framework.servicehub.JAsyncCallback;
import j.jave.framework.servicehub.JEventExecution;

public class B {
	
	static class JAsyncCallbackDemo implements JAsyncCallback{
		private static final long serialVersionUID = -5422256558841828177L;
		private String id="11111";
		public String name="namema";
		@Override
		public void callback(Object[] result, JEventExecution eventExecution) {
			System.out.println("INNER");
		}
	}

	public static void main(String[] args) {
		List<JAsyncCallback> asyncCallbacks=new ArrayList<JAsyncCallback>();
		JAsyncCallback asyncCallback=new JAsyncCallbackDemo();
		System.out.println(JJSON.get().format(asyncCallbacks));
		System.out.println(JJSON.get().format(asyncCallback));
		asyncCallbacks.add(asyncCallback);
		System.out.println(JJSON.get().format(asyncCallbacks));
		
	}
	
}
