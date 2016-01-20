package com.hl.qq;

import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.proto.WatcherEvent;

public class ZkNodeChangedWatcher implements Watcher {

	protected CountDownLatch countDownLatch = new CountDownLatch(1);
	
	@Override
	public void process(WatchedEvent event) {
		if(event.getType() == Event.EventType.NodeDataChanged){
			//某个节点的data修改后，根据需要执行某个操作。
			String path = event.getPath();
			System.out.println(path);
			countDownLatch.countDown();
		}
	}

}
