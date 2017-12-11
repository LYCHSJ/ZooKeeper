package ZK1;

import org.apache.zookeeper.*;

public class ZK1 {

    private static String connectString = "127.0.0.1:2181";

    private static int sessionTimeout = 999999;

    public static void main(String[] args) throws Exception{
        Watcher watcher = new Watcher() {
            public void process(WatchedEvent watchedEvent) {
                System.out.println("Have got a new event:"+watchedEvent);
            }
        };

        final ZooKeeper zooKeeper = new ZooKeeper(connectString,sessionTimeout,watcher);

        String path= "/ZK1";

        zooKeeper.create(path,"helloworld".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);

        System.out.println("got a connect:"+zooKeeper);

        final byte[] data=zooKeeper.getData(path,watcher,null);

        System.out.println("got the data:"+new String(data));

        zooKeeper.close();
    }
}
