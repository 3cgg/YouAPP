package me.bunny.kernel._c.transaction;

import java.beans.PropertyEditorSupport;

import me.bunny.kernel._c.transaction.interceptor.JNoRollbackRuleAttribute;
import me.bunny.kernel._c.transaction.interceptor.JRollbackRuleAttribute;
import me.bunny.kernel._c.transaction.interceptor.JRuleBasedTransactionAttribute;
import me.bunny.kernel._c.utils.JStringUtils;

/**
 * PropertyEditor for {@link TransactionAttribute} objects. Accepts a String of form
 * <p>{@code PROPAGATION_NAME, ISOLATION_NAME, readOnly, timeout_NNNN,+Exception1,-Exception2}
 * <p>where only propagation code is required. For example:
 * <p>{@code PROPAGATION_MANDATORY, ISOLATION_DEFAULT}
 *
 * <p>The tokens can be in <strong>any</strong> order. Propagation and isolation codes
 * must use the names of the constants in the TransactionDefinition class. Timeout values
 * are in seconds. If no timeout is specified, the transaction manager will apply a default
 * timeout specific to the particular transaction manager.
 *
 * <p>A "+" before an exception name substring indicates that transactions should commit
 * even if this exception is thrown; a "-" that they should roll back.
 *
 * @author Rod Johnson
 * @author Juergen Hoeller
 * @since 24.04.2003
 * @see org.springframework.transaction.TransactionDefinition
 * @see org.springframework.core.Constants
 */
public class JTransactionAttributeEditor extends PropertyEditorSupport {

	/**
	 * Format is PROPAGATION_NAME,ISOLATION_NAME,readOnly,timeout_NNNN,+Exception1,-Exception2.
	 * Null or the empty string means that the method is non transactional.
	 * @see java.beans.PropertyEditor#setAsText(java.lang.String)
	 */
	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		if (JStringUtils.hasLength(text)) {
			// tokenize it with ","
			String[] tokens = JStringUtils.commaDelimitedListToStringArray(text);
			JRuleBasedTransactionAttribute attr = new JRuleBasedTransactionAttribute();
			for (int i = 0; i < tokens.length; i++) {
				// Trim leading and trailing whitespace.
				String token = JStringUtils.trimWhitespace(tokens[i].trim());
				// Check whether token contains illegal whitespace within text.
				if (JStringUtils.containsWhitespace(token)) {
					throw new IllegalArgumentException(
							"Transaction attribute token contains illegal whitespace: [" + token + "]");
				}
				// Check token type.
				if (token.startsWith(JRuleBasedTransactionAttribute.PREFIX_PROPAGATION)) {
					attr.setPropagationBehaviorName(token);
				}
				else if (token.startsWith(JRuleBasedTransactionAttribute.PREFIX_ISOLATION)) {
					attr.setIsolationLevelName(token);
				}
				else if (token.startsWith(JRuleBasedTransactionAttribute.PREFIX_TIMEOUT)) {
					String value = token.substring(JDefaultTransactionAttribute.PREFIX_TIMEOUT.length());
					attr.setTimeout(Integer.parseInt(value));
				}
				else if (token.equals(JRuleBasedTransactionAttribute.READ_ONLY_MARKER)) {
					attr.setReadOnly(true);
				}
				else if (token.startsWith(JRuleBasedTransactionAttribute.PREFIX_COMMIT_RULE)) {
					attr.getRollbackRules().add(new JNoRollbackRuleAttribute(token.substring(1)));
				}
				else if (token.startsWith(JRuleBasedTransactionAttribute.PREFIX_ROLLBACK_RULE)) {
					attr.getRollbackRules().add(new JRollbackRuleAttribute(token.substring(1)));
				}
				else {
					throw new IllegalArgumentException("Invalid transaction attribute token: [" + token + "]");
				}
			}
			setValue(attr);
		}
		else {
			setValue(null);
		}
	}

}
