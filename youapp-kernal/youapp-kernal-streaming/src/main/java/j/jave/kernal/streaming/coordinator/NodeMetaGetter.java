package j.jave.kernal.streaming.coordinator;

import java.lang.management.ManagementFactory;
import java.net.InetAddress;

import j.jave.kernal.JConfiguration;
import j.jave.kernal.jave.utils.JAssert;
import j.jave.kernal.jave.utils.JStringUtils;
import j.jave.kernal.streaming.ConfigNames;
import j.jave.kernal.streaming.NetUtil;

public abstract class NodeMetaGetter<T extends NodeMeta> {

	private final JConfiguration configuration;
	
	public NodeMetaGetter(JConfiguration configuration) {
		this.configuration = configuration;
	}

	abstract public T nodeMeta();
	
	protected void _setBasicOnNodeMeta(NodeMeta nodeMeta){
		int id=configuration.getInt(ConfigNames.STREAMING_NODE_ID, -1);
		JAssert.isTrue(id!=-1,"node id must be positive");
		InetAddress inetAddress=NetUtil.getLocalAddress();
		nodeMeta.setHost(inetAddress.getHostAddress());
		String name=configuration.getString(ConfigNames.STREAMING_NODE_NAME);
		if(JStringUtils.isNullOrEmpty(name)){
			name=inetAddress.getHostName();
		}
		nodeMeta.setName(name);
		String pid = ManagementFactory.getRuntimeMXBean().getName();  
        int indexOf = pid.indexOf('@');  
        if (indexOf > 0){ 
            pid = pid.substring(0, indexOf);  
        }  
        nodeMeta.setPid(Integer.parseInt(pid));
	}
	
}
