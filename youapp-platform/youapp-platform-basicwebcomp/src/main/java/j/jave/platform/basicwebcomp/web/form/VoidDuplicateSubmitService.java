package j.jave.platform.basicwebcomp.web.form;

public interface VoidDuplicateSubmitService {
	
	/**
	 * check if the form submit if valid.
	 * @param formId
	 * @param token
	 * @return
	 */
	boolean validate(String formId,String token);
	
	/**
	 * same as {@link #validate(String, String)}
	 * @param formIdentification
	 * @return
	 * @throws NullPointerException
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

