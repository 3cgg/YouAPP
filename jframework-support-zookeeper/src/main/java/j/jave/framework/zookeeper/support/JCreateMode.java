package j.jave.framework.zookeeper.support;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public enum JCreateMode {

	 /**
     * The znode will not be automatically deleted upon client's disconnect.
     */
    PERSISTENT (0, false, false),
    /**
    * The znode will not be automatically deleted upon client's disconnect,
    * and its name will be appended with a monotonically increasing number.
    */
    PERSISTENT_SEQUENTIAL (2, false, true),
    /**
     * The znode will be deleted upon the client's disconnect.
     */
    EPHEMERAL (1, true, false),
    /**
     * The znode will be deleted upon the client's disconnect, and its name
     * will be appended with a monotonically increasing number.
     */
    EPHEMERAL_SEQUENTIAL (3, true, true);

    private static final Logger LOG = LoggerFactory.getLogger(CreateMode.class);

    private boolean ephemeral;
    private boolean sequential;
    private int flag;

    JCreateMode(int flag, boolean ephemeral, boolean sequential) {
        this.flag = flag;
        this.ephemeral = ephemeral;
        this.sequential = sequential;
    }

    public boolean isEphemeral() { 
        return ephemeral;
    }

    public boolean isSequential() { 
        return sequential;
    }

    public int toFlag() {
        return flag;
    }

    /**
     * Map an integer value to a CreateMode value
     */
    static public JCreateMode fromFlag(int flag) throws KeeperException {
        switch(flag) {
        case 0: return JCreateMode.PERSISTENT;

        case 1: return JCreateMode.EPHEMERAL;

        case 2: return JCreateMode.PERSISTENT_SEQUENTIAL;

        case 3: return JCreateMode.EPHEMERAL_SEQUENTIAL ;

        default:
            String errMsg = "Received an invalid flag value: " + flag
                    + " to convert to a CreateMode";
            LOG.error(errMsg);
            throw new KeeperException.BadArgumentsException(errMsg);
        }
    }
    
    public CreateMode mapping(){
    	switch (this) {
		case EPHEMERAL: return CreateMode.EPHEMERAL;
		case EPHEMERAL_SEQUENTIAL: return CreateMode.EPHEMERAL_SEQUENTIAL;
		case PERSISTENT: return CreateMode.PERSISTENT;
		case PERSISTENT_SEQUENTIAL: return CreateMode.PERSISTENT_SEQUENTIAL;
		default:return null;
		}
    }
    
    
    
    
    
	
}
