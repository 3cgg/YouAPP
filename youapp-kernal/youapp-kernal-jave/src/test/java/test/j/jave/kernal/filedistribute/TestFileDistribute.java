package test.j.jave.kernal.filedistribute;

import java.io.File;

import junit.framework.TestCase;
import me.bunny.kernel.filedistribute.JFileDistService;
import me.bunny.kernel.filedistribute.JFileDistServicers;
import me.bunny.kernel.http.JHttpFactoryProvider;
import me.bunny.kernel.jave.io.JFile;

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
