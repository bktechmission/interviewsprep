package dynamicprogramming;

import java.util.Arrays;

public class LongestIncreasingSubsequenceProblem {

	public static void main(String[] args) {
		lisExample();
		maximumSumLISExample();
	}
	
	static void lisExample(){
		int[] arr = {10, 22, 9, 33, 21, 50, 2, 1};
		maxGlobal = Integer.MIN_VALUE;
		lis(arr, arr.length);
		System.out.println("Base Recursion : lis is of length: "+maxGlobal);
		System.out.println("BottomUp DP : lis is of length: "+lisBottomUp(arr, arr.length));
		
	}
	
	static int lis(int[]a, int n) {
		// single element
		if(n==1) {
			return 1;
		}
		
		int maxLenSoFar = 1;
		
		/* Recursively get all LIS ending with arr[0], arr[1] ...
	       arr[n-2]. If   arr[i-1] is smaller than arr[n-1], and
	       max ending with arr[n-1] needs to be updated, then
	       update it */
		for(int i=1; i<n; i++) {
			int result = lis(a,i);
			if(a[n-1]>a[i-1] && result+1>maxLenSoFar)
				maxLenSoFar =  result+1;
		}
		
		if(maxGlobal<maxLenSoFar) {
			maxGlobal = maxLenSoFar;
		}
		
		return maxLenSoFar;
	}
	
	static int lisBottomUp(int[]a, int n) {
		int[] lis = new int[n];
		
		Arrays.fill(lis, 1);
		
		for(int i=1;i<a.length;i++) {
			for(int j=0;j<i;j++) {
				if(a[i]>a[j]&&lis[j]+1>lis[i]) {
					lis[i] = lis[j]+1;
				}
			}
		}
		
		int maxLen = Integer.MIN_VALUE;
		for(int i=0;i<lis.length;i++) {
			if(maxLen<lis[i]) {
				maxLen = lis[i];
			}
		}
		
		return maxLen;
	}
	
	static void maximumSumLISExample(){
		int[] a = {1, 101, 2, 3, 100, 4, 5};
		maxGlobal = Integer.MIN_VALUE;
		lisMaxSum(a, a.length);
		
		System.out.println("Base Recursion: LIS Maxsum is of length: "+maxGlobal);
		
		System.out.println("BottomUp DP Recursion: LIS Maxsum is of length: "
						+ maximumSumLISBottomUp(a, a.length));
	}
	
	static int maxGlobal = Integer.MIN_VALUE;
	
	static int lisMaxSum(int[]a, int n) {
		// single element
		if(n==1) {
			return a[n-1];
		}
		
		int maxInCurrentState = a[n-1];
		
		/* Recursively get all LIS ending with arr[0], arr[1] ...
	       arr[n-2]. If   arr[i-1] is smaller than arr[n-1], and
	       max ending with arr[n-1] needs to be updated, then
	       update it */
		for(int i=1; i<n; i++) {
			int result = lisMaxSum(a,i);
			if(a[n-1]>a[i-1] && result+a[n-1]>maxInCurrentState)
				maxInCurrentState =  result+a[n-1];
		}
		
		if(maxGlobal<maxInCurrentState) {
			maxGlobal = maxInCurrentState;
		}
		return maxInCurrentState;
	}
	
	
	static int maximumSumLISBottomUp(int[]a, int n) {
		int[] lis = new int[n];
		int[] pos = new int[n];
		
		for(int i=0;i<a.length;i++) {
			lis[i] = a[i];
			pos[i] = i;
		}
		
		for(int i=1;i<a.length;i++) {
			for(int j=0;j<i;j++) {
				if(a[i]>a[j]&&lis[j]+a[i]>lis[i]) {
					lis[i] = lis[j]+a[i];
					pos[i] = j;
				}
			}
		}
		
		int maxSum = Integer.MIN_VALUE;
		int indexToStart = 0;
		for(int i=0;i<lis.length;i++) {
			if(maxSum<lis[i]) {
				maxSum = lis[i];
				indexToStart = i;
			}
		}
		System.out.println(Arrays.toString(pos));
		System.out.print("\n Sum Sequence is:  {");
		while(indexToStart>=0) {
			System.out.print(a[indexToStart]+", ");
			
			if(pos[indexToStart] == indexToStart) {
				break;
			}
			
			indexToStart = pos[indexToStart];
			
		}
		System.out.print("}");
		return maxSum;
	}
	
}
