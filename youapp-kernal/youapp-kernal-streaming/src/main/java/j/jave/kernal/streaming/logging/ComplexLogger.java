package j.jave.kernal.streaming.logging;

import java.util.ArrayList;
import java.util.List;

import j.jave.kernal.jave.logging.JLogger;
import j.jave.kernal.jave.logging.JSimplePipelineLogger;

public class ComplexLogger extends JSimplePipelineLogger {

	public ComplexLogger(String name) {
		super(name);
	}
	
	public ComplexLogger(Class<?> clazz) {
		super(clazz);
	}
	
	
	@Override
	protected List<JLogger> init(String name) {
		List<JLogger>loggers= super.init(name);
		List<JLogger> logs=new ArrayList<>(loggers);
		logs.add(new KafkaLogger(name));
		return logs;
	}
	
	@Override
	protected List<JLogger> init(Class<?> clazz) {
		List<JLogger>loggers= super.init(clazz);
		List<JLogger> logs=new ArrayList<>(loggers);
		logs.add(new KafkaLogger(clazz));
		return logs;
	}
	
	
}
