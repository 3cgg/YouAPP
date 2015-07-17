package j.jave.framework.commons.xmldb.utils;

import j.jave.framework.commons.utils.JUtilException;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.dom4j.Document;
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
	
	
	
	
	
	
	

}
