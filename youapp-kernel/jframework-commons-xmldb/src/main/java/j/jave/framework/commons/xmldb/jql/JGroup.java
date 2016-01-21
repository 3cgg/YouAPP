package j.jave.framework.commons.xmldb.jql;

import j.jave.framework.commons.model.JBaseModel;
import j.jave.framework.commons.utils.JCollectionUtils;
import j.jave.framework.commons.utils.JStringUtils;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

public class JGroup extends JAbstractFilter  {
	
	private LinkedList<JFilter> filters=new LinkedList<JFilter>();
	
	public void push(JFilter filter){
		
		if(JIF.class.isInstance(filter)
				||JLink.class.isInstance(filter)
				||JGroup.class.isInstance(filter)){
			JFilter latest=filters.peekLast();
			if(latest==null){
				if(JLink.class.isInstance(filter)){
					throw new RuntimeException(
							"Group must begin with IF elemnet, not for link element."+filter.getClass().getName()); 
				}else{
					filters.offer(filter);
				}
			}else{
				if(JLink.class.isInstance(latest)){
					if(JIF.class.isInstance(filter)
							||JGroup.class.isInstance(filter)
							){
						filters.offer(filter);
					}
					else{
						throw new RuntimeException(
								"invalid conditions link : lastest is "+latest.name()
								+", currently added is "+filter.name());
					}
				}
				else{
					if(JLink.class.isInstance(filter)){
						filters.offer(filter);
					}
					else{
						throw new RuntimeException(
								"invalid conditions link : lastest is "+latest.name()
								+", currently added is "+filter.name());
					}
				}
				
			}
		}
		else{
			throw new RuntimeException("paramter object type must be IF or Link");
		}
	}

	@Override
	public String name() {
		return JGroup.class.getName();
	}
	
	@Override
	public boolean validate(Map<String, JBaseModel> models) {
		if(JCollectionUtils.hasInCollect(filters)){
			boolean pass=true;
			Iterator<JFilter> iterator = filters.iterator(); 
			JFilter fist =iterator.next();
			pass=fist.validate(models);
			
			// must begin with link ( and | or ) element.
			for (; iterator.hasNext();) {
				JFilter filter = iterator.next();
				if(JLink.class.isInstance(filter)){
					if(iterator.hasNext()){
						JFilter next = iterator.next();
						if(JAndLink.class.isInstance(filter)){
							pass=pass&&next.validate(models);
						}
						else if(JOrLink.class.isInstance(filter)){
							pass=pass||next.validate(models);
						}
					}
					else{
						throw new RuntimeException("express invalid,ends at "+filter.getClass().getName());
					}
				}
				else{
					throw new RuntimeException("express invalid, at "+filter.getClass().getName());
				}
			}
			return pass;
		}
		
		return false;
	}
	
	
	public void validateExpress(Map<String, Class<? extends JBaseModel>> from){
		if(JCollectionUtils.hasInCollect(filters)){
			for (Iterator<JFilter> iterator = filters.iterator(); iterator.hasNext();) {
				JFilter filter =  iterator.next();
				filter.validateExpress(from);
			}
		}
		
		
	}

	@Override
	public String jql() {
		String aql="";
		if(JCollectionUtils.hasInCollect(filters)){
			for (Iterator<JFilter> iterator = filters.iterator(); iterator.hasNext();) {
				JFilter filter = iterator.next();
				aql=aql+" "+filter.jql();
			}
		}
		return JStringUtils.isNullOrEmpty(aql)?aql:"("+aql+")";
	}
	
}
