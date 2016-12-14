package j.jave.kernal.streaming.storm.examples.loganalysis;

import java.io.Serializable;
import java.util.Map;

import org.apache.storm.generated.StormTopology;
import org.apache.storm.topology.TopologyBuilder;


@SuppressWarnings("serial")
public class LogAnalysisTopologyBuilder implements Serializable {

	public StormTopology build(Map<String, Object> conf) throws Exception {
		TopologyBuilder topologyBuilder=new TopologyBuilder();
		topologyBuilder.setSpout("loganalysis-task-spout", new LogAnalysisKafkaSpout());
		topologyBuilder.setBolt("loganalysis-cen-bolt", new LogAnalysisCenterBolt(),1)
		.shuffleGrouping("loganalysis-task-spout");
		topologyBuilder.setBolt("loganalysis-logging-bolt", new LogAnalysisLoggerBolt(),1)
		.shuffleGrouping("loganalysis-task-spout","log-stream");
		return topologyBuilder.createTopology();
	}
	
}
