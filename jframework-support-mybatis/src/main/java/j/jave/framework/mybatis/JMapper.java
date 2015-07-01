/**
 * 
 */
package j.jave.framework.mybatis;

import j.jave.framework.commons.model.JBaseModel;
import j.jave.framework.commons.model.JPagination;
import j.jave.framework.commons.model.JUNDeletable;
import j.jave.framework.commons.persist.JIPersist;
import j.jave.framework.mybatis.plugin.JPagePlugin;

import java.util.List;

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
	
	/**
	 * get records thats matches the criteria , the sub-implementation must be for pagination.
	 * <p>To the Mybatis, the SQL with the id of *ByPage can be intercepted by {@link JPagePlugin},
	 * <p>in the case, the method uses to be for searching a single table. like (select * from dual ). to manager data of a table. 
	 * @param pagination
	 * @return 
	 */
	public List<T> getsByPage(JPagination pagination);
	
}
