package arraysexamples;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;


public class MinimumSizeSubArraySum {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		sumSubArraySumExamples();
		
	}
	
	static void sumSubArraySumExamples() {
		int[] a = {2,4,3,6};
		int sum = 7;
		
		minSizeSubArrSum(a,sum);
		
		int[] b = {2, 5, -3, 12, 10, -4, -5, -2, 2, 24};
		System.out.println(maxSubArrayLen(b,19));
		
		int[] c = {2, 5, -3, 12, 10, -4};
		minSizeSubArrSumGreaterThanK(c,8);
		
		
		
		int k = 8;
		System.out.println("maxSum array close to "+k+" is : "+maxSumSubarrayCloseToK(c,k));
		
		System.out.println("Max Sum subarray Kadanes is :"+ Arrays.toString(maxSumSubArrayKadanesAlgo(c)));
		
		
		maxSumRectangleExample();

	}
	

	static int minSizeSubArrSum(int[]a, int sum) {
		int i = 0;
		int j = 0;
		int curSum = 0;
		int minLen = Integer.MAX_VALUE;
		int minStart = -1;
		int minSum = 0;
		while(i<a.length) {
			curSum += a[i];
			
			while(curSum >= sum) {
				// a single element is the minimum we can form so just return from here
				if(i-j+1==1) {
					minLen = (i-j+1);
					minStart = j;
					System.out.println("Found single element greater equal than sum: "+a[i]);
					return minLen;
				}
				
				if(minLen>(i-j)+1) {
					minLen = (i-j+1);
					minStart = j;
					minSum = curSum;
				}
				
				curSum -= a[j];
				j++;
			}
			i++;
		}
		
		System.out.println("Found sum at : "+minStart +"  with len : "+minLen +"  sum is "+minSum);
		return minLen;
	}
	
	// [1, -1, 5, -2, 3]
	//         ^
	//
	static int maxSubArrayLen(int[]a, int k){
		Map<Integer, Integer> map = new HashMap<Integer, Integer>();
		
		int sum = 0;
		int maxLen = Integer.MIN_VALUE;
		int maxSumEndsAt = -1;
		for(int i=0;i<a.length;i++) {
			sum += a[i];
			
			int diff = sum - k;
			
			if(diff==0) {
				// we have found the
				if(maxLen<=(i+1)) {
					maxLen = i;
					maxSumEndsAt = i;
				}
			}
			
			if(map.containsKey(diff)) {
				int start = map.get(diff);
				// we have found the
				if(maxLen<=(i-start)) {
					maxLen = i-start;
					maxSumEndsAt = i;
				}
			}
			
			
			if(!map.containsKey(sum)) {
				map.put(sum, i);
			}
			
		}
		
		System.out.println("MaxLen found ending at: "+maxSumEndsAt+"   with len: "+maxLen);
		return maxLen;
	}
	
	// can contains -ve numbers
	static void minSizeSubArrSumGreaterThanK(int[]a, int k) {
		
		TreeMap<Integer, Integer> map = new TreeMap<>();
		
		int minLen = Integer.MAX_VALUE;
		int curSum = 0;
		int minLenSumEndAt = 0;
		int minSum = Integer.MAX_VALUE;
		for(int i=0;i<a.length;i++) {
			curSum += a[i];
			
			int diff = curSum - k;
			
			if(diff==0) {
				if(minLen > (i+1)) {
					minSum = curSum;
					minLen = i+1;
					minLenSumEndAt = i;
				}
			}
			
			Integer floor = map.floorKey(diff);
			if(floor!=null) {
				int index = map.get(floor);
				int lenDiff = i - index;
				if( minSum > (curSum-floor) && minLen>lenDiff) {
					minLen = i-index;
					minLenSumEndAt = i;
					minSum = curSum-floor;
				}
			}
			map.put(curSum, i);
		}
		
		System.out.println("found sum at: "+minLenSumEndAt + "  sum is : "+minSum + "  len is "+minLen);
		
	}
	
	
	static int maxSumSubarrayCloseToK(int[]a, int k) {
		TreeSet<Integer> set = new TreeSet<>();
		
		set.add(0);
		int maxSum = Integer.MIN_VALUE;
		int curSum = 0;
		for(int i=0; i<a.length; i++) {
			curSum += a[i];
			
			int diff = curSum - k;
			Integer ceiling = set.ceiling(diff);
			if(ceiling!=null) {
				if(maxSum < (curSum - ceiling)) {
					maxSum = curSum - ceiling;
				}
			}
			
			set.add(curSum);
		}
		
		return maxSum;
	}
	
	
	static int[] maxSumSubArrayKadanesAlgo(int[]a){
		int curSum = 0;
		int maxSum = Integer.MIN_VALUE;
		int start = 0;
		int end = 0;
		int j = 0;
		
		for(int i=0;i<a.length;i++) {
			curSum += a[i];
			
			if(curSum<0) {
				curSum = 0;
				j = i+1;
			}
			else {
				if(curSum>maxSum) {
					maxSum = curSum;
					end = i;
					start = j;
				}
			}
		}
		
		//System.out.println("Max Sum found at : start "+ start+"  end:"+end+" and sum is "+maxSum);
		
		return new int[] {start,end,maxSum};
	}
	
	
	static void maxSumRectangleExample(){
		int m = 2;
		int n = 3;
		int[][] matrix = {{1,0,1},{0,-2,3}};
		
		maxSumRectangle(matrix);
		
	}
	
	static void maxSumRectangle(int[][] matrix){
		
		int leftBound = 0;
		int rightBound = 0;
		int top = 0;
		int bottom = 0;
		int maxSum = Integer.MIN_VALUE;
		
		for(int leftCol=0; leftCol<matrix[0].length; leftCol++) {
			int[] eachColMat = new int[matrix.length];
			for(int rightCol=leftCol; rightCol<matrix[0].length; rightCol++) {
				
				for(int r = 0; r<matrix.length; r++) {
					eachColMat[r] += matrix[r][rightCol];
				}
				
				int[] boundaries = maxSumSubArrayKadanesAlgo(eachColMat);
				if(boundaries[2]>maxSum) {
					maxSum = boundaries[2];
					leftBound = leftCol;
					rightBound = rightCol;
					top = boundaries[0];
					bottom = boundaries[1];
				}
			}
		}
		
		System.out.println("MaxSum found is "+maxSum + " at l:"+leftBound + " r:"+rightBound+ "  t:"+top +"  b:"+bottom);
	}
	
	
}
