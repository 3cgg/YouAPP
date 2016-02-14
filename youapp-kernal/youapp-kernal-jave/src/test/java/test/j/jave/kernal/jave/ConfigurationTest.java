package test.j.jave.kernal.jave;

import j.jave.kernal.JConfiguration;

public class ConfigurationTest {

public static void main(String[] args) {
		System.out.println(System.getProperty("user.dir"));
		JConfiguration configuration= JConfiguration.get();
		System.out.println(configuration.getString("youapp.atomic.resource.session.provider", "no-exists"));;
		System.out.println(configuration.getString("youapp.xmldb.url", "no-exists"));;
		System.out.println("end");
	}

}
