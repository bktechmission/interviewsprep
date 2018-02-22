package leetcodeexamples;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;

public class LRUCacheEx {
	public static void main(String[] args) {
		LRUCacheNew cache = new LRUCacheNew(5);
		cache.put(1, "Bhi1");
		cache.put(2, "Bhi2");
		cache.put(3, "Bhi3");
		cache.put(4, "Bhi4");
		cache.put(5, "Bhi5");
		cache.put(6, "Bhi6");
		cache.put(7, "Bhi7");
		System.out.println("on 2 get: "+cache.get(2));
		Assert.assertNull("node should be null",cache.get(2));
		
	}
}


class LRUCacheNew{
	Map<Integer, Node> map;
	Node head,tail;
	int capacity;
	
	
	private class Node{
		Integer key;
		String val;
		Node next,prev;
		
		Node(Integer key, String val){
			this.key = key;
			this.val = val;
		}
	}
	
	LRUCacheNew(int capacity){
		map = new HashMap<>(capacity);
		this.capacity = capacity;
	}
	
	public void put(Integer key, String val) {
		if(map.containsKey(key)) {
			Node oldNode = map.get(key);
			oldNode.val = val;
			removeNode(oldNode);
			setHead(oldNode);
		}
		else {
			Node newNode = new Node(key,val);
			if(map.size()>=capacity) {
				map.remove(tail.key);
				removeNode(tail);
			}
			setHead(newNode);
			map.put(key,newNode);
		}
	}
	
	public String get(Integer key) {
		if(map.containsKey(key)) {
			Node foundNode = map.get(key);
			removeNode(foundNode);
			setHead(foundNode);
			return foundNode.val;
		}
		return null;
	}
	
	private void removeNode(Node n) {
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
	
	private void setHead(Node n) {
		n.next = head;
		n.prev  = null;
		
		if(head==null) {
			head = tail =n;
		}
		else {
			head.prev = n;
			head = n;
		}
	}
}