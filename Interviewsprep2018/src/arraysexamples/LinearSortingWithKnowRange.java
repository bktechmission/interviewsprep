package arraysexamples;

import java.util.Arrays;

public class LinearSortingWithKnowRange {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		sortingWithRange();
	}
	
	static void sortingWithRange() {
		int[] a = {3,5,2,6,7,1,4,9,11,10,8}; // here 0-n elements with length or array is n =11 and 0-11 numbers, so we can use them as index too
		
		specialSort(a);
		
		System.out.println(Arrays.toString(a));
		
		int[] b = {1,2,4,2,3,2,5};
		System.out.println("Cycle found: "+findCycle(b));
	}
	
	// O(n): we go each position at max 2 times so 2n is still O(n)
	static void specialSort(int[]a) {
		boolean dup = false;
		for(int i=0;i<a.length;i++) {
			// for each ith try to fix ith char
			while(a[i] != (i+1)) {// array is zero based index that why we need to a[0] should contains 0+1 = 1  a[1] should contains 1+1 =2
				if(a[i] == a[a[i]-1]) {
					System.out.println("find duplicate "+a[i]);
					dup = true;
					break;
				}
				int temp  = a[a[i]-1];
				a[a[i]-1] = a[i];
				a[i] = temp;
			}
			if(dup) {
				break;
			}
		}
	}
	
	
	static int findCycle(int[]a) {
		int slow = 0;
		int fast = 0;
		do {
			slow = a[slow];
			fast = a[a[fast]];
		}while(slow!=fast);
		
		int find = 0;
		while(find!=slow) {
			slow = a[slow];
			find = a[find];
		}
		return find;
		
	}

}
