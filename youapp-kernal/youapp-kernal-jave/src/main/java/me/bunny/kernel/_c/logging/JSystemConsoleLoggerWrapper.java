package me.bunny.kernel._c.logging;


public class JSystemConsoleLoggerWrapper implements JLogger {
	
	private String prefix;
	
	public JSystemConsoleLoggerWrapper(String prefix) {
		this.prefix=prefix;
	}
	
	@Override
	public boolean isDebugEnabled() {
		return true;
	}

	@Override
	public boolean isErrorEnabled() {
		return true;
	}

	@Override
	public boolean isFatalEnabled() {
		return true;
	}

	@Override
	public boolean isInfoEnabled() {
		return true;
	}

	@Override
	public boolean isTraceEnabled() {
		return true;
	}

	@Override
	public boolean isWarnEnabled() {
		return true;
	}

	@Override
	public void trace(Object message) {
		System.out.println(parsePrefix()+parsePrefix()+String.valueOf(message));
	}

	@Override
	public void trace(Object message, Throwable t) {
		System.out.println(parsePrefix()+parsePrefix()+parsePrefix()+String.valueOf(message));
		t.printStackTrace();
	}

	@Override
	public void debug(Object message) {
		System.out.println(parsePrefix()+parsePrefix()+String.valueOf(message));
	}

	@Override
	public void debug(Object message, Throwable t) {
		System.out.println(parsePrefix()+String.valueOf(message));
		t.printStackTrace();
	}

	@Override
	public void info(Object message) {
		System.out.println(parsePrefix()+String.valueOf(message));
	}

	@Override
	public void info(Object message, Throwable t) {
		System.out.println(parsePrefix()+String.valueOf(message));
		t.printStackTrace();
	}

	@Override
	public void warn(Object message) {
		System.out.println(parsePrefix()+String.valueOf(message));
	}

	@Override
	public void warn(Object message, Throwable t) {
		System.out.println(parsePrefix()+String.valueOf(message));
		t.printStackTrace();
	}

	@Override
	public void error(Object message) {
		System.err.println(parsePrefix()+String.valueOf(message));
	}

	@Override
	public void error(Object message, Throwable t) {
		System.err.println(parsePrefix()+parsePrefix()+String.valueOf(message));
		t.printStackTrace();
	}

	@Override
	public void fatal(Object message) {
		System.err.println(parsePrefix()+parsePrefix()+String.valueOf(message));
	}

	@Override
	public void fatal(Object message, Throwable t) {
		System.err.println(parsePrefix()+String.valueOf(message));
		t.printStackTrace();
	}

	private String parsePrefix(){
		return prefix+"----";
	}
	
}
