package j.jave.kernal.jave.xml.xmldb;

import j.jave.kernal.jave.logging.JLogger;
import j.jave.kernal.jave.logging.JLoggerFactory;
import j.jave.kernal.jave.model.JBaseModel;
import j.jave.kernal.jave.model.support.detect.JColumnInfo;
import j.jave.kernal.jave.model.support.detect.JModelDetect;
import j.jave.kernal.jave.reflect.JClassUtils;
import j.jave.kernal.jave.utils.JCollectionUtils;
import j.jave.kernal.jave.utils.JNumberUtils;
import j.jave.kernal.jave.utils.JStringUtils;
import j.jave.kernal.jave.xml.xmldb.exception.JJQLParseException;
import j.jave.kernal.jave.xml.xmldb.jql.JAggregate;
import j.jave.kernal.jave.xml.xmldb.jql.JAggregateType;
import j.jave.kernal.jave.xml.xmldb.jql.JAndLink;
import j.jave.kernal.jave.xml.xmldb.jql.JAscSort;
import j.jave.kernal.jave.xml.xmldb.jql.JAvgAggregate;
import j.jave.kernal.jave.xml.xmldb.jql.JCountAggregate;
import j.jave.kernal.jave.xml.xmldb.jql.JDescSort;
import j.jave.kernal.jave.xml.xmldb.jql.JFilter;
import j.jave.kernal.jave.xml.xmldb.jql.JGroup;
import j.jave.kernal.jave.xml.xmldb.jql.JIF;
import j.jave.kernal.jave.xml.xmldb.jql.JIFPlain;
import j.jave.kernal.jave.xml.xmldb.jql.JIFRef;
import j.jave.kernal.jave.xml.xmldb.jql.JIFString;
import j.jave.kernal.jave.xml.xmldb.jql.JLink;
import j.jave.kernal.jave.xml.xmldb.jql.JMaxAggregate;
import j.jave.kernal.jave.xml.xmldb.jql.JMinAggregate;
import j.jave.kernal.jave.xml.xmldb.jql.JOrLink;
import j.jave.kernal.jave.xml.xmldb.jql.JResult;
import j.jave.kernal.jave.xml.xmldb.jql.JResultPlain;
import j.jave.kernal.jave.xml.xmldb.jql.JResultRef;
import j.jave.kernal.jave.xml.xmldb.jql.JSort;
import j.jave.kernal.jave.xml.xmldb.jql.JSumAggregate;
import j.jave.kernal.jave.xml.xmldb.jql.JValueRef;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class JXMLSelect implements JSelect {

	private JModelDetect modelDetect = JModelDetect.get();
	
	private static JXMLDataSource xmlDataSource=JSinglePropertiesXMLDataSource.get();
	
	private static final JLogger logger = JLoggerFactory.getLogger(JXMLSelect.class);

	/**
	 * KEY :  FULL CLASS NAME
	 * <br/>VALUE : CLASS
	 */
	private Map<String, Class<? extends JBaseModel>> from
					=new LinkedHashMap<String, Class<? extends JBaseModel>>();
	
	/**
	 * RESULT PART 
	 */
	private List<JResult> results=new ArrayList<JResult>();

	/**
	 * AGGREGATE PART , DEFINED IN THE JQL
	 */
	private List<JAggregate> aggregates=new ArrayList<JAggregate>();

	/**
	 * GROUP BY PART , 
	 */
	private List<JValueRef> groupBy=new ArrayList<JValueRef>();
	/**
	 * KEY :  THE UNIQUE HASH CODE THAT INDICATES THE SAME RECORD 
	 * <br/>VALUE : ALL CLONED AGGREGATE DEFINED IN THE JQL 
	 * {@link #aggregates}
	 */
	private Map<Integer,List<JAggregate>> groupAggregates= new HashMap<Integer, List<JAggregate>>();
	/**
	 *  KEY :  THE UNIQUE HASH CODE THAT INDICATES THE SAME RECORD 
	 *  <br/> the model index array , value is consists of different model index of {@link #models}
	 */
	private Map<Integer,int[]> groupItemWithNoSort= new LinkedHashMap<Integer, int[]>();

	/**
	 * SORT PART
	 */
	private List<JSort> sorts=new ArrayList<JSort>();
	
	/**
	 * the model index array , value is consists of different model index of {@link #models}
	 */
	private List<int[]> sortResult=new ArrayList<int[]>();
	
	/**
	 * KEY :  hash code of the same value according to the order by items
	 * <br/>VALUE : the scope of index in the {@link #sortResult} list  
	 */
	private Map<Integer, int[]> orderGroup=new HashMap<Integer, int[]>();

	/**
	 * WHERE PART 
	 */
	private JFilter filter=null;

	private int maxsize=1000;

	/**
	 * KEY :  ALIAS
	 * <br/>VALUE : ALL MODELS
	 */
	private Map<String, List<JBaseModel>> models=null;

	JXMLSelect(){}
	
	public void putFrom(Class<? extends JBaseModel> clazz,String alias){

		if(JStringUtils.isNullOrEmpty(alias)){
			throw new JJQLParseException("alias cannot be empty.");
		}

		if(DEFAULT_ALIAS.equals(alias)){
			throw new JJQLParseException("alias "+alias+" is used by system,please specify other alias.");
		}

		if(from.get(alias)==null){
			from.put(alias, clazz);
		}
		else{
			throw new JJQLParseException("alias already exists. please define other difference alias.");
		}
	}

	public void putFrom(Class<? extends JBaseModel> clazz){
		if(JCollectionUtils.hasInMap(from)){
			throw new JJQLParseException("one already exists. more than one in form need define alias for it.");
		}
		else{
			from.put(DEFAULT_ALIAS, clazz);
		}
	}


	/**
	 * please specify the full top condition , like where in the SQL.
	 * i.e. where 1=1 AND 2=2 AND 3=3 AND (5=6 AND 5=5)
	 * 2=2 is invalid, 5=5 is invalid , and (5=6 AND 5=5) is also invalid.
	 * only 1=1 AND 2=2 AND 3=3 AND (5=6 AND 5=5) is valid.
	 * Only {@link JIF} , {@link JGroup} is valid.
	 * @param group
	 */
	public void putWhere(JFilter filter){
		if(JLink.class.isInstance(filter)){
			throw new JJQLParseException("only IF and Group are valid.");
		}
		else{
			this.filter=filter;
		}
	}


	public void addResult(JResult resultRef){
		results.add(resultRef);
	}


	public void addResult(List<JResult> resultRefs){
		results.addAll(resultRefs);
	}

	public void addResult(String alias){
		Class<? extends JBaseModel> clazz=from.get(alias);

		if(clazz==null) {
			throw new JJQLParseException("the result with alias "+alias+" does not exists in From part.");
		}

		List<JColumnInfo> mappings= getMappingFields(clazz);
		if(JCollectionUtils.hasInCollect(mappings)){
			for (Iterator<JColumnInfo> iterator = mappings.iterator(); iterator.hasNext();) {
				JColumnInfo columnInfo = iterator.next();
				addResult(new JResultRef(columnInfo.getField().getName(), alias));
			}
		}
	}


	/**
	 * return the collection of map.
	 * a map express a record.
	 * @return
	 */
	public List<Map<String, Object>> select(){
		validate();
		reset();
		List<Map<String, Object>> result=null;
		if(JCollectionUtils.hasInMap(from)){

			models=new HashMap<String, List<JBaseModel>>();
//			Persit persit=Persit.get();
			int i=0;
			for (Iterator<Entry<String, Class<? extends JBaseModel>>> iterator = from.entrySet().iterator(); iterator.hasNext();) {
				Entry<String, Class<? extends JBaseModel>> entry=
						iterator.next();
//				models.put(entry.getKey(),persit.gets((Class<JBaseModel>)entry.getValue()));
				models.put(entry.getKey(),xmlDataSource.gets(entry.getValue()));
				i++;
			}

			String[] alias=new String[i];
			Count[] counts=new Count[i];
			if(JCollectionUtils.hasInMap(models)){
				i=0;
				for (Iterator<Entry<String,List<JBaseModel>>> iterator = models.entrySet().iterator(); iterator
						.hasNext();) {
					Entry<String,List<JBaseModel>> entry=iterator.next();
					alias[i]=entry.getKey();
					counts[i]=new Count(entry.getValue().size());
					i++;
				}

				List<Map<String, JBaseModel>> list =select(alias, counts);
				if(JCollectionUtils.hasInCollect(list)){
					result=new ArrayList<Map<String,Object>>();
					
					if(JCollectionUtils.hasInCollect(this.aggregates)
							&&!JCollectionUtils.hasInCollect(groupBy)){
						result.add(aggregateValue(this.aggregates));
					}
					else{
						for (Iterator<Map<String, JBaseModel>> iterator = list.iterator(); iterator
								.hasNext();) {
							Map<String, JBaseModel> map = iterator
									.next();
							Map<String, Object> item=new LinkedHashMap<String, Object>();
							
							// get aggregate
							if(JCollectionUtils.hasInCollect(groupBy)){
								List<String> strings=new ArrayList<String>();
								for(int j=0;j<this.groupBy.size();j++){
									JValueRef valueRef=groupBy.get(j);
									Object obj= valueRef.value(map);
									strings.add(obj==null?"":String.valueOf(obj));
								}
								Integer key=getKey(strings);

								if(JCollectionUtils.hasInMap(groupAggregates)){
									item.putAll(aggregateValue(groupAggregates.get(key)));
								}
							}
//							else{
//								List<String> strings=new ArrayList<String>();
//								for(int j=0;j<this.groupBy.size();j++){
//									ValueRef valueRef=groupBy.get(j);
//									Object obj= valueRef.value(map);
//									strings.add(obj==null?"":String.valueOf(obj));
//								}
//								Integer key=getKey(strings);
	//
//								if(JCollectionUtils.hasInMap(groupAggregates)){
//									item.putAll(aggregateValue(groupAggregates.get(key)));
//								}
//							}

							if(JCollectionUtils.hasInCollect(results)){
								for(int j=0;j<results.size();j++){
									JResult resultRef=results.get(j);
									Object value=resultRef.value(map);
									item.put(queueName(resultRef.getName(), item) , value==null?"":value);
								}
							}
//							else if(!JCollectionUtils.hasInCollect(this.aggregates)){
							// may get all defined columns in the class. 
							else {

								for (Iterator<Entry<String, Class<? extends JBaseModel>>> iterator3 = from.entrySet().iterator(); iterator3.hasNext();) {

									// alias- class
									Entry<String, Class<? extends JBaseModel>> entry=
											iterator3.next();
									Class<? extends JBaseModel> clazz=entry.getValue();
									List<JColumnInfo> mappings= getMappingFields(clazz);

									// properties in the mapping class
									if(JCollectionUtils.hasInCollect(mappings)){
										for (Iterator<JColumnInfo> iterator2 = mappings.iterator(); iterator2.hasNext();) {
											JColumnInfo columnInfo =  iterator2.next();
											String property=columnInfo.getField().getName();
											Object value= JClassUtils.get(property
													, map.get(entry.getKey()));

											item.put(queueName(property, item), value==null?"":value);

										}
									}
								}
							}
							result.add(item);
						}
					}
				}

			}

		}
		return result;
	}


	private String queueName(String property,Map<String , Object> map){
		String tempName=property;
		int i=1;
		while(true){
			if(map.get(tempName)!=null){
				tempName=property;
				tempName=tempName+"_"+i;
				break;
			}
			else{
				break;
			}
		}
		return tempName;

	}

	/**
	 * convert the result data to type.
	 * @param clazz
	 * @return
	 */
	public <T> List<T> select(Class<T> clazz){
		validate();
		reset();
		List<T> models=null;
		try{
			List<Map<String, Object>> list=select();
			if(JCollectionUtils.hasInCollect(list)){
				models=new ArrayList<T>();
				for (Iterator<Map<String, Object>> iterator = list.iterator(); iterator.hasNext();) {
					T model=clazz.newInstance();

					Map<String, Object> map = iterator.next();
					for (Iterator<Entry<String, Object>> iterator2 = map.entrySet().iterator(); iterator2.hasNext();) {
						Entry<String, Object> entry=iterator2.next();
//						B.set(entry.getKey(), entry.getValue(), (JBaseModel)model);
						JClassUtils.set(entry.getKey(), entry.getValue(), model);
					}
					models.add(model);
				}
			}
		}catch (Exception e) {
			throw new JJQLParseException(e);
		}

		return models;
	}

	private void reset() {
		if(JCollectionUtils.hasInCollect(aggregates)){
			for (Iterator<JAggregate> iterator = aggregates.iterator(); iterator.hasNext();) {
				JAggregate aggregate =  iterator.next();
				aggregate.reset();
			}
		}
		models=null;
	}


	/**
	 * get most records according to the where part,(filter)
	 * , and can also break when the returned record number exceed the max .
	 * @param alias
	 * @param counts
	 * @return
	 */
	private List<Map<String, JBaseModel>> select(String[] alias,Count[] counts){

		List<Map<String, JBaseModel>> result=new ArrayList<Map<String,JBaseModel>>();

		int size=0;
		while(true){
			if(counts[counts.length-1].now>counts[counts.length-1].max){
				break;
			}
			Count count=counts[0];
			if(count.now<=count.max){
				Map<String, JBaseModel> mods=getMap(alias, counts);
				boolean pass=true;
				if(filter!=null){
					pass=filter.validate(mods);
				}
				if(pass){

					boolean isSort=true;
					
					// get model index array to get different part from the related model. 
					int[] modelIndex=new int[counts.length];
					for (int i = 0; i < counts.length; i++) {
						modelIndex[i]=counts[i].now-1;
					}

					if(JCollectionUtils.hasInCollect(groupBy)){
						List<String> strings=new ArrayList<String>();
						for(int i=0;i<this.groupBy.size();i++){
							JValueRef valueRef=groupBy.get(i);
							Object obj=valueRef.value(mods);
							strings.add(obj==null?"":String.valueOf(obj));
						}
						// get an unique integer value to indicate those records which have different values.
						Integer key=getKey(strings);

						// calculate aggregate based on the group
						if(groupAggregates.containsKey(key)){
							List<JAggregate> aggregates=groupAggregates.get(key);
							if(JCollectionUtils.hasInCollect(aggregates)){
								for (Iterator<JAggregate> iterator = aggregates.iterator(); iterator.hasNext();) {
									JAggregate aggregate =  iterator.next();
									aggregate.execute(mods);
								}
							}
							isSort=false;
						}
						else { // no aggregates exist.
							//always get only one model index that has the same value as other model index
							groupItemWithNoSort.put(key, modelIndex);

							if(JCollectionUtils.hasInCollect(this.aggregates)){
								List<JAggregate> aggregates =new ArrayList<JAggregate>();
								for (Iterator<JAggregate> iterator = this.aggregates.iterator(); iterator.hasNext();) {
									JAggregate aggregate =  iterator.next();
									try {
										JAggregate cloneAggregate=(JAggregate)aggregate.clone();
										cloneAggregate.execute(mods);
										aggregates.add(cloneAggregate);
									} catch (CloneNotSupportedException e) {
										throw new JJQLParseException(e);
									}
								}
								groupAggregates.put(key, aggregates);
							}
							else{
								groupAggregates.put(key, null);
							}
						}
					}


					if(JCollectionUtils.hasInCollect(sorts)&&isSort){
						sort(alias, modelIndex, mods);
					}

					if(!JCollectionUtils.hasInCollect(groupBy)
							&&JCollectionUtils.hasInCollect(aggregates)){
						for (Iterator<JAggregate> iterator = aggregates.iterator(); iterator.hasNext();) {
							JAggregate aggregate =  iterator.next();
							aggregate.execute(mods);
						}
					}

					result.add(mods);
					
					size++;
					// check if need interrupt the loop.
					if(size>maxsize
						&&!JCollectionUtils.hasInCollect(sorts)
								&&!JCollectionUtils.hasInCollect(groupBy)
								&&!JCollectionUtils.hasInCollect(aggregates)){
						break;
					}
					
//					if(!JCollectionUtils.hasInCollect(sorts)
//							&&!JCollectionUtils.hasInCollect(groupBy)
//							&&size<maxsize) {
//						result.add(mods);
//					}
//					else{
//						if(!JCollectionUtils.hasInCollect(aggregates)
//								&&!JCollectionUtils.hasInCollect(sorts)){
//							break;
//						}
//					}
				}
				count.now++;
			}
			else{
				// plus at the higher bit.
				for (int i = 0; i < counts.length; i++) {
					Count inner=counts[i];
					if(inner.now>inner.max&&i==counts.length-1){
						break;
					}
					if(inner.now>inner.max){
						counts[i+1].now++;
						inner.now=1;
					}
				}
			}

		}

		if(JCollectionUtils.hasInCollect(sorts)&&JCollectionUtils.hasInCollect(sortResult)){
			result=new ArrayList<Map<String,JBaseModel>>();
			for (Iterator<int[]> iterator = sortResult.iterator(); iterator.hasNext();) {
				int[] modelIndexs =  iterator.next();
				Map<String, JBaseModel> map=new HashMap<String, JBaseModel>();
				for (int i = 0; i < modelIndexs.length; i++) {
					map.put(alias[i], models.get(alias[i]).get(modelIndexs[i]));
				}
				result.add(map);
			}
		}
		else if(JCollectionUtils.hasInCollect(groupBy)&&JCollectionUtils.hasInMap(groupItemWithNoSort)){
			result=new ArrayList<Map<String,JBaseModel>>();
			for (Iterator<int[]> iterator = groupItemWithNoSort.values().iterator(); iterator.hasNext();) {
				int[] modelIndexs = (int[]) iterator.next();
				Map<String, JBaseModel> map=new HashMap<String, JBaseModel>();
				for (int i = 0; i < modelIndexs.length; i++) {
					map.put(alias[i], models.get(alias[i]).get(modelIndexs[i]));
				}
				result.add(map);
			}
		}

		return result;
	}

	private Map<String, JBaseModel> getMap(String[] alias,Count[] counts){
		Map<String, JBaseModel> map=new HashMap<String, JBaseModel>();
		for (int i = 0; i < counts.length; i++) {
			map.put(alias[i], models.get(alias[i]).get(counts[i].now-1));
		}
		return map;
	}



	private class Count{
		private int max;

		public Count(int max) {
			this.max=max;
		}
		int now=1;
	}

	public String table(List<Map<String, Object>> list){
		String table="<SN0.> ";
		if(JCollectionUtils.hasInCollect(list)){
			for (int i=0;i<list.size();i++) {
				Map<String, Object> map = (Map<String, Object>) list.get(i);
				String values="";
				for (Iterator<String> iterator = map.keySet().iterator(); iterator
						.hasNext();) {
					String name=iterator.next();
					if(i==0){
						table=table+name+"  ";
					}
					values=values+map.get(name)+",";
				}
				table=table+"\n"+(i+1)+" , "+values;
			}
		}
		return table;

	}

	/**
	 * define the result count according to the coditions in where part.
	 * default value is 1000, in other words, all after 1000 is dropped.
	 * if you need any records , such as have more than 1000 , please put what value
	 * you want.
	 * @param maxsize
	 */
	public void setMaxsize(int maxsize) {
		this.maxsize = maxsize;
	}

	/**
	 * {@link JAggregateType} results belong to this item.
	 * @param aggregate
	 */
	public void addAggregate(JResultRef valueRef,JAggregateType aggregateType){

		if(aggregateType==JAggregateType.AVG){
			this.aggregates.add(new JAvgAggregate(valueRef.getProperty() ,
					valueRef.getAlias(),valueRef.getName()));
		}
		else if(aggregateType==JAggregateType.COUNT){
			this.aggregates.add(new JCountAggregate(valueRef.getProperty() ,
					valueRef.getAlias(),valueRef.getName()));
		}
		else if(aggregateType==JAggregateType.MAX){
			this.aggregates.add(new JMaxAggregate(valueRef.getProperty() ,
					valueRef.getAlias(),valueRef.getName()));
		}
		else if(aggregateType==JAggregateType.MIN){
			this.aggregates.add(new JMinAggregate(valueRef.getProperty() ,
					valueRef.getAlias(),valueRef.getName()));
		}
		else if(aggregateType==JAggregateType.SUM){
			this.aggregates.add(new JSumAggregate(valueRef.getProperty() ,
					valueRef.getAlias(),valueRef.getName()));
		}
	}

	/**
	 * {@link JAggregateType} results belong to this item.
	 * {@link JMinAggregate},{@link JMaxAggregate} {@link JSumAggregate}
	 * {@link JCountAggregate} {@link JAvgAggregate} can be used.
	 * @param aggregate
	 */
	public void addAggregate(JAggregate aggregate){
		this.aggregates.add(aggregate);
	}

	private Map<String, Object> aggregateValue(List<JAggregate> aggregates){
		Map<String, Object> maps=new LinkedHashMap<String, Object>();
		if(JCollectionUtils.hasInCollect(aggregates)){
			for (Iterator<JAggregate> iterator = aggregates.iterator(); iterator.hasNext();) {
				JAggregate aggregate =  iterator.next();
				maps.put(aggregate.getName(), aggregate.get());
			}
			return maps;
		}
		return maps;
	}


	/**
	 * add group by part.
	 * such as select * from ** <strong>group by ** </strong>.
	 * @param valueRef
	 */
	public void addGroupBy(JValueRef valueRef){
		if(this.groupBy.contains(valueRef)){
			throw new JJQLParseException("same column already exists in the group by part.");
		}
		this.groupBy.add(valueRef);
	}


	public void addOrderBy(JSort sort){
		if(this.sorts.contains(sort)){
			throw new JJQLParseException("same column already exists in the order by part.");
		}
		this.sorts.add(sort);
	}

	/**
	 * get an unique hash code value according to the passed string,
	 * the hash code can be used to indicate a group of records that have the same value.
	 * @param strings
	 * @return
	 */
	private Integer getKey(List<String> strings){
		String split="~~~~~~~";
		String string="";
		for (Iterator<String> iterator = strings.iterator(); iterator.hasNext();) {
			String key =  iterator.next();
			string=string+split+key+split;
		}
		return string.hashCode();
	}

	
	/**
	 * sort the records, the sort order is based on which the item appears followed by the order by,
	 * i..e order by column1 asc, column2 asc, column3 desc etc.
	 * @param alias
	 * @param modelIndex the model index array , value is consists of different model index of {@link #models}
	 * @param currentModel  KEY :  ALIAS
	 */
	private void sort(String[] alias,int[] modelIndex,Map<String, JBaseModel> currentModel){
		if(JCollectionUtils.hasInCollect(sorts)){
			List<String> strings=new ArrayList<String>();
			for(int i=0;i<this.sorts.size();i++){
				JSort sort=sorts.get(i);
				Object obj= sort.value(currentModel);
				strings.add(obj==null?"":String.valueOf(obj));
				Integer key=getKey(strings);
				// get the order chunk which has the same value till the order element
				if(orderGroup.get(key)==null){
					Integer groupKey=null;
					// not the first element
					if(i!=0){
						strings.remove(strings.size()-1);
						groupKey=getKey(strings);
					}
					sort.sort(sorts, alias, modelIndex,currentModel,sortResult, orderGroup,groupKey,key, models);
					break;
				}
				else{
					if((sorts.size()-1)==i){
						sort.sort(sorts, alias, modelIndex,currentModel,sortResult, orderGroup,key,key, models);
						break;
					}
				}
			}
		}

	}



	/**
	 * do validation on the JQL.
	 */
	private void validate(){

		if(JCollectionUtils.hasInCollect(results)){
			for (Iterator<JResult> iterator = results.iterator(); iterator.hasNext();) {
				JResult result = iterator.next();
				result.validateExpress(from);
			}
		}


		if(JCollectionUtils.hasInCollect(aggregates)){
			for (Iterator<JAggregate> iterator = aggregates.iterator(); iterator.hasNext();) {
				JAggregate aggregate = iterator.next();
				aggregate.validateExpress(from);
			}
		}


		if(JCollectionUtils.hasInCollect(groupBy)){
			for (Iterator<JValueRef> iterator = groupBy.iterator(); iterator.hasNext();) {
				JValueRef valueRef = iterator.next();
				valueRef.validateExpress(from);
			}

			if(JCollectionUtils.hasInCollect(results)){
				for (Iterator<JResult> iterator = results.iterator(); iterator.hasNext();) {
					JResult result = iterator.next();

					if(JResultRef.class.isInstance(result)){
						JResultRef resultRef=(JResultRef) result;
						boolean exist=false;
						for (Iterator<JValueRef> iterator1 = groupBy.iterator(); iterator1.hasNext();) {
							JValueRef valueRef = iterator1.next();

							if(resultRef.getAlias().equals(valueRef.getAlias())
									&&resultRef.getProperty().equals(valueRef.getProperty())){
								exist=true;
								break;
							}
						}

						if(!exist){
							throw new JJQLParseException("result "+resultRef.getProperty()+" must be existing in group by part.");
						}
					}


				}
			}
			else{
				throw new JJQLParseException("result cannot be empty when existing group by part.");
			}

			if(JCollectionUtils.hasInCollect(sorts)){
				for (Iterator<JSort> iterator = sorts.iterator(); iterator.hasNext();) {
					JValueRef sortRef = (JValueRef) iterator.next();
					boolean exist=false;
					for (Iterator<JValueRef> iterator1 = groupBy.iterator(); iterator1.hasNext();) {
						JValueRef valueRef = iterator1.next();

						if(sortRef.getAlias().equals(valueRef.getAlias())
								&&sortRef.getProperty().equals(valueRef.getProperty())){
							exist=true;
							break;
						}
					}

					if(!exist){
						throw new JJQLParseException("order by "+sortRef.jql()+" must be existing in group by part.");
					}
				}
			}

		}

		if(JCollectionUtils.hasInCollect(sorts)){
			for (Iterator<JSort> iterator = sorts.iterator(); iterator.hasNext();) {
				JSort sort =  iterator.next();
				sort.validateExpress(from);
			}
		}
		if(filter!=null){
			filter.validateExpress(from);
		}

	}

	private static Map<String,Class<? extends JBaseModel>> classes=new HashMap<String, Class<? extends JBaseModel>>();

//	private void loadClasses(){
//		try{
//			URL url=Thread.currentThread().getContextClassLoader().getResource(Py.get().get(Py.DB_PACKAGE).replace(".", File.separator));
//			File file=new File(url.toURI());
//			process(file);
//		}catch (Exception e) {
//			logger.error(e);
//			throw new JJQLParseException(e);
//		}
//	}

	public static Class<? extends JBaseModel> getClass(String classNamein){
		final String className=classNamein.trim();
		if(JCollectionUtils.hasInMap(classes)){
			if(className.indexOf(".")!=-1){
				return classes.get(className);
			}
			else{
				int count=0;
				Class<? extends JBaseModel> clazz=null;
				for (Iterator<Entry<String, Class<? extends JBaseModel>>>  iterator = classes.entrySet().iterator(); iterator
						.hasNext();) {
					Entry<String, Class<? extends JBaseModel>> entry =iterator.next();
					String[] keys=entry.getKey().split("[.]");
					if(className.equals(keys[keys.length-1])){
						clazz= entry.getValue();
						count++;
					}
				}
				if(count>1){
					throw new JJQLParseException("class "+className+" is ambiguous. in other words, more than one xml can be mapping to this class.");
				}

				if(clazz==null){
					throw new JJQLParseException("class "+className+" cannot map to an unique xml.");
				}

				return clazz;
			}
		}
		return null;
	}


//	private boolean validClass(Class classin) throws JJQLParseException{
//		Class clazz=classin;
//		while(clazz!=null){
//			Field[] fields=clazz.getDeclaredFields();
//			if(JCollectionUtils.hasInArray(fields)){
//				for (int i = 0; i < fields.length; i++) {
//					Field field=fields[i];
//					field.setAccessible(true);
//					ModelField modelField=field.getAnnotation(ModelField.class);
//					if(modelField!=null){
//						if(isKeyword(field.getName())){
//							throw new JJQLParseException("properties \""+clazz.getName()+"."+field.getName()+"\" mapping to xml cannot be in ["+keyword+"]");
//						}
//					}
//				}
//			}
//
//			clazz=clazz.getSuperclass();
//		}
//		return true;
//	}

//	private void process(File file) {
//		try{
//			if(file==null) return ;
//			if(file.isDirectory()){
//				File[] files=file.listFiles();
//				for (int i = 0; i < files.length; i++) {
//					File f=files[i];
//					if(f.isFile()){
//						String filePath=f.getAbsolutePath();
//						String className=
//								(filePath.substring(filePath.indexOf(Py.get().get(Py.DB_PACKAGE).replace(".", File.separator)))
//										.replace(".class", "")).replace(File.separator, ".");
//
//						Class<JBaseModel> clazz= (Class<JBaseModel>)Thread.currentThread().getContextClassLoader().getClass().forName(className);
//						Class superClass=clazz.getSuperclass();
//						while(superClass!=null){
//							if(superClass==_BaseModel.class){
//								if(validClass(clazz)){
//
//									logger.info("class \""+className+"\" loaded -------------------------");
//
//									classes.put(className, clazz);
//									break;
//								}
//							}
//							superClass=superClass.getSuperclass();
//						}
//					}
//					else{
//						process(f);
//					}
//				}
//			}
//		}catch (Exception e) {
//			throw new JJQLParseException(e);
//		}
//	}

	public JSelect parse(String jql){
		return parse(jql, new HashMap<String, Object>());
	}

	public JSelect parse(String jql,Map<String, Object> params) {
		logger.info("Input parseing JQL : "+jql);
		JXMLSelect obj=this;

		Pattern fromPattern=Pattern.compile("([\\s(),]{1}[fF][rR][oO][mM][\\s(),]{1})|(^[\\s]*[fF][rR][oO][mM][\\s(),]{1})");
		Matcher matcher=fromPattern.matcher(jql);
		int i=0;
		int nextStart=0;
		int fromStart=-1;
		while(matcher.find(nextStart)){
			i++;
			fromStart=matcher.start();
			nextStart=fromStart+matcher.group().length()-1;
		}

		if(i==0){
			throw new JJQLParseException("invlid expression. FROM keyword missing.");
		}
		else if(i>1){
			throw new JJQLParseException("invlid expression. FROM keyword only can appears once.");
		}


		Pattern wherePattern=Pattern.compile("[\\s(),]{1}[Ww][Hh][Ee][Rr][Ee][\\s(),]{1}");
		matcher=wherePattern.matcher(jql);
		i=0;
		nextStart=0;
		int whereStart=-1;
		while(matcher.find(nextStart)){
			i++;
			whereStart=matcher.start();
			nextStart=whereStart+matcher.group().length()-1;
		}
		if(i>1){
			throw new JJQLParseException("invlid expression. WHERE keyword only can appears once.");
		}


		Pattern groupByPattern=Pattern.compile("[\\s(),]{1}[Gg][Rr][Oo][Uu][Pp]\\s+[Bb][Yy][\\s(),]{1}");
		matcher=groupByPattern.matcher(jql);
		i=0;
		nextStart=0;
		int groupByStart=-1;
		String groupBy="";
		while(matcher.find(nextStart)){
			i++;
			groupByStart=matcher.start();
			groupBy=matcher.group();
			nextStart=groupByStart+groupBy.length()-1;
		}
		if(i>1){
			throw new JJQLParseException("invlid expression. GROUP BY keyword only can appears once.");
		}

		Pattern orderByPattern=Pattern.compile("[\\s(),]{1}[Oo][Rr][Dd][Ee][Rr]\\s+[Bb][Yy][\\s(),]{1}");
		matcher=orderByPattern.matcher(jql);
		i=0;
		nextStart=0;
		int orderByStart=-1;
		String orderBy="";
		while(matcher.find(nextStart)){
			i++;
			orderByStart=matcher.start();
			orderBy=matcher.group();
			nextStart=orderByStart+orderBy.length()-1;
		}
		if(i>1){
			throw new JJQLParseException("invlid expression. ORDER BY keyword only can appears once.");
		}


		if(orderByStart!=-1){
			if(orderByStart<groupByStart
					||orderByStart<whereStart
					||orderByStart<fromStart){
				throw new JJQLParseException("invlid expression.");
			}
		}

		if(groupByStart!=-1){
			if(groupByStart<whereStart
					||groupByStart<fromStart){
				throw new JJQLParseException("invlid expression.");
			}
		}

		if(whereStart!=-1){
			if(whereStart<fromStart){
				throw new JJQLParseException("invlid expression.");
			}
		}



		if(fromStart!=-1){
			String fromPart="";
			if(whereStart!=-1){
				fromPart=jql.substring(fromStart, whereStart);
			}
			else if(groupByStart!=-1){
				fromPart=jql.substring(fromStart,groupByStart);
			}
			else if(orderByStart!=-1){
				fromPart=jql.substring(fromStart,orderByStart);
			}
			else{
				fromPart=jql.substring(fromStart);
			}

			fromPart=fromPart.trim().substring(4);
			String[] tables=fromPart.split(",");
			if(JCollectionUtils.hasInArray(tables)){
				for (int j = 0; j < tables.length; j++) {
					String table=tables[j].trim();
					String[] class_names=table.split("\\s+");
					if(JCollectionUtils.hasInArray(class_names)){
						// no alias
						if(class_names.length==1){
							obj.putFrom(getClass(class_names[0]));
						}
						// with alias
						else if(class_names.length==2){
							obj.putFrom(getClass(class_names[0]),class_names[1]);
						}

						else{
							throw new JJQLParseException("invalid expression \" "+table+"\", from error.");
						}
					}

				}
			}
		}


		if(whereStart!=-1){

			String wherePart="";
			if(groupByStart!=-1){
				wherePart=jql.substring(whereStart,groupByStart);
			}
			else if(orderByStart!=-1){
				wherePart=jql.substring(whereStart,orderByStart);
			}
			else{
				wherePart=jql.substring(whereStart);
			}
			
			if(wherePart.trim().equalsIgnoreCase("where")){
				throw new JJQLParseException("some condition must follow the 'where'; ["+jql+"]");
			}
			
			wherePart="("+wherePart.trim().substring(5).trim()+")";
			if(JStringUtils.isNotNullOrEmpty(wherePart)){

				Map<Integer, JGroup> groups=new HashMap<Integer, JGroup>();

				int count=0;
				JGroup group=new JGroup();
				groups.put(count, group);
				boolean ifed=false;
				for(int j=0;j<wherePart.length();j++){
					char ch=wherePart.charAt(j);
					if('('==ch){
						count++;
						group=new JGroup();
						groups.put(count, group);
						groups.get(count-1).push(group);
					}
					else if(')'==ch){
						groups.remove(count);
						count--;
					}
					else if(ifed&&('a'==ch||'A'==ch)){
						j=j+(3-1);
						JAndLink andLink=new JAndLink();
						groups.get(count).push(andLink);
						ifed=false;
					}
					else if(ifed&&('o'==ch||'O'==ch)){
						j=j+(2-1);
						JOrLink orLink=new JOrLink();
						groups.get(count).push(orLink);
						ifed=false;
					}
					else if(' '!=ch&&!ifed) {

						String part=wherePart.substring(j);

						Pattern ifPattern=Pattern.compile("[\\s(),]+([Oo][Rr]|[Aa][Nn][Dd])[\\s(),]{1}");
						Matcher ifMatcher=ifPattern.matcher(part);
						String[] ifes=null;
						if(ifMatcher.find()){
							ifes=part.split("[\\s(),]+([Oo][Rr]|[Aa][Nn][Dd])[\\s(),]{1}");
						}
						else{
							ifPattern=Pattern.compile("[)]+");
							ifMatcher=ifPattern.matcher(part);
							if(ifMatcher.find()){
								ifes=part.split("[)]+");
							}
						}
						String ifString=ifes[0];
						JIF if1=null;
						try{
							if1=parseIF(ifString, params);
						}catch (Exception e) {
							throw new JJQLParseException("invalid expression \""+ifString+"\", cannot parse as a filter condition .",e);
						}
						j=j+ifString.length()-1;
						groups.get(count).push(if1);
						ifed=true;
					}

				}
				JGroup finalGroup=groups.get(0);
				obj.putWhere(finalGroup);
			}

		}

		if(groupByStart!=-1){
			String groupByPart="";
			if(orderByStart!=-1){
				groupByPart=jql.substring(groupByStart+groupBy.length(),orderByStart).trim();
			}
			else{
				groupByPart=jql.substring(groupByStart+groupBy.length()).trim();
			}

			String[] groupByParts=groupByPart.split(",");
			if(JCollectionUtils.hasInArray(groupByParts)){
				for (int k = 0; k < groupByParts.length; k++) {
					obj.addGroupBy((JValueRef)parseValueRef(groupByParts[k]));
				}
			}
		}

		if(orderByStart!=-1){
			String orderByPart=jql.substring(orderByStart+orderBy.length()).trim();

			String[] orderByParts=orderByPart.split(",");
			if(JCollectionUtils.hasInArray(orderByParts)){
				for (int k = 0; k < orderByParts.length; k++) {
					obj.addOrderBy(parseSort(orderByParts[k]));
				}
			}

		}


		String resultPart=jql.substring(0, fromStart).trim();

		if(JStringUtils.isNotNullOrEmpty(resultPart)){
			Pattern selectPattern=Pattern.compile(Regex.SELECT);
			Matcher selectMatcher=selectPattern.matcher(resultPart);
			int resultIndexBegin=0;
			if(selectMatcher.find()){
				resultIndexBegin=selectMatcher.end();
			}
			resultPart=resultPart.substring(resultIndexBegin);
			String[] results=resultPart.split(",");
			if(JCollectionUtils.hasInArray(results)){
				for (int k = 0; k < results.length; k++) {

					JResult result=parseResult(results[k]);
					if(JAggregate.class.isInstance(result)){
						obj.aggregates.add((JAggregate)result);
					}
					else{
						obj.addResult(result);
					}
				}
			}
		}
		else{
			if(JCollectionUtils.hasInMap(obj.from)){
				for (Iterator<String> iterator = obj.from.keySet().iterator(); iterator
						.hasNext();) {
					String alias = iterator.next();
					obj.addResult(alias);
				}
			}
		}

		return obj;
	}

	private JIF parseIF(String ifPart,Map<String, Object> params){


		String likeRegex="\\s+[Ll][Ii][Kk][Ee](:|\\s+)";
		Pattern likeEqualPattern=Pattern.compile(likeRegex);

		String gtEqualRegex=">\\s*=";
		Pattern gtEqualPattern=Pattern.compile(gtEqualRegex);

		String ltEqualRegex="<\\s*=";
		Pattern ltEqualPattern=Pattern.compile(ltEqualRegex);

		String gtRegex=">";
		Pattern gtPattern=Pattern.compile(gtRegex);

		String ltRegex="<";
		Pattern ltPattern=Pattern.compile(ltRegex);

		String notEqualRegex="<\\s*>";
		Pattern notEqualPattern=Pattern.compile(notEqualRegex);

		String equalRegex="=";
		Pattern equalPattern=Pattern.compile(equalRegex);

		Matcher likeMatcher=likeEqualPattern.matcher(ifPart);
		Matcher gtEqualMatcher=gtEqualPattern.matcher(ifPart);
		Matcher ltEqualMatcher=ltEqualPattern.matcher(ifPart);
		Matcher notEqualMatcher=notEqualPattern.matcher(ifPart);

		Matcher gtMatcher=gtPattern.matcher(ifPart);
		Matcher ltMatcher=ltPattern.matcher(ifPart);
		Matcher equalMatcher=equalPattern.matcher(ifPart);

		String compare=null;
		String[] keyValues=null;
		if(likeMatcher.find()){
			// like
			compare=JIF.LIKE;

			String likePart=likeMatcher.group();
			keyValues=ifPart.split(likeRegex);
			if(likePart.indexOf(":")!=-1){
				keyValues[1]=":"+keyValues[1].trim();
			}
		}
		else if(gtEqualMatcher.find()){
			//>=
			compare=JIF.GTEQUAL;
			keyValues=ifPart.split(gtEqualRegex);
		}
		else if(ltEqualMatcher.find()){
			// <=
			compare=JIF.LTEQUAL;
			keyValues=ifPart.split(ltEqualRegex);
		}
		else if(notEqualMatcher.find()){
			// <>
			compare=JIF.NOTEQUAL;
			keyValues=ifPart.split(notEqualRegex);
		}
		else if(gtMatcher.find()){
			// >
			compare=JIF.GT;
			keyValues=ifPart.split(gtRegex);
		}
		else if(ltMatcher.find()){
			// <
			compare=JIF.LT;
			keyValues=ifPart.split(ltRegex);
		}
		else if(equalMatcher.find()){
			// =
			compare=JIF.EQUAL;
			keyValues=ifPart.split(equalRegex);
		}

		String key=keyValues[0].trim();
		String value=keyValues[1].trim();

		Object ifKey=parseValueRef(key);
		Object ifValue=null;

		JIF obj=null;

		if(value.startsWith(":")){
			final String param=value.substring(1).trim();

			Pattern pattern=Pattern.compile("^(\\w|_)+$");
			Matcher matcher=pattern.matcher(param);
			if(!matcher.find()){
				throw new JJQLParseException("invalid expresion \""+value+"\" ,variable is only single word including \"_\" or not ");
			}

			if(JCollectionUtils.hasInMap(params)
					&&!params.containsKey(param)){
				throw new JJQLParseException("missing parameter \""+param+"\"");
			}

			Object acValue=params.get(param);
			if(JValueRef.class.isInstance(ifKey)){
				obj=new JIFPlain((JValueRef)ifKey, compare, acValue==null?null:String.valueOf(acValue));
			}
			else{
				obj=new JIFString((String)ifKey, compare, acValue==null?null:String.valueOf(acValue));
			}
			obj.setParam(value.substring(1).trim());
		}
		else{
			ifValue=parseValueRef(value);

			if(String.class.isInstance(ifKey)
					&&String.class.isInstance(ifValue)){
				obj=new JIFString((String)ifKey, compare, (String)ifValue);
			}
			else if(JValueRef.class.isInstance(ifKey)
					&&JValueRef.class.isInstance(ifValue)){
				obj=new JIFRef((JValueRef)ifKey, compare, (JValueRef)ifValue);
			}
			else if(JValueRef.class.isInstance(ifKey)
					&&String.class.isInstance(ifValue)){
				obj=new JIFPlain((JValueRef)ifKey, compare, (String)ifValue);
			}
			else{
				throw new JJQLParseException("invalid condition,"+ifPart+",variable should be left from consitants");
			}
		}
		return obj;
	}

	private Object parseValueRef(String key) {
		final String valueKey=key.trim();
		Object ifKey;
		if(valueKey.startsWith("'")&&valueKey.endsWith("'")){
			final String tempKey=valueKey.replace("'", "");
			if(JNumberUtils.isNumber(tempKey.trim())){
				ifKey=tempKey.trim();
			}else{
				ifKey=tempKey;
			}
		}
		else if(JNumberUtils.isNumber(valueKey)){
			ifKey=valueKey;
		}
		else if((valueKey.startsWith("'")||valueKey.endsWith("'"))){
			throw new JJQLParseException("invalid expression \""+key+"\", cannot parse as a String or variable ");
		}
		else{
			if(valueKey.indexOf(".")!=-1){
				String[] aliasProperty=valueKey.split(Regex.ALIAS_PROPERTY);
				ifKey=new JValueRef(aliasProperty[1], aliasProperty[0]);
			}
			else{
				ifKey=new JValueRef(valueKey);
			}
		}
		return ifKey;
	}


	private JResult parseResult(String resultString) {

		final String resultKey=resultString.trim();

		JResult result=null;

		// a plain result.
		if((resultKey.startsWith("'")&&resultKey.endsWith("'"))
				||JNumberUtils.isNumber(resultKey)){
			return new JResultPlain(resultKey);
		}

		String asRegex=Regex.AS;
		Pattern asPattern=Pattern.compile(asRegex);
		Matcher matcher=asPattern.matcher(resultKey);

		String key="";
		String name="";
		if(matcher.find()){
			// find result with alias
			String[] results=resultKey.trim().split("[\\s]+[Aa][Ss][\\s]+");
			key=results[0].trim();
			name=results[1].trim();
		}
		else{

			Pattern pattern=Pattern.compile("(\\S)+\\s+(\\w|_)+\\s*$");
			if(pattern.matcher(resultKey).find()){
				throw new JJQLParseException("inlivad expression \""+resultKey+"\",\"as\" is mandatory if defined an alias for result.");
			}
			key=resultKey;
		}

		JAggregate aggregate=parseAggregate(key, name);

		if(aggregate!=null){
			return aggregate;
		}
		// a result its value is from xml.
		else{

			if(key.trim().startsWith("'")
					&&key.trim().endsWith("'")){
				result=new JResultPlain(key);
			}
			else if(key.indexOf(".")!=-1){
				String[] aliasProperty=key.split(Regex.ALIAS_PROPERTY);
				result=new JResultRef(aliasProperty[1], aliasProperty[0]);
			}
			else{
				result=new JResultRef(key);
			}

			if(JStringUtils.isNotNullOrEmpty(name)){
				result.setName(name);
			}
		}
		return result;
	}

	private JSort parseSort(String string){

		String sortString=string.trim();

		JSort sort=null;

		String ascRegex=Regex.ASC;
		String descRegex=Regex.DESC;
		Pattern ascPattern=Pattern.compile(ascRegex);
		Pattern descPattern=Pattern.compile(descRegex);

		Matcher matcher=descPattern.matcher(sortString);
		if(matcher.find()){
			String[] descParts=sortString.split(descRegex);
			JValueRef valueRef=(JValueRef) parseValueRef(descParts[0]);
			sort=new JDescSort(valueRef.getProperty(), valueRef.getAlias());
		}
		else{
			matcher=ascPattern.matcher(sortString);
			String key=null;
			if(matcher.find()){
				String[] ascParts=sortString.split(ascRegex);
				key=ascParts[0];
			}
			else{
				key=sortString;
			}
			JValueRef valueRef=(JValueRef) parseValueRef(key);
			sort=new JAscSort(valueRef.getProperty(), valueRef.getAlias());
		}
		return sort;
	}

	private JAggregate parseAggregate(final String key,final String name){

		JAggregate aggregate=null;
		String inner=null;
		String countRegex=Regex.COUNT;
		Pattern countPattern=Pattern.compile(countRegex);
		Matcher countMatcher=countPattern.matcher(key);
		if(countMatcher.find()){
			aggregate= new JCountAggregate();
		}
		
		if(aggregate==null){
			String maxRegex=Regex.MAX;
			Pattern maxPattern=Pattern.compile(maxRegex);
			Matcher maxMatcher=maxPattern.matcher(key);
			if(maxMatcher.find()){
				aggregate= new JMaxAggregate();
			}
		}

		if(aggregate==null){
			String minRegex=Regex.MIN;
			Pattern minPattern=Pattern.compile(minRegex);
			Matcher minMatcher=minPattern.matcher(key);
			if(minMatcher.find()){
				aggregate= new JMinAggregate();
			}
		}
		
		if(aggregate==null){
			String sumRegex=Regex.SUM;
			Pattern sumPattern=Pattern.compile(sumRegex);
			Matcher sumMatcher=sumPattern.matcher(key);
			if(sumMatcher.find()){
				aggregate= new JSumAggregate();
			}
		}
		
		if(aggregate==null){
			String avgRegex=Regex.AVG;
			Pattern avgPattern=Pattern.compile(avgRegex);
			Matcher avgMatcher=avgPattern.matcher(key);
			if(avgMatcher.find()){
				aggregate= new JAvgAggregate();
			}
		}

		Matcher innerMatcher=Pattern.compile("[(][\\w_.\\s*]*[)]").matcher(key);
		if(innerMatcher.find()){
			inner=innerMatcher.group();
			inner=inner.replace("(", "").replace(")", "").trim();
		}

		if(aggregate!=null){

			if(JStringUtils.isNotNullOrEmpty(inner)){
				if(inner.indexOf(".")!=-1){
					String[] aliasProperty=inner.split(Regex.ALIAS_PROPERTY);
					aggregate.setProperty(aliasProperty[1]);
					aggregate.setAlias(aliasProperty[0]);
				}
				else{
					aggregate.setProperty(inner);
				}
			}
			else{
				throw new JJQLParseException(key+" aggregate invalid .");
			}

			if(JStringUtils.isNotNullOrEmpty(name)){
				aggregate.setName(name);
			}
		}

		return aggregate;

	}


	public String jql(){
		String jql="select ";

		if(JCollectionUtils.hasInCollect(aggregates)){
			for (Iterator<JAggregate> iterator = aggregates.iterator(); iterator.hasNext();) {
				JAggregate aggregate = iterator.next();
				jql=jql+" "+aggregate.jql()+",";
			}
			jql=jql.substring(0, jql.length()-1);
		}

		if(JCollectionUtils.hasInCollect(results)){
			if(JCollectionUtils.hasInCollect(aggregates)){
				jql=jql+",";
			}
			for (Iterator<JResult> iterator = results.iterator(); iterator.hasNext();) {
				JResult resultRef = iterator.next();
				jql=jql+" "+resultRef.jql()+",";
			}
			jql=jql.substring(0, jql.length()-1);
		}

		if(JCollectionUtils.hasInMap(from)){
			jql=jql+" from";
			for (Iterator<Entry<String, Class<? extends JBaseModel>>> iterator = from.entrySet().iterator(); iterator.hasNext();) {
				Entry<String, Class<? extends JBaseModel>> entry = iterator.next();
				jql=jql+" "+entry.getValue().getName()+" "+(DEFAULT_ALIAS.equals(entry.getKey())?"":entry.getKey())+",";
			}
			jql=jql.substring(0, jql.length()-1);
		}

		if(filter!=null){
			jql=jql+" where "+filter.jql();
		}

		if(JCollectionUtils.hasInCollect(groupBy)){
			jql=jql+" group by";
			for (Iterator<JValueRef> iterator = groupBy.iterator(); iterator.hasNext();) {
				JValueRef valueRef = iterator.next();
				jql=jql+" "+valueRef.jql()+",";
			}
			jql=jql.substring(0, jql.length()-1);
		}

		if(JCollectionUtils.hasInCollect(sorts)){

			jql=jql+" order by";
			for (Iterator<JSort> iterator = sorts.iterator(); iterator.hasNext();) {
				JSort sort = iterator.next();
				jql=jql+" "+sort.jql()+",";
			}
			jql=jql.substring(0, jql.length()-1);
		}

		return jql;
	}


	public boolean isKeyword(String keyword){
		for (int i = 0; i < KEYWORD.length; i++) {
			String key=KEYWORD[i];
			if(key.equals(keyword.trim().toUpperCase())){
				return true;
			}
		}
		return false;
	}



	private class Regex{

		private static final String ALIAS_PROPERTY="(\\s+|\\s*)[.](\\s+|\\s*)";
		private static final String COUNT="^[\\s]*[Cc][Oo][Uu][Nn][Tt][\\s]*[(][\\w_.\\s*]*[)][\\s]*$";
		private static final String MAX="^[\\s]*[Mm][Aa][Xx][\\s]*[(][\\w_.\\s]*[)][\\s]*$";
		private static final String MIN="^[\\s]*[Mm][Ii][Nn][\\s]*[(][\\w_.\\s]*[)][\\s]*$";
		private static final String SUM="^[\\s]*[Ss][Uu][Mm][\\s]*[(][\\w_.\\s]*[)][\\s]*$";
		private static final String AVG="^[\\s]*[Aa][Vv][Gg][\\s]*[(][\\w_.\\s]*[)][\\s]*$";


		private static final String ASC="[\\s]{1}[Aa][Ss][Cc][\\s]*$";
		private static final String DESC="[\\s]{1}[Dd][Ee][Ss][Cc][\\s]*$";
		private static final String AS="^[\\w_().\\s*]+[\\s]+[Aa][Ss][\\s]+[\\w_]+$";

		private static final String SELECT="^\\s*[Ss][Ee][Ll][Ee][Cc][Tt]\\s+";


	}

//	private static boolean isTrigger=false;
//	public void trigger(StartupEvent startupEvent) {
//		if(!isTrigger){
//			loadClasses();
//			isTrigger=true;
//		}
//	}

	@Override
	public JSelect parse(String jql, boolean isNewSelect) {
		JXMLSelect xmlSelect=new JXMLSelect();
		return xmlSelect.parse(jql);
	}

	@Override
	public JSelect parse(String jql, Map<String, Object> params,
			boolean isNewSelect) {
		JXMLSelect xmlSelect=new JXMLSelect();
		return xmlSelect.parse(jql, params);
	}

	
	static void putClass(Class<? extends JBaseModel> clazz){
		classes.put(clazz.getName(), clazz);
	}
	
	static void putClass(String className){
		Class<? extends JBaseModel> clazz= JClassUtils.load(className, 
				Thread.currentThread().getContextClassLoader());
		classes.put(clazz.getName(), clazz);
	}

	private List<JColumnInfo> getMappingFields(Class<? extends JBaseModel> clazz){
		return modelDetect.getColumnInfos(clazz);
	}





}
