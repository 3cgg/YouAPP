/**
 * 
 */
package j.jave.framework.components.web.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author J
 */
public class TR {

	private String id;
	
	private List<TD> tds=new ArrayList<TD>();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<TD> getTds() {
		return tds;
	}

	public void setTds(List<TD> tds) {
		this.tds = tds;
	}
	
	
	
}
