package me.bunny.kernel._c.auth;

import java.security.Principal;
import java.util.Iterator;
import java.util.Set;

import javax.security.auth.Subject;

import me.bunny.kernel._c.utils.JCollectionUtils;
import me.bunny.kernel._c.utils.JLangUtils;

public class JAuthUtils {
	
	/**
	 * check whether the both subjects are the same or not.
	 * @param subject
	 * @param providedSubject
	 * @return
	 */
	public static boolean checkSamePrincipal(Subject subject,Subject providedSubject){
		
		Set<Principal> principals= subject.getPrincipals();
		Set<Principal> providedPrincipals=providedSubject.getPrincipals();
		
		if(!JCollectionUtils.hasInCollect(providedPrincipals))  return false;
		boolean allIncluded=true;
		for (Iterator<Principal> iterator = providedPrincipals.iterator(); iterator
				.hasNext();) {
			Principal providedPrincipal = (Principal) iterator.next();
			if(principals.contains(providedPrincipal)){
				continue;
			}
			else{
				allIncluded=false;
				break;
			}
		}
		return allIncluded;
	}
	
	/**
	 * check whether the provided subject matches the subject of authorized information or  not.
	 * @param subject
	 * @param providedSubject
	 * @return
	 */
//	public static boolean matchesAccess(Subject subject,Subject providedSubject){
//		
//		Set<JCredential> credentials= subject.getPublicCredentials(JCredential.class);
//		Set<JCredential> providedCredentials=providedSubject.getPublicCredentials(JCredential.class);
//		
//		if(!JCollectionUtils.hasInCollect(credentials)) return true;
//		
//		if(!JCollectionUtils.hasInCollect(providedCredentials))  return false;
//		
//		boolean allIncluded=true;
//		for (Iterator<JCredential> iterator = providedCredentials.iterator(); iterator
//				.hasNext();) {
//			JCredential providedCredential = (JCredential) iterator.next();
//			if(credentials.contains(providedCredential)){
//				continue;
//			}
//			else{
//				allIncluded=false;
//				break;
//			}
//		}
//		return allIncluded;
//	}
	
	/**
	 * check if the provided credential matches the original one. 
	 * @param hold
	 * @param provided
	 * @return
	 */
	public static boolean matchesAccess(JCredentials hold,JCredentials provided){
		Principal holdPrincipal =hold.getUserPrincipal();
		Principal proviedPrincipal =provided.getUserPrincipal();
		
		if(JLangUtils.equals(holdPrincipal, proviedPrincipal)){  // the same principal
			String holdPassword=hold.getPassword();
			String providedPassword=provided.getPassword();
			if(JLangUtils.equals(holdPassword, providedPassword)){
				return true;
			}
		}
		return false;
	}
	
	
	
	
	
	
	
}
