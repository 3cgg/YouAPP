package j.jave.kernal.jave.logging;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JSimplePipelineLogger implements JLogger {

	private List<JLogger> loggers=new ArrayList<>();
	
	private boolean debugEnabled;
	
	private boolean infoEnabled;
	
	private boolean traceEnabled;
	
	private boolean errorEnabled;
	
	private boolean fatalEnabled;
	
	private boolean warnEnabled;
	
	public JSimplePipelineLogger(String name) {
		List<JLogger> loggers=init(name);
		prepare(loggers);
	}
	
	public JSimplePipelineLogger(Class<?> clazz) {
		this.loggers=init(clazz);
		prepare(loggers);
	}

	private void prepare(List<JLogger> loggers) {
		loggers.forEach(logger->{
			if(!debugEnabled){
				debugEnabled=logger.isDebugEnabled();
			}
			if(!infoEnabled){
				infoEnabled=logger.isInfoEnabled();
			}
			if(!warnEnabled){
				warnEnabled=logger.isWarnEnabled();
			}
			if(!errorEnabled){
				errorEnabled=logger.isErrorEnabled();
			}
			if(!traceEnabled){
				traceEnabled=logger.isTraceEnabled();
			}
			if(!fatalEnabled){
				fatalEnabled=logger.isFatalEnabled();
			}
		});
	}
	
	
	
	protected List<JLogger> init(String name){
		JLogger slf4j=new JSLF4JLogWrapper(name);
		return Arrays.asList(slf4j);
	}
	
	protected List<JLogger> init(Class<?> clazz){
		JLogger slf4j=new JSLF4JLogWrapper(clazz);
		return Arrays.asList(slf4j);
	}
	
	
	@Override
	public boolean isDebugEnabled() {
		return debugEnabled;
	}

	@Override
	public boolean isErrorEnabled() {
		return errorEnabled;
	}

	@Override
	public boolean isFatalEnabled() {
		return fatalEnabled;
	}

	@Override
	public boolean isInfoEnabled() {
		return infoEnabled;
	}

	@Override
	public boolean isTraceEnabled() {
		return traceEnabled;
	}

	@Override
	public boolean isWarnEnabled() {
		return warnEnabled;
	}

	@Override
	public void trace(Object message) {
		loggers.forEach(logger->{
			logger.trace(message);
		});
	}

	@Override
	public void trace(Object message, Throwable t) {
		loggers.forEach(logger->{
			logger.trace(message, t);
		});
	}

	@Override
	public void debug(Object message) {
		loggers.forEach(logger->{
			logger.debug(message);
		});
	}

	@Override
	public void debug(Object message, Throwable t) {
		loggers.forEach(logger->{
			logger.debug(message, t);
		});
	}

	@Override
	public void info(Object message) {
		loggers.forEach(logger->{
			logger.info(message);
		});
	}

	@Override
	public void info(Object message, Throwable t) {
		loggers.forEach(logger->{
			logger.info(message, t);
		});
	}

	@Override
	public void warn(Object message) {
		loggers.forEach(logger->{
			logger.warn(message);
		});
	}

	@Override
	public void warn(Object message, Throwable t) {
		loggers.forEach(logger->{
			logger.warn(message, t);
		});
	}

	@Override
	public void error(Object message) {
		loggers.forEach(logger->{
			logger.error(message);
		});
	}

	@Override
	public void error(Object message, Throwable t) {
		loggers.forEach(logger->{
			logger.error(message, t);
		});
	}

	@Override
	public void fatal(Object message) {
		loggers.forEach(logger->{
			logger.fatal(message);
		});
	}

	@Override
	public void fatal(Object message, Throwable t) {
		loggers.forEach(logger->{
			logger.fatal(message, t);
		});
	}

}
