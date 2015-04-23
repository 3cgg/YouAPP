package j.jave.framework.components.tablemanager.action;

import j.jave.framework.components.tablemanager.model.Record;
import j.jave.framework.components.tablemanager.model.TableSearch;
import j.jave.framework.components.tablemanager.service.TableManagerService;
import j.jave.framework.components.web.jsp.JSPAction;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

@Controller(value="tablemanager.tablemanageraction")
@Scope(value=ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class TableManagerAction extends JSPAction {

	@Autowired
	private TableManagerService tableManagerService;
	
	private TableSearch tableSearch;
	
	/**
	 * get all records with pagination. 
	 * @return
	 */
	public String getRecords(){
		List<Record> records= tableManagerService.getRecords(getServiceContext(), tableSearch);
		setAttribute("records", records);
		return "";
	}
	
	/**
	 * get record .
	 * @return
	 */
	public String getRecord(){
		// primary key 
		String id=getParameter("id");
		String modelName=getParameter("modelName");
		Record record=tableManagerService.getRecord(getServiceContext(), modelName, id);
		setAttribute("record", record);
		return "";
	}
	
	public String updateRecord(){
		// primary key 
		String id=getParameter("id");
		String modelName=getParameter("modelName");
		Record record=tableManagerService.getRecord(getServiceContext(), modelName, id);
		setAttribute("record", record);
		return "";
	}
	
	
	
	
	
	
}
