package j.jave.framework.tkdd;

import java.io.Serializable;

public interface JTaskMetadata extends Serializable {
	
	/**
	 * name , the definition name. all instances should has the same.
	 * @return
	 */
	String name();
	
	Class<? extends JTask> task(); 
	
	/**
	 * id , it should be unique in the whole system. generally use the full class name as the value.
	 * @return
	 */
	String id();
	
	/**
	 * snapshot id , it should be unique in its owner task context.
	 * or as an unique participator of a certain linked task.
	 * @return
	 */
	String snapshotId();
	
	/**
	 * if it's enabled.
	 * @return
	 */
	boolean enabled();
	
	/**
	 * if it's installed.
	 * @return
	 */
	boolean installed();
	
	
	/**
	 * if it's uninstalled.
	 * @return
	 */
	boolean uninstalled();
	
	/**
	 * what to describer itself.
	 * @return
	 */
	String describer();
	
	/**
	 * the owner that has an access to.
	 * @return
	 */
	JTaskMetadata owner();
	
	public void setName(String name) ;

	public void setEnabled(boolean enabled);

	public void setInstalled(boolean installed);

	public void setUninstalled(boolean uninstalled) ;

	public void setDescriber(String describer);

	public void setOwner(JTaskMetadata owner) ;
	

}
