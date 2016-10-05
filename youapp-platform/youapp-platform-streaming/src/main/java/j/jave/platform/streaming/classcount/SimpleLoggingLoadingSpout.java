package j.jave.platform.streaming.classcount;

import java.io.File;
import java.net.URI;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.storm.Config;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.trident.operation.TridentCollector;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;
import org.apache.storm.utils.Utils;

public class SimpleLoggingLoadingSpout implements LoggingAnalyseSpout {
	
	private int recordCountPerBatch=20;
	
	public SimpleLoggingLoadingSpout(int recordCountPerBatch) {
		this.recordCountPerBatch=recordCountPerBatch;
	}
	
	private List<String> data=new ArrayList<String>(recordCountPerBatch*100);
	
	private long pre;
	
	@Override
	public void open(Map conf, TopologyContext context) {
		try {
			String fileURI=(String) conf.get(ClassCountConfig.FILE_URI);
			File file=new File(new URI(fileURI));
			data=new FileReadOnce().read(file);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
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
		
		if(start>=data.size()){
			Utils.sleep(1000000);
		}
		if(end>=data.size()){
			end=(long) (data.size()-1);
		}
		
		List<String> subData=data.subList(start.intValue(), end.intValue());
		for( String recod:subData){
			//System.out.println("emit value : --> "+recod);
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
