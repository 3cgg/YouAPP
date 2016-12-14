package j.jave.kernal.streaming.storm;

import java.util.Map;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.FailedException;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;

import j.jave.kernal.jave.logging.JLogger;
import j.jave.kernal.jave.logging.JLoggerFactory;
import j.jave.kernal.streaming.zookeeper.ZooKeeperConnector.ZookeeperExecutor;
import j.jave.kernal.streaming.zookeeper.ZooKeeperExecutorGetter;

@SuppressWarnings({"serial","rawtypes"})
public abstract class BaseSimpleRichBolt extends org.apache.storm.topology.base.BaseRichBolt {

	private transient Map stormConf;
	
	private transient TopologyContext topologyContext;
	
	private transient OutputCollector collector;
	
	private transient ZookeeperExecutor executor;
	
	private transient Object sync;
	
	private transient JLogger logger;

	@Override
	public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
		this.collector=collector;
		this.stormConf=stormConf;
		this.topologyContext=context;
		sync=new Object();
		zk();
	}

	protected void zk() {
		executor=ZooKeeperExecutorGetter.getDefault();
	}
	
	protected final Map getStormConf() {
		return stormConf;
	}
	
	@Override
	public final void execute(Tuple tuple) {
		try{
			doExecute(tuple);
		}catch (Exception e) {
			logger().error(e.getMessage(), e);
			throw new FailedException(e);
		}
	}
	
	protected void emit(Values values){
		collector.emit(values);
	}
	
	
	protected abstract void doExecute(Tuple tuple) throws Exception;
	
	protected ZookeeperExecutor getExecutor() {
		return executor;
	}
	
	
	protected TopologyContext getTopologyContext() {
		return topologyContext;
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
	
}
