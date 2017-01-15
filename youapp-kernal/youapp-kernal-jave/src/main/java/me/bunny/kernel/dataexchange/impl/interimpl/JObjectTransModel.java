package me.bunny.kernel.dataexchange.impl.interimpl;

import java.util.HashMap;
import java.util.Map;

import me.bunny.kernel.jave.model.JModel;

@SuppressWarnings("serial")
public class JObjectTransModel implements JModel{
	
	private JObjectTransModelProtocol protocol;
	
	private Map<String, Object> params=new HashMap<String, Object>();

	private String parser;
	
	public JObjectTransModelProtocol getProtocol() {
		return protocol;
	}

	public void setProtocol(JObjectTransModelProtocol protocol) {
		this.protocol = protocol;
	}

	public Map<String, Object> getParams() {
		return params;
	}

	public void setParams(Map<String, Object> params) {
		this.params = params;
	}

	public String getParser() {
		return parser;
	}

	public void setParser(String parser) {
		this.parser = parser;
	} 
	
}
