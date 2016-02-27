/**
 * 
 */
package j.jave.kernal.jave.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author J
 */
public class JPageImpl<T extends JModel> implements JPage<T> {
	
	private long totalRecordNumber=Long.MAX_VALUE;

	private int totalPageNumber;
	
	private JPageable pageable;
	
	private List<T> content=new ArrayList<T>();
	
	public void caculatePageNumber() {
		
		if(totalPageNumber < pageable.getPageSize()) {
			setTotalPageNumber(1);
		} else {
			if ((totalPageNumber % pageable.getPageSize()) > 0) {
				this.setTotalPageNumber(totalPageNumber/pageable.getPageSize()+1);
			} else {
				this.setTotalPageNumber(totalPageNumber/pageable.getPageSize());
			}
		}
		
		if (pageable.getPageNumber() > totalPageNumber) {
			JPageRequest pageRequest=(JPageRequest)pageable;
			pageRequest.setPageNumber(totalPageNumber);
		}
		
	}

	public long getTotalRecordNumber() {
		return totalRecordNumber;
	}



	public void setTotalRecordNumber(long totalRecordNumber) {
		this.totalRecordNumber = totalRecordNumber;
	}



	public int getTotalPageNumber() {
		return totalPageNumber;
	}


	public void setTotalPageNumber(int totalPageNumber) {
		this.totalPageNumber = totalPageNumber;
	}


	public JPageable getPageable() {
		return pageable;
	}


	public void setPageable(JPageable pageable) {
		this.pageable = pageable;
	}


	public List<T> getContent() {
		return content;
	}


	public void setContent(List<T> content) {
		this.content = content;
	}
	
}
