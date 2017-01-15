package j.jave.web.htmlclient.request;

import java.util.HashMap;
import java.util.Map;

import me.bunny.kernel.jave.model.JModel;

public class FileAttachedParamVO implements JModel {
	
	private Map<String, Object> params=new HashMap<String, Object>();
	
	private String fileUrl;
	
	private String fileType;

	public String getFileUrl() {
		return fileUrl;
	}

	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	
	public Map<String, Object> getParams() {
		return params;
	}

	public void setParams(Map<String, Object> params) {
		this.params = params;
	}

	public void put(String name,Object value ){
		params.put(name, value);
	}
}
