package j.jave.framework.components.tablemanager.ext;

import j.jave.framework.components.core.context.SpringContextSupport;
import j.jave.framework.components.tablemanager.model.Table;
import j.jave.framework.components.tablemanager.service.TableManagerService;
import j.jave.framework.components.views.menu.Item;
import j.jave.framework.components.web.jsp.ServletRenderContext;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TableManagerServletRenderContext implements ServletRenderContext {

	private static final Logger LOGGER=LoggerFactory.getLogger(TableManagerServletRenderContext.class);
	
	@Override
	public void changeRenderContext(HttpServletRequest request) {
		
		LOGGER.info("inital "+TableManagerServletRenderContext.class.getName());
		
		TableManagerService tableManagerService=SpringContextSupport.getApplicationContext().getBean("tableManagerServiceImpl", TableManagerService.class);
		List<Item> items=new ArrayList<Item>();
		List<Table> tables= tableManagerService.getTables();
		if(tables!=null){
			for(int i=0;i<tables.size();i++){
				Table table=tables.get(i);
				Item item=new Item();
				item.setLabel(table.getTableName());
				item.setUrl("/tablemanager.tablemanageraction/toViewRecords");
				item.setParam("modelName="+table.getModelName());
				items.add(item);
				
				LOGGER.info(" table load... "+table.getTableName());
			}
		}
		request.setAttribute("items", items);
		
		LOGGER.info(" table count : "+items.size());
	}

}
