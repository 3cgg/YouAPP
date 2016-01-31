/**
 * 
 */
package j.jave.framework.components.web.youappmvc.subhub.resourcecached;


/**
 * system resource interface , to used in request response cached. 
 * @author J
 */
public interface ResourceCached {
	
	void setUrl(String url);
	
	String getUrl();
	
	void setCached(String cached);
	
	String getCached();
	
}
