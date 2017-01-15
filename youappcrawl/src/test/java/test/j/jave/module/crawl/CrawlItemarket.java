package test.j.jave.module.crawl;

import j.jave.module.crawl.def.JWebModel;
import j.jave.module.crawl.fun.itemarket.MapstoryModel;
import j.jave.module.crawl.htmlunit.HtmlUnitNodeGetter;
import j.jave.module.crawl.kernel.JCrawlContext;
import j.jave.module.crawl.kernel.JCrawlExecution;
import j.jave.module.crawl.kernel.JPropertiesKeys;
import j.jave.module.crawl.parser.JElementWithSingleTextParser;
import j.jave.module.crawl.parser.JNodeValueParser;
import j.jave.module.crawl.parser.JTextNodeParser;
import me.bunny.kernel.jave.json.JJSON;
import me.bunny.kernel.jave.xml.node.JNodeGetter;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.ProxyConfig;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.DomNodeList;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;

public class CrawlItemarket {

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
        webClient.getOptions().setThrowExceptionOnScriptError(false);
//        ProxyConfig proxyConfig=new ProxyConfig("localhost", 8888);
//        webClient.getOptions().setProxyConfig(proxyConfig);
        webClient.getOptions().setCssEnabled(false);
        //获取页面
        HtmlPage page = webClient.getPage("http://www.itemarket.com/market.asp");
        //获取页面的TITLE
        str = page.getTitleText();
        System.out.println(str);
        //获取页面的XML代码
        str = page.asXml();
        System.out.println(str);
        //获取页面的文本
        str = page.asText();
        System.out.println(str);
        
        JCrawlContext crawlContext=initializeContext(page);
        JCrawlExecution crawlExecution=new JCrawlExecution(MapstoryModel.class, crawlContext);
        List<JWebModel> attrWebModels2=crawlExecution.execute();
        System.out.println(JJSON.get().formatObject(attrWebModels2));
        
        DomElement domElement=  page.getElementByName("key");
        domElement.setNodeValue("布莱克");
        domElement.setAttribute("value", "布莱克");
        HtmlForm form=(HtmlForm) page.getElementById("searchform");
        List<HtmlElement> htmlElements= form.getElementsByAttribute("input", "type", "submit");
        
        HtmlSubmitInput submitInput=null;
        DomNodeList<DomElement>domElements= page.getElementsByTagName("input");
        for (Iterator iterator = domElements.iterator(); iterator.hasNext();) {
			DomElement domElement2 = (DomElement) iterator.next();
			if("submit".equals(domElement2.getAttribute("type"))
					&&"搜索".equals(domElement2.getAttribute("value"))){
				submitInput=(HtmlSubmitInput) domElement2;
				break;
			}
		}
        
        HtmlPage htmlPage=submitInput.click();
        crawlContext=initializeContext(htmlPage);
        crawlExecution=new JCrawlExecution(MapstoryModel.class, crawlContext);
        attrWebModels2=crawlExecution.execute();
        System.out.println(JJSON.get().formatObject(attrWebModels2));
        
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
