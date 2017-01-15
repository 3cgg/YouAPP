package j.jave.kernal.streaming.kafka;

import java.io.Serializable;

import j.jave.kernal.streaming.kafka.ProducerConnector.ProducerExecutor;
import me.bunny.kernel.jave.serializer.JSerializerFactory;
import me.bunny.kernel.jave.serializer.SerializerUtils;
import me.bunny.kernel.jave.serializer._JSONSerializerFactoryGetter;

@SuppressWarnings("serial")
public class SimpleProducer implements Serializable {

	private ProducerExecutor<String, Object> executor;
	
	public SimpleProducer(ProducerExecutor<String, Object> executor) {
		this.executor = executor;
	}
	
	public void send(Object object,String topic){
		send(object, topic,_JSONSerializerFactoryGetter.get());
	}
	
	public void send(Object object,String topic,JSerializerFactory serializerFactory){
		send(object, topic, null, serializerFactory);
	}
	
	public void send(Object object,String topic,Integer partition,JSerializerFactory serializerFactory){
		Object val=SerializerUtils.serialize(serializerFactory, object);
		String key=null;
		if(object instanceof KafkaFetchObj){
			key=((KafkaFetchObj) object).hashKey();
		}
		if(partition==null){
			executor.send(topic,key,val);
		}
		else{
			executor.send(topic, partition, key, val);
		}
	}
	
	public void send(Object object,String topic,Integer partition){
		send(object, topic, partition, _JSONSerializerFactoryGetter.get());
	}
	
}
