package j.jave.kernal.jave.support.treeview;

/**
 * any model that need represent tree view should implement the interface to
 * enable the function.
 * @author J
 *
 */
public interface JTreeStrcture {

	/**
	 * the identification of the model.
	 * @return
	 */
	public String getId();
	
	/**
	 * the represent name.
	 * @return
	 */
	public String getName();
	
	/**
	 * the identification of the parent model.
	 * @return
	 */
	public String getParentId();
	
	/**
	 * if true , the model can potentially contains other models as children,
	 * otherwise the model is underlying.
	 * @return
	 */
	public boolean isText();
	
}
