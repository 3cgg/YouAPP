package j.jave.kernal.jave.support._resource;

import j.jave.kernal.jave.exception.JInitializationException;
import j.jave.kernal.jave.io.JFile;
import j.jave.kernal.jave.io.JResourceException;
import j.jave.kernal.jave.utils.JCollectionUtils;
import j.jave.kernal.jave.utils.JFileUtils;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * scan all resources from the passed<strong> jar</strong> file. 
 * @author J
 * @see JFilePathFilterConfig
 * @see JFileNameFilterConfig
 */
public class JJARResourceURIScan extends JAbstractResourceURIScan {
	private final JarFile jarFile;
	
	private ClassLoader classLoader=ClassLoader.getSystemClassLoader();
	
	public void setClassLoader(ClassLoader classLoader) {
		this.classLoader = classLoader;
	}
	
	public JJARResourceURIScan(JarFile jarFile){
		this.jarFile=jarFile;
	}
	
	public JJARResourceURIScan(URI uri){
		try {
			this.jarFile=new JarFile(new File(uri));
		} catch (IOException e) {
			throw new JInitializationException(e);
		}
	}
	
	private boolean validateRelative(String path){
		if(JCollectionUtils.hasInCollect(this.relativePaths)){
			
			// directory
			if(path.endsWith(SLASH)){
				return false;
			}
			
			if(relativePaths.contains(ROOT)){
				return true;
			}
			
			String directoryPath=path.substring(0, path.lastIndexOf(SLASH));
			if(recurse){
				while(true){
					if(relativePaths.contains(directoryPath+SLASH)){
						return true;
					}
					else{
						int slashIndex=directoryPath.lastIndexOf(SLASH);
						if(slashIndex==-1){
							break;
						}
						directoryPath=directoryPath.substring(0, slashIndex);
					}
				}
				
			}
			else{
				return relativePaths.contains(directoryPath+SLASH);
			}
			
			return relativePaths.contains(path);
		}
		return false;
	}
	
	@Override
	public List<URI> scan() {
		List<URI> uris =new ArrayList<URI>(16);
		Enumeration<JarEntry> jarEntries= jarFile.entries();
		while(jarEntries.hasMoreElements()){
			JarEntry jarEntry=jarEntries.nextElement();
			String fileName=jarEntry.getName();
			if(validateRelative(fileName)&&validate(JFileUtils.getFileName(fileName))){
				try {
					URL url=classLoader.getResource(fileName); 
					if(url!=null){
						uris.add(classLoader.getResource(fileName).toURI());
					}
				} catch (URISyntaxException e) {
					throw new JResourceException(new JFile(new File(jarFile.getName())), e);
				}
			}
		}
		return uris;
	}
	
	
}
