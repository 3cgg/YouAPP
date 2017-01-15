package me.bunny.kernel._c.support._resource;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import me.bunny.kernel._c.exception.JInitializationException;
import me.bunny.kernel._c.io.JFile;
import me.bunny.kernel._c.io.JPathResolver;
import me.bunny.kernel._c.io.JResourceException;
import me.bunny.kernel._c.utils.JCollectionUtils;

/**
 * scan all resource underlying the <strong>file directory</strong> (specially a local file system)
 * @author J
 *
 */
public class JFileSystemResourceURIScanner extends JAbstractResourceURIScanner {

	private final JFile file;
	
	public JFileSystemResourceURIScanner(JFile file){
		this.file=file;
	}
	
	public JFileSystemResourceURIScanner(JPathResolver pathResolver){
		
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
