package j.jave.platform.webcomp.web.form;

import j.jave.kernal.jave.service.JService;
import j.jave.kernal.jave.support.validate.JValidatingException;

public interface VoidDuplicateSubmitService extends JService {
	
	/**
	 * check if the form submit if valid.
	 * @param formId
	 * @param token
	 * @return
	 * @throws JValidatingException
	 */
	boolean validate(String formId,String token);
	
	/**
	 * same as {@link #validate(String, String)}
	 * @param formIdentification
	 * @return 
	 * @throws NullPointerException
	 * @throws JValidatingException
	 */
	boolean validate(FormIdentification formIdentification);
	
	/**
	 * cleanup all related form-tokens of any session context.
	 * @param sessionId
	 * @return
	 */
	boolean cleanup(String sessionId);

	/**
	 * get new form-token.
	 * @return
	 */
	FormIdentification newFormIdentification();
	
}

