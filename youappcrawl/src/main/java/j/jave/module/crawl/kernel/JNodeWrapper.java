package j.jave.module.crawl.kernel;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class JNodeWrapper {
	
	private List<JNodeWrapper> children=new ArrayList<JNodeWrapper>();
	
	private JNodeWrapper parent;
	
	private int deep;
	
	private Node node;
	
	private boolean scanned;
	
	private int index;
	
	public JNodeWrapper(Node node){
		this.node=node;
	}
	
	public JNodeWrapper(Node node,boolean mapping){
		this.node=node;
		if(mapping){
			initNodeWrapper(node, this);
		}
	}

	private void initNodeWrapper(Node node,JNodeWrapper parent){
		NodeList nodeList= node.getChildNodes();
		if(nodeList.getLength()>0){
			for (int i = 0; i < nodeList.getLength(); i++) {
				Node thisNode=nodeList.item(i);
				JNodeWrapper thisNodeWrapper=new JNodeWrapper(thisNode);
				thisNodeWrapper.setParent(parent);
				thisNodeWrapper.setDeep(parent.getDeep()+1);
				thisNodeWrapper.setIndex(i);
				parent.getChildren().add(thisNodeWrapper);
				initNodeWrapper(thisNode, thisNodeWrapper);
			}
		}
	}

	public List<JNodeWrapper> getChildren() {
		return children;
	}

	public void setChildren(List<JNodeWrapper> children) {
		this.children = children;
	}

	public JNodeWrapper getParent() {
		return parent;
	}

	public void setParent(JNodeWrapper parent) {
		this.parent = parent;
	}

	public int getDeep() {
		return deep;
	}

	public void setDeep(int deep) {
		this.deep = deep;
	}

	public Node getNode() {
		return node;
	}

	public void setNode(Node node) {
		this.node = node;
	}

	public boolean isScanned() {
		return scanned;
	}

	public void setScanned(boolean scanned) {
		this.scanned = scanned;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}
	
	
	
}
