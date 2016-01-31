package j.jave.kernal.xmldb;

import j.jave.kernal.jave.model.JBaseModel;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;

public interface JModelXMLService {
	
	public void write(JXMLData xmlData,OutputStream outputStream) throws Exception;
	
	public void write(JXMLData xmlData,File file) throws Exception;
	
	public JXMLData read(InputStream inputStream) throws Exception;
	
	public JXMLData read(File file) throws Exception;
	
	public <T> T modelToXML(final JBaseModel model) throws Exception;
	
	
}
