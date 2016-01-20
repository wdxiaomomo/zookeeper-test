package com.hl.qq;

public class ZkMain {
	
	public static void main(String[] args) throws Exception {
		ZkNodeChangedWatcher watcher = new ZkNodeChangedWatcher();
		ZkConnectionWatcher zkConnect = new ZkConnectionWatcher();
		zkConnect.connect("192.168.0.4");
		String oldData = new String(zkConnect.read("/root/child", watcher));
		zkConnect.write("/root/child", new byte[]{'a', 'b', 'c'});
		String newData = new String(zkConnect.read("/root/child", watcher));
		System.out.println(oldData + "---->" + newData);
		zkConnect.close();
	}
}
