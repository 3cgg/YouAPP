/**
 * 
 */
package me.bunny.kernel.jave.support.validate;

import java.util.ArrayList;
import java.util.List;


/**
 * hierarchical validators. 
 * @author Administrator
 */
public class JHierarchicalValidator implements JValidator {

	private JHierarchicalValidator parent;
	
	private List<JHierarchicalValidator> children=new ArrayList<JHierarchicalValidator>();
	
	private int level;
	
	private static final int root=0;

	public JHierarchicalValidator(JHierarchicalValidator parent) {
		this.parent=parent;
		if(parent==null){
			this.level=root;
		}
		else{
			this.parent.children.add(parent);
			this.level=this.parent.level+1;
		}
	}
	
	@Override
	public boolean validate(Object object) {
		return false;
	}
	
	/**
	 * validate self an all children validators. 
	 * @param object
	 * @return
	 */
	@SuppressWarnings("unused")
	public final boolean validateOnChildrenHierarchical(Object object){
		boolean matched=validate(object);
		if(matched){
			// if matches, continue to do validation on children 
			if(this.children!=null){
				for (int i = 0; i < children.size(); i++) {
					JHierarchicalValidator validator=children.get(i);
					return validator.validateOnChildrenHierarchical(object);
				}
			}
			return matched;  // value is true. 
		}
		else{
			return false;
		}
	}
	
	/**
	 * validate beginning from root . 
	 * @param object
	 * @return
	 */
	public final boolean validateOnRootHierarchical(Object object){
		if(this.level!=root){
			throw new RuntimeException("operation on the object is not root");
		}
		return validateOnChildrenHierarchical(object);
	}

}
