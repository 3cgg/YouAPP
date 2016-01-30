/**
 * 
 */
package j.jave.framework.components.web.multi.platform.support;

/**
 * @author J
 */
public enum Platform {
	
	PC ,ANDROID,IOS ;
	
	public static Platform parse(String platform){
		if("PC".equals(platform)){
			return PC;
		}
		else if("ANDROID".equals(platform)){
			return ANDROID;
		}
		else if("IOS".equals(platform)){
			return IOS;
		}
		return null;
	}
	
	
}
