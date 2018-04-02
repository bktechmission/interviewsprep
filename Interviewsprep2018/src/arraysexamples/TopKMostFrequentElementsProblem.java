package arraysexamples;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

public class TopKMostFrequentElementsProblem {
	public static void main(String[] args) {
		topKElementsExample();
	}
	
	static void topKElementsExample() {
		int[]a = {1,4,3,6,2,3,4,2,3,4,5,3,2,1,3,4,5,7,8,4,6,2,1,5,3};
		
		int k = 3;
		
		List<Pair> result = getTopKElementsUsingPQ(a,k);
		System.out.println(result);
		
		// O(N) bucket sort example
		result = getTopKElementsUsingBucketsSort(a,k);
		System.out.println(result);
	}
	
	static class Pair{
		int num;
		int freq;
		Pair(int num, int freq){
			this.num = num;
			this.freq = freq;
		}
		
		public String toString() {
			return "{n:"+num+",f:"+freq +"}";
		}
	}
	
	// nlogk with k space used
	static List<Pair> getTopKElementsUsingPQ(int[]a, int k){
		Map<Integer, Integer> freqmap = new HashMap<Integer, Integer>();
		
		for(int num:a) {
			if(freqmap.containsKey(num)) {
				freqmap.put(num, freqmap.get(num)+1);
			}
			else {
				freqmap.put(num, 1);
			}
		}
		
		// now main a min PQ of k elements
		Queue<Pair> pq = new PriorityQueue<>(k,new Comparator<Pair>() {
			public int compare(Pair p1, Pair p2) {
				return p1.freq - p2.freq;
			}
		});
		
		// nlogK
		for(Map.Entry<Integer, Integer> entry : freqmap.entrySet()) {
			int num = entry.getKey();
			int freq = entry.getValue();
			
			pq.offer(new Pair(num,freq));
			
			if(pq.size()>k) {
				pq.poll();
			}
		}
		
		// now our min priority queue contains last k elements in increasing order
		// so get one by one elements and insert into result set of size and sort in reverse order
		List<Pair> finalResult = new ArrayList<>();
		while(!pq.isEmpty()) {
			finalResult.add(pq.poll());
		}
		
		Collections.sort(finalResult, new Comparator<Pair>() {
			public int compare(Pair p1, Pair p2) {
				return p2.freq - p1.freq;
			}
		});
		
		return finalResult;
	}
	
	// bucket sort: O(N) with  space used = mostFreq
	static List<Pair> getTopKElementsUsingBucketsSort(int[]a, int k) {
		Map<Integer, Integer> freqmap = new HashMap<>();
		
		for(int num: a) {
			if(freqmap.containsKey(num)) {
				freqmap.put(num,freqmap.get(num)+1);
			}else {
				freqmap.put(num,1);
			}
		}
		
		int maxFreq = Integer.MIN_VALUE;
		for(Map.Entry<Integer, Integer> entry: freqmap.entrySet()) {
			maxFreq = Math.max(maxFreq, entry.getValue());
		}
		
		ArrayList<Integer>[] buckets = new ArrayList[maxFreq+1];
		for(int i=1;i<buckets.length;i++) {
			buckets[i] = new ArrayList<Integer>();
		}
		
		
		for(Map.Entry<Integer, Integer> entry: freqmap.entrySet()) {
			int n = entry.getKey();
			int f = entry.getValue();
			buckets[f].add(n);
		}
		
		List<Pair> finalResult = new ArrayList<>();
		for(int i = maxFreq; i>0; i--) {
			List<Integer> l = buckets[i];
			int  c = 0;
			while(c<l.size()) {
				finalResult.add(new Pair(l.get(c),i));
				c++;
				if(finalResult.size()==k) {
					return finalResult;
				}
			}
		}
		return new ArrayList<Pair>();
	}
}
