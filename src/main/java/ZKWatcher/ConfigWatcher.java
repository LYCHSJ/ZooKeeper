package ZKWatcher;

import org.apache.zookeeper.*;

public class ConfigWatcher implements Watcher {
    private ZooKeeper client;
    private String path;
    public ConfigWatcher(ZooKeeper client,String path){
        this.client=client;
        this.path=path;
    }
    @Override
    public void process(WatchedEvent event){
        try{
            /*register Znode*/
            client.create(path,"helloworld".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
            final Event.EventType TYPE = event.getType();
            switch (TYPE){
                case NodeCreated:
                    break;
                case NodeDataChanged:
                    //get the last data
                    final byte[] news=client.getData(path,false,null);
                    // TODO
                    System.out.println(news);
                    break;
                case NodeChildrenChanged:
                    break;
                case NodeDeleted:
                default:
                    break;
            }
        }catch (KeeperException | InterruptedException e){
            e.printStackTrace();
        }
    }
}
