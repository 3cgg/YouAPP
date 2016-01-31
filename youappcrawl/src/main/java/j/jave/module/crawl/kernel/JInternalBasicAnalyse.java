package j.jave.module.crawl.kernel;

import j.jave.kernal.xml.util.JNodeWrapper;
import j.jave.module.crawl.def.JWebModel;
import j.jave.module.crawl.def.JWebNodeFieldKey;
import j.jave.module.crawl.def.JWebNodeModel;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.w3c.dom.Node;

class JInternalBasicAnalyse implements JNodeAnalyse {

	protected Node node;
	
	protected Class<? extends JWebModel> webModelClass;
	
	protected JNodeWrapper nodeWrapper;
	
	protected Map<String, JWebModelMethodInfo> keyMethods=new HashMap<String, JWebModelMethodInfo>(); 
	
	protected JWebModelClassInfo webModelClassInfo;
	
	public JInternalBasicAnalyse(){
		
	}
	
	private void init(Node node,Class<? extends JWebModel> webModelClass){
		this.node=node;
		this.webModelClass=webModelClass;
		nodeWrapper=new JNodeWrapper(node,true);
		Class<?> thisClass= webModelClass;
		try{
			webModelClassInfo=new JWebModelClassInfo();
			JWebNodeModel webNodeModel= thisClass.getAnnotation(JWebNodeModel.class);
			if(webNodeModel!=null){
				webModelClassInfo.setKey(thisClass.getName());
				webModelClassInfo.setTableOrient(webNodeModel.tableOrient());
			}
			
			Method[] methods=thisClass.getMethods();
			if(methods.length>0){
				for (int i = 0; i < methods.length; i++) {
					Method method=methods[i];
					JWebNodeFieldKey webNodeFieldKey= method.getAnnotation(JWebNodeFieldKey.class);
					if(webNodeFieldKey!=null){
						//process 
						
						Class<?> returnType=method.getReturnType();
						//TODO do type convert
						String methodName=method.getName();
						String setMethodName=methodName.replaceFirst("get", "set");
						Method setMethod=thisClass.getMethod(setMethodName,returnType);
						
						JWebModelMethodInfo webModelMethodInfo=new JWebModelMethodInfo();
						String key=webNodeFieldKey.matchesValue();
						webModelMethodInfo.setKey(key);
						webModelMethodInfo.setGetMethod(method);
						webModelMethodInfo.setSetMethod(setMethod);
						keyMethods.put(key, webModelMethodInfo);
					}
				}
			}
			
		}catch(Exception e){
			throw new RuntimeException(e);
		}
		
		
	}
	
	
	@Override
	public List<JWebModel> analyse(Node node,Class<? extends JWebModel> webModelClass){
		//init
		init(node, webModelClass);
		
		//do analyse
		try {
			return doAnalyse(nodeWrapper, webModelClass);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} 
	}
	
	@SuppressWarnings("unchecked")
	protected List<JWebModel> doAnalyse(JNodeWrapper nodeWrapper,Class<? extends JWebModel> webModelClass) throws Exception{
		return Collections.EMPTY_LIST;
	}
	
	protected JWebModel doAnalyse(JNodeWrapper nodeWrapper,JWebModel webModel) throws Exception{
		return webModel;
	}
	
	protected boolean containsKey(String key){
		return keyMethods.containsKey(key);
	}
	
	protected JWebModelMethodInfo getByKey(String key){
		return keyMethods.get(key);
	}

	@Override
	public JWebModel analyse(Node node, JWebModel webModel) {
		
		init(node, webModel.getClass());
		
		//do analyse
		try {
			return doAnalyse(nodeWrapper, webModel);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} 
	}
	
	

}
