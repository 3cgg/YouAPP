package j.jave.kernal.zookeeper;


import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Id;
import org.apache.zookeeper.data.Stat;

import me.bunny.kernel._c.logging.JLogger;
import me.bunny.kernel._c.logging.JLoggerFactory;
import me.bunny.kernel._c.utils.JCollectionUtils;

public class JZooKeeperClient {
	
	protected final JLogger LOGGER=JLoggerFactory.getLogger(JZooKeeperClient.class);

	final String hostPort;
	
	private ZooKeeper zooKeeper;
	
	private final JWatcher watch;
	
	public JZooKeeperClient(String hostPort,JWatcher watch){
		this.hostPort=hostPort;
		this.watch=watch;
		init();
	}
	public JZooKeeperClient(final String hostPort){
		this.hostPort=hostPort;
		this.watch=new JWatcher() {
			@Override
			public void doProcess(WatchedEvent event) {
				LOGGER.info("["+hostPort+"]type->"+event.getType()+";status->"+event.getState());
			}
		};
		init();
	}
	private void init(){
		try {
			zooKeeper=new ZooKeeper(hostPort, 30000, watch);
		} catch (IOException e) {
			LOGGER.error(e.getMessage(), e);
			throw new JZooKepperException(e);
		}
	}
	
	/**
	 * schema :  auth, digest,world,ip
	 * id: name:password
	 * @param schema
	 * @param id
	 */
	public void addAuth(String schema,String id){
		zooKeeper.addAuthInfo(schema, id.getBytes());
	}
	
	public void addAuth(JBaseAuth baseAuth){
		try{
			zooKeeper.addAuthInfo(baseAuth.getSchema(), baseAuth.authorizingId().getBytes("utf-8"));
		}catch(Exception e){
			LOGGER.error(e.getMessage(), e);
			throw new JZooKepperException(e);
		}
	}
	
	/**
	 * create node with all permission
	 * @param zooKeeperNode
	 */
	public void createNode(JZooKeeperNode zooKeeperNode){
		try {
			List<ACL> intoAcls=null;
			List<JACL> predefinedACLs=zooKeeperNode.getAcls();
			if(JCollectionUtils.hasInCollect(predefinedACLs)){
				JACL[] jacls=new JACL[predefinedACLs.size()];
				zooKeeperNode.getAcls().toArray(jacls);
				intoAcls =  getACL(jacls);
			}
			if(!JCollectionUtils.hasInCollect(intoAcls)){
				intoAcls=Ids.OPEN_ACL_UNSAFE;
			}
			zooKeeper.create(zooKeeperNode.getZooNodePath().getPath(), zooKeeperNode.getInstance().getValue(),
					intoAcls, zooKeeperNode.getCreateMode().mapping());
		} catch (KeeperException e) {
			throw new JZooKepperException(e);
		} catch (InterruptedException e) {
			throw new JZooKepperException(e);
		}catch (Exception e) {
			throw new JZooKepperException(e);
		}
	}
	
	/**
	 * create not with ACL defined.
	 * @param zooKeeperNode
	 * @param acls override the default ACL in the {@link JZooKeeperNode#getAcls()} if the argument is not empty.
	 */
	public void createNode(JZooKeeperNode zooKeeperNode,JACL... acls){
		for(JACL acl:acls){
			zooKeeperNode.getAcls().add(acl);
		}
		createNode(zooKeeperNode);
	}
	
	private List<ACL> getACL(JACL... acls) throws NoSuchAlgorithmException {
		List<ACL> intoAcls=new ArrayList<ACL>();
		if(acls!=null){
			for (int i = 0; i < acls.length; i++) {
				JACL jacl=acls[i];
				Id id=new Id();
				id.setScheme(jacl.getBaseAuth().getSchema());
				id.setId(jacl.getBaseAuth().authorizingIdSHA1());
				ACL acl=new ACL(jacl.getPerm(), id);
				intoAcls.add(acl);
			}
		}
		return intoAcls;
	}
	
	public byte[] getValue(JZooKeeperNode zooKeeperNode){
		Stat stat = new Stat();
		JZooKeeperNodePath zooNodePath=  zooKeeperNode.getZooNodePath();
		try {
			addAuth(zooKeeperNode);
			return zooKeeper.getData(zooNodePath.getPath(), null, stat);
		} catch (KeeperException e) {
			throw new JZooKepperException(e);
		} catch (InterruptedException e) {
			throw new JZooKepperException(e);
		}
	}
	
	public byte[] getValue(JZooKeeperNode zooKeeperNode,JWatcher watch){
		Stat stat = new Stat();
		JZooKeeperNodePath zooNodePath=  zooKeeperNode.getZooNodePath();
		try {
			addAuth(zooKeeperNode);
			return zooKeeper.getData(zooNodePath.getPath(), watch, stat);
		} catch (KeeperException e) {
			throw new JZooKepperException(e);
		} catch (InterruptedException e) {
			throw new JZooKepperException(e);
		}
	}
	
