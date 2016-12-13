package j.jave.kernal.streaming.kafka;

import java.io.Serializable;

import j.jave.kernal.jave.serializer.JSerializerFactory;
import j.jave.kernal.jave.serializer.SerializerUtils;
import j.jave.kernal.jave.serializer._JSONSerializerFactoryGetter;
import j.jave.kernal.streaming.kafka.ProducerConnector.ProducerExecutor;

@SuppressWarnings("serial")
public class SimpleProducer implements Serializable {

	private ProducerExecutor<String, Object> executor;
	
	public SimpleProducer(ProducerExecutor<String, Object> executor) {
		this.executor = executor;
	}
	
	public void send(FetchObj fetchObj,String topic){
		send(fetchObj, topic,_JSONSerializerFactoryGetter.get());
	}
	
	public void send(FetchObj fetchObj,String topic,JSerializerFactory serializerFactory){
		send(fetchObj, topic, null, serializerFactory);
	}
	
	public void send(FetchObj fetchObj,String topic,Integer partition,JSerializerFactory serializerFactory){
		Object val=SerializerUtils.serialize(serializerFactory, fetchObj);
		if(partition==null){
			executor.send(topic,fetchObj.hashKey(),val);
		}
		else{
			executor.send(topic, partition, fetchObj.hashKey(), val);
		}
	}
	
	public void send(FetchObj fetchObj,String topic,Integer partition){
		send(fetchObj, topic, partition, _JSONSerializerFactoryGetter.get());
	}
	
}
