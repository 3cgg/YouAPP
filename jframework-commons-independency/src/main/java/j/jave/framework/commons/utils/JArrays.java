package j.jave.framework.commons.utils;


public class JArrays {
	
	/**
	 * 
	 * @param obj1  only as primitive type, int , long , float,String etc.
	 * @param obj2  only as primitive type, int , long , float,String etc.
	 * @return
	 */
	public static boolean includeIn(Object[] set,Object[] seeds){
		
		if(set==null
				||seeds==null
				||set.length==0
				||seeds.length==0
				) return false;
		boolean allIncluded=true;
		for (int i = 0; i < seeds.length; i++) {
			Object seed=seeds[i];
			boolean included=false;
			for (int j = 0; j < set.length; j++) {
				Object seedEle=set[j];
				if((included=seed.equals(seedEle))){
					break;
				}
			}
			
			if(!included){
				allIncluded=false;
				break;
			}
		}
		return allIncluded; 
	}
	
	

}
