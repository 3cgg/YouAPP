/**
 * 
 */
package me.bunny.kernel.filedistribute;

import java.io.File;
import java.net.URI;
import java.util.Date;

import me.bunny.kernel.jave.io.JFile;
import me.bunny.kernel.jave.support.validate.JFileExtensionValidator;
import me.bunny.kernel.jave.support.validate.JFileNameValidator;
import me.bunny.kernel.jave.support.validate.JValidator;
import me.bunny.kernel.jave.utils.JDateUtils;
import me.bunny.kernel.jave.utils.JStringUtils;

/**
 * resolve path, 
 * which the file translating from the remote will be put under 
 * the hierarchical like as :\ {0} is a sequence number from zero (0,1,2,3...)
 *<li>date(2014-04-15)</li>
 *<li>date(2014-04-15)/filetype(txt)</li>
 *<li>date(2014-04-15)/filetype(txt)/{0}</li>, every placeholder only contains one hundred files. 
 *<li>date(2014-04-15)/filetype(txt)/{0}/targetfile</li>
 * @author J
 * @see JHierarchicalPath
 */
public class JDefaultLocalFilePathStrategy implements JLocalFilePathStrategy {

	private String localDirectory;
	
	public JDefaultLocalFilePathStrategy(String localDirectory) {
		this.localDirectory=localDirectory;
	}
	
	public JDefaultLocalFilePathStrategy(){
	}
	
	/**
	 * @param localDirectory the localDirectory to set
	 */
	public void setLocalDirectory(String localDirectory) {
		this.localDirectory = localDirectory;
	}
	
	/**
	 * @return the localDirectory
	 */
	public String getLocalDirectory() {
		return localDirectory;
	}
	
	@Override
	public URI resolveURI(JFile file) {
		JHierarchicalPath hierarchicalPath= new JHierarchicalPath(file);
		hierarchicalPath.setRoot(localDirectory);
		return resolveURI(hierarchicalPath);
	}
	
	private URI resolveURI(JHierarchicalPath hierarchicalPath) {
		String root=hierarchicalPath.getRoot();
		String extensionWithDot=hierarchicalPath.getFileExtension();

		if(JStringUtils.isNullOrEmpty(extensionWithDot)){
			extensionWithDot=".empty"; // set default extension, if the file does have extension.
		}
		
		File rootFile=new File(root);
		File parentFile=rootFile;
		
		// find by date string.
		if(parentFile!=null){
			String today=JDateUtils.format(new Date());
			File temp=find(parentFile, new JFileNameValidator(today));
			if(temp!=null){
				parentFile=temp;
			}
			else{
				String newDir=parentFile.getAbsolutePath()+"/"+today+"/"+extensionWithDot+"/"+"0";
				mkdir(newDir);
				return new File(newDir+"/"+hierarchicalPath.uniqueName()).toURI();
			}
		}
		//find by "file type"
		if(parentFile!=null){
			File temp=find(parentFile, new JFileExtensionValidator(extensionWithDot));
			if(temp!=null){
				parentFile=temp;
			}
			else{
				String newDir=parentFile.getAbsolutePath()+"/"+(extensionWithDot)+"/"+"0";
				mkdir(newDir);
				return new File(newDir+"/"+hierarchicalPath.uniqueName()).toURI();
			}
		}
		
		// get file with max number 
		if(parentFile!=null){
			parentFile=findMax(parentFile);
			if(parentFile==null){
				String newDir=parentFile.getAbsolutePath()+"/"+"0";
				mkdir(newDir);
				return new File(newDir+"/"+hierarchicalPath.uniqueName()).toURI();
			}
			else{
				//check the number of containing files is not larger than 100. ignore the concurrence. 
				if(2>parentFile.listFiles().length){
					return new File(parentFile.getAbsolutePath()+"/"+hierarchicalPath.uniqueName()).toURI();
				}
				else{
					int max=Integer.parseInt(parentFile.getName())+1;
					String newDir=parentFile.getParentFile().getAbsoluteFile()+"/"+max;
					mkdir(newDir);
					return new File(newDir+"/"+hierarchicalPath.uniqueName()).toURI();
				}
			}
			
		}
		return null;
	}
	
	private synchronized void mkdir(String path){
		File file=new File(path);
		if(file!=null&&!file.exists()){
			file.mkdirs();
		}
	}
	
	private File find(File file,JValidator<String> validator){
		if(file!=null){
			File[] files=file.listFiles();
			if(files!=null){
				for(int i=0;i<files.length;i++){
					boolean matches=validator.validate(files[i].getName());
					if(matches){
						return files[i];
					}
				}
			}
		}
		return null;
	}

	private File findMax(File file){
		File max=null;
		if(file!=null){
			File[] files=file.listFiles();
			for(int i=0;i<files.length;i++){
				File inner=files[i];
				if(max==null){
					max=inner;
				}
				else{
					int maxNum=Integer.parseInt(max.getName());
					int curNum=Integer.parseInt(inner.getName());
					if(maxNum<curNum){
						max=inner;
					}
				}
			}
		}
		return max;
	}
	
	
	
	
}
