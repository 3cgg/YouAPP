package j.jave.kernal.jave.support._resource;

import j.jave.kernal.jave.exception.JInitializationException;
import j.jave.kernal.jave.io.JFile;
import j.jave.kernal.jave.io.JPathResolver;
import j.jave.kernal.jave.io.JResourceException;
import j.jave.kernal.jave.utils.JCollectionUtils;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * scan all resource underlying the <strong>file directory</strong> (specially a local file system)
 * @author J
 *
 */
public class JFileSystemResourceURIScan extends JAbstractResourceURIScan {

	private final JFile file;
	
	public JFileSystemResourceURIScan(JFile file){
		this.file=file;
	}
	
	public JFileSystemResourceURIScan(JPathResolver pathResolver){
		
		URI uri=null;
		try {
			uri = pathResolver.resolve();
		} catch (Exception e) {
			throw new JInitializationException(e);
		}
		file=new JFile(new File(uri));
	}
	
	private List<URI> doScan(List<URI> urls , File file){
		if(file!=null&&file.exists()){
			File[] files= file.listFiles();
			if(files!=null){
				for (int i = 0; i < files.length; i++) {
					File innerFile=files[i];
					if(innerFile.isFile()){
						String fileName=innerFile.getName();
						if(validate(fileName)){
							URI uri = innerFile.toURI();
							urls.add(uri);
						}
					}
					else if(recurse&&innerFile.isDirectory()){
						return doScan(urls, innerFile);
					}
				}
			}
			
		}
		return urls;
	}
	
	@Override
	public List<URI> scan() {
		
		try {
			List<URI> urls =new ArrayList<URI>(16);
			File includeFile= file.getFile();
			
			if(includeFile.isFile()){
				if(validate(includeFile.getName())){
					URI uri = includeFile.toURI();
					urls.add(uri);
					return urls;
				}
			}
			
			String absolutePath= includeFile.getAbsolutePath();
			if(JCollectionUtils.hasInCollect(this.relativePaths)){
				for (Iterator<String> iterator = relativePaths.iterator(); iterator
						.hasNext();) {
					String relativePath =  iterator.next();
					String scanPath=ROOT.equals(relativePath)?absolutePath:(absolutePath+relativePath);
					doScan(urls, new File(scanPath));
				}
			}
			return urls;
		} catch (IOException e) {
			throw new JResourceException(file, e);
		}
	}

}
