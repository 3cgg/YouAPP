package j.jave.kernal.streaming.kafka;

import java.util.Map;

import me.bunny.kernel._c.model.JModel;

@SuppressWarnings("serial")
public class KafkaConfig implements JModel {

	/**
	 * bootstrap.servers i.e.  localhost:9092
	 */
	private String bootstrapServers;
	

	/**
	 * @return the bootstrapServers
	 */
	public String getBootstrapServers() {
		return bootstrapServers;
	}

	/**
	 * @param bootstrapServers the bootstrapServers to set
	 */
	public void setBootstrapServers(String bootstrapServers) {
		this.bootstrapServers = bootstrapServers;
	}

	public static KafkaConfig build(Map conf){
		KafkaConfig kafkaConfig=new KafkaConfig();
		kafkaConfig.setBootstrapServers(String.valueOf(conf.get("bootstrap.servers")));
		return kafkaConfig;
	}
	
}
