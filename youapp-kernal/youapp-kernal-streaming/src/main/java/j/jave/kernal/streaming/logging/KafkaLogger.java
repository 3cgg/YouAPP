package j.jave.kernal.streaming.logging;

import j.jave.kernal.jave.logging.JLogger;
import j.jave.kernal.jave.logging.LoggerType;

public class KafkaLogger implements JLogger {

	private final String prefix;
	
	private IKafkaLoggerProducer loggerProducer=new DefaultKafkaLoggerProducer();
	
	public KafkaLogger(String name) {
		this.prefix=name;
	}
	
	public KafkaLogger(Class<?> clazz) {
		this.prefix=clazz.getName();
	}
	
	@Override
	public boolean isDebugEnabled() {
		return true;
	}

	@Override
	public boolean isErrorEnabled() {
		return  true;
	}

	@Override
	public boolean isFatalEnabled() {
		return  true;
	}

	@Override
	public boolean isInfoEnabled() {
		return  true;
	}

	@Override
	public boolean isTraceEnabled() {
		return  true;
	}

	@Override
	public boolean isWarnEnabled() {
		return  true;
	}

	@Override
	public void trace(Object message) {
		loggerProducer.send(this, LoggerType.TRACE, getMessage(LoggerType.TRACE, message));
	}

	@Override
	public void trace(Object message, Throwable t) {
		loggerProducer.send(this, LoggerType.TRACE, getMessage(LoggerType.TRACE, message), t);
	}

	@Override
	public void debug(Object message) {
		loggerProducer.send(this,LoggerType.DEBUG ,getMessage(LoggerType.DEBUG, message));
	}

	@Override
	public void debug(Object message, Throwable t) {
		loggerProducer.send(this, LoggerType.DEBUG,getMessage(LoggerType.DEBUG, message), t);
	}

	@Override
	public void info(Object message) {
		loggerProducer.send(this,LoggerType.INFO, getMessage(LoggerType.INFO, message));
	}

	@Override
	public void info(Object message, Throwable t) {
		loggerProducer.send(this,LoggerType.INFO, getMessage(LoggerType.INFO, message), t);
	}

	@Override
	public void warn(Object message) {
		loggerProducer.send(this, LoggerType.WARN,getMessage(LoggerType.WARN, message));
	}

	@Override
	public void warn(Object message, Throwable t) {
		loggerProducer.send(this,LoggerType.WARN, getMessage(LoggerType.WARN, message), t);
	}

	@Override
	public void error(Object message) {
		loggerProducer.send(this, LoggerType.ERROR,getMessage(LoggerType.ERROR, message));
	}

	@Override
	public void error(Object message, Throwable t) {
		loggerProducer.send(this, LoggerType.ERROR,getMessage(LoggerType.ERROR, message), t);
	}

	@Override
	public void fatal(Object message) {
		loggerProducer.send(this, LoggerType.FATAL,getMessage(LoggerType.FATAL, message));
	}

	@Override
	public void fatal(Object message, Throwable t) {
		loggerProducer.send(this, LoggerType.FATAL,getMessage(LoggerType.FATAL, message), t);
	}

	private String getMessage(LoggerType loggerType,Object message){
		String prefix=this.prefix;
		int index=-1;
		if((index=prefix.lastIndexOf("."))!=-1){
			prefix=prefix.substring(index);
		}
		return loggerType.getName()+" "+prefix+" "+message;
	}
	
}
