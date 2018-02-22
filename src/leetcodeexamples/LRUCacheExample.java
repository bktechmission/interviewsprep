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

public class LRUCacheExample {
	public static void main(String[] args) {
		LRUCache<Integer,String> cache = new LRUCache<>(6,6);
		
		cache.put(1, "Bhupender1");
		cache.put(2, "Bhupender2");
		cache.put(3, "Bhupender3");
		cache.put(4, "Bhupender4");
		Assert.assertEquals("cache entry did not match for key 3", "Bhupender3", cache.get(3));
		try {
			TimeUnit.SECONDS.sleep(8);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		Assert.assertNull("cache entry still exists even time past", cache.get(3));

		cache.put(5, "Bhupender5");
		Assert.assertEquals("cache entry did not exists for 5", "Bhupender5", cache.get(5));
		try {
			TimeUnit.SECONDS.sleep(7);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Assert.assertNull("cache entry still exists even time past", cache.get(5));
		cache.put(1, "Bhupender11");
		cache.put(2, "Bhupender22");
		cache.put(3, "Bhupender33");
		cache.put(4, "Bhupender44");
		cache.put(5, "Bhupender55");
		cache.put(6, "Bhupender66");
		cache.put(7, "Bhupender77");
		cache.put(8, "Bhupender88");
		cache.put(9, "Bhupender77");
		Assert.assertNull("cache Least Recently did not evict on full cache", cache.get(2));
		Assert.assertNotNull("cached 8 did not exists but it should have been in cache", cache.get(8));
		System.out.println("All tests passed on cache.");
		
		System.out.println("Program is ready to put entry in cache:-> ");
		try {
			TimeUnit.HOURS.sleep(3);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}

}


class LRUCache<K,V>{
	private ReentrantLock lock = new ReentrantLock();
	private Map<K, Node> map;
	private volatile Node head, tail;
	private int capacity;
	private int ttl;
	
	private ScheduledExecutorService sc = Executors.newScheduledThreadPool(1);
	
	private Runnable task = ()->{
		Iterator<Map.Entry<K, Node>> itr = map.entrySet().iterator();
		while(itr.hasNext()) {
			Node node = itr.next().getValue();
			if((DateTime.now().getMillis()-node.getLastUpdatedAt())>ttl*1000) {
				lock.lock();
				try {
					removeNode(node);
					itr.remove();
				}finally {
					lock.unlock();
				}
			}
		}
	};
	
	
	
	public LRUCache(int capacity, int secs){
		map = new ConcurrentHashMap<>(capacity);
		this.capacity = capacity;
		this.ttl = secs;
		sc.scheduleWithFixedDelay(task, 0, 1, TimeUnit.SECONDS);
	}
	
	private class Node{
		K key;
		V data;
		Node next;
		Node prev;
		long createdAt;
		
		Node(K key, V data,long createdAt){
			this.key = key;
			this.data = data;
			this.createdAt = createdAt;
			
		}
		
		public long getLastUpdatedAt() {
			return createdAt;
		}
	
	}
	
	
	public V get(K key) {
		if(map.containsKey(key)) {
			Node node = map.get(key);
			lock.lock();
			try{
				removeNode(node);
				setHead(node);
			}finally {
				lock.unlock();
			}
			return node.data;
		}
		return null;
	}
	
	private void setHead(Node node) {
		node.next = head;
		node.prev = null;
		if(head==null) {
			head = node;
			tail = node;
		}
		else {
			head.prev = node;
			head = node;
		}
	}
	
	private void removeNode(Node node) {
		if(node.next!=null) {
			node.next.prev = node.prev;
		}
		else {
			tail = node.prev;
		}
		
		if(node.prev!=null) {
			node.prev.next = node.next;
		}
		else {  
			head = node.next;
		}
	}
	
	public void put(K key, V val) {
		if(map.containsKey(key)) {
			Node oldNode = map.get(key);
			oldNode.data = val;
			lock.lock();
			try {
				removeNode(oldNode);
				setHead(oldNode);
			}finally {
				lock.unlock();
			}
			
		}else {
			Node newNode = new Node(key,val, DateTime.now().getMillis());
			lock.lock();
			try{
				if(map.size()>capacity) {					
					map.remove(tail.key);
					removeNode(tail);			
				}
				setHead(newNode);
				map.put(key, newNode);
			}
			finally {
				lock.unlock();
			}
		}
	}
}
