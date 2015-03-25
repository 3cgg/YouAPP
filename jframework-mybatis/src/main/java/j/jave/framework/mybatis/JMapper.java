/**
 * 
 */
package j.jave.framework.mybatis;

import j.jave.framework.model.JBaseModel;
import j.jave.framework.model.JUNDeletable;
import j.jave.framework.persist.JIPersist;

import org.apache.ibatis.annotations.Param;

/**
 * the interface indicate all children interface is a mapper used in mybatis. 
 * @author J
 */
public interface JMapper<T extends JBaseModel> extends JIPersist<JMapper<T>,T> {
	
	/**
	 * delete the existing record through marking the column named "deleted";
	 * all implementation should check whether the model implements {@link JUNDeletable}<br/>
	 * if yes , the method should throw an exception shows developer the table does not contain delete column
	 * @see {@link JUNDeletable}
	 */
	public void markDeleted(
			@Param(value="id")
	String id);
	
}
