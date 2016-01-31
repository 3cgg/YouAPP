package j.jave.kernal.xml.dom4j.util;

import j.jave.kernal.jave.utils.JUtilException;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Iterator;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

public class JXMLUtils {
	
	public static Document loadDocument(InputStream inputStream){
		try{
			SAXReader reader = new SAXReader();
			return reader.read(inputStream);
		}catch (Exception e) {
			throw new JUtilException(e);
		}
	}
	
	public static void write(Document document,OutputStream out){
		XMLWriter writer =null;
		try{
			writer = new XMLWriter(out);
            writer.write(document);
		}catch (Exception e) {
			throw new JUtilException(e);
		}finally{
			if(writer!=null){
				try {
					writer.close();
				} catch (IOException e) {
					throw new JUtilException(e);
				}
			}
		}
	}
	
	public static void each(Element element,JElementHandler elementHandler,JElementFilter... elementFilters){
		if(element==null) return;
		
		boolean filter=true; 
		if(elementFilters!=null&&elementFilters.length>0){
			for (int i = 0; i < elementFilters.length; i++) {
				if(!elementFilters[i].filterElement(element)){
					filter=false;
					break;
				}
			}
		}
		
		if(filter&&!elementHandler.handleElement(element)){
			elementHandler.noExist=false;
			return ;
		}
		Iterator<?> iterator =element.elementIterator();
		while(elementHandler.noExist&&iterator.hasNext()){
			Node node= (Node) iterator.next(); 
			if(node.getNodeType()==Node.ELEMENT_NODE){
				each((Element) node, elementHandler,elementFilters);
			}
		}
	}
	
	
	
	

}
