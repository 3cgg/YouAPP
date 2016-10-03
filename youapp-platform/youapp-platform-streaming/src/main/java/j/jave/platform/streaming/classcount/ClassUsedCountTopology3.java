package j.jave.platform.streaming.classcount;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.trident.TridentTopology;
import org.apache.storm.trident.operation.BaseFilter;
import org.apache.storm.trident.operation.ReducerAggregator;
import org.apache.storm.trident.operation.TridentCollector;
import org.apache.storm.trident.spout.IBatchSpout;
import org.apache.storm.trident.testing.MemoryMapState;
import org.apache.storm.trident.tuple.TridentTuple;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;
import org.apache.storm.utils.Utils;

import j.jave.kernal.jave.utils.JIOUtils;

@SuppressWarnings("serial")
public class ClassUsedCountTopology3 {

	public static class LoggingSpout implements IBatchSpout{

		private int recordCountPerBatch=20;
		
		private List<String> data=new ArrayList<String>();
		
		private long pre;
		
		@Override
		public void open(Map conf, TopologyContext context) {
			File file=new File("D:\\youapp-log-dev\\youap-html.log");
			try {
				byte[] bytes=JIOUtils.getBytes(new FileInputStream(file));
				String string=new String(bytes,"utf-8");
				for(String line:string.split("\\r\\n")){
					data.add(line);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println("open  batch...");
		}

		@Override
		public void emitBatch(long batchId, TridentCollector collector) {
			long now=new Date().getTime();
			long inte=now-pre;
			pre=now;
			System.out.println("ready to emit  batch..."+ batchId+" time : "+ inte);
			
			Long start=(batchId-1)*recordCountPerBatch;
			Long end=(batchId)*recordCountPerBatch;
			
			List<String> subData=data.subList(start.intValue(), end.intValue());
			for( String recod:subData){
				System.out.println("emit value : --> "+recod);
				collector.emit(new Values(recod));
			}
			
			System.out.println("emit  batch..."+ batchId);
		}

		@Override
		public void ack(long batchId) {
			
			System.out.println("ack  batch..." +batchId);
		}

		@Override
		public void close() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public Map<String, Object> getComponentConfiguration() {
			Config conf = new Config();
	        conf.setMaxTaskParallelism(1);
	        return conf;
		}

		@Override
		public Fields getOutputFields() {
			return new Fields("record");
		}
		
	}
	
	
	public static void main(String[] args) {
		
		TridentTopology tridentTopology=new TridentTopology();
		tridentTopology.newStream("class-count", new LoggingSpout())
		.shuffle()
		.each(new Fields("record"), new BaseFilter() {
			@Override
			public boolean isKeep(TridentTuple tuple) {
				String record=tuple.getString(0);
				return record.length()>0;
			}
		})
		.parallelismHint(3)
		.shuffle()
		.persistentAggregate(new MemoryMapState.Factory(), new Fields("record"), new ReducerAggregator<Integer>() {
			@Override
			public Integer init() {
				return 0;
			}
			
			@Override
			public Integer reduce(Integer curr, TridentTuple tuple) {
				return curr++;
			}
			
			
		}, new Fields("reducer-count")).parallelismHint(5);
		;
		
		Config conf = new Config();
		conf.setMessageTimeoutSecs(5000);
		LocalCluster cluster = new LocalCluster();
		cluster.submitTopology("test", conf, tridentTopology.build());
		Utils.sleep(10000000);
		cluster.killTopology("test");
		cluster.shutdown();
		
		
		
		
		
		
		
		
		
		
		
		
		
	}
}
