package j.jave.kernal.streaming.kafka;

import java.io.Serializable;
import java.util.Properties;
import java.util.concurrent.Future;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

@SuppressWarnings({ "serial", "rawtypes" })
public class JProducerConnecter implements Serializable {

	private JKafkaProducerConfig kafkaProducerConfig;

	public JProducerConnecter(JKafkaProducerConfig kafkaProducerConfig) {
		this.kafkaProducerConfig = kafkaProducerConfig;
	}

	@SuppressWarnings("unchecked")
	public <K, V> ProducerExecutor<K, V> connect() {
		validate();
		return new ProducerExecutor<K, V>() {
			@Override
			protected JKafkaProducerConfig kafkaProducerConfigProvide() {
				return kafkaProducerConfig;
			}
		}.connecting();
	}

	private void validate() {

	}

	public abstract class ProducerExecutor<K, V> implements Serializable {

		private Producer<K, V> producer;

		protected abstract JKafkaProducerConfig kafkaProducerConfigProvide();

		ProducerExecutor connecting() {
			JKafkaProducerConfig kafkaProducerConfig = kafkaProducerConfigProvide();
			Properties props = new Properties();
			props.put("bootstrap.servers", kafkaProducerConfig.getBootstrapServers());
			props.put("acks", kafkaProducerConfig.getAcks());
			props.put("retries", kafkaProducerConfig.getRetries());
			props.put("batch.size", kafkaProducerConfig.getBatchSize());
			props.put("linger.ms", kafkaProducerConfig.getLingerMs());
			props.put("buffer.memory", kafkaProducerConfig.getBufferMemory());
			props.put("key.serializer", kafkaProducerConfig.getKeySerializer());
			props.put("value.serializer", kafkaProducerConfig.getValueSerializer());
			producer = new KafkaProducer<>(props);
			return this;
		}

		public Future<RecordMetadata> send(String topic, K key, V value) {
			ProducerRecord<K, V> record = new ProducerRecord<>(topic, key, value);
			return producer.send(record);
		}

		public Future<RecordMetadata> send(String topic, Integer partition, K key, V value) {
			ProducerRecord<K, V> record = new ProducerRecord<>(topic, partition, key, value);
			return producer.send(record);
		}

		/**
		 * for testing case
		 * 
		 * @return
		 */
		public Producer<K, V> backend() {
			return producer;
		}

	}

}
