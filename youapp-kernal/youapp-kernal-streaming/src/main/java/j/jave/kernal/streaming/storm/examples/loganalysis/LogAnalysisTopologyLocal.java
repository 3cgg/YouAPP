package j.jave.kernal.streaming.storm.examples.loganalysis;

import org.apache.storm.Config;
import org.apache.storm.generated.StormTopology;

import j.jave.kernal.streaming.storm.StormConfigNames;
import j.jave.kernal.streaming.storm.StormRunner;


public class LogAnalysisTopologyLocal {

	public static void main(String[] args) throws Exception {
		Config conf=new ConfigParser().parse(args);
		StormTopology stormTopology=new LogAnalysisTopologyBuilder().build(conf);
		int runtimeInSeconds=100000;
		StormRunner.runTopologyLocally(stormTopology, 
				(String)conf.get(StormConfigNames.STORM_TOPOLOGY_NAME)
				, conf, runtimeInSeconds);
		
	}
	
}
