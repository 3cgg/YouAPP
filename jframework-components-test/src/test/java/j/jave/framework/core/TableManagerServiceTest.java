package j.jave.framework.core;

import java.util.List;

import junit.framework.Assert;
import j.jave.framework.components.login.model.User;
import j.jave.framework.components.tablemanager.model.Record;
import j.jave.framework.components.tablemanager.model.Table;
import j.jave.framework.components.tablemanager.service.TableManagerService;

public class TableManagerServiceTest extends StandaloneTest {

	TableManagerService tableManagerService=getBean("tableManagerServiceImpl", TableManagerService.class);
	
	public void testGetTables(){
		
		List<Table> tables= tableManagerService.getTables();
		
		Assert.assertTrue(tables.size()>0);
		
	}
	
	public void testGetRecord(){
		
		
		User user=new User();
		user.setId("bd2713e6ad5d493ab2e25c34f6cd339a");
		
		Record record= tableManagerService.getRecord(null, user);
		
		Assert.assertNotNull(record);
		
	}
	
	
	
}
