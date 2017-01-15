package j.jave.module.crawl.kernel;

import j.jave.module.crawl.def.JWebModel;
import j.jave.module.crawl.def.JWebModelDefProperties;
import me.bunny.kernel._c.xml.util.JNodeWrapper;
import me.bunny.kernel._c.xml.util.JNodeWrapperWalker;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.w3c.dom.Node;


public class JTableAnalyse extends JInternalBasicAnalyse{
	
	private static final String TABLE="table";
	
	private static final String TBODY="tbody";
	
	private static final String THEAD="thead";
	
	private static final String TFOOT="tfoot";
	
	private static final String TR="tr";
	
	private static final String TH="th";
	
	private static final String TD="td";
	
	@Override
	public List<JWebModel> doAnalyse(JNodeWrapper nodeWrapper,Class<? extends JWebModel> webModelClass)  throws Exception{
		
		if(JWebModelDefProperties.TABLE_ORIENT_VERTICAL.equals(webModelClassInfo.getTableOrient())){
			return vertical(nodeWrapper, webModelClass.newInstance());
		}
		else if(JWebModelDefProperties.TABLE_ORIENT_HORIZONTAL.equals(webModelClassInfo.getTableOrient())){
			return horizontal(nodeWrapper, webModelClass);
		}
		return Collections.EMPTY_LIST;
	}
	
	private JNodeWrapper getNextExpected(JNodeWrapperWalker nodeWrapperWalker,JNodeWrapper upTo){
		
		while(nodeWrapperWalker.hasNext()){
			JNodeWrapper thisNodeWrapper=nodeWrapperWalker.nextNode();
			
			if(this.nodeWrapper==upTo){
				nodeWrapperWalker.skipChildren();
				return null;
			}
			
			Node node=thisNodeWrapper.getNode();
			String nodeName=node.getNodeName();
			if(TH.equalsIgnoreCase(nodeName)
					||TD.equalsIgnoreCase(nodeName)
					){
				return thisNodeWrapper;
			}
		}
		return null;
		
	}
	
	private List<JWebModel> vertical(JNodeWrapper nodeWrapper,JWebModel webModel)throws Exception{

		List<JWebModel> webModels=new ArrayList<JWebModel>();
		JNodeWrapperWalker nodeWrapperWalker=new JNodeWrapperWalker(nodeWrapper);
		while(nodeWrapperWalker.hasNext()){
			JNodeWrapper thisNodeWrapper=nodeWrapperWalker.nextNode();
			Node node=thisNodeWrapper.getNode();
			String nodeName=node.getNodeName();
			if(TH.equalsIgnoreCase(nodeName)
					||TD.equalsIgnoreCase(nodeName)
					){
				String text=node.getTextContent().trim();
				JWebModelMethodInfo webModelMethodInfo=null;
				if(containsKey(text)){
					webModelMethodInfo=getByKey(text);
					//skip children
					nodeWrapperWalker.skipChildren();
					
					//get next th||td, may include value
					JNodeWrapper valueNodeWrapper= getNextExpected(nodeWrapperWalker, thisNodeWrapper.getParent());
					if(valueNodeWrapper!=null){
						String value=valueNodeWrapper.getNode().getTextContent();
						webModelMethodInfo.getSetMethod().invoke(webModel, value);
					}
				}
			}
		}
		webModels.add(webModel);
		return webModels; 
	}
	
	
	/**
	 * 
	 * @param nodeWrapper contains all columns
	 * @return
	 */
	private Map<Integer, JWebModelMethodInfo> getMethodMappings(JNodeWrapper nodeWrapper){
		Map<Integer, JWebModelMethodInfo> methodInfoIndexs=new HashMap<Integer, JWebModelMethodInfo>();
		if(nodeWrapper!=null){
			List<JNodeWrapper> nodeWrappers= nodeWrapper.getChildren();
			for(int i=0;i<nodeWrappers.size();i++){
				JNodeWrapper thisNodeWrapper=nodeWrappers.get(i);
				Node node=thisNodeWrapper.getNode();
				String nodeName=node.getNodeName();
				if(TH.equalsIgnoreCase(nodeName)
						||TD.equalsIgnoreCase(nodeName)
						){
					String text=node.getTextContent().trim();
					JWebModelMethodInfo webModelMethodInfo=null;
					if(containsKey(text)){
						webModelMethodInfo=getByKey(text);
						methodInfoIndexs.put(thisNodeWrapper.getOffset(), webModelMethodInfo);
					}
				}
			}
		}
		return methodInfoIndexs;
	}
	
	private List<JWebModel> horizontal(JNodeWrapper nodeWrapper,Class<? extends JWebModel> webModelClass)throws Exception{
		
		List<JWebModel> webModels=new ArrayList<JWebModel>();
		Map<Integer, JWebModelMethodInfo> webMethodInfoMap=null;
		JNodeWrapperWalker nodeWrapperWalker=new JNodeWrapperWalker(nodeWrapper);
		while(nodeWrapperWalker.hasNext()){
			JNodeWrapper thisNodeWrapper=nodeWrapperWalker.nextNode();
			Node node=thisNodeWrapper.getNode();
			String nodeName=node.getNodeName();
			if(TH.equalsIgnoreCase(nodeName)
					||TD.equalsIgnoreCase(nodeName)
					){
				String text=node.getTextContent().trim();
				JWebModelMethodInfo webModelMethodInfo=null;
				if(containsKey(text)){
					webModelMethodInfo=getByKey(text);
					//filin columns
					webMethodInfoMap=  getMethodMappings(thisNodeWrapper.getParent());
					//skip all children under tr or 
					nodeWrapperWalker.skipAllChildrenAndSelf(thisNodeWrapper.getParent());
					break;
				}
			}
		}
		
		if(webMethodInfoMap==null||webMethodInfoMap.isEmpty()){
			return Collections.EMPTY_LIST;
		}
		
		JWebModel webModel=null;
		
		//get value
		while(nodeWrapperWalker.hasNext()){
			JNodeWrapper thisNodeWrapper=nodeWrapperWalker.nextNode();
			Node node=thisNodeWrapper.getNode();
			String nodeName=node.getNodeName();
			if(TR.equalsIgnoreCase(nodeName)){
				webModel=webModelClass.newInstance();
				webModels.add(webModel);
			}
			if(TH.equalsIgnoreCase(nodeName)
					||TD.equalsIgnoreCase(nodeName)
					){
				String text=node.getTextContent().trim();
				if(webMethodInfoMap.containsKey(thisNodeWrapper.getOffset())){
					JWebModelMethodInfo webModelMethodInfo= webMethodInfoMap.get(thisNodeWrapper.getOffset());
					webModelMethodInfo.getSetMethod().invoke(webModel, text);
				}
				//skip children
				nodeWrapperWalker.skipChildren();
			}
		}
		return webModels;
	}
	
	
	
	@Override
	protected JWebModel doAnalyse(JNodeWrapper nodeWrapper, JWebModel webModel)
			throws Exception {
		return vertical(nodeWrapper, webModel).get(0);
	}
	
}
