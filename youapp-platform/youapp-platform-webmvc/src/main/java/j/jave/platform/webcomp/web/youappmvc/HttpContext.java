package j.jave.platform.webcomp.web.youappmvc;

import j.jave.kernal.dataexchange.impl.interimpl.JObjectTransModel;
import j.jave.kernal.dataexchange.impl.interimpl.JObjectTransModelProtocol;
import j.jave.platform.webcomp.core.service.ServiceContext;

import java.net.URI;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

public interface HttpContext {
	
	URI getUrl();
	
	/**
	 * @return
	 */
	String getRequestPath();

	public abstract String getParameter(String key);

	public abstract String getHead(String headName);

	public abstract String[] getParameterValues(String key);

	public abstract Object getParameterValue(String key);

	/**
	 * {@link #parameters}, which store query string parameter or any other added by program later.
	 * @return the parameters
	 */
	public abstract Map<String, Object> getParameters();
	
	Collection<String> getKeys();

	public abstract HttpClientInfo getClientInfo();

	public abstract ServiceContext getServiceContext();

	VerMappingMeta getVerMappingMeta();
	
	void setVerMappingMeta(VerMappingMeta verMappingMeta);
	
	JObjectTransModelProtocol getProtocol();
	
	void setProtocol(JObjectTransModelProtocol protocol);
	
	JObjectTransModel getObjectTransModel();
	
	JObjectTransModel setObjectTransModel(JObjectTransModel objectTransModel);
	
}