package j.jave.kernal.streaming.storm;

import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichSpout;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;

import j.jave.kernal.streaming.kafka.ConsumerConnector;
import j.jave.kernal.streaming.kafka.ConsumerConnector.ConsumerExecutor;
import me.bunny.kernel.jave.logging.JLogger;
import me.bunny.kernel.jave.logging.JLoggerFactory;
import j.jave.kernal.streaming.kafka.KafkaConsumerConfig;

public abstract class BaseSimpleKafkaSpout extends BaseRichSpout {

	private transient ConsumerExecutor<String, Object> consumer;
	
	private transient Map conf ; 
	
	private transient TopologyContext context;
	
	protected transient SpoutOutputCollector collector;
	
	private transient Object sync;
	
	private transient JLogger logger;
	
	@Override
	public void open(Map conf, TopologyContext context, SpoutOutputCollector collector) {
		this.conf=conf;
		this.context=context;
		this.collector=collector;
		sync=new Object();
		this.consumer=_consumer(conf);
	}

	protected ConsumerExecutor<String, Object> _consumer(Map conf) {
		KafkaConsumerConfig config=KafkaConsumerConfig.build(conf);
		String[] partitionStrs=((String)conf.get(StormConfigNames.KAFKA_TOPIC_PARTITIONS)).split(",");
		int[] partitions=new int[partitionStrs.length];
		for(int i=0;i<partitionStrs.length;i++){
			partitions[i]=Integer.parseInt(partitionStrs[i]);
		}
		ConsumerExecutor<String, Object> consumer=new ConsumerConnector(config)
				.manualPartitionAssign().addPartition(
						(String)conf.get(StormConfigNames.KAFKA_TOPIC_NAME), 
						partitions).connect();
		return consumer;
	}

	@Override
	public final void nextTuple() {
		try{
			doNextTuple();
		}catch (Exception e) {
			logger().error(e.getMessage(), e);
		}
	}
	
	protected void doNextTuple() throws Exception{
		ConsumerRecords<String, Object> consumerRecords= consumer.poll(0);
		for (ConsumerRecord<String, Object> consumerRecord : consumerRecords) {
			collector.emit(new Values(consumerRecord.value()));
		}
	}
	
	protected final Object getConf(String key){
		return conf.get(key);
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("record"));
		declarer.declareStream("log-stream", new Fields("record"));
	}

	protected JLogger logger(){
		if(logger==null){
			synchronized (sync) {
				if(logger==null){
					this.logger=JLoggerFactory.getLogger(this.getClass());
				}
			}
		}
		return logger;
	}
	
	protected ConsumerExecutor<String, Object> consumer() {
		return consumer;
	}
	
}
