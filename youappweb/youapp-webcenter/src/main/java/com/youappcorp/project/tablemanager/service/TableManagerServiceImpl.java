package com.youappcorp.project.tablemanager.service;

import j.jave.platform.data.web.model.Criteria;
import me.bunny.kernel.eventdriven.exception.JServiceException;
import me.bunny.kernel.jave.logging.JLogger;
import me.bunny.kernel.jave.logging.JLoggerFactory;
import me.bunny.kernel.jave.model.JBaseModel;
import me.bunny.kernel.jave.model.JPageable;
import me.bunny.kernel.jave.model.support.JColumn;
import me.bunny.kernel.jave.model.support.JModelRepo;
import me.bunny.kernel.jave.model.support.JTable;
import me.bunny.kernel.jave.persist.JIPersist;
import me.bunny.kernel.jave.reflect.JClassUtils;
import me.bunny.kernel.jave.support._package.JDefaultClassesScanner;

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
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import com.youappcorp.project.tablemanager.model.Cell;
import com.youappcorp.project.tablemanager.model.Column;
import com.youappcorp.project.tablemanager.model.Record;
import com.youappcorp.project.tablemanager.model.Table;
import com.youappcorp.project.tablemanager.model.TableSearch;


@Service(value="tableManagerServiceImpl")
public class TableManagerServiceImpl implements TableManagerService ,ApplicationContextAware{

	private static final JLogger LOGGER=JLoggerFactory.getLogger(TableManagerServiceImpl.class);
	
	private ApplicationContext applicationContext=null;
	
	private volatile boolean loaded=false;
	
	/**
	 * key :  sub-class of {@link JIPersist}
	 * <p> value : object 
	 */
	private Map<Class<?>, JIPersist<?,JBaseModel, String>> mappersWithMapperClass=new ConcurrentHashMap<Class<?>, JIPersist<?,JBaseModel, String>>();

	/**
	 * key :  name of mapping model.
	 * <p> value : object 
	 */
	private Map<String, JIPersist<?,JBaseModel, String>> mappersWithModelName=new ConcurrentHashMap<String, JIPersist<?,JBaseModel, String>>();

	
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
			JDefaultClassesScanner defaultPackageScan= new JDefaultClassesScanner(JIPersist.class);
			defaultPackageScan.setIncludePackages(new String[]{"j.jave"});
			Set<Class<?>> classes=defaultPackageScan.scan();
			if(classes!=null){
				for (Iterator<Class<?>> iterator = classes.iterator(); iterator.hasNext();) {
					Class<?> clazz = iterator.next();
					JModelRepo modelMapper=clazz.getAnnotation(JModelRepo.class);
					if(modelMapper!=null){
						// load mapper 
						String component=modelMapper.component();
						JIPersist<?, JBaseModel, String> mapper=null;
						try{
							mapper=applicationContext.getBean(component,JIPersist.class);
						}catch(NoSuchBeanDefinitionException e ){
							LOGGER.info(e.getMessage(), e);
							continue;
						}
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
	public List<Column> getColumnsByTable(
			String tableName) {
		init();
		
		return columnsWithTableName.get(tableName);
	}
	
	@Override
	public List<Column> getColumnsByModelName(
			String modelName) {
		init();
		
		return columnsWithModelName.get(modelName);
	}

	private String getModelName(JBaseModel model){
		return model.getClass().getName();
	}
	
	@Override
	public Record getRecord( JBaseModel model) {
		init();
		
		JIPersist<?,JBaseModel, String> mapper=mappersWithModelName.get(getModelName(model));
		JBaseModel baseModel=mapper.getModel(model.getId());
		return internalGetRecord(baseModel);
	}
	
	
	private Record internalGetRecord(JBaseModel baseModel){
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
	public List<Record> getRecords( Criteria model) {
		
		if(!JPageable.class.isInstance(model)){
			throw new RuntimeException(model.getClass().getName()+" not supported, as not the sub-clss of "+JPageable.class.getName());
		}
		
		init();
		
		TableSearch tableSearch=(TableSearch) model;
		
		JIPersist<?,JBaseModel, String> mapper=mappersWithModelName.get(tableSearch.getModelName());
		
		List<? extends JBaseModel> models=mapper.getModelsByPage((JPageable) model);
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
	public Record getRecord( String modelName,
			String id) {
		init();
		
		JIPersist<? , JBaseModel, String> mapper=mappersWithModelName.get(modelName);
		JBaseModel baseModel=mapper.getModel(id);
		return getRecord(baseModel);
	}
	
	private void init(){
		if(!loaded){
			loadMapper();
		}
	}
	
	@Override
	public void updateRecord( Record record) throws JServiceException {
		init();
		
		try {
			String modelName=record.getModelName();
			JBaseModel model=get(record);
			JIPersist<?,JBaseModel, String> mapper=mappersWithModelName.get(modelName);
			mapper.updateModel( model);
			
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
