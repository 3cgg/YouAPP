package j.jave.kernal.jave.model;

public interface JPageAware {

	<T extends JModel> void setPage(JPage<T> page);
	
	<T extends JModel> JPage<T> getPage();
	
}
