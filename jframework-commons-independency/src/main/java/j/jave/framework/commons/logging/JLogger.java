package j.jave.framework.commons.logging;



/**
 * the log interface for all platform.
 * see standard log implementations of Apache <code>org.apache.commons.logging.Log</code> for details.
 * @author J
 */
public interface JLogger  {


    // ----------------------------------------------------- Logging Properties

    /**
     * Is debug logging currently enabled?
     * <p>
     * Call this method to prevent having to perform expensive operations
     * (for example, <code>String</code> concatenation)
     * when the log level is more than debug.
     *
     * @return true if debug is enabled in the underlying logger.
     */
    public boolean isDebugEnabled();

    /**
     * Is error logging currently enabled?
     * <p>
     * Call this method to prevent having to perform expensive operations
     * (for example, <code>String</code> concatenation)
     * when the log level is more than error.
     *
     * @return true if error is enabled in the underlying logger.
     */
    public boolean isErrorEnabled();

    /**
     * Is fatal logging currently enabled?
     * <p>
     * Call this method to prevent having to perform expensive operations
     * (for example, <code>String</code> concatenation)
     * when the log level is more than fatal.
     *
     * @return true if fatal is enabled in the underlying logger.
     */
    public boolean isFatalEnabled();

    /**
     * Is info logging currently enabled?
     * <p>
     * Call this method to prevent having to perform expensive operations
     * (for example, <code>String</code> concatenation)
     * when the log level is more than info.
     *
     * @return true if info is enabled in the underlying logger.
     */
    public boolean isInfoEnabled();

    /**
     * Is trace logging currently enabled?
     * <p>
     * Call this method to prevent having to perform expensive operations
     * (for example, <code>String</code> concatenation)
     * when the log level is more than trace.
     *
     * @return true if trace is enabled in the underlying logger.
     */
    public boolean isTraceEnabled();

    /**
     * Is warn logging currently enabled?
     * <p>
     * Call this method to prevent having to perform expensive operations
     * (for example, <code>String</code> concatenation)
     * when the log level is more than warn.
     *
     * @return true if warn is enabled in the underlying logger.
     */
    public boolean isWarnEnabled();

    // -------------------------------------------------------- Logging Methods

    /**
     * Log a message with trace log level.
     *
     * @param message log this message
     */
    public void trace(Object message);

    /**
     * Log an error with trace log level.
     *
     * @param message log this message
     * @param t log this cause
     */
    public void trace(Object message, Throwable t);

    /**
     * Log a message with debug log level.
     *
     * @param message log this message
     */
    public void debug(Object message);

    /**
     * Log an error with debug log level.
     *
     * @param message log this message
     * @param t log this cause
     */
    public void debug(Object message, Throwable t);

    /**
     * Log a message with info log level.
     *
     * @param message log this message
     */
    public void info(Object message);

    /**
     * Log an error with info log level.
     *
     * @param message log this message
     * @param t log this cause
     */
    public void info(Object message, Throwable t);

    /**
     * Log a message with warn log level.
     *
     * @param message log this message
     */
    public void warn(Object message);

    /**
     * Log an error with warn log level.
     *
     * @param message log this message
     * @param t log this cause
     */
    public void warn(Object message, Throwable t);

    /**
     * Log a message with error log level.
     *
     * @param message log this message
     */
    public void error(Object message);

    /**
     * Log an error with error log level.
     *
     * @param message log this message
     * @param t log this cause
     */
    public void error(Object message, Throwable t);

    /**
     * Log a message with fatal log level.
     *
     * @param message log this message
     */
    public void fatal(Object message);

    /**
     * Log an error with fatal log level.
     *
     * @param message log this message
     * @param t log this cause
     */
    public void fatal(Object message, Throwable t);

}
