package j.jave.kernal.jave.support._resource;

import j.jave.kernal.jave.exception.JInitializationException;
import j.jave.kernal.jave.io.JFile;
import j.jave.kernal.jave.io.JResourceException;
import j.jave.kernal.jave.utils.JClassPathUtils;
import j.jave.kernal.jave.utils.JCollectionUtils;
import j.jave.kernal.jave.utils.JFileUtils;
import j.jave.kernal.jave.utils.JJARUtils;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
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
public class JJARResourceURIScanner extends JAbstractResourceURIScanner {

	private final JarFile jarFile;
	
	public JJARResourceURIScanner(JarFile jarFile){
		this.jarFile=jarFile;
	}
	
	public JJARResourceURIScanner(URI uri){
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
		String jarFileURIPath=new File(jarFile.getName()).toURI().toString();
		List<URI> uris =new ArrayList<URI>(16);
		Enumeration<JarEntry> jarEntries= jarFile.entries();
		while(jarEntries.hasMoreElements()){
			JarEntry jarEntry=jarEntries.nextElement();
			String fileName=jarEntry.getName();
			if(validateRelative(fileName)&&validate(JFileUtils.getFileName(fileName))){
				try {
					URI uri=new URI(JJARUtils.getURI(jarFileURIPath, fileName));
					uris.add(uri);
				} catch (URISyntaxException e) {
					throw new JResourceException(new JFile(new File(jarFile.getName())), e);
				}
			}
		}
		return uris;
	}
	
	public static void main(String[] args) {
		try {
			JJARResourceURIScanner resourceURIScanner=new JJARResourceURIScanner(
					new URI("file:/d:/youapp-business-bill-1.0.0.jar"));
			resourceURIScanner.setIncludeFileName("*");
			resourceURIScanner.scan();
			
			List<File> files=JClassPathUtils.getRuntimeClassPathFiles();
			
			List<String> filePaths= JClassPathUtils.getRuntimeClassPathFileAbsolutePaths();
			
			System.out.println(files.toString()+filePaths.toString());
		
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
}
