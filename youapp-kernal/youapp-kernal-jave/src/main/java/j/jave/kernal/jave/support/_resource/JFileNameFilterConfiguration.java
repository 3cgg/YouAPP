package j.jave.kernal.jave.support._resource;

import j.jave.kernal.jave.support.validate.JValidator;
import j.jave.kernal.jave.utils.JCollectionUtils;
import j.jave.kernal.jave.utils.JStringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class JFileNameFilterConfiguration implements JFileNameFilterConfig , JValidator<String>{
	
	protected List<Pattern> includePatterns=new ArrayList<Pattern>();
	{
		//default get all
		includePatterns.add(Pattern.compile(".*"));
	}
	protected List<String> includeFileNames=new ArrayList<String>();
	
	protected List<Pattern> excludePatterns=new ArrayList<Pattern>();
	
	protected List<String> excludeFileNames=new ArrayList<String>();
	
	@Override
	public void setIncludeFileName(String... fileNames) {
		if(JCollectionUtils.hasInArray(fileNames)){
			for (int i = 0; i < fileNames.length; i++) {
				String fileName=fileNames[i];
				if(JStringUtils.isNotNullOrEmpty(fileName)){
					includeFileNames.add(fileName.trim());
				}
			}
		}
	}
	
	@Override
	public void setIncludeExpression(String... expressions) {
		if(JCollectionUtils.hasInArray(expressions)){
			includePatterns.clear();
			for(int i=0;i<expressions.length;i++){
				String expression=expressions[i];
				if(JStringUtils.isNotNullOrEmpty(expression)){
					includePatterns.add(Pattern.compile(expression.trim()));
				}
			}
		}
	}
	
	@Override
	public void setExcludeExpression(String... expressions) {
		if(JCollectionUtils.hasInArray(expressions)){
			for(int i=0;i<expressions.length;i++){
				String expression=expressions[i];
				if(JStringUtils.isNotNullOrEmpty(expression)){
					excludePatterns.add(Pattern.compile(expression.trim()));
				}
			}
		}
	}
	
	@Override
	public void setExcludeFileName(String... fileNames) {
		if(JCollectionUtils.hasInArray(fileNames)){
			for (int i = 0; i < fileNames.length; i++) {
				String fileName=fileNames[i];
				if(JStringUtils.isNotNullOrEmpty(fileName)){
					excludeFileNames.add(fileName.trim());
				}
			}
		}
	}

	/**
	 * validate the passed file name with file extension.
	 */
	@Override
	public boolean validate(String fileName) {
		boolean matches=true;
		if(excludePatterns!=null){
			if(fileName.indexOf("spring")!=-1){
				System.out.println(fileName);
			}
			for (int i = 0; i < excludePatterns.size(); i++) {
				Pattern pattern=excludePatterns.get(i);
				if(pattern.matcher(fileName).matches()){
					matches=false;
					return matches;
				}
			}
		}
		
		if(excludeFileNames!=null){
			for (int i = 0; i < excludeFileNames.size(); i++) {
				String excludeFileName=excludeFileNames.get(i);
				if(excludeFileName.equals(fileName)){
					matches=false;
					return matches;
				}
			}
		}
		
		if(includePatterns!=null){
			for (int i = 0; i < includePatterns.size(); i++) {
				Pattern pattern=includePatterns.get(i);
				if(pattern.matcher(fileName).matches()){
					matches=true;
					return matches;
				}
			}
		}
		
		if(includeFileNames!=null){
			for (int i = 0; i < includeFileNames.size(); i++) {
				String includeFileName=includeFileNames.get(i);
				if(includeFileName.equals(fileName)){
					matches=true;
					return matches;
				}
			}
		}
		// should not include the file.
		return false;
	}
	
	
	public static void main(String[] args) {
		boolean mth=Pattern.compile("spring.*[.]xml").matcher("spring-context.xml").matches();
		System.out.println(mth);
	}
}
