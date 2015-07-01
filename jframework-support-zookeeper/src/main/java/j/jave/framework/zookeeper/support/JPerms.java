package j.jave.framework.zookeeper.support;

import org.apache.zookeeper.ZooDefs.Perms;

public interface JPerms {
    public static final int READ = Perms.READ;

    public static final int WRITE = Perms.WRITE;

    public static final int CREATE = Perms.CREATE;

    public static final int DELETE = Perms.DELETE;

    public static final int ADMIN = Perms.ADMIN;

    public static final int ALL = READ | WRITE | CREATE | DELETE | ADMIN;
    
}
