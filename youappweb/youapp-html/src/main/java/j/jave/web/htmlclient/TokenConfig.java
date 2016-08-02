package j.jave.web.htmlclient;

import j.jave.kernal.jave.model.JModel;

import java.util.List;

public class TokenConfig implements JModel{

	private List<PatternConfig> include;
	
	private List<PatternConfig> exclude;

	public List<PatternConfig> getInclude() {
		return include;
	}

	public void setInclude(List<PatternConfig> include) {
		this.include = include;
	}

	public List<PatternConfig> getExclude() {
		return exclude;
	}

	public void setExclude(List<PatternConfig> exclude) {
		this.exclude = exclude;
	}
	
	
	
}
