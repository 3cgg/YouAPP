package j.jave.module.crawl.kernel;

import j.jave.kernal.xml.node.JMixedGetter;
import j.jave.kernal.xml.node.JNodeGetter;
import j.jave.module.crawl.def.JWebModel;
import j.jave.module.crawl.def.JWebNodeModel;
import j.jave.module.crawl.node.JNodeGetterUtil;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.w3c.dom.Node;

public class JScopeWebDataGetter extends JAbstractWebDataGetter {
	
	public JScopeWebDataGetter(){
	}
	
	@Override
	public List<JWebModel> get() {
		return webModels;
	}
	
	@Override
	public boolean success() {
		if(exception!=null){
			success=false;
		}
		return success;
	}
	
	@Override
	public void execute() {
		Class<? extends JWebModel> thisClass= webModelClass;
		try{
			JWebNodeModel webNodeModel= thisClass.getAnnotation(JWebNodeModel.class);
			if(webNodeModel!=null){
				
				List<Object> nodes=new ArrayList<Object>();
				String[] scopes=webNodeModel.scope();
				// {"class:consume,tag:table","id:j-area-filter"} 
				if(scopes.length>0){
					for(int i=0;i<scopes.length;i++){
						String scope=scopes[i];
						JMixedGetter mixedGetter=JNodeGetterUtil.getNodeGetter(
								JNodeGetter.MIXED_PROTOCOL,crawlContext);
						nodes.addAll(mixedGetter.getNodesByMixed(scope));
					}
				}
				
				if(!nodes.isEmpty()){
					JWebModel webModel=null;
					if(webNodeModel.single()){
						webModel=thisClass.newInstance();
						webModels.add(webModel);
					}
					
					for (Iterator<Object> iterator = nodes.iterator(); iterator
							.hasNext();) {
						Node node = (Node) iterator.next(); 
						if(node!=null){
							List<JNodeAnalyse> nodeAnalyses= JNodeAnalyseUtil.get(thisClass);
							for (Iterator<JNodeAnalyse> iterator2 = nodeAnalyses.iterator(); iterator2
									.hasNext();) {
								JNodeAnalyse nodeAnalyse =  iterator2
										.next();
								try{
									if(webNodeModel.single()){
										nodeAnalyse.analyse(node, webModel);
									}else{
										webModels.addAll(nodeAnalyse.analyse(node, thisClass));
									}
									break;
								}catch(Exception e){
									e.printStackTrace(); // do an chance 
									continue;
								}
							}
						}
					}
				}
			}
		}catch(Exception e){
			exception=e;
			e.printStackTrace();
		}
	}

}
