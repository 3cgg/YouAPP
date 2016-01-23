package j.jave.module.crawl.kernel;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Node;

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
