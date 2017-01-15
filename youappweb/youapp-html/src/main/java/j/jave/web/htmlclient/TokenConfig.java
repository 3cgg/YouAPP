package j.jave.web.htmlclient;

import java.util.List;

import me.bunny.kernel._c.model.JModel;

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
