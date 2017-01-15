package me.bunny.kernel.jave.xml.xmldb;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;

import me.bunny.kernel.jave.model.JBaseModel;

public interface JModelXMLService {
	
	public void write(JXMLData xmlData,OutputStream outputStream) throws Exception;
	
	public void write(JXMLData xmlData,File file) throws Exception;
	
	public JXMLData read(InputStream inputStream) throws Exception;
	
	public JXMLData read(File file) throws Exception;
	
	public <T> T modelToXML(final JBaseModel model) throws Exception;
	
	
}
