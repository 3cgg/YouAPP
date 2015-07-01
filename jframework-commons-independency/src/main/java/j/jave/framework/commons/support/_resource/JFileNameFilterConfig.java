package j.jave.framework.commons.support._resource;


/**
 * file name filter configuration.
 * @author J
 */
public interface JFileNameFilterConfig {

	/**
	 * simple literal file name.
	 * @param fileNames
	 */
	void setIncludeFileName(String... fileNames );
	
	/**
	 * regular expression
	 * @param expression
	 */
	void setIncludeExpression(String... expressions);
	
	/**
	 * regular expression
	 * @param expressions
	 */
	void setExcludeExpression(String... expressions );
	
	/**
	 * simple literal file name.
	 * @param fileNames
	 */
	void setExcludeFileName(String... fileNames );
	
}
