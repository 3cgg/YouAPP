package j.jave.kernal.streaming.storm.examples.loganalysis;

import java.util.Random;

import org.apache.storm.Config;
import org.apache.storm.utils.Utils;

import j.jave.kernal.streaming.ConfigNames;
import j.jave.kernal.streaming.kafka.KafkaConsumerConfig;
import j.jave.kernal.streaming.kafka.KafkaNameKeys;
import j.jave.kernal.streaming.storm.StormConfigNames;
import me.bunny.kernel.JConfiguration;
import me.bunny.kernel._c.support.parser.JParser;
import me.bunny.kernel._c.utils.JStringUtils;

public class ConfigParser implements JParser {

	public Config parse(String[] args){
		Config conf=new Config();
		conf.setMessageTimeoutSecs(600);
		conf.setDebug(true);
		conf.put(Config.TOPOLOGY_EVENTLOGGER_EXECUTORS, 1);
		
		conf.putAll(KafkaConsumerConfig.def());
		JConfiguration configuration=JConfiguration.newInstance();
		if(JStringUtils.isNullOrEmpty(KafkaNameKeys.getKafkaServer(conf))){
			String server=configuration.getString(ConfigNames.STREAMING_KAFKA_SERVER);
			if(JStringUtils.isNullOrEmpty(server)){
				throw new RuntimeException("kafka server host is missing.");
			}
			KafkaNameKeys.setKafkaServer(conf, server);
		}
		conf.put(StormConfigNames.STORM_TOPOLOGY_NAME, 
				"test"+new Random().nextInt(10000));
		conf.put(StormConfigNames.KAFKA_TOPIC_NAME, "streaming-logger");
		conf.put(StormConfigNames.KAFKA_TOPIC_PARTITIONS, "0");
		if(!Utils.isValidConf(conf)) {
            throw new IllegalArgumentException("--Storm conf is not valid. Must be json-serializable");
        }
		return conf;
	}
}
