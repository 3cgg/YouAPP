package me.bunny.kernel.jave.xml.xmldb.jql;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import me.bunny.kernel.jave.model.JBaseModel;
import me.bunny.kernel.jave.model.support.JSQLType;
import me.bunny.kernel.jave.model.support.detect.JGetSQLTypeOnModel;
import me.bunny.kernel.jave.reflect.JClassUtils;
import me.bunny.kernel.jave.utils.JCollectionUtils;

public abstract class JSort extends JValueRef {
	
	public JSort(String property, String alias) {
		super(property, alias);
	}
	
	/*
	public Sort(String property, String alias, String name) {
		super(property, alias, name);
	}
	*/
	public JSort(String property) {
		super(property);
	}
	
	/**
	 * recalculate the index ,the index that is followed by the argument index need plus one.
	 * i.e. the argument key is from 10 to 20 , then the larger index is 20 , so all other group keys that is larger and equal than 20 need
	 * plus one
	 * @param orderGroup all order group
	 * @param index the larger index of the argument key
	 * @param key the group key, that is the hash code of consists of all column value
	 */
	private void recalculate(Map<Integer, int[]> orderGroup,int index,Integer key){
		if(JCollectionUtils.hasInMap(orderGroup)){
			for (Iterator<Entry<Integer, int[]>> iterator = orderGroup.entrySet().iterator(); iterator.hasNext();) {
				Entry<Integer, int[]> entry = (Entry<Integer, int[]>) iterator.next();
				if(!entry.getKey().equals(key)){
					
					int[] fromTo=entry.getValue();
					if(fromTo[0]>=index){
						fromTo[0]=fromTo[0]+1;
					}
					if(fromTo[1]>=index){
						fromTo[1]=fromTo[1]+1;
					}
				}
				
			}
		}
	}
	
	/**
	 * sort the result
	 * @param sorts
	 * @param aliasArray
	 * @param modelIndex the model index array , value is consists of different model index of {@link #models}
	 * @param currentModel all tables of from part data
	 * @param sortResult the sort result
	 * @param orderGroup  order group graph
	 * @param groupKey
	 * @param key
	 * @param allmodels
	 */
	public void sort(List<JSort> sorts,String[] aliasArray,int[] modelIndex
						,Map<String, JBaseModel> currentModel,List<int[]> sortResult
						,Map<Integer, int[]> orderGroup,Integer groupKey,Integer key,Map<String, List<JBaseModel>> allmodels){
		
		if(JCollectionUtils.hasInCollect(sortResult)){
			int aliasIndex=0;
			for(int j=0;j<aliasArray.length;j++){
				if(alias.equals(aliasArray[j])){
					aliasIndex=j;
					break;
				}
			}
			
			int from=0;
			int to=0;
			int[] scope=null;
			if(groupKey!=null){
				scope=orderGroup.get(groupKey);
				from=scope[0];
				to=scope[1];
			}
			else{
				from=0;
				to=sortResult.size()-1;
			}
			
			// sortResult should not be empty. 
			for(int i=from;i<=to;i++){
				int[] beforeModelIndex=sortResult.get(i);
				if(beforeModelIndex!=null){
					JBaseModel beforeModel=allmodels.get(this.alias).get(beforeModelIndex[aliasIndex]);
					Object beforeValue= JClassUtils.get(property, beforeModel) ;
					Object thisValue=JClassUtils.get(property, currentModel.get(alias));
					
					JGetSQLTypeOnModel getSQLTypeOnModel=new JGetSQLTypeOnModel(currentModel.get(alias).getClass());
					JSQLType dataType= getSQLTypeOnModel.get(property);
					
					if(compare(dataType, thisValue, beforeValue)){
						sortResult.add(i, modelIndex);
						if(groupKey!=null){
//							scope[1]=to+1;
//							orderGroup.put(groupKey, scope);
							
							if(groupKey!=key){
								orderGroup.put(key, new int[]{i,i});
							}
							recalculate(orderGroup, i, key);
						}else{
							orderGroup.put(key, new int[]{i,i});
							recalculate(orderGroup, i, key);
						}
						break;
					}
					else{
						if(i==to){
							
							int index=0;
							
							if(i==sortResult.size()-1){
								sortResult.add(modelIndex);
								index=sortResult.size()-1;
							}
							else{
								sortResult.add(i+1,modelIndex);
								index=i+1;
							}
							
							
							if(groupKey!=null){
//								scope[1]=i+1;
//								orderGroup.put(groupKey, scope);
								if(groupKey!=key){
									orderGroup.put(key, new int[]{index,index});
								}
								recalculate(orderGroup, index, key);
							}else{
								orderGroup.put(key, new int[]{index,index});
								recalculate(orderGroup, index, key);
							}
							
							
						}
						
					}
				}
			}
		}
		else{
			sortResult.add(0, modelIndex);
			orderGroup.put(key, new int[]{0,0});
		}
		
	}
	
	protected abstract boolean compare(JSQLType dataType,Object thisValue,Object beforeValue);
	
}
