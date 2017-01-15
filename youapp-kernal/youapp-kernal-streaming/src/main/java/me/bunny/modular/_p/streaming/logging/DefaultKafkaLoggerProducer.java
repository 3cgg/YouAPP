package me.bunny.modular._p.streaming.logging;

import java.util.List;
import java.util.Map;

import me.bunny.kernel.JConfiguration;
import me.bunny.kernel._c.logging.LoggerType;
import me.bunny.kernel._c.utils.JStringUtils;
import me.bunny.modular._p.streaming.ConfigNames;
import me.bunny.modular._p.streaming.Util;
import me.bunny.modular._p.streaming.kafka.KafkaNameKeys;
import me.bunny.modular._p.streaming.kafka.KafkaProducerConfig;
import me.bunny.modular._p.streaming.kafka.ProducerConnector;
import me.bunny.modular._p.streaming.kafka.SimpleProducer;
import me.bunny.modular._p.streaming.kafka.ProducerConnector.ProducerExecutor;

public class DefaultKafkaLoggerProducer implements IKafkaLoggerProducer {

	private final Map conf;
	
	private KafkaLoggerTopicMatch topicMatch;
	
	private final SimpleProducer producer;
	
	public DefaultKafkaLoggerProducer(Map conf) {
		this.conf=conf;
		JConfiguration configuration=JConfiguration.newInstance();
		
		if(JStringUtils.isNullOrEmpty(KafkaNameKeys.getKafkaServer(conf))){
			String server=configuration.getString(ConfigNames.STREAMING_KAFKA_SERVER);
			if(JStringUtils.isNullOrEmpty(server)){
				throw new RuntimeException("kafka server host is missing.");
			}
			KafkaNameKeys.setKafkaServer(conf, server);
		}
		
		topicMatch=new KafkaLoggerTopicMatch("streaming-logger");
		KafkaProducerConfig producerConfig=KafkaProducerConfig.build(this.conf);
		ProducerConnector producerConnecter=new ProducerConnector(producerConfig);
		ProducerExecutor<String,Object> producerExecutor=  producerConnecter.connect();
		producer =new SimpleProducer(producerExecutor);
	}
	
	public DefaultKafkaLoggerProducer() {
		this(def());
	}

	private static Map<String, Object> def() {
		Map<String, Object> def= KafkaProducerConfig.def();
		return def;
	}
	
	@Override
	public void send(KafkaLogger logger,final LoggerType type,Object message) {
		try{
			if(topicMatch.matches(logger)){
				List<String> topics= topicMatch.getTopics();
				for (String topic : topics) {
					producer.send(newLoggerRecord(type,message,null), topic);
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	private Object newLoggerRecord(LoggerType type,Object message,Throwable t) {
//		KafkaLoggerRecord kafkaLoggerRecord= new KafkaLoggerRecord(message,null);
//		Date date=new Date();
////		kafkaLoggerRecord.setRecordTime(date.getTime());
//		kafkaLoggerRecord.setRecordTimeStr(JDateUtils.formatWithMSeconds(date));
//		kafkaLoggerRecord.setHashKey(type.getName());
////		kafkaLoggerRecord.setId(JUniqueUtils.unique());
//		return kafkaLoggerRecord;
		
		return message+(t==null?"":"\r\n"+Util.getMsg(t));
	}
	
	
	@Override
	public void send(KafkaLogger logger,final LoggerType type,Object message, Throwable t) {
		try{
			if(topicMatch.matches(logger)){
				List<String> topics= topicMatch.getTopics();
				for (String topic : topics) {
					producer.send(newLoggerRecord(type,message,t), topic);
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
}