	public byte[] getValue(JZooKeeperNode zooKeeperNode,boolean watch){
		Stat stat = new Stat();
		JZooKeeperNodePath zooNodePath=  zooKeeperNode.getZooNodePath();
		try {
			addAuth(zooKeeperNode);
			return zooKeeper.getData(zooNodePath.getPath(), watch, stat);
		} catch (KeeperException e) {
			throw new JZooKepperException(e);
		} catch (InterruptedException e) {
			throw new JZooKepperException(e);
		}
	}
	
	private void addAuth(JZooKeeperNode zooKeeperNode){
		List<JACL> acls= zooKeeperNode.getAcls();
		if(JCollectionUtils.hasInCollect(acls)){
			for(JACL acl:acls){
				addAuth(acl.getBaseAuth());
			}
		}
	}
	
	public JValue getNodeStat(JZooKeeperNode zooKeeperNode,JWatcher watch){
		
		JZooKeeperNodePath zooNodePath=  zooKeeperNode.getZooNodePath();
		try {
			addAuth(zooKeeperNode);
			Stat stat = new Stat();
			byte[] bytes= zooKeeper.getData(zooNodePath.getPath(), watch, stat);
			JValue value=new JValue();
			value.setBytes(bytes);
			JStatWrapper statWrapper=new JStatWrapper();
			statWrapper.setStat(stat);
			value.setStatWrapper(statWrapper);
			return value;
		} catch (KeeperException e) {
			throw new JZooKepperException(e);
		} catch (InterruptedException e) {
			throw new JZooKepperException(e);
		}
	}
	
	public void deleteNode(JZooKeeperNode zooKeeperNode,int version){
		JZooKeeperNodePath zooNodePath=  zooKeeperNode.getZooNodePath();
		try {
			addAuth(zooKeeperNode);
			zooKeeper.delete(zooNodePath.getPath(), version);
		} catch (InterruptedException e) {
			throw new JZooKepperException(e);
		} catch (KeeperException e) {
			throw new JZooKepperException(e);
		}
	}
	
	public void deleteNode(JZooKeeperNode zooKeeperNode){
		addAuth(zooKeeperNode);
		deleteNode(zooKeeperNode, -1);
	}
	
	
	public void setNode(JZooKeeperNode zooKeeperNode,byte[] bytes,int version){
		JZooKeeperNodePath zooNodePath=  zooKeeperNode.getZooNodePath();
		try {
			addAuth(zooKeeperNode);
			zooKeeper.setData(zooNodePath.getPath(), bytes, version);
		} catch (KeeperException e) {
			throw new JZooKepperException(e);
		} catch (InterruptedException e) {
			throw new JZooKepperException(e);
		}
	}
	
	public void setNode(JZooKeeperNode zooKeeperNode,byte[] bytes){
		addAuth(zooKeeperNode);
		setNode(zooKeeperNode, bytes, -1);
	}
	
	public boolean exist(JZooKeeperNode zooKeeperNode){
		return exist(zooKeeperNode, null);
	}
	
	public boolean exist(JZooKeeperNode zooKeeperNode,JWatcher watch){
		JZooKeeperNodePath zooNodePath=  zooKeeperNode.getZooNodePath();
		try {
			addAuth(zooKeeperNode);
			Stat stat=null;
			if(watch==null){
				stat=zooKeeper.exists(zooNodePath.getPath(), false);
			}
			else{
				stat=zooKeeper.exists(zooNodePath.getPath(), watch);
			}
			return stat!=null;
		} catch (KeeperException e) {
			throw new JZooKepperException(e);
		} catch (InterruptedException e) {
			throw new JZooKepperException(e);
		}
	}
	
	
	public JStatWrapper getLatest(JZooKeeperNode zooKeeperNode){
		JZooKeeperNodePath zooNodePath=  zooKeeperNode.getZooNodePath();
		try {
			addAuth(zooKeeperNode);
			Stat stat=zooKeeper.exists(zooNodePath.getPath(), false);
			return stat==null?null:new JStatWrapper(stat);
		} catch (KeeperException e) {
			throw new JZooKepperException(e);
		} catch (InterruptedException e) {
			throw new JZooKepperException(e);
		}
	}
	
	
	public void getChildren(JZooKeeperNode zooKeeperNode, JWatcher watcher,
			JChildren2Callback cb, Object ctx){
		addAuth(zooKeeperNode);
		zooKeeper.getChildren(zooKeeperNode.getZooNodePath().getPath(), watcher, cb, ctx);
	}
	
	public List<String> getChildren(JZooKeeperNode zooKeeperNode, JWatcher watcher){
		try {
			addAuth(zooKeeperNode);
			return zooKeeper.getChildren(zooKeeperNode.getZooNodePath().getPath(), watcher);
		} catch (KeeperException e) {
			throw new RuntimeException(e);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}
	
	public List<String> getChildren(JZooKeeperNode zooKeeperNode){
		return getChildren(zooKeeperNode, null);
	}

	
}
