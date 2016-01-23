package j.jave.framework.commons.mail;

import j.jave.framework.commons.mail.exception.JMailException;
import j.jave.framework.commons.utils.JCollectionUtils;

import java.util.Iterator;
import java.util.Properties;
import java.util.Set;

import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * the default mail servicer, getting mail session from session pool, provide an ability to send mail information.
 * You can custom yourself <strong>mail session cache factory</strong> to replace the default hash table cache. 
 * see <code>JMailSessionCacheFactory</code> for details, and also note the config must be in the 'commons-mail.properties'.
 * @author J
 * @see JMailSessionCacheFactory
 */
public class JDefaultMailService implements JMailService {

	private static JMailSessionPool mailSessionPool=JMailSessionPool.get();
	
	@Override
	public void send(JMailMessage mailMessage) {
		JMailSession mailSession= mailSessionPool.getLatestMailSession();
		send(mailSession, mailMessage);
	}

	@Override
	public void send(JMailSession mailSession, JMailMessage mailMessage) {
		try{
			final MimeMessage message = new MimeMessage(mailSession.getSession());
			//set from
			message.setFrom(new InternetAddress(mailMessage.getSender()));
			
			//set recipients to,cc,bcc
			Set<JRecipient> recipients= mailMessage.getRecipients();
			if(JCollectionUtils.hasInCollect(recipients)){
				for (Iterator<JRecipient> iterator = recipients.iterator(); iterator.hasNext();) {
					JRecipient recipient =  iterator.next();
					recipient.addRecipient(message); 
				}
			}
			//set subject
			message.setSubject(mailMessage.getSubject());
			//set content & type
			message.setContent(mailMessage.getContent(),mailMessage.getContentType());
			//transport message
			Transport.send(message);
		}catch(Exception e){
			throw new JMailException(e);
		}
	}

	@Override
	public void send(JMail mail) {
		JMailAuthenticator mailAuthenticator= mail.getMailAuthenticator();
		Properties properties = System.getProperties();
		// set protocol properties
		mail.getMailProtocol().set(properties);
		JMailSession mailSession=null;
		try {
			mailSession = mailSessionPool.connect(mailAuthenticator, properties, true);
		} catch (Exception e) {
			throw new JMailException(e);
		}
		send(mailSession, mail.getMailMessage());
	}

}
