package j.jave.kernal.streaming.logging;

import j.jave.kernal.jave.logging.LoggerType;

public interface IKafkaLoggerProducer {

	void send(KafkaLogger logger,LoggerType type,Object message);
	
	void send(KafkaLogger logger,LoggerType type,Object message, Throwable t);
	
	
}
