package j.jave.kernal.streaming.kafka;

import java.io.Serializable;

import j.jave.kernal.jave.json.JJSON;
import j.jave.kernal.streaming.kafka.ProducerConnector.ProducerExecutor;

@SuppressWarnings("serial")
public class SimpleProducer implements Serializable {

	private ProducerExecutor<String, String> executor;
	
	public SimpleProducer(ProducerExecutor<String, String> executor) {
		this.executor = executor;
	}
	
	public void send(FetchObj fetchObj,String topic){
		send(fetchObj, topic, null);
	}
			
	public void send(FetchObj fetchObj,String topic,Integer partition){
		String val=JJSON.get().formatObject(fetchObj);
		if(partition==null){
			executor.send(topic,fetchObj.hashKey(),val);
		}
		else{
			executor.send(topic, partition, fetchObj.hashKey(), val);
		}
	}
	
}
