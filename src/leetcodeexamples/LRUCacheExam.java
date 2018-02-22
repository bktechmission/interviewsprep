package leetcodeexamples;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

import org.joda.time.DateTime;

import org.junit.Assert;

public class LRUCacheExam {
	public static void main(String[] args) {
		LRUCacheWithTTL cache = new LRUCacheWithTTL(5,2);
		cache.put(1, 11);
		cache.put(2, 21);
		cache.put(3, 31);
		try {
			TimeUnit.SECONDS.sleep(3);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("get on 1 : "+cache.get(1));
		Assert.assertNull("No key should be in cache",cache.get(1));
		
		
		cache.put(4, 41);
		cache.put(5, 51);
		cache.put(6, 61);
		cache.put(7, 41);
		cache.put(8, 51);
		cache.put(9, 61);
		Assert.assertNull("key 4 should be in cache",cache.get(4));
		Assert.assertNotNull("key 5 should be in cache",cache.get(5));
		
		
	}
}


class LRUCacheWithTTL{
	ReentrantLock lock=new ReentrantLock();
	Map<Integer,Node> map;
	Node head, tail;
	int capacity;
	int ttl;
	
	ScheduledExecutorService sc = Executors.newScheduledThreadPool(1);
	Runnable task = ()->{
		Iterator<Map.Entry<Integer, Node>> itr = map.entrySet().iterator();
		while(itr.hasNext()) {
			Node nd = (Node)itr.next().getValue();
			if((DateTime.now().getMillis()-nd.lastUpdatedAt)>ttl*1000) {
				lock.lock();
				try {
					System.out.println("removing node "+nd.getKey());
					removeNode(nd);
					itr.remove();	
				}
				finally {
					lock.unlock();
				}

			}
		}
	};
	
	LRUCacheWithTTL(int capacity, int ttl){
		this.capacity = capacity;
		this.ttl=ttl;
		map = new ConcurrentHashMap<>();
		sc.scheduleWithFixedDelay(task, 0, 1, TimeUnit.SECONDS);
	}
	
	class Node{
		int key, val;
		long lastUpdatedAt;
		Node next, prev;
		
		Node(int key, int val, long lastUpdatedAt){
			this.key = key;
			this.val = val;
			this.lastUpdatedAt = lastUpdatedAt;
		}
		
		int getKey(){
			return key;
		}
	}
	
	
	void put(int key, int val){
		if(map.containsKey(key)) {
			Node oldValue = map.get(key);
			oldValue.val = val;
			lock.lock();
			try {
				removeNode(oldValue);
				setHead(oldValue);
			}finally {
				lock.unlock();
			}
		}else {
			Node createdNode = new Node(key,val,DateTime.now().getMillis());
			while(!lock.tryLock())
			{
				try {
					Thread.sleep(20);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			try {
				if(map.size()==capacity) {
					System.out.println("removed Node "+tail.key);
					map.remove(tail.key); 
					removeNode(tail);
				}
				System.out.println("created node is "+ createdNode.key + "  ,  "+createdNode.val);
				setHead(createdNode);
				map.put(key, createdNode);
			}
			finally {
				lock.unlock();
			}
		}
	}
	
	Integer get(int key) {
		if(map.containsKey(key)) {
			Node returnNode = map.get(key);
			removeNode(returnNode);
			setHead(returnNode);
			return returnNode.val;
		}
		return null;
	}
	
	void removeNode(Node n) {
		if(n.prev!=null) {
			n.prev.next = n.next;
		}else {
			head = n.next;
		}
		
		if(n.next!=null) {
			n.next.prev = n.prev;
		}
		else {
			tail = n.prev;
		}
	}
	
	void setHead(Node n) {
		n.next = head;
		n.prev = null;
		
		if(head==null) {
			head = tail = n;
		}
		else {
			head.prev = n;
			head = n;
		}
	}
}