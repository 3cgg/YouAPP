package j.jave.framework.logging;

import j.jave.framework.extension.logger.JLogger;

import org.slf4j.Logger;

public class JSL4JLogWrapper implements JLogger {

	private final Logger LOGGER;

	public JSL4JLogWrapper(Logger LOGGER) {
		this.LOGGER=LOGGER;
	}
	
	@Override
	public boolean isDebugEnabled() {
		return LOGGER.isDebugEnabled();
	}

	@Override
	public boolean isErrorEnabled() {
		return LOGGER.isErrorEnabled();
	}

	@Override
	public boolean isFatalEnabled() {
		return LOGGER.isErrorEnabled();
	}

	@Override
	public boolean isInfoEnabled() {
		return LOGGER.isInfoEnabled();
	}

	@Override
	public boolean isTraceEnabled() {
		return LOGGER.isTraceEnabled();
	}

	@Override
	public boolean isWarnEnabled() {
		return LOGGER.isWarnEnabled();
	}

	@Override
	public void trace(Object message) {
		LOGGER.trace(String.valueOf(message));
	}

	@Override
	public void trace(Object message, Throwable t) {
		LOGGER.trace(String.valueOf(message), t);
	}

	@Override
	public void debug(Object message) {
		LOGGER.debug(String.valueOf(message));
	}

	@Override
	public void debug(Object message, Throwable t) {
		LOGGER.debug(String.valueOf(message), t);
	}

	@Override
	public void info(Object message) {
		LOGGER.info(String.valueOf(message));
	}

	@Override
	public void info(Object message, Throwable t) {
		LOGGER.info(String.valueOf(message), t);
	}

	@Override
	public void warn(Object message) {
		LOGGER.warn(String.valueOf(message));
	}

	@Override
	public void warn(Object message, Throwable t) {
		LOGGER.warn(String.valueOf(message), t);
	}

	@Override
	public void error(Object message) {
		LOGGER.error(String.valueOf(message));
	}

	@Override
	public void error(Object message, Throwable t) {
		LOGGER.error(String.valueOf(message), t);
	}

	@Override
	public void fatal(Object message) {
		LOGGER.error(String.valueOf(message));
	}

	@Override
	public void fatal(Object message, Throwable t) {
		LOGGER.error(String.valueOf(message), t);
	}

}
