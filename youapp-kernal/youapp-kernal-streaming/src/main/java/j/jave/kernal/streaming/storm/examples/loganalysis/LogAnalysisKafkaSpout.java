package j.jave.kernal.streaming.storm.examples.loganalysis;

import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.tuple.Values;

import j.jave.kernal.jave.serializer.JSerializerFactory;
import j.jave.kernal.jave.serializer.SerializerUtils;
import j.jave.kernal.jave.serializer._JSONSerializerFactoryGetter;
import j.jave.kernal.streaming.storm.BaseSimpleKafkaSpout;

public class LogAnalysisKafkaSpout extends BaseSimpleKafkaSpout {
	
	private JSerializerFactory serializerFactory;

	private AtomicLong atomicLong;
	
	@Override
	public void open(Map conf, TopologyContext context, SpoutOutputCollector collector) {
		super.open(conf, context, collector);
		serializerFactory=_JSONSerializerFactoryGetter.get();
		atomicLong=new AtomicLong(0);
	}
	
	@Override
	protected void doNextTuple() throws Exception {
		ConsumerRecords<String, Object> consumerRecords= consumer().poll(0);
		for (ConsumerRecord<String, Object> consumerRecord : consumerRecords) {
			String record=SerializerUtils.deserialize(serializerFactory,
					(byte[])consumerRecord.value(), String.class);
			collector.emit(new Values(record));
			collector.emit("log-stream",new Values(atomicLong.incrementAndGet()));
		}
	}
	
	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		super.declareOutputFields(declarer);
	}

	
}
