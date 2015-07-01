package j.jave.framework.core;

import j.jave.framework.components.login.model.User;
import j.jave.framework.components.tablemanager.model.Record;
import j.jave.framework.components.tablemanager.model.Table;
import j.jave.framework.components.tablemanager.service.TableManagerService;

import java.util.List;

import junit.framework.Assert;

public class TableManagerServiceTest extends StandaloneTest {

	TableManagerService tableManagerService=getBean("tableManagerServiceImpl", TableManagerService.class);
	
	public void testGetTables(){
		
		List<Table> tables= tableManagerService.getTables();
		
		Assert.assertTrue(tables.size()>0);
		
	}
	
	public void testGetRecord(){
		
		
		User user=new User();
		user.setId("ad7394d5900e439ba6670c0ee3d4f5a9");
		
		Record record= tableManagerService.getRecord(null, user);
		
		Assert.assertNotNull(record);
		
	}
	
	
	
}
