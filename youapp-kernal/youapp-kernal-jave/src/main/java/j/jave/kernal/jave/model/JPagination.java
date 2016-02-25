/**
 * 
 */
package j.jave.kernal.jave.model;

/**
 * @author J
 */
public interface JPagination extends JCriteria {

	/**
	 * set {@link JPage} , any criteria implements {@link JCriteria}
	 * @param page
	 */
	<T extends JModel> void setPage(JPage<T> page);
	
	<T extends JModel> JPage<T> getPage();
	
	/**
	 * quickly set total record number. 
	 * @param totalRecordNum
	 */
	void setTotalRecordNum(int totalRecordNum);
	
}
