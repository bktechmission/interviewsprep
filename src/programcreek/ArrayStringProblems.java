package programcreek;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public class ArrayStringProblems {

	public static void main(String[] args)
	{
		/*int[] a = {2, 7, 11, 15};
		twoSum(a,17);
		Arrays.sort(a);
		twoSumSorted(a,9);
		int[] b = {-1,0,1,2,-1,-4};
		threeSum(b,0);
		int[] c = {1,0,-1,0,-2,2};
		fourSum(c,0);
		
		int[] d = {-1,2,1,-4};
		threeSumClosest(d,1);*/
		
		int[] a = {2,3,-3,-1,-100,2,4,6,7,-200,4,8,9};
		maxSubSumArray(a);
		maxSubSumArrayKadaneAlgo(a);
		
		maxSumSubarrayDP1(a);
		maxSumSubarrayDP2(a);
		
		int[] b = {1,2,-3,4,0,-12,-10,-2,2,-1};
		maxProductSubArray(b);
		
		int[] c = {1,2,3,-1,-1,2,22,-10,-12};
		maxSubArraySumToK(c,3);
		
		sumClosestToKInAnySubArray(c,21);
		
		int[] d = {1,2,3,4,5,6};
		productOfArrayExceptSelf(d);
		
		productOfArrayExceptSelfSpaceOptimized(d);
		
		int[][] e = {
					{1,   2,  3, 4}, 
					{12, 13, 14, 5}, 
					{11, 16, 15, 6}, 
					{10,  9,  8, 7}};
		spiralPrint(e);
		
		String s1 = "abcdefghi";
		String s2 = "mnopcdefgho";
		longestCommonSubstring(s1,s2);
		
	}
	
	// 2 sums unsorted: https://www.programcreek.com/2012/12/leetcode-solution-of-two-sum-in-java/
	public static void twoSum(int[] a, int target)	// O(n) time and O(n) space for hashmap
	{
		Map<Integer, Integer> map = new HashMap<Integer, Integer>();
		for(int i=0;i<a.length;i++)
		{
			int k = target-a[i];
			if(map.containsKey(k)) {
				System.out.println("match found at indicies "+(map.get(k)+1)+" and "+(i+1));
				return;
			}
			map.put(a[i], i);
		}
		System.out.println("no match found.");
	}
	
	// 2 sums sorted : https://www.programcreek.com/2014/03/two-sum-ii-input-array-is-sorted-java/
	public static void twoSumSorted(int[] a, int target)	// already sorted, so On time and o1 space
	{
		int i=0,j=a.length-1;
		while(i<j)
		{
			int sum = a[i]+a[j];
			if(sum==target)
			{
				System.out.println("match found at indicies "+(i+1)+" and "+(j+1));
				return;
			}
			else if(sum<target)
			{
				i++;
			}
			else
			{
				j--;
			}
		
		}
		System.out.println("no match found.");
	}
	
	static class TwoSum{
		private static Map<Integer, Integer> map = new HashMap<Integer, Integer>();
		public static void add(int n)
		{
			if(map.containsKey(n)) {
				map.put(n, map.get(n)+1);
			}
			else
			{
				map.put(n, 1);
			}
		}
		
		public static boolean find(int target) {
			for(Integer k: map.keySet()) {
				int c = target - k;
				if(map.containsKey((c))) {
					if(k==c && map.get(k)<2)
						continue;
					return true;
				}
			}
			return false;
		}
		
	}
	
	//https://www.programcreek.com/2012/12/leetcode-3sum/
	//O(n^2)
	public static void threeSum(int[] a, int target) {
		int n= a.length;
		Arrays.sort(a);
		for(int i=0;i<n-2;i++) {
			if(i==0 || a[i]>a[i-1])
			{
				int j=i+1;
				int k = n-1;
				while(j<k)
				{
					if(a[i]+a[j]+a[k]==target)
					{
						System.out.println("found the match at : "+ a[i]+" "+ a[j]+" "+a[k]);
						
						j++;
						k--;
						
						//handle duplicate here
						while(j<k && a[j]==a[j-1])
						{
							j++;
						}
						while(j<k && a[k]==a[k+1])
						{
							k--;
						}
					}
					else if(a[i]+a[j]+a[k]<target) {
						j++;
					}
					else {
						k--;
					}
				}
			}
		}
	}
	
	// https://www.programcreek.com/2013/02/leetcode-4sum-java/
	// O(n^3)
	public static void fourSum(int[] a, int target) {
		int n= a.length;
		Arrays.sort(a);
		for(int i=0;i<n-3;i++) {
			if(i==0 || a[i]>a[i-1])
			{
				for(int j=i+1; j<n-2;j++)
				{
					if(j==1 || a[j]>a[j-1])
					{
						int k=j+1;
						int l = n-1;
						while(k<l)
						{
							if(a[i]+a[j]+a[k] +a[l]==target)
							{
								System.out.println("found the match at : "+ a[i]+" "+ a[j]+" "+a[k] + " "+a[l]);
								
								k++;
								l--;
								
								//handle duplicate here
								while(k<l && a[k]==a[k-1])
								{
									k++;
								}
								while(k<l && a[l]==a[l+1])
								{
									l--;
								}
							}
							else if(a[i]+a[j]+a[k] +a[l]<target) {
								k++;
							}
							else {
								l--;
							}
						}
					}
					
				}
			}
		}
	}
	
	// https://www.programcreek.com/2013/02/leetcode-3sum-closest-java/
	// O(N^2)
	public static void threeSumClosest(int[] a, int target) {
		int n =a.length;
		Arrays.sort(a);
		System.out.println("array after sort is "+ Arrays.toString(a));
		int min = Integer.MAX_VALUE;
		
		int ii=0,jj=0,kk = 0;
		
		for(int i=0;i<n-2;i++)
		{
			if(i==0 || a[i]>a[i-1])
			{
				int j = i+1;
				int k = n-1;
				while(j<k)
				{
					int sum = a[i] + a[j] + a[k];
					
					int diff = Math.abs(sum-target);
					
					if(diff==0)
					{
						return;
					}
					
					if(diff<min)
					{
						min = diff;
						ii =i;
						jj=j;
						kk=k;
					}
					
					if(sum<target)
					{
						j++;
					}
					else {
						k--;
					}
				}
			}
		}
		System.out.println("found closest to sum as three sums as "+ a[ii] + " "+ a[jj] +" "+a[kk]);
	}
	
	// http://www.geeksforgeeks.org/largest-sum-contiguous-subarray/ 
	//Kadane's algorithm is to look for all positive contiguous segments
	public static void maxSubSumArray(int[] a)
	{
		int curr_sum =0;
		int max_sum = 0;
		int start=0; int end=0;
		for(int i=0;i<a.length;i++) {
			curr_sum =0;
			for(int j=i;j<a.length;j++)
			{
				curr_sum+=a[j];
				if(max_sum<curr_sum)
				{
					max_sum = curr_sum;
					start = i;
					end = j;
				}
			}
		}
		System.out.println("max sum subarray is "+ max_sum +" which starts at index "+start+" end at index "+end);
	}
	
	public static void maxSubSumArrayKadaneAlgo(int[] a) {
		int currSum = 0;
		int maxSumSoFar = 0;
		int start=0, s=0, end=0;
		for(int i=0;i<a.length;i++)
		{
			currSum+=a[i];
			if(currSum<0)
			{
				currSum=0;
				s = i+1;
			}
			else if(maxSumSoFar<currSum)
			{
				maxSumSoFar = currSum;
				start = s;
				end = i;
			}
		}
		System.out.println("max sum subarray is "+ maxSumSoFar +" which starts at index "+start+" end at index "+end);
		
	}
	
	
	public static void maxSumSubarrayDP1(int[] a) {
		int[] max =  new int[a.length+1];
		max[0] = a[0];
		int result = Integer.MIN_VALUE;
		for(int i=0;i<a.length;i++)
		{
			max[i+1] = Math.max(a[i], max[i]+a[i]);
			result = Math.max(result, max[i+1]);
					
		}
		System.out.println("max sum is "+result);
	}
	
	public static void maxSumSubarrayDP2(int[] a) {
		int max = Integer.MIN_VALUE;
		int currMax = 0;
		for(int i=0;i<a.length;i++)
		{
			currMax = Math.max(a[i], currMax+a[i]);
			max = Math.max(max, currMax);
					
		}
		System.out.println("max sum is "+max);
	}
	
	public static void maxProductSubArray(int[] a)
	{
		int[] max = new int[a.length+1];
		int[] min = new int[a.length+1];
		
		max[0] = 1;
		min[0] = 1;
		int start=0;
		int end=0;int s =0;
		int result = Integer.MIN_VALUE;
		for(int i=0;i<a.length;i++)
		{
			if(a[i]>0)
			{
				max[i+1] = Math.max(a[i], max[i]*a[i]);
				if(a[i]>max[i]*a[i])
				{
					s = i;
				}
				
				min[i+1] = Math.min(a[i], min[i]*a[i]);
				
			}else if(a[i]<0)
			{
				
				max[i+1] = Math.max(a[i], min[i]*a[i]);
				if(a[i]>min[i]*a[i])
				{
					s = i;
				}
				min[i+1] = Math.min(a[i], max[i]*a[i]);
			}
			else {
				s = i+1;
				max[i+1] = 1;
				min[i+1] = 1;
			}
			if(result<max[i+1]) {
				start = s;
				end = i;
			}
			result = Math.max(result, max[i+1]);
			
		}
		
		System.out.println("max product is at "+result  +" which starts at index "+start+" end at index "+end);
	}
	
	
	public static void maxSubArraySumToK(int[] a, int k)
	{
		Map<Integer, Integer> lookup = new HashMap<Integer, Integer>();
		int currSum = 0;
		int maxLen = 0;
		int end = 0;
		for(int i=0;i<a.length;i++)
		{
			currSum+=a[i];
			int diff = currSum - k;
			
			if(lookup.containsKey(diff))
			{
				if(maxLen<(i-lookup.get(diff))) {
					end = i;
				}
				maxLen = Math.max(maxLen, i-lookup.get(diff));
			}
			
			if(!lookup.containsKey(currSum)) {
				lookup.put(currSum, i);
			}
			
		}
		if(maxLen!=0)
		{
			System.out.println("found maxArray which sums to K start at k "+ (end-maxLen+1) +" end at "+end+"  and sums to k "+k);
		}

		else {
			System.out.println("did not find any subarray sums to k");
		}
	}
	
	public static void sumClosestToKInAnySubArray(int[] a, int k)
	{
		TreeMap<Integer,Integer> map = new TreeMap<Integer,Integer>();
		
		int currSum = 0;
		int maxDiff = Integer.MIN_VALUE;
		int start  = 0;
		int end = 0;
		for(int i=0;i<a.length;i++) {
			currSum+=a[i];
			
			int diff = currSum-k;
			if(diff == 0) {
				System.out.println("We have found exact match sum at start at "+0 +" end at "+i);
				return;
			}

			Integer ceiling = map.ceilingKey(diff);
			if(ceiling!=null)
			{
				if(maxDiff<currSum-ceiling) {
					start = map.get(ceiling) + 1;
					end = i;
				}
				maxDiff = Math.max(maxDiff, currSum-ceiling);
			}
			
			map.put(currSum, i);
			
		}
		
		if(maxDiff>0) {
			System.out.println("Found closest sum to K start at "+ start +"  end at "+ end+ "  k was "+k +"  and match found was "+ maxDiff);
		}
		else {
			System.out.println("did mot find any matching closest sums");
		}
	}
	
	//https://www.programcreek.com/2014/07/leetcode-product-of-array-except-self-java/
	public static void productOfArrayExceptSelf(int[] a)
	{
		int n = a.length;
		int [] left = new int[n];
		int [] right = new int[n];
		int[] result = new int[n];
		
		left[0] = 1;
		right[n-1] =1;
		
		
		//first fill from left 
		for(int i=0;i<n-1;i++) {
			left[i+1] = left[i]*a[i];
		}
		
		// now from right to left
		for(int j=n-1;j>0;j--)
		{
			right[j-1] = right[j]*a[j];
		}
		
		
		// now multiply
		for(int i=0;i<n;i++)
		{
			result[i] = left[i]*right[i];
		}
		
		System.out.println("product is "+ Arrays.toString(result));
	}
	
	public static void productOfArrayExceptSelfSpaceOptimized(int[] a)
	{
		int n = a.length;
		int[] result = new int[n];
		
		result[n-1] = 1;
		// now from right to left
		for(int j=n-1;j>0;j--)
		{
			result[j-1] = result[j]*a[j];
		}
		
		// now fill from left to right
		int left =1;
		for(int j=0;j<n;j++)
		{
			result[j] = result[j]*left;
			left*=a[j];
		}

		System.out.println("product is "+ Arrays.toString(result));
	}
	
	public static void spiralPrint(int[][] a) {
		int top =0;
		int bottom = a.length-1;
		int left =0;
		int right = a[0].length-1;
		int[] result = new int[a.length*a[0].length];
		int index=0;
		while(true)
		{
			// left to right
			for(int i=left; i<=right;i++)
			{
				result[index++] = a[top][i];
			}
			top++;
			
			if(top>bottom) {
				break;
			}
			
			// top to bottom
			for(int i=top; i<=bottom;i++)
			{
				result[index++] = a[i][right];
			}
			
			right--;
			
			if(right<left) {
				break;
			}
			
			// right to left
			for(int i=right; i>=left;i--)
			{
				result[index++] = a[bottom][i];
			}
			
			bottom--;
			if(top>bottom) {
				break;
			}
			
			// bottom to top
			for(int i=bottom; i>=top;i--)
			{
				result[index++] = a[i][left];
			}
			
			left++;
			if(right<left) {
				break;
			}
		}
		System.out.println("array to "+ Arrays.toString(result));
	}
	
	// longest common substring
	public static void longestCommonSubstring(String s1, String s2) {
		int[][] m = new int[s1.length()][s2.length()];
		int maxlen = 0;
		int maxStringStartAt = 0;
		int previousStart = 0;
		StringBuilder sb = new StringBuilder();
		for(int i=0;i<s1.length();i++)
		{
			for(int j=0;j<s2.length();j++) {
				if(s1.charAt(i)!=s2.charAt(j))
				{
					m[i][j] = 0;
				}
				else {
					if(i==0 || j==0) {
						m[i][j] = 1;
					}
					else {
						m[i][j] = m[i-1][j-1] + 1;
					}
					
					if(m[i][j]>maxlen) {
						maxlen = m[i][j];
						maxStringStartAt = i-maxlen+1;
						if(maxStringStartAt == previousStart )
						{
							sb.append(s1.charAt(i));	
						}
						else {
							previousStart = maxStringStartAt;
							sb.setLength(0);
							sb.append(s1.substring(maxStringStartAt,maxStringStartAt+maxlen));
						}
					}
				}
			}
		}
		System.out.println("max substring is "+ sb.toString());
	}
	
	//https://www.programcreek.com/2014/09/find-a-path-in-a-matrix/
	
	
	//https://www.programcreek.com/2014/05/leetcode-longest-increasing-path-in-a-matrix-java/
	
	//https://www.programcreek.com/2014/04/leetcode-search-a-2d-matrix-ii-java/
	
	//https://www.programcreek.com/2013/01/leetcode-search-a-2d-matrix-java/
		
	//https://www.programcreek.com/2016/08/leetcode-kth-smallest-element-in-a-sorted-matrix-java/
	
	//https://www.programcreek.com/2014/05/leetcode-ugly-number-java/
	
	// https://www.programcreek.com/2014/05/leetcode-super-ugly-number-java/
	//https://www.youtube.com/watch?v=WxEPKvlidKE
	
	
	// Median of 2 sorted arrays same size
	
	// Median of 2 sorted arrays n,m size
	// https://www.youtube.com/watch?v=LPFhl65R7ww
	
	
}
