package test.j.jave.module.crawl;

import j.jave.module.crawl.def.JWebModel;
import j.jave.module.crawl.htmlunit.HtmlUnitNodeGetter;
import j.jave.module.crawl.kernel.JCrawlContext;
import j.jave.module.crawl.kernel.JCrawlExecution;
import j.jave.module.crawl.kernel.JPropertiesKeys;
import j.jave.module.crawl.kernel.JScopeWebDataGetter;
import j.jave.module.crawl.kernel.JXPathWebDataGetter;
import j.jave.module.crawl.parser.JElementWithSingleTextParser;
import j.jave.module.crawl.parser.JNodeValueParser;
import j.jave.module.crawl.parser.JTextNodeParser;
import me.bunny.kernel._c.xml.node.JNodeGetter;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Node;

import test.j.jave.module.crawl.model.Alert;
import test.j.jave.module.crawl.model.Attributes;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.ProxyConfig;
import com.gargoylesoftware.htmlunit.ScriptResult;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.DomNodeList;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class CrawlMain {

	private static List<JNodeValueParser> nodeValueParsers=new ArrayList<JNodeValueParser>(){
		{
			add(new JElementWithSingleTextParser());
			add(new JTextNodeParser());
		}
	};
	
	
	public static void main(String[] args) throws FailingHttpStatusCodeException, MalformedURLException, IOException {
		String str;
        //创建一个webclient
        WebClient webClient = new WebClient(BrowserVersion.FIREFOX_38);
        //htmlunit 对css和javascript的支持不好，所以请关闭之
        webClient.getOptions().setJavaScriptEnabled(true);
//        ProxyConfig proxyConfig=new ProxyConfig("localhost", 3213);
//        webClient.getOptions().setProxyConfig(proxyConfig);
        webClient.getOptions().setCssEnabled(false);
        //获取页面
        HtmlPage page = webClient.getPage("http://www.nuomi.com/deal/c86ifosj.html?s=2c8c35060fcec53ac0a7675383e28207");
        //获取页面的TITLE
        str = page.getTitleText();
        System.out.println(str);
        //获取页面的XML代码
        str = page.asXml();
        System.out.println(str);
        //获取页面的文本
        str = page.asText();
        System.out.println(str);
        
        
        List<?> res=page.getByXPath("//*[@id=\"j-info-all\"]/li[1]/div[3]/div/table/tbody/tr[1]/th/div/text()");
        for(int i=0;i<res.size();i++){
        	
        	Node obj=(Node) res.get(i);
        	String value="";
        	for(int j=0;j<nodeValueParsers.size();j++){
        		JNodeValueParser nodeValueParser=nodeValueParsers.get(j);
        		if(nodeValueParser.supported( obj)){
            		value=nodeValueParser.parse(obj);
            		break;
            	}
        	}
        	
        	System.out.println(obj+"------>"+value);
        }
        ScriptResult scriptResult= page.executeJavaScript("document.getElementsByClassName('comsume');");
        
        DomNodeList<DomElement> domElements=  page.getElementsByTagName("table");
        for(int i=0;i<domElements.size();i++){
        	DomElement domElement=domElements.get(i);
        	
        	System.out.println(domElement);
        	
        }
        
        if(DomNodeList.class.isInstance(scriptResult.getJavaScriptResult())){
        	System.out.println("is");
        }
        
        JCrawlContext crawlContext= initializeContext(page);
        
        JXPathWebDataGetter pathWebDataGetter=new JXPathWebDataGetter();
        pathWebDataGetter.setCrawlContext(crawlContext);
        pathWebDataGetter.setWebModelClass(Alert.class);
        List<JWebModel> webModels=pathWebDataGetter.get();
        System.out.println(webModels);
        
        JScopeWebDataGetter scopeWebDataGetter=new JScopeWebDataGetter();
        scopeWebDataGetter.setCrawlContext(crawlContext);
        scopeWebDataGetter.setWebModelClass(Alert.class);
        List<JWebModel> webModels2=scopeWebDataGetter.get();
        System.out.println(webModels2);
        
        page = webClient.getPage("http://www.w3schools.com/tags/tag_table.asp");
        
        crawlContext=initializeContext(page);
        
        JScopeWebDataGetter scopeWebDataGetterAttr=new JScopeWebDataGetter();
        scopeWebDataGetterAttr.setCrawlContext(crawlContext);
        scopeWebDataGetterAttr.setWebModelClass(Attributes.class);
        List<JWebModel> attrWebModels=scopeWebDataGetterAttr.get();
        System.out.println(attrWebModels);
        
        JCrawlExecution crawlExecution=new JCrawlExecution(Attributes.class, crawlContext);
        List<JWebModel> attrWebModels2=crawlExecution.execute();
        System.out.println(attrWebModels2);
        
        
        //ScriptResult scriptResult= page.executeJavaScript("getFunURL()");
        //Object obj=scriptResult.getJavaScriptResult();
        //System.out.println("---->"+obj);
        //关闭webclient
        webClient.close();
        
        System.out.println("end");
	}


	private static JCrawlContext initializeContext(HtmlPage page) {
		JCrawlContext crawlContext=new JCrawlContext();
        JNodeGetter nodeGetter=new HtmlUnitNodeGetter(page);
        crawlContext.put(JPropertiesKeys.NODE_NAME_GETTER, nodeGetter);
        crawlContext.put(JPropertiesKeys.NODE_CLASSNAME_GETTER, nodeGetter);
        crawlContext.put(JPropertiesKeys.NODE_TAGNAME_GETTER, nodeGetter);
        crawlContext.put(JPropertiesKeys.NODE_XPATH_GETTER, nodeGetter);
        crawlContext.put(JPropertiesKeys.NODE_ID_GETTER, nodeGetter);
        crawlContext.put(JPropertiesKeys.NODE_MIXED_GETTER, nodeGetter);
        
        return crawlContext;
	}
}
