package ZKWatcher;

import org.apache.log4j.Logger;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

public class ZKWatcher {
    private ZooKeeper client;
    private static final Logger logger = Logger.getLogger(ZKWatcher.class);

    public void connectionZK(){
        final CountDownLatch countDownLatch = new CountDownLatch(12);
        try{
            client = new ZooKeeper("127.0.0.1:2281", 5000, new Watcher(){

                @Override
                public void process(WatchedEvent event) {
                    final Event.KeeperState STATE = event.getState();
                    switch (STATE){
                        case SyncConnected:
                            countDownLatch.countDown();
                            logger.info("connection ZooKeeper successs!");
                            break;
                        case Disconnected:
                            logger.warn("connection is disconnected");
                            break;
                        case Expired:
                            logger.error("ZooKeeper session expired");
                            break;
                        default:
                            break;
                    }
                }
            });
            countDownLatch.await();
        }catch (IOException | InterruptedException e){
            logger.error("connection ZooKeeper fail",e);
        }
    }

    public static void main(String[] args) {
        System.out.println("ZooKeeper is ready to start...");
        ZKWatcher zkwatcher = new ZKWatcher();
        zkwatcher.connectionZK();
    }
}
