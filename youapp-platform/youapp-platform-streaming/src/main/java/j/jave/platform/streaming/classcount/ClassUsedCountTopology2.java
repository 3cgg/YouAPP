package j.jave.platform.streaming.classcount;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.trident.TridentTopology;
import org.apache.storm.trident.operation.BaseFilter;
import org.apache.storm.trident.operation.BaseFunction;
import org.apache.storm.trident.operation.CombinerAggregator;
import org.apache.storm.trident.operation.Consumer;
import org.apache.storm.trident.operation.TridentCollector;
import org.apache.storm.trident.spout.IBatchSpout;
import org.apache.storm.trident.testing.MemoryMapState;
import org.apache.storm.trident.tuple.TridentTuple;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;
import org.apache.storm.utils.Utils;

import j.jave.kernal.jave.utils.JIOUtils;

public class ClassUsedCountTopology2 {

	public static class LoggingSpout implements IBatchSpout{

		private int recordCountPerBatch=3;
		
		private List<String> data=new ArrayList<String>();
		
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
			
			Long start=(batchId-1)*recordCountPerBatch;
			Long end=(batchId)*recordCountPerBatch;
			
			List<String> subData=data.subList(start.intValue(), end.intValue());
			for( String recod:subData){
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
		.each(new Fields("record"), new BaseFilter() {
			@Override
			public boolean isKeep(TridentTuple tuple) {
				String record=tuple.getString(0);
				return record.length()>0;
			}
		})
		.each(new Fields("record"), new BaseFunction() {
			@Override
			public void execute(TridentTuple tuple, TridentCollector collector) {
				String record=tuple.getString(0);
				String info=" INFO ";
				String debug="DEBUG ";
				String split="";
				if(record.indexOf(info)!=-1){
					split=info;
				}else if(record.indexOf(debug)!=-1){
					split=debug;
				}
				String partString=record.substring(record.indexOf(split)+split.length());
				String className=partString.substring(0, partString.indexOf(":")).trim();
				collector.emit(new Values(className,1));
			}
		}, new Fields("class","count"))
		.partitionAggregate(new Fields("count"),new CombinerAggregator<Integer>() {
			@Override
			public Integer init(TridentTuple tuple) {
				try{
					return (Integer) tuple.get(0);
				}catch (Exception e) {
					e.printStackTrace();
					return 0;
				}
				
			}
			@Override
			public Integer combine(Integer val1, Integer val2) {
				return val1+val2;
			}
			@Override
			public Integer zero() {
				return 0;
			}
		}, new Fields("count_aggregator"))
		.peek(new Consumer() {
			@Override
			public void accept(TridentTuple input) {
				System.out.println("accpet :  ---- >  "+input.get(0) );
			}
		})
		.persistentAggregate(new MemoryMapState.Factory(),new Fields("count_aggregator"), new CombinerAggregator<Integer>() {
			@Override
			public Integer init(TridentTuple tuple) {
				try{
					return (Integer) tuple.get(0);
				}catch (Exception e) {
					e.printStackTrace();
					return 0;
				}
				
			}
			@Override
			public Integer combine(Integer val1, Integer val2) {
				return val1+val2;
			}
			@Override
			public Integer zero() {
				return 0;
			}
		}, new Fields("sum_count"))
		/*.map(new MapFunction() {
			@Override
			public Values execute(TridentTuple input) {
				return new Values(input.getString(1).toUpperCase(),input.getInteger(2));
			}
		})
		
		.peek(new Consumer() {
			@Override
			public void accept(TridentTuple input) {
				System.out.println("accpet :  ---- >  "+input.getString(0) + "["+input.getInteger(1)+"]");
			}
		})
		*/
		;
		
		Config conf = new Config();

		LocalCluster cluster = new LocalCluster();
		cluster.submitTopology("test", conf, tridentTopology.build());
		Utils.sleep(10000000);
		cluster.killTopology("test");
		cluster.shutdown();
		
		
		
		
		
		
		
		
		
		
		
		
		
	}
}
