package me.bunny.kernel._c.support._resource;

import java.util.ArrayList;
import java.util.List;

import me.bunny.kernel._c.utils.JCollectionUtils;
import me.bunny.kernel._c.utils.JStringUtils;

public class JResourceURIConfiguration extends JFileNameFilterConfiguration implements JFilePathFilterConfig{
	
	public static final String SLASH="/";
	
	public static final String ROOT=SLASH;
	
	
	
	protected List<String> relativePaths=new ArrayList<String>();
	{
		//root
		relativePaths.add(ROOT);
	}
	@Override
	public void setRelativePath(String... paths) {
		if(JCollectionUtils.hasInArray(paths)){
			relativePaths.clear();
			for (int i = 0; i < paths.length; i++) {
				String path=paths[i];
				if(JStringUtils.isNotNullOrEmpty(path)){
					path=path.trim();
					relativePaths.add(path+(path.endsWith(SLASH)?"":SLASH));
				}
				
			}
		}
	}
	
}
