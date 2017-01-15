package j.jave.kernal.streaming.coordinator;

import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.util.Map;

import j.jave.kernal.streaming.ConfigNames;
import j.jave.kernal.streaming.NetUtil;
import me.bunny.kernel.JConfiguration;
import me.bunny.kernel._c.utils.JAssert;
import me.bunny.kernel._c.utils.JStringUtils;

public abstract class NodeMetaGetter<T extends NodeMeta> {

	protected final JConfiguration configuration;
	
	protected final Map conf;
	
	public NodeMetaGetter(JConfiguration configuration,Map conf) {
		this.configuration = configuration;
		this.conf=conf;
	}

	abstract public T nodeMeta();
	
	protected void _setBasicOnNodeMeta(NodeMeta nodeMeta){
		int id=getInt(ConfigNames.STREAMING_NODE_ID, -1);
		JAssert.isTrue(id!=-1,"node id must be positive");
		nodeMeta.setId(id);
		InetAddress inetAddress=NetUtil.getLocalAddress();
		nodeMeta.setHost(inetAddress.getHostAddress());
		String name=getString(ConfigNames.STREAMING_NODE_NAME,"");
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
	
	protected int getInt(String key,int defVal){
		Integer count=(Integer) conf.get(key);
		if(count==null){
			count=configuration.getInt(key,defVal);
		}
		return count;
	}
	
	protected String getString(String key,String defVal){
		String string=(String) conf.get(key);
		if(string==null){
			string=configuration.getString(key,defVal);
		}
		return string;
	}
	
}
