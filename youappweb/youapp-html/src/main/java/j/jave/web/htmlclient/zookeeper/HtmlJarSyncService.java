package j.jave.web.htmlclient.zookeeper;

import j.jave.kernal.JConfiguration;
import j.jave.kernal.eventdriven.JServiceOrder;
import j.jave.kernal.eventdriven.servicehub.JServiceFactorySupport;
import j.jave.kernal.eventdriven.servicehub.JServiceHubDelegate;
import j.jave.kernal.eventdriven.servicehub.listener.JServiceHubInitializedEvent;
import j.jave.kernal.eventdriven.servicehub.listener.JServiceHubInitializedListener;
import j.jave.kernal.jave.json.JJSON;
import j.jave.kernal.jave.logging.JLogger;
import j.jave.kernal.jave.logging.JLoggerFactory;
import j.jave.kernal.jave.service.JService;
import j.jave.kernal.jave.utils.JCollectionUtils;
import j.jave.kernal.zookeeper.JChildren2Callback;
import j.jave.kernal.zookeeper.JCreateMode;
import j.jave.kernal.zookeeper.JWatcher;
import j.jave.kernal.zookeeper.JZooKeeperNode;
import j.jave.kernal.zookeeper.JZooKeeperService;
import j.jave.web.htmlclient.ModuleInstallEvent;
import j.jave.web.htmlclient.ModuleMeta;
import j.jave.web.htmlclient.ModuleState;
import j.jave.web.htmlclient.WebHtmlClientProperties;

import java.util.List;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.data.Stat;

@JServiceOrder(value=999,listenerClasses={JServiceHubInitializedListener.class})
public class HtmlJarSyncService
extends JServiceFactorySupport<HtmlJarSyncService>
implements JService , JServiceHubInitializedListener {

	private static final JLogger LOGGER=JLoggerFactory.getLogger(HtmlJarSyncService.class);
	
	private JZooKeeperService zooKeeperService=
			JServiceHubDelegate.get().getService(this, JZooKeeperService.class);
	
	@Override
	protected HtmlJarSyncService doGetService() {
		return this;
	}
	
	@Override
	public Object trigger(JServiceHubInitializedEvent event) {
		sync();
		return true;
	}
	
	private void sync(){
		JConfiguration configuration=JConfiguration.get();
		String root=configuration.getString(WebHtmlClientProperties.YOUAPPMVC_HTML_ROOT_IN_ZOOKEEPER,
				"/youapp/view");
		JZooKeeperNode zooKeeperNode=new JZooKeeperNode();
		zooKeeperNode.setPath(root);
		zooKeeperNode.setValue("it's root for html scanning.");
		zooKeeperNode.setCreateMode(JCreateMode.PERSISTENT);
		zooKeeperService.createDir(zooKeeperNode, true);
		
		JChildren2Callback callback=new JChildren2Callback() {
			@Override
			protected void doProcessResult(int rc, String path, Object ctx,
					List<String> children, Stat stat) {
				try{
					if(JCollectionUtils.hasInCollect(children)){
						for(String module:children){
							try{
								JZooKeeperNode zooKeeperNode=new JZooKeeperNode();
								zooKeeperNode.setPath(path+"/"+module);
								byte[] bytes=zooKeeperService.getValue(zooKeeperNode);
								ModuleState moduleState= JJSON.get().parse(new String(bytes,"utf-8"), ModuleState.class);
								
								ModuleMeta moduleMeta=new ModuleMeta();
								moduleMeta.setJarUrl(moduleState.getJarUrl());
								JServiceHubDelegate.get().addDelayEvent(new ModuleInstallEvent(this, JJSON.get().formatObject(moduleMeta)));
							}catch(Exception e){
								LOGGER.error(e.getMessage(), e);
							}
						}
					}
				}catch(Exception e){
					LOGGER.error(e.getMessage(), e);
					throw new RuntimeException(e);
				}
				
			}
		};
		zooKeeperService.getChildren(zooKeeperNode, new JWatcher() {
			@Override
			protected void doProcess(WatchedEvent event) {
				zooKeeperService.getChildren(zooKeeperNode, this, callback, null);
			}
		}, callback, null);
		
	}

	
}
