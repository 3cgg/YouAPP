/**
 * 
 */
package j.jave.framework.model;

/**
 * @author J
 */
public interface JPagination extends JCriteria {

	/**
	 * set {@link JPage} , any criteria implements {@link JCriteria}
	 * @param page
	 */
	void setPage(JPage page);
	
	JPage getPage();
	
	/**
	 * quickly set total record number. 
	 * @param totalRecordNum
	 */
	void setTotalRecordNum(int totalRecordNum);
	
}
