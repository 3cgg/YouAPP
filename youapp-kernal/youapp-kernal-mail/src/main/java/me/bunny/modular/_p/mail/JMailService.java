package me.bunny.modular._p.mail;

import me.bunny.kernel._c.service.JService;

/**
 * mail service
 * @author J
 *
 */
public interface JMailService extends JService {
	
	/**
	 * always get the latest, may or may not be random , mail session in the pool to send the message
	 * @param mailMessage
	 */
	public void send(JMailMessage mailMessage);
	
	/**
	 * use the specified mail session to send message
	 * @param mailSession
	 * @param mailMessage
	 */
	public void send(JMailSession mailSession,JMailMessage mailMessage); 
	
	/**
	 * always create a new session to send message, 
	 * unless the session had been initialized and cached in the pool  
	 * @param mail
	 */
	public void send(JMail mail);
	
}
