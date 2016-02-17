package j.jave.kernal.jave.support.treeview;

/**
 * any model that need represent tree view should implement the interface to
 * enable the function.
 * @author J
 *
 */
public interface JAdvancedTreeStrcture extends JSimpleTreeStrcture {
	/**
	 * if true , the model can potentially contains other models as children,
	 * otherwise the model is underlying.
	 * @return
	 */
	public boolean isText();
	
}
