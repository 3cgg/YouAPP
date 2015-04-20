package j.jave.framework.components.tablemanager.service;

import j.jave.framework._package.JDefaultPackageScan;
import j.jave.framework.components.core.service.ServiceContext;
import j.jave.framework.components.tablemanager.model.Cell;
import j.jave.framework.components.tablemanager.model.Column;
import j.jave.framework.components.tablemanager.model.Record;
import j.jave.framework.components.tablemanager.model.Table;
import j.jave.framework.model.JBaseModel;
import j.jave.framework.model.support.JColumn;
import j.jave.framework.model.support.JModelMapper;
import j.jave.framework.model.support.JTable;
import j.jave.framework.mybatis.JMapper;
import j.jave.framework.reflect.JReflect;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;


@Service(value="tableManagerServiceImpl")
public class TableManagerServiceImpl implements TableManagerService ,ApplicationContextAware{

	private ApplicationContext applicationContext=null;
	
	private boolean loaded=false;
	
	/**
	 * key :  sub-class of {@link JMapper}
	 * <p> value : object 
	 */
	private Map<Class<?>, JMapper<? extends JBaseModel>> mappersWithMapperClass=new ConcurrentHashMap<Class<?>, JMapper<? extends JBaseModel>>();

	/**
	 * key :  name of mapping model.
	 * <p> value : object 
	 */
	private Map<String, JMapper<? extends JBaseModel>> mappersWithModelName=new ConcurrentHashMap<String, JMapper<? extends JBaseModel>>();

	
	/**
	 * KEY : Table Name. 
	 */
	private Map<String, List<Column>> columnsWithTableName=new ConcurrentHashMap<String, List<Column>>();
	
	
	
	/**
	 * KEY: model name 
	 */
	private Map<String, List<Column>> columnsWithModelName=new ConcurrentHashMap<String, List<Column>>();
	
	/**
	 * KEY: model name 
	 */
	private Map<String, Map<String,Column>> columns=new ConcurrentHashMap<String, Map<String,Column>>();
	
	private List<Table> tables=new ArrayList<Table>();
	
	private synchronized void loadMapper(){
		if(!loaded){
			// get all mappers 
			JDefaultPackageScan defaultPackageScan= new JDefaultPackageScan(JMapper.class);
			defaultPackageScan.setIncludePackages(new String[]{"j"});
			Set<Class<?>> classes=defaultPackageScan.scan();
			if(classes!=null){
				for (Iterator<Class<?>> iterator = classes.iterator(); iterator.hasNext();) {
					Class<?> clazz = iterator.next();
					JModelMapper modelMapper=clazz.getDeclaredAnnotation(JModelMapper.class);
					if(modelMapper!=null){
						// load mapper 
						String component=modelMapper.component();
						JMapper mapper=applicationContext.getBean(component,JMapper.class);
						mappersWithMapperClass.put(clazz, mapper);
						
						Class<? extends JBaseModel>  model=modelMapper.name();
						mappersWithModelName.put(model.getSimpleName(), mapper); 
						
						// load table ... 
						JTable tableAnnotation=model.getDeclaredAnnotation(JTable.class);
						if(tableAnnotation!=null){
							
							// load table
							Table table=new Table();
							table.setTableName(tableAnnotation.name());
							table.setOwner(tableAnnotation.schema());
							table.setModelName(model.getSimpleName());
							tables.add(table);
							
							//load column 
							addColumn(model, tableAnnotation.name(), columnsWithTableName);
							addColumn(model, model.getSimpleName(), columnsWithModelName);
							
							convertColumns(columnsWithModelName, columns);
						}
						
					}
				}
			}
			
			loaded=true;
		}
	}
	
	private void convertColumns(Map<String, List<Column>> columnsWithModelName,Map<String, Map<String,Column>> columns){
		if(columnsWithModelName!=null){
			for (Iterator<Entry<String, List<Column>>> iterator = columnsWithModelName.entrySet().iterator(); iterator.hasNext();) {
				Entry<String, List<Column>> entry = iterator.next();
				Map<String, Column> maps=new HashMap<String, Column>();
				columns.put(entry.getKey(), maps);
				List<Column> colms=entry.getValue();
				for (Column clm : colms) {
					maps.put(clm.getPropertyName(), clm);
				}
			}
		}
	}
	
	private void addColumn(Class<? extends JBaseModel> model,String key,Map<String, List<Column>> columns) {
		Class<?> superClass=model;
		while(superClass!=null){
			Field[] fields=superClass.getDeclaredFields();
			for(int i=0;i<fields.length;i++){
				Field field=fields[i];
				if(!JReflect.isAccessable(field)){
					field.setAccessible(true);
				}
				
				JColumn column=field.getAnnotation(JColumn.class);
				// check whether the property is mapping to a column of a table.
				if(column==null){
					continue;
				}
				else{
					Column clm=new Column();
					clm.setColumnName(column.name());
					clm.setPropertyName(field.getName());
					clm.setPropertyType(field.getType().getSimpleName());
					clm.setSqlType(column.type().name());
					
					List<Column> inners=columns.get(key);
					if(inners==null){
						inners=new ArrayList<Column>();
						inners.add(clm);
						columns.put(key, inners);
					}
					else{
						inners.add(clm);
					}
				}
			}
			superClass=superClass.getSuperclass();
		}
	}
	
	
	@Override
	public List<Table> getTables() {
		if(!loaded){
			loadMapper();
		}
		return tables;
	}

	@Override
	public List<Column> getColumnsByTable(ServiceContext serviceContext,
			String tableName) {
		if(!loaded){
			loadMapper();
		}
		return columnsWithTableName.get(tableName);
	}

	private String getModelName(JBaseModel model){
		return model.getClass().getSimpleName();
	}
	
	@Override
	public Record getRecord(ServiceContext serviceContext, JBaseModel model) {
		if(!loaded){
			loadMapper();
		}
		JMapper<? extends JBaseModel> mapper=mappersWithModelName.get(getModelName(model));
		JBaseModel baseModel=mapper.get(model.getId());
		return getRecord(baseModel);
	}
	
	
	private Record getRecord(JBaseModel baseModel){
		Record record=new Record();
		List<Cell> cells=new ArrayList<Cell>();
		record.setCells(cells);

		Class<?> superClass=baseModel.getClass();
		while(superClass!=null){
			Field[] fields=superClass.getDeclaredFields();
			for(int i=0;i<fields.length;i++){
				Field field=fields[i];
				if(!JReflect.isAccessable(field)){
					field.setAccessible(true);
				}
				
				JColumn column=field.getAnnotation(JColumn.class);
				// check whether the property is mapping to a column of a table.
				if(column==null){
					continue;
				}
				else{
					Cell cell=new Cell();
					cell.setColumn(columns.get(getModelName(baseModel)).get(field.getName())) ; 
					try {
						cell.setObject(field.get(baseModel));
					} catch (Exception e) {
						throw new RuntimeException(e);
					} 
					cells.add(cell);
				}
			}
			superClass=superClass.getSuperclass();
		}
		
		return record;
	}
	
	
	
	
	
	@Override
	public List<Record> getRecords(ServiceContext serviceContext, JBaseModel model) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.applicationContext=applicationContext;
	}

}
