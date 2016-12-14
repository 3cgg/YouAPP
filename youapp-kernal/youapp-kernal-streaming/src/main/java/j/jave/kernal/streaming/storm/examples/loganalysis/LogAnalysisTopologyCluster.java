package j.jave.kernal.streaming.storm.examples.loganalysis;

import org.apache.storm.Config;
import org.apache.storm.generated.StormTopology;

import j.jave.kernal.streaming.storm.StormConfigNames;
import j.jave.kernal.streaming.storm.StormRunner;


public class LogAnalysisTopologyCluster {

	public static void main(String[] args) throws Exception {
		Config conf=new ConfigParser().parse(args);
		conf.put(Config.TOPOLOGY_WORKERS, Integer.parseInt(args[2]));
		
		StormTopology stormTopology=new LogAnalysisTopologyBuilder().build(conf);
		StormRunner.runTopologyRemotely(stormTopology, 
				(String)conf.get(StormConfigNames.STORM_TOPOLOGY_NAME)
				, conf);
	}
	
}
