package j.jave.framework.commons.xml.util;

import java.util.Stack;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class JNodeWalker {

  // the root node the the stack holding the nodes
  private Node currentNode;
  
  private NodeList currentChildren;
  
  private Stack<Node> nodes;

  /**
   * Starts the <code>Node</code> tree from the root node.
   * 
   * @param rootNode
   */
  public JNodeWalker(Node rootNode) {

    nodes = new Stack<Node>();
    nodes.add(rootNode);
  }

  /**
   * <p>
   * Returns the next <code>Node</code> on the stack and pushes all of its
   * children onto the stack, allowing us to walk the node tree without the use
   * of recursion. If there are no more nodes on the stack then null is
   * returned.
   * </p>
   * 
   * @return Node The next <code>Node</code> on the stack or null if there isn't
   *         a next node.
   */
  public Node nextNode() {

    // if no next node return null
    if (!hasNext()) {
      return null;
    }

    // pop the next node off of the stack and push all of its children onto
    // the stack
    currentNode = nodes.pop();
    currentChildren = currentNode.getChildNodes();
    int childLen = (currentChildren != null) ? currentChildren.getLength(): 0;

    // put the children node on the stack in first to last order
    for (int i = childLen - 1; i >= 0; i--) {
      nodes.add(currentChildren.item(i));
    }

    return currentNode;
  }
  
  public Node peekNextNode(){
	  return nodes.peek();
  }

  /**
   * <p>
   * Skips over and removes from the node stack the children of the last node.
   * When getting a next node from the walker, that node's children are
   * automatically added to the stack. You can call this method to remove those
   * children from the stack.
   * </p>
   * 
   * <p>
   * This is useful when you don't want to process deeper into the current path
   * of the node tree but you want to continue processing sibling nodes.
   * </p>
   * 
   */
  public void skipChildren() {

    int childLen = (currentChildren != null) ? currentChildren.getLength() : 0;

    for (int i = 0; i < childLen; i++) {
      Node child = nodes.peek();
      if (child.equals(currentChildren.item(i))) {
        nodes.pop();
      }
    }
  }
  
  
  private boolean isMatchParent(Node thisNode,Node parent){
	  if(parent.equals(thisNode)) return true;
	  Node currentNodeParent=thisNode.getParentNode();
		boolean matchesParent=false;
		do{
			if(parent.equals(currentNodeParent)){
				matchesParent=true;
				break;
			}
		}while((currentNodeParent=currentNodeParent.getParentNode())!=null);
		return matchesParent;
  }
  
  	/**
  	 * SKIP ALL CHILDREN of the node
  	 * @param parent the parent must be parent of the current node.
  	 */
  	public void skipAllChildrenAndSelf(Node parent) {

  		if(!isMatchParent(currentNode, parent)){
  			throw new IllegalArgumentException("the arguemnt must be parent of the current node.");
  		}
  		Node thisNodeWrapper=null;
  		while(nodes.size()>0&&(thisNodeWrapper=nodes.peek())!=null){
  			
  			if(thisNodeWrapper.equals(parent)){
  				nodes.pop();
  				break;
  			}
  			
  			if(isMatchParent(thisNodeWrapper, parent)){
  				nodes.pop();
  				continue;
  			}
  			// break the loop, no node under the parent.
  			break;
  		}
	  }
  

  /**
   * Return the current node.
   * 
   * @return Node
   */
  public Node getCurrentNode() {
    return currentNode;
  }

  /**
   * * Returns true if there are more nodes on the current stack.
   * 
   * @return
   */
  public boolean hasNext() {
    return (nodes.size() > 0);
  }
}
