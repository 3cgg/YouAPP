package j.jave.platform.webcomp.web.youappmvc;

import j.jave.platform.webcomp.core.service.DefaultServiceContext;
import j.jave.platform.webcomp.core.service.ServiceContext;
import j.jave.platform.webcomp.core.service.SessionUser;
import j.jave.platform.webcomp.web.util.JCookieUtils;
import j.jave.platform.webcomp.web.youappmvc.controller.ControllerExecutor;
import j.jave.platform.webcomp.web.youappmvc.support.LinkedRequestSupport;
import j.jave.platform.webcomp.web.youappmvc.utils.YouAppMvcUtils;
import me.bunny.kernel.dataexchange.impl.JDefaultMessageMetaReceiverBuilder;
import me.bunny.kernel.dataexchange.impl.JMessageHeadNames;
import me.bunny.kernel.dataexchange.impl.interimpl.JObjectTransModel;
import me.bunny.kernel.dataexchange.impl.interimpl.JObjectTransModelDecoder;
import me.bunny.kernel.dataexchange.impl.interimpl.JObjectTransModelProtocol;
import me.bunny.kernel.dataexchange.model.MessageMeta;
import me.bunny.kernel.jave.model.JModel;
import me.bunny.kernel.jave.support.databind.JDataBindingException;
import me.bunny.kernel.jave.utils.JCollectionUtils;
import me.bunny.kernel.jave.utils.JIOUtils;
import me.bunny.kernel.jave.utils.JStringUtils;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * HTTP CONTEXT Wrapper , with which ACTION EXECUTOR can perform operation.
 * The class detect if the attribute {@link LinkedRequestSupport#LINKED_REQUEST_PARAM_KEY} indicates linked request is existing , if exists extract them into {@link #parameters}
 * <p><strong>note that properties below is mandatory :</strong>
 * <p> {@link #ticket}
 * <p> {@link #user}
 * <p> {@link #targetPath}
 * @author J
 * @see ControllerExecutor
 */
@SuppressWarnings("serial")
public class ServletHttpContext implements JModel, HttpContext {
	
	/**
	 * ticket to indicate the unique request to apart from other users.
	 * <strong>mandatory</strong>
	 */
	private String ticket;

	/**
	 * user information , generally see {@link SessionUser}
	 * <strong>mandatory</strong>
	 */
	private SessionUser user;
	
	/**
	 * HTTP Servlet Request. 
	 * <strong>optional</strong>
	 */
	private transient  HttpServletRequest request;

	/**
	 * HTTP Servlet Response. 
	 * <strong>optional</strong>
	 */
	private transient HttpServletResponse response;
	
	/**
	 * http client info, such as ip , browser version.
	 */
	private transient HttpClientInfo httpClientInfo;
	
	/**
	 * HTTP Servlet Request Parameter.  may processed after file distribute service. 
	 * <strong>optional</strong>
	 */
	private transient Map<String, Object> parameters = new HashMap<String, Object>();
	
	/**
	 * HTTP Servlet Request Head.  may processed after file distribute service. 
	 * <strong>optional</strong>
	 */
	private transient Map<String, String> heads = new HashMap<String, String>();
	
	/**
	 * the key is used to for whose parameters that are added in request scope by framework later.
	 * {@link HttpServletRequest#setAttribute(String, Object)}
	 * as key {@value #ADDITIONAL_PARAM_KEY}
	 */
	public static final String ADDITIONAL_PARAM_KEY="j.jave.framework.components.web.additional.param";
	
	/**
	 * true if the context is created by the last request of the linked request. 
	 * i.e. {@value #LINKED_REQUEST_PARAM_KEY} exists in the request scope.
	 */
	private transient volatile boolean linked=false; 
	
	private transient JObjectTransModelProtocol protocol;
	
	private transient JObjectTransModel objectTransModel;
	
	private transient VerMappingMeta verMappingMeta;
	
	private transient ServiceContext serviceContext;
	
	public ServletHttpContext(HttpServletRequest request,HttpServletResponse response){
		initHttp(request,response);
	}
	
	public HttpContext initHttpClient(HttpServletRequest request,HttpServletResponse response){
		this.request = request;
		this.response = response;
		this.httpClientInfo=new HttpClientInfo();
		httpClientInfo.setIp(YouAppMvcUtils.getIP(request));
		httpClientInfo.setClient(YouAppMvcUtils.getClient(request));
		return this;
	}
	
	public ServletHttpContext initHttp(HttpServletRequest request,HttpServletResponse response){
		init(request, response);
		return this;
	}
	
	public ServletHttpContext(){
		this(null, null);
	}

	public static final JObjectTransModelDecoder OBJECT_TRANS_MODEL_DECODER =
			new JObjectTransModelDecoder();
	
	private void init(HttpServletRequest request,HttpServletResponse response){
		boolean isParseProtocol=false;
		
		if(parameters==null){
			parameters=new HashMap<String, Object>();
		}
		if(heads==null){
			heads=new HashMap<String, String>();
		}
		
		if(request!=null){
			// parse head parameters
			heads.putAll(YouAppMvcUtils.parseRequstHeads(request));
		}
		
		if(request!=null){
			String protocolHead= request.getHeader(JMessageHeadNames.DATA_EXCHNAGE_IDENTIFIER);
			if(JStringUtils.isNotNullOrEmpty(protocolHead)){
				isParseProtocol=true;
				try{
					MessageMeta messageMeta=JDefaultMessageMetaReceiverBuilder.get(JIOUtils.getBytes(request.getInputStream()))
							.build().receive();
					objectTransModel=OBJECT_TRANS_MODEL_DECODER.encode(messageMeta);
					protocol=objectTransModel.getProtocol();
					Object _ticket=objectTransModel.getParams().get(ViewConstants.TICKET_QUERY_PARAMETER);
					String _ticketStr=null;
					if(_ticket!=null&&JStringUtils.isNotNullOrEmpty(_ticketStr=String.valueOf(_ticket))){
						ticket=_ticketStr;
					}
					
					Map<String, Object> params= objectTransModel.getParams();
					for(Entry<String, Object> entry:params.entrySet()){
						parameters.put(entry.getKey(), entry.getValue());
					}
					
				}catch(Exception e){
					throw new JDataBindingException(e);
				}
			}
			if(isParseProtocol) return ;
		}
		
		
		boolean parse=false;
		// process request with context type : multipart/form-data
		if(request!=null&&YouAppMvcUtils.isFileContextType(request)){
			Map<String, Object> parameterValues=YouAppMvcUtils.doWithRequestParameterWithFileAttached(request);
			this.parameters.putAll(parameterValues);
			parse=true;
		}

		//initialize linked parameters from the request scope.
		// avoid process those requests of FORWARD, INCLUDE ， specially aware of  FORWARD
		if(request!=null&&YouAppMvcUtils.isRequestTypeAndGET(request)&&LinkedRequestSupport.isLinked(request)){
			linked=true;
			Map<String,Object> linkedParameters=(Map<String, Object>) LinkedRequestSupport.getParameters(request);
			if(JCollectionUtils.hasInMap(linkedParameters)){
				for (Iterator<Entry<String, Object>> iterator = linkedParameters.entrySet().iterator(); iterator.hasNext();) {
					Entry<String, Object> entry =  iterator.next();
					if(JStringUtils.isNotNullOrEmpty(entry.getKey())){
						this.parameters.put(entry.getKey(), entry.getValue());
					}
				}
			}
			parse=true;
		}
		
		
		if(request!=null&&!parse){
			this.parameters.putAll(YouAppMvcUtils.parseRequestParameters(request));
			parse=true;
		}
		
		this.request=request;
		this.response=response;
	}
	
	public void setAttribute(String key, Object obj) {
		request.setAttribute(key, obj);
	}
	
	/* (non-Javadoc)
	 * @see j.jave.platform.webcomp.web.youappmvc.HttpContext#getParameter(java.lang.String)
	 */
	@Override
	public String getParameter(String key) {
		if(parameters==null){
			return null;
		}
		String value = (String) parameters.get(key);
		return value;
	}
	
	/* (non-Javadoc)
	 * @see j.jave.platform.webcomp.web.youappmvc.HttpContext#getHead(java.lang.String)
	 */
	@Override
	public String getHead(String headName){
		if(heads==null){
			return null;
		}
		return this.heads.get(headName);
	}
	
	/* (non-Javadoc)
	 * @see j.jave.platform.webcomp.web.youappmvc.HttpContext#getParameterValues(java.lang.String)
	 */
	@Override
	public String[] getParameterValues(String key) {
		if(parameters==null){
			return null;
		}
		String[] value =  (String[]) parameters.get(key);
		return value;
	}
	
	/* (non-Javadoc)
	 * @see j.jave.platform.webcomp.web.youappmvc.HttpContext#getParameterValue(java.lang.String)
	 */
	@Override
	public Object getParameterValue(String key) {
		if(parameters==null){
			return null;
		}
		return parameters.get(key);
	}
	

	public Cookie getCookie(String name) {
		return JCookieUtils.getCookie(request, name);
	}

	public void deleteCookie(Cookie cookie) {
		JCookieUtils.deleteCookie(request, response, cookie);
	}
	
	public void deleteCookie(String name){
		deleteCookie(getCookie(name));
	}
	

	public void setCookie(String name, String value) {
		JCookieUtils.setCookie(request, response, name, value);
	}

	public void setCookie(String name, String value, int maxAge) {
		JCookieUtils.setCookie(request, response, name, value, maxAge);
	}

	/* (non-Javadoc)
	 * @see j.jave.platform.webcomp.web.youappmvc.HttpContext#getParameters()
	 */
	@Override
	public Map<String, Object> getParameters() {
		return parameters;
	}
	
	@Override
	public Collection<String> getKeys() {
		return Collections.unmodifiableCollection(parameters.keySet());
	}
	
	/**
	 * the method is only used to initialize parameters for testing without HTTP context.
	 * @param parameters
	 */
	public void initParametersWithoutHTTP(Map<String, Object> parameters) {
		this.parameters = parameters;
	}
	
	/**
	 * the method is only used to initialize parameters for testing without HTTP context.
	 * @param parameters
	 */
	public void initHeadsWithoutHTTP(Map<String, String> heads) {
		this.heads = heads;
	}
	
	public String getTicket() {
		return ticket;
	}

	public void setTicket(String ticket) {
		this.ticket = ticket;
	}

	/**
	 * {@link ServletHttpContext#user}
	 * @return generally <code>SessionUser</code> returned.
	 */
	public SessionUser getUser() {
		return user;
	}

	public void setUser(SessionUser user) {
		this.user = user;
	}
	
	public String getIP(){
		return httpClientInfo.getIp();
	}
	
	/* (non-Javadoc)
	 * @see j.jave.platform.webcomp.web.youappmvc.HttpContext#getClient()
	 */
	public String getClient(){
		return httpClientInfo.getClient();
	}
	
	/**
	 * see {@link #linked}
	 * @return
	 */
	public boolean isLinked() {
		return linked;
	}
	
	public JObjectTransModelProtocol getProtocol() {
		return protocol;
	}
	
	@Override
	public void setProtocol(JObjectTransModelProtocol objectTransModelProtocol) {
		this.protocol=objectTransModelProtocol;
	}
	
	
	public JObjectTransModel getObjectTransModel() {
		return objectTransModel;
	}
	
	/* (non-Javadoc)
	 * @see j.jave.platform.webcomp.web.youappmvc.HttpContext#getServiceContext()
	 */
	@Override
	public ServiceContext getServiceContext(){
		if(serviceContext==null){
			ServiceContext serviceContext=new ServiceContext();
			serviceContext.setTicket(ticket);
			if(user==null){
				ServiceContext defaultServiceContext=DefaultServiceContext.getDefaultServiceContext();
				serviceContext.setUserId(defaultServiceContext.getUserId());
				serviceContext.setUserName(defaultServiceContext.getUserName());
			}
			else{
				serviceContext.setUserId(user.getUserId());
				serviceContext.setUserName(user.getUserName());
			}
			this.serviceContext=serviceContext;
		}
		return serviceContext;
	}
	
	@Override
	public ServiceContext getServiceContext(boolean refresh) {
		if(refresh){
			serviceContext=null;
		}
		return getServiceContext();
	}
	
	@Override
	public HttpClientInfo getClientInfo() {
		return this.httpClientInfo;
	}
	
	@Override
	public URI getUrl() {
		try {
			return new URI(request.getRequestURL().toString());
		} catch (URISyntaxException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public VerMappingMeta getVerMappingMeta() {
		return this.verMappingMeta;
	}

	@Override
	public void setVerMappingMeta(VerMappingMeta verMappingMeta) {
		this.verMappingMeta=verMappingMeta;
	}
	
	@Override
	public String getRequestPath() {
		return request.getPathInfo();
	}

	@Override
	public JObjectTransModel setObjectTransModel(
			JObjectTransModel objectTransModel) {
		return this.objectTransModel=objectTransModel;
	}
}
