package j.jave.framework.components.tablemanager.ext;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import j.jave.framework.components.core.context.SpringContext;
import j.jave.framework.components.core.jsp.ServletRenderContext;
import j.jave.framework.components.core.menu.Item;
import j.jave.framework.components.tablemanager.model.Table;
import j.jave.framework.components.tablemanager.service.TableManagerService;

public class TableManagerServletRenderContext implements ServletRenderContext {

	@Override
	public void changeRenderContext(HttpServletRequest request) {
		TableManagerService tableManagerService=SpringContext.get().getApplicationContext().getBean("tableManagerServiceImpl", TableManagerService.class);
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
			}
		}
		request.setAttribute("items", items);
	}

}
