package test.j.jave.kernal.filedistribute;

import java.io.File;

import j.jave.kernal.filedistribute.JFileDistService;
import j.jave.kernal.filedistribute.JFileDistServicers;
import j.jave.kernal.http.JHttpFactoryProvider;
import j.jave.kernal.jave.io.JFile;
import junit.framework.TestCase;

public class TestFileDistribute extends TestCase{

	public void testFileDistribute(){
		try{
			JFileDistService fileDistService= JFileDistServicers.newSingleLocalFileDistService("c:/file-store");
			JFile file=new JFile(new File("baidu.html"));
			String content=String.valueOf(JHttpFactoryProvider.getHttpFactory().getHttpGet().execute("http://www.baidu.com/"));
			file.setFileContent(content.getBytes("utf-8"));
			fileDistService.distribute(file);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
