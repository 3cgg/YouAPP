package j.jave.framework.inner.support.rs.security;

import j.jave.framework.commons.security.exception.JSecurityException;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;


@Path(value="/security")
@Produces
public interface JSecurityService {
	
	/**
	 * encrypt the value using DESede-algorithm
	 * @param plain
	 * @return
	 */
	@GET
	@Path(value=JSecurityServiceEndpoint.encryptOnDESede)
	@Produces(value="application/json")
	public String encryptOnDESede(@PathParam(value="plain")String plain) throws JSecurityException;
	
	/**
	 * decrypt the encrypted value using DESede-algorithm
	 */
	@GET
	@Path(value=JSecurityServiceEndpoint.decryptOnDESede)
	@Produces(value="application/json")
	public String decryptOnDESede(@PathParam(value="encrypted")String encrypted) throws JSecurityException;
	
	/**
	 * encrypt the value using MD5-algorithm
	 * @param plain
	 * @return
	 */
	@POST
	@Path(value=JSecurityServiceEndpoint.encryptOnMD5)
	@Produces(value="application/json")
	public String encryptOnMD5(String plain) throws JSecurityException;
	
	
	
	public static interface JSecurityServiceEndpoint{
		
		/**
		 * the value should be the same as the {@link Path}
		 */
		public static final String prefix="/security";
		
		public static final String encryptOnDESede="encryptOnDESede/plainValue/{plain}";
		
		public static final String decryptOnDESede="decryptOnDESede/encryptedValue/{encrypted}";

		public static final String encryptOnMD5="encryptOnMD5/plainValue";
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
