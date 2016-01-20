package com.hl.qq;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;

public class ZkConnectionWatcher implements Watcher {
    
    private static final int SESSION_TIMEOUT = 5000;
    protected ZooKeeper zooKeeper;
    protected CountDownLatch countDownLatch = new CountDownLatch(1);

    public void connect(String hosts) throws IOException, InterruptedException {
    	zooKeeper = new ZooKeeper(hosts, SESSION_TIMEOUT, this);
    	countDownLatch.await();
    }

    @Override
    public void process(WatchedEvent event) {
    	if (event.getState() == Event.KeeperState.SyncConnected) {
    		countDownLatch.countDown();
        }
    }

    public void close() throws InterruptedException {
    	zooKeeper.close();
    }
    
    //写数据
  	public void write(String path, byte[] data) throws KeeperException,
  			InterruptedException {
  		if(zooKeeper.exists(path, false) == null){
  			zooKeeper.create(path, data, Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
  		}else{
  			zooKeeper.setData(path, data, -1);
  		}
  	}

  	//获取数据
  	public byte[] read(String path, Watcher watcher) throws KeeperException,
  			InterruptedException {
  		return zooKeeper.getData(path, watcher, null);
  	}
}