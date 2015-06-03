package j.jave.framework.extension.logger;

public interface JILoggerFactory {
	JLogger getLogger(String name);
	
	JLogger getLogger(Class<?> clazz);
}
