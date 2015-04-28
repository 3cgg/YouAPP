package j.jave.framework.components.tablemanager.action;

import j.jave.framework.components.tablemanager.model.Cell;
import j.jave.framework.components.tablemanager.model.Column;
import j.jave.framework.components.tablemanager.model.Record;
import j.jave.framework.components.tablemanager.model.Table;
import j.jave.framework.components.tablemanager.model.TableSearch;
import j.jave.framework.components.tablemanager.service.TableManagerService;
import j.jave.framework.components.web.jsp.JSPAction;
import j.jave.framework.json.JJSON;
import j.jave.framework.model.JPage;
import j.jave.framework.utils.JDateUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	
	
	public void getTables(){  }
	
	
	public String toViewRecords(){
		
		
		
		String modelName=getParameter("modelName");
		List<Column> columns=tableManagerService.getColumnsByModelName(getServiceContext(), modelName);
	
		setAttribute("columns", columns);
		setAttribute("table", getTableByModelName(modelName));
		
		return "/WEB-INF/jsp/tablemanager/view-all-tablemanager.jsp";
	}


	private Table getTableByModelName(String modelName) {
		List<Table> tables=tableManagerService.getTables();
		if(tables!=null){
			for(int i=0;i<tables.size();i++){
				Table table=tables.get(i);
				if(table.getModelName().equals(modelName)){
					return table;
				}
			}
		}
		Table table=new Table();
		table.setModelName(modelName);
		return table;
	}
	
	
	/**
	 * get all records with pagination. 
	 * @return
	 */
	public String getRecords(){
		
		String sEcho=getParameter("sEcho");
		int iDisplayStart=Integer.parseInt(getParameter("iDisplayStart"));
		int iDisplayLength=Integer.parseInt(getParameter("iDisplayLength"));
		
		JPage page=new JPage();
		page.setPageSize(iDisplayLength);
		int pageNum=iDisplayStart/iDisplayLength;
		page.setCurrentPageNum(pageNum+1);
		
		page.setSortColumn(getParameter("sortColumn"));
		page.setSortType(getParameter("sortType"));
		
		tableSearch.setPage(page);
		
		List<Record> records= tableManagerService.getRecords(getServiceContext(), tableSearch);
		List<Map<String, Object>> objects=new ArrayList<Map<String,Object>>();
		for(int i=0;i<records.size();i++){
			Record record=records.get(i);
			Map<String, Object> object=new HashMap<String, Object>();
			List<Cell> cells=record.getCells();
			for(int j=0;j<cells.size();j++){
				Cell cell=cells.get(j);
				Column column=cell.getColumn();
				object.put(column.getColumnName(), format(column.getPropertyType(), cell.getObject()));
			}
			objects.add(object);
		}
		
		Map<String, Object> pagination=new HashMap<String, Object>();
		
		pagination.put("iTotalRecords", page.getTotalRecordNum());
		pagination.put("sEcho",sEcho); 
		pagination.put("iTotalDisplayRecords", page.getTotalRecordNum());
		pagination.put("aaData", objects);
		
		return JJSON.get().format(pagination); 
	}
	
	private Object format(Class<?> propertyType,Object obj){
		try{
			if(Date.class.isAssignableFrom(propertyType)){
				return JDateUtils.formatWithSeconds((Date) obj);
			}
		}catch(Exception e){
			return obj;
		}
		return obj;
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
