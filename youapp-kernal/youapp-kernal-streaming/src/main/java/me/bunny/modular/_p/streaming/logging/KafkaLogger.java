package me.bunny.modular._p.streaming.logging;

import java.util.Date;

import me.bunny.kernel._c.logging.JLogger;
import me.bunny.kernel._c.logging.LoggerType;
import me.bunny.kernel._c.utils.JDateUtils;

public class KafkaLogger implements JLogger {

	private final String prefix;
	
	private static IKafkaLoggerProducer loggerProducer=new DefaultKafkaLoggerProducer();
	
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
		if(isTraceEnabled())
		loggerProducer.send(this, LoggerType.TRACE, getMessage(LoggerType.TRACE, message));
	}

	@Override
	public void trace(Object message, Throwable t) {
		if(isTraceEnabled())
		loggerProducer.send(this, LoggerType.TRACE, getMessage(LoggerType.TRACE, message), t);
	}

	@Override
	public void debug(Object message) {
		if(isDebugEnabled())
		loggerProducer.send(this,LoggerType.DEBUG ,getMessage(LoggerType.DEBUG, message));
	}

	@Override
	public void debug(Object message, Throwable t) {
		if(isDebugEnabled())
		loggerProducer.send(this, LoggerType.DEBUG,getMessage(LoggerType.DEBUG, message), t);
	}

	@Override
	public void info(Object message) {
		if(isInfoEnabled())
		loggerProducer.send(this,LoggerType.INFO, getMessage(LoggerType.INFO, message));
	}

	@Override
	public void info(Object message, Throwable t) {
		if(isInfoEnabled())
		loggerProducer.send(this,LoggerType.INFO, getMessage(LoggerType.INFO, message), t);
	}

	@Override
	public void warn(Object message) {
		if(isWarnEnabled())
		loggerProducer.send(this, LoggerType.WARN,getMessage(LoggerType.WARN, message));
	}

	@Override
	public void warn(Object message, Throwable t) {
		if(isWarnEnabled())
		loggerProducer.send(this,LoggerType.WARN, getMessage(LoggerType.WARN, message), t);
	}

	@Override
	public void error(Object message) {
		if(isErrorEnabled())
		loggerProducer.send(this, LoggerType.ERROR,getMessage(LoggerType.ERROR, message));
	}

	@Override
	public void error(Object message, Throwable t) {
		if(isErrorEnabled())
		loggerProducer.send(this, LoggerType.ERROR,getMessage(LoggerType.ERROR, message), t);
	}

	@Override
	public void fatal(Object message) {
		if(isFatalEnabled())
		loggerProducer.send(this, LoggerType.FATAL,getMessage(LoggerType.FATAL, message));
	}

	@Override
	public void fatal(Object message, Throwable t) {
		if(isFatalEnabled())
		loggerProducer.send(this, LoggerType.FATAL,getMessage(LoggerType.FATAL, message), t);
	}

	private String getMessage(LoggerType loggerType,Object message){
		String prefix=this.prefix;
		int index=-1;
		if((index=prefix.lastIndexOf("."))!=-1){
			prefix=prefix.substring(index);
		}
		return 
				JDateUtils.formatWithMSeconds(new Date())
				+" "+loggerType.getName()
				+" "+prefix
				+" "+message;
	}
	
}
