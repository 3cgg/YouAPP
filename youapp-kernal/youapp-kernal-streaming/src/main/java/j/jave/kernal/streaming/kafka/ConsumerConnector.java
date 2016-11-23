package j.jave.kernal.streaming.kafka;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.PartitionInfo;
import org.apache.kafka.common.TopicPartition;

@SuppressWarnings({"serial","rawtypes"})
public class ConsumerConnector implements Serializable {

	private KafkaConsumerConfig kafkaConsumerConfig;
	
	public class ManualPatitionAssignConsumer{
		/**
		 *  topic:0,topic:1,topic:2,...
		 */
		private List<TopicPartition> topicPartitions=new ArrayList<>();
		
		public ManualPatitionAssignConsumer addPartition(String topic,int partition){
			TopicPartition topicPartition=new TopicPartition(topic, partition);
			topicPartitions.add(topicPartition);
			return this;
		}
		
		public <K,V>ConsumerExecutor<K, V> connect(){
			ConsumerExecutor<K, V> consumerExecutor=new ConsumerExecutor<K, V>() {
				@Override
				protected KafkaConsumerConfig kafkaConsumerConfigProvied() {
					return getKafkaConsumerConfig();
				}
			};
			consumerExecutor.connecting();
			consumerExecutor.manualPartitionAssignment(topicPartitions);
			return consumerExecutor;
			
		}
		
	}
	
	public class RandomTopicConsumer{
		/**
		 *  topic1,topic2,topic3,...
		 */
		private List<String> topics=new ArrayList<>();
		
		public RandomTopicConsumer addTopic(String topic){
			topics.add(topic);
			return this;
		}
		
		public <K,V>ConsumerExecutor<K, V> connect(){
			ConsumerExecutor<K, V> consumerExecutor=new ConsumerExecutor<K, V>() {
				@Override
				protected KafkaConsumerConfig kafkaConsumerConfigProvied() {
					return getKafkaConsumerConfig();
				}
			};
			consumerExecutor.connecting();
			consumerExecutor.randomSubscriber(topics);
			return consumerExecutor;
			
		}
		
	}

	public ConsumerConnector(KafkaConsumerConfig kafkaConsumerConfig) {
		this.kafkaConsumerConfig =kafkaConsumerConfig;
	}
	
	/**
	 *  topic:0,topic:1,topic:2,...
	 */
	public ManualPatitionAssignConsumer manualPartitionAssign(){
		ManualPatitionAssignConsumer manual=new ManualPatitionAssignConsumer();
		return manual;
	}
	
	public RandomTopicConsumer randomTopic(){
		RandomTopicConsumer random=new RandomTopicConsumer();
		return random;
	}
	
	public abstract class ConsumerExecutor<K,V> implements Serializable{
		
		private KafkaConsumer<K, V> consumer;
		
		protected abstract KafkaConsumerConfig kafkaConsumerConfigProvied();
		
		private ConsumerExecutor connecting(){

			KafkaConsumerConfig kafkaConsumerConfig = kafkaConsumerConfigProvied();
			Properties props = new Properties();
			props.put("bootstrap.servers", kafkaConsumerConfig.getBootstrapServers());
			props.put("group.id", kafkaConsumerConfig.getGroupId());
			props.put("enable.auto.commit", "false");
			props.put("auto.commit.interval.ms", "1000");
			props.put("session.timeout.ms", kafkaConsumerConfig.getSessionTimeoutMs());
			props.put("request.timeout.ms", kafkaConsumerConfig.getRequestTimeoutMs());
			props.put("key.deserializer", kafkaConsumerConfig.getKeyDeserializer());
			props.put("value.deserializer", kafkaConsumerConfig.getValueDeserializer());
			consumer = new KafkaConsumer<>(props);
			return this;
		}
		
		private ConsumerExecutor manualPartitionAssignment(List<TopicPartition> topicPartitions){
			consumer.assign(topicPartitions);
			return this;
		}
		
		private ConsumerExecutor randomSubscriber(Collection<String> topics){
			consumer.subscribe(topics);
			return this;
		}
		
		public ConsumerRecords<K,V> poll(int timeout){
			ConsumerRecords<K, V> records = consumer.poll(100);
			return records;
		}
		
		Consumer<K, V> backend(){
			return consumer;
		}
		
		public long position(String topic, int partition){
			return consumer.position(new TopicPartition(topic, partition));
		}
		
		public void seek(String topic, int partition,long offset){
			consumer.seek(new TopicPartition(topic, partition), offset);
		}
		
		public void commitSync(String topic, int partition,long offset){
			TopicPartition topicPartition=new TopicPartition(topic, partition);
			OffsetAndMetadata offsetAndMetadata=new OffsetAndMetadata(offset,
					new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
			Map<TopicPartition, OffsetAndMetadata> map=new HashMap<>();
			map.put(topicPartition, offsetAndMetadata);
			consumer.commitSync(map);
		}
		
		public void commitSync(){
			consumer.commitSync();
		}
		
		
		public List<PartitionInfo> partitionsFor(String topic){
			return consumer.partitionsFor(topic);
		}
	}
	
	public KafkaConsumerConfig getKafkaConsumerConfig() {
		return kafkaConsumerConfig;
	}
	
	
}
