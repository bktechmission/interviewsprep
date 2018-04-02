package arraysexamples;

import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

public class SlidingWindiowKSizeMax {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		slidingWindowExample();
	}
	
	static void slidingWindowExample() {
		int[] a = {2,5,3,4,6,8,5,1,3,6};
		int k = 3;
		
		List<Integer> result = slidingWindowSizeKMaximum(a,k);
		System.out.println(result);
	}
	
	static List<Integer> slidingWindowSizeKMaximum(int[]a, int k){
		List<Integer> result = new LinkedList<>();
		Deque<Integer> indicesDQ = new LinkedList<>();
		
		int start = 0;
		for(int cur = 0; cur<a.length; cur++) {
			// addition to DQ: Process, make sure head of DQ is always maximum element index
			if(indicesDQ.isEmpty()) {
				indicesDQ.addFirst(cur);
			}
			else {
				while(!indicesDQ.isEmpty() && a[indicesDQ.getLast()]<=a[cur]) {
					indicesDQ.removeLast();
				}
				indicesDQ.addLast(cur);
			}
			
			// if # of elements in current window is not k then just continue to include next element
			if(cur-start+1<k) {
				continue;
			}
			
			// if we have window of k elements then, 
			// 1) getFirst on DQ as it is maximum ele index
			result.add(a[indicesDQ.getFirst()]);
			
			// 2) check do we need to drop that index in case the index will be smaller than cur-k+1
			if( (cur-k+1) >= indicesDQ.getFirst()) {
				indicesDQ.removeFirst();
			}
			
			// as we are moving cur to next please make start also move by 1 to make size remain k
			start++;
		}
		return result;
	}

}
