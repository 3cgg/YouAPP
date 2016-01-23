package j.jave.module.crawl.kernel;

import j.jave.module.crawl.def.JWebModel;
import j.jave.module.crawl.def.JWebNodeFieldValue;
import j.jave.module.crawl.node.JNodeGetterUtil;
import j.jave.module.crawl.node.JXPathGetter;
import j.jave.module.crawl.parser.JNodeValueParserUtil;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Node;

public class JXPathWebDataGetter extends JAbstractWebDataGetter {
	
	public JXPathWebDataGetter(Class<? extends JWebModel> webModelClass){
		super(webModelClass);
	}
	
	public JXPathWebDataGetter(){
	}
	
	@Override
	public List<JWebModel> parser() {
		
		List<JWebModel>  webModels=new ArrayList<JWebModel>();
		
		JWebModel webModel=null;
		Class<?> thisClass= webModelClass;
		try{
			Method[] methods=thisClass.getMethods();
			if(methods.length>0){
				for (int i = 0; i < methods.length; i++) {
					Method method=methods[i];
					JWebNodeFieldValue webNodeFieldValue= method.getAnnotation(JWebNodeFieldValue.class);
					if(webNodeFieldValue!=null){
						//process 
						String xpath=webNodeFieldValue.xpath();
						JXPathGetter pathGetter= JNodeGetterUtil.getNodeGetter(JNodeGetterUtil.XPATH_PROTOCOL);
						List<?> nodes=pathGetter.getNodesByXPath(xpath);

						if(nodes.isEmpty()){ 
							// process next
							continue;
						}
						
						String[] parsers=empty;
						String parser=webNodeFieldValue.parser();
						if(parser!=null&&parser.length()>0){
							parsers=parser.split(",");
						}
						String value=JNodeValueParserUtil.parser((Node) nodes.get(0), parsers);
						
						Class<?> returnType=method.getReturnType();
						//TODO do type convert
						String methodName=method.getName();
						String setMethodName=methodName.replaceFirst("get", "set");
						
						if(webModel==null){
							webModel=webModelClass.newInstance();
						}
						thisClass.getMethod(setMethodName,returnType).invoke(webModel, value);
					}
					
				}
			}
			
		}catch(Exception e){
			throw new RuntimeException(e);
		}
		
		if(webModel!=null){
			webModels.add(webModel);
		}
		return webModels;
	}

}
