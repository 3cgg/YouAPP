package j.jave.framework.android.logging;

import j.jave.framework.exception.JOperationNotSupportedException;
import j.jave.framework.extension.logger.JILoggerFactory;
import j.jave.framework.extension.logger.JLogger;
import android.util.Log;

public class JAndroidLoggerFactory implements JILoggerFactory {

	static class JAndroidLoggerWrapper implements JLogger {
		
		private String tag;
		
		public JAndroidLoggerWrapper(String tag) {
			this.tag=tag;
		}
		
		@Override
		public boolean isDebugEnabled() {
			throw new JOperationNotSupportedException("feature not supported.");
		}

		@Override
		public boolean isErrorEnabled() {
			throw new JOperationNotSupportedException("feature not supported.");
		}

		@Override
		public boolean isFatalEnabled() {
			throw new JOperationNotSupportedException("feature not supported.");
		}

		@Override
		public boolean isInfoEnabled() {
			throw new JOperationNotSupportedException("feature not supported.");
		}

		@Override
		public boolean isTraceEnabled() {
			throw new JOperationNotSupportedException("feature not supported.");
		}

		@Override
		public boolean isWarnEnabled() {
			throw new JOperationNotSupportedException("feature not supported.");
		}

		@Override
		public void trace(Object message) {
			Log.v(tag, String.valueOf(message));
		}

		@Override
		public void trace(Object message, Throwable t) {
			Log.v(tag, String.valueOf(message), t);
		}

		@Override
		public void debug(Object message) {
			Log.d(tag, String.valueOf(message));
		}

		@Override
		public void debug(Object message, Throwable t) {
			Log.d(tag, String.valueOf(message), t);
		}

		@Override
		public void info(Object message) {
			Log.i(tag, String.valueOf(message));
		}

		@Override
		public void info(Object message, Throwable t) {
			Log.i(tag, String.valueOf(message), t);
		}

		@Override
		public void warn(Object message) {
			Log.w(tag, String.valueOf(message));
		}

		@Override
		public void warn(Object message, Throwable t) {
			Log.w(tag, String.valueOf(message), t);
		}

		@Override
		public void error(Object message) {
			Log.e(tag, String.valueOf(message));
		}

		@Override
		public void error(Object message, Throwable t) {
			Log.e(tag, String.valueOf(message), t);
		}

		@Override
		public void fatal(Object message) {
			Log.e(tag, String.valueOf(message));
		}

		@Override
		public void fatal(Object message, Throwable t) {
			Log.e(tag, String.valueOf(message), t);
		}
		
	}
	
	@Override
	public JLogger getLogger(String name) {
		return new JAndroidLoggerWrapper(name);
	}

	@Override
	public JLogger getLogger(Class<?> clazz) {
		return new JAndroidLoggerWrapper(clazz.getName());
	}

}
