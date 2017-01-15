package j.jave.web.htmlclient.request;

import me.bunny.kernel.jave.json.JJSON;
import me.bunny.kernel.jave.model.JModel;

public class RequestVO implements JModel {
	
	/**
	 * the target URL
	 */
	private String endpoint;
	
	/**
	 * THE METHOD EXECUTING CONTEXT (JSON FORMAT)
	 * @see ServiceContextVO
	 * @see SessionUserVO
	 */
	private String serviceContext;
	
	/**
	 * THR FORM DATA(JSON FORMAT)
	 */
	private String formData;
	
	/**
	 * PAGINATION DATA IF PAGEABLE (JSON FORMAT)
	 * @see SimplePageRequestVO
	 */
	private String paginationData;
	
	/**
	 * TOKEN INFO RELATED TO FORM(JSON FORMAT)
	 * @see FormIdentificationVO 
	 */
	private String token;
	
	
	/**
	 * TICKET INFO.
	 */
	private String ticket;
	
	public RequestVO(){
		
	}

	public RequestVO(String endpoint, String serviceContext, String formData, String paginationData, String token) {
		this.endpoint = endpoint;
		this.serviceContext = serviceContext;
		if(formData!=null&&formData.trim().length()==0){
			this.formData = JJSON.get().formatObject("");
		}
		else{
			this.formData=formData;
		}
		if(paginationData!=null&&paginationData.trim().length()==0){
			this.paginationData = JJSON.get().formatObject("");
		}
		else{
			this.paginationData=paginationData;
		}
		
		if(token!=null&&token.trim().length()==0){
			this.token = JJSON.get().formatObject("");
		}
		else{
			this.token=token;
		}
	}

	public String getServiceContext() {
		return serviceContext;
	}

	public void setServiceContext(String serviceContext) {
		this.serviceContext = serviceContext;
	}

	public String getFormData() {
		return formData;
	}

	public void setFormData(String formData) {
		this.formData = formData;
	}

	public String getPaginationData() {
		return paginationData;
	}

	/**
	 * 
	 * @param paginationData
	 * @see FormIdentificationVO
	 */
	public void setPaginationData(String paginationData) {
		this.paginationData = paginationData;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getEndpoint() {
		return endpoint;
	}

	public void setEndpoint(String endpoint) {
		this.endpoint = endpoint;
	}

	public String getTicket() {
		return ticket;
	}

	public void setTicket(String ticket) {
		this.ticket = ticket;
	}

}
