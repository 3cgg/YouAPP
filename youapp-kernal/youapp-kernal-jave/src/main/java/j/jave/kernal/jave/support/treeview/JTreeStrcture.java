package j.jave.kernal.jave.support.treeview;

/**
 * any model that need represent tree view should implement the interface to
 * enable the function.
 * @author J
 *
 */
public interface JTreeStrcture {

	public String getId();
	
	public String getParentId();
	
	public boolean isText();
	
}
