package leetcodeexamples;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

public class PriorityQueueExample {
	public static void main(String[] args) {
		PriorityQueueEfficient pq = new PriorityQueueEfficient(5);
		pq.offer(5);
		pq.offer(25);
		pq.offer(13);
		pq.offer(19);
		pq.offer(3);
		System.out.println(pq.peek());
		if(pq.contains(25)) {
			System.out.println("queue contains 25");
		}
		else {
			System.out.println("queue does not contains 25");
		}
		
		System.out.println("queue poll now "+ pq.poll());
		System.out.println("at peek : " +pq.peek());
		System.out.println("queue poll now "+ pq.poll());
		System.out.println("at peek : " +pq.peek());
		System.out.println("queue poll now "+ pq.poll());
		System.out.println("at peek : " +pq.peek());
		
		pq.offer(3);
		pq.offer(2);
		if(pq.contains(25)) {
			System.out.println("now queue contains 25");
		}
		else {
			System.out.println("now queue does not contains 25");
		}
		
		System.out.println("at peek : " +pq.peek());
	}
	
}

// build heap in O(n)  search O(1)  extract min is logn  peek a min element is O(1)
class PriorityQueueEfficient{
	int[] heap;
	int size;
	int capacity;
	Map<Integer,Integer> map = new HashMap<>();
	
	PriorityQueueEfficient(int capapcity){
		heap = new int[capapcity];
		this.capacity = capapcity;
	}
	
	
	void offer(int val) {
		if(size==capacity) throw new NullPointerException();
		if(map.containsKey(val)) {
			return;
		}
		int pos = size;
		heap[pos] = val;
		size++;
		bubbleUp(pos);
	}
	
	void bubbleUp(int pos) {
		// now bubble up
		while(pos>0) {
			int parent = (pos-1)/2;
			if(heap[parent]<heap[pos]) {
				break;
			}
			
			map.put(heap[parent], pos);
			map.put(heap[pos], parent);
			swap(heap,pos,parent);
			pos = parent;
		}
	}
	
	void swap(int[]a, int i, int j) {
		int temp = a[i];
		a[i]= a[j];
		a[j] = temp;
	}
	
	int peek() {
		if(size==0) throw new NullPointerException();
		return heap[0];
	}
	
	int poll(){
		if(size==0) throw new NullPointerException();
		
		int pos = 0;
		int returnEle = heap[pos];
		map.remove(returnEle);
		
		heap[pos] = heap[size-1];
		map.put(heap[pos], pos);
		size--;
		
		sinkDown(pos);
		
		return returnEle;
	}
	
	void sinkDown(int pos) {
		while(pos<size/2) {
			int leftChild = 2*pos+1;
			int rightChild = 2*pos+2;
			int childNodePicked = leftChild;
			if(rightChild<size&&heap[rightChild]<heap[leftChild]) {
				childNodePicked = rightChild;
			}
			
			if(heap[childNodePicked]>heap[pos]) {
				break;
			}
			
			map.put(heap[childNodePicked], pos);
			map.put(heap[pos], childNodePicked);
			swap(heap,childNodePicked,pos);
		}

	}
	
	int size(){
		return size;
	}
	
	boolean contains(int val) {
		return map.containsKey(val);
	}
	
	void remove(int val) {
		if(size==0) throw new NullPointerException();
		
		if(!map.containsKey(val)) {
			throw new NoSuchElementException();
		}
		
		int pos=map.get(val);
		map.remove(val);
		heap[pos] = Integer.MIN_VALUE;
		
		bubbleUp(pos);
		
		// now the least element is at top and that is Integer.Min so least,
		// replace it with last element in the heap and call ajust on the 0
		pos=0;
		heap[pos]=heap[size-1];
		size--;
		
		sinkDown(pos);
	}
}
