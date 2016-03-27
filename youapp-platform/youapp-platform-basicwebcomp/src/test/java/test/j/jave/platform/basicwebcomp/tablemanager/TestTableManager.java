package test.j.jave.platform.basicwebcomp.tablemanager;

import j.jave.platform.basicwebcomp.core.service.DefaultServiceContext;
import j.jave.platform.basicwebcomp.core.service.ServiceContext;
import j.jave.platform.basicwebcomp.param.model.ParamCode;
import j.jave.platform.basicwebcomp.tablemanager.model.Record;
import j.jave.platform.basicwebcomp.tablemanager.model.Table;
import j.jave.platform.basicwebcomp.tablemanager.model.TableSearch;
import j.jave.platform.basicwebcomp.tablemanager.service.TableManagerService;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:spring-context.xml"})
public class TestTableManager implements ApplicationContextAware {

	private ApplicationContext applicationContext;
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.applicationContext=applicationContext;
	}
	@Test
	public void testTableMag(){
//		tableManagerServiceImpl
		TableManagerService tableManagerService=applicationContext.getBean("tableManagerServiceImpl", TableManagerService.class);
	
		List<Table> tables= tableManagerService.getTables();
	
		System.out.println(tables.size());
		
		ServiceContext context=DefaultServiceContext.getDefaultServiceContext();
		TableSearch tableSearch=new TableSearch();
		tableSearch.setModelName(ParamCode.class.getName());
		List<Record> records=tableManagerService.getRecords(context, tableSearch);
		System.out.println(records.size());
		
	}
	
	
}
