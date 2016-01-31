package j.jave.kernal.jave.support.treeview;

public class JDefaultNodeRepresentStrategy implements
		JTreeNodeRepresentStrategy {

	private final JTreeNode treeNode;
	
	public JDefaultNodeRepresentStrategy(JTreeNode treeNode){
		this.treeNode=treeNode;
	}
	
	@Override
	public String represent() {
		String represent=null;
		JTreeElement treeElement= treeNode.getParent();
		if(treeElement!=null){
			represent=treeElement.getNodeConfig().getTreeNodeRepresentStrategy().represent();
		}
		JTreeNodeConfig treeNodeConfig= treeNode.getNodeConfig();
		represent=represent==null?(""+treeNodeConfig.getOffset()):(represent+"."+treeNodeConfig.getOffset());
		return represent;
	}

}
