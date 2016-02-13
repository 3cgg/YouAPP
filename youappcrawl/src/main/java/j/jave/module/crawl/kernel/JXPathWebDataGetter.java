package j.jave.module.crawl.kernel;

import j.jave.kernal.jave.xml.node.JNodeGetter;
import j.jave.kernal.jave.xml.node.JXPathGetter;
import j.jave.module.crawl.def.JWebModel;
import j.jave.module.crawl.def.JWebModelDefProperties;
import j.jave.module.crawl.def.JWebNodeFieldValue;
import j.jave.module.crawl.node.JNodeGetterUtil;
import j.jave.module.crawl.parser.JNodeValueParserUtil;

import java.lang.reflect.Method;
import java.util.List;

import org.w3c.dom.Node;

public class JXPathWebDataGetter extends JAbstractWebDataGetter {
	
	private int xpathMatchNum;
	
	private double xpathMatchRate;
	
	private int xpathSum;
	
	public JXPathWebDataGetter(JCrawlContext crawlContext){
		xpathMatchRate=crawlContext.getDouble(JPropertiesKeys.XPATH_MATCH_RATE, 1);
	}
	
	public JXPathWebDataGetter(){
	}
	
	@Override
	public void setCrawlContext(JCrawlContext crawlContext) {
		super.setCrawlContext(crawlContext);
		xpathMatchRate=crawlContext.getDouble(JPropertiesKeys.XPATH_MATCH_RATE, 1);
	}
	
	@Override
	public List<JWebModel> get() {
		return webModels;
	}
	
	@Override
	public boolean success() {
		
		if(exception!=null){
			success=false;
			return success;
		}
		return xpathMatchNum/xpathSum>xpathMatchRate;
	}
	
	@Override
	public void execute() {
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
						xpathSum++;
						String xpath=webNodeFieldValue.xpath();
						if(JWebModelDefProperties.EMPTY.equals(xpath)){
							continue;
						}
						JXPathGetter pathGetter= JNodeGetterUtil.getNodeGetter(
								JNodeGetter.XPATH_PROTOCOL,crawlContext);
						List<?> nodes=pathGetter.getNodesByXPath(xpath);

						if(nodes.isEmpty()){ 
							// process next
							continue;
						}
						
						xpathMatchNum++;
						
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
			exception=e;
			e.printStackTrace();
		}
		
		if(webModel!=null){
			webModels.add(webModel);
		}
	}
}
