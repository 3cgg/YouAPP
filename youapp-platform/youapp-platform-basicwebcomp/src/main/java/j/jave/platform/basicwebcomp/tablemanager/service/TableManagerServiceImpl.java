package j.jave.platform.basicwebcomp.tablemanager.service;

import j.jave.kernal.eventdriven.exception.JServiceException;
import j.jave.kernal.jave.model.JBaseModel;
import j.jave.kernal.jave.model.JPagination;
import j.jave.kernal.jave.model.support.JColumn;
import j.jave.kernal.jave.model.support.JModelMapper;
import j.jave.kernal.jave.model.support.JTable;
import j.jave.kernal.jave.reflect.JClassUtils;
import j.jave.kernal.jave.support._package.JDefaultClassesScanner;
import j.jave.platform.basicwebcomp.core.model.SearchCriteria;
import j.jave.platform.basicwebcomp.core.service.ServiceContext;
import j.jave.platform.basicwebcomp.tablemanager.model.Cell;
import j.jave.platform.basicwebcomp.tablemanager.model.Column;
import j.jave.platform.basicwebcomp.tablemanager.model.Record;
import j.jave.platform.basicwebcomp.tablemanager.model.Table;
import j.jave.platform.basicwebcomp.tablemanager.model.TableSearch;
import j.jave.platform.mybatis.JMapper;

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
	
	private volatile boolean loaded=false;
	
	/**
	 * key :  sub-class of {@link JMapper}
	 * <p> value : object 
	 */
	private Map<Class<?>, JMapper< JBaseModel>> mappersWithMapperClass=new ConcurrentHashMap<Class<?>, JMapper<JBaseModel>>();

	/**
	 * key :  name of mapping model.
	 * <p> value : object 
	 */
	private Map<String, JMapper<JBaseModel>> mappersWithModelName=new ConcurrentHashMap<String, JMapper<JBaseModel>>();

	
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
			JDefaultClassesScanner defaultPackageScan= new JDefaultClassesScanner(JMapper.class);
			defaultPackageScan.setIncludePackages(new String[]{"j"});
			Set<Class<?>> classes=defaultPackageScan.scan();
			if(classes!=null){
				for (Iterator<Class<?>> iterator = classes.iterator(); iterator.hasNext();) {
					Class<?> clazz = iterator.next();
					JModelMapper modelMapper=clazz.getAnnotation(JModelMapper.class);
					if(modelMapper!=null){
						// load mapper 
						String component=modelMapper.component();
						JMapper mapper=applicationContext.getBean(component,JMapper.class);
						mappersWithMapperClass.put(clazz, mapper);
						
						Class<? extends JBaseModel>  model=modelMapper.name();
						mappersWithModelName.put(model.getName(), mapper); 
						
						// load table ... 
						JTable tableAnnotation=model.getAnnotation(JTable.class);
						if(tableAnnotation!=null){
							
							// load table
							Table table=new Table();
							table.setTableName(tableAnnotation.name());
							table.setOwner(tableAnnotation.schema());
							table.setModelName(model.getName());
							tables.add(table);
							
							//load column 
							addColumn(model, tableAnnotation.name(), columnsWithTableName);
							addColumn(model, model.getName(), columnsWithModelName);
							
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
				if(!JClassUtils.isAccessable(field)){
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
					clm.setPropertyTypeName(field.getType().getName());
					clm.setPropertyType(field.getType());
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
		init();
		
		return tables;
	}

	@Override
	public List<Column> getColumnsByTable(ServiceContext serviceContext,
			String tableName) {
		init();
		
		return columnsWithTableName.get(tableName);
	}
	
	@Override
	public List<Column> getColumnsByModelName(ServiceContext serviceContext,
			String modelName) {
		init();
		
		return columnsWithModelName.get(modelName);
	}

	private String getModelName(JBaseModel model){
		return model.getClass().getName();
	}
	
	@Override
	public Record getRecord(ServiceContext serviceContext, JBaseModel model) {
		init();
		
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
				if(!JClassUtils.isAccessable(field)){
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
	public List<Record> getRecords(ServiceContext serviceContext, SearchCriteria model) {
		
		if(!JPagination.class.isInstance(model)){
			throw new RuntimeException(model.getClass().getName()+" not supported, as not the sub-clss of "+JPagination.class.getName());
		}
		
		init();
		
		TableSearch tableSearch=(TableSearch) model;
		
		JMapper<? extends JBaseModel> mapper=mappersWithModelName.get(tableSearch.getModelName());
		
		List<? extends JBaseModel> models=mapper.getsByPage((JPagination) model);
		List<Record> records=new ArrayList<Record>();
		for (int i = 0; i < models.size(); i++) {
			JBaseModel baseModel=models.get(i);
			records.add(getRecord(baseModel));
		}
		return records;
	}
	
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.applicationContext=applicationContext;
	}

	@Override
	public Record getRecord(ServiceContext serviceContext, String modelName,
			String id) {
		init();
		
		JMapper<? extends JBaseModel> mapper=mappersWithModelName.get(modelName);
		JBaseModel baseModel=mapper.get(id);
		return getRecord(baseModel);
	}
	
	private void init(){
		if(!loaded){
			loadMapper();
		}
	}
	
	@Override
	public void updateRecord(ServiceContext serviceContext, Record record) throws JServiceException {
		init();
		
		try {
			String modelName=record.getModelName();
			JBaseModel model=get(record);
			JMapper<JBaseModel> mapper=mappersWithModelName.get(modelName);
			mapper.update( model);
			
		} catch (Exception e) {
			throw new JServiceException(e);
		}
	}
	
	
	private JBaseModel get(Record record) throws Exception{
		String modelName=record.getModelName();
		Class<?> clazz=JClassUtils.load(modelName, Thread.currentThread().getContextClassLoader());
		Object model=clazz.newInstance();
		List<Cell> cells=record.getCells();
		for(int i=0;i<cells.size();i++){
			Cell cell=cells.get(i);
			JClassUtils.set(cell.getColumn().getPropertyName(), cell.getObject(), model);
		}
		return (JBaseModel) model;
	}
	
	
	
	
	
	
	
	
	
}
