package j.jave.kernal.jave.model;

import java.util.List;

public interface JPage<T> {

	JPageable getPageable();
	
	List<T> getContent();
	
	int getTotalPageNumber();
	
	long getTotalRecordNumber();
	
	
}
