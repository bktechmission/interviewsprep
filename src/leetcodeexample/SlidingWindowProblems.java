package leetcodeexample;

public class SlidingWindowProblems {
	
	public static void main(String[] args)
	{
		int set[] = {3, 34, 4, 12, 5, 2};
        int sum = 9;
        int n = set.length;
        if (isSubsetSum(set, n, sum) == true)
           System.out.println("Found a subset with given sum");
        else
           System.out.println("No subset with given sum");
	}
	
	//Subset Sum Problem
	/*
	 * Given a set of non-negative integers, and a value sum, determine if there is a subset of the given set with sum equal to given sum.
	 * Examples: set[] = {3, 34, 4, 12, 5, 2}, sum = 9
	 * Output:  True  //There is a subset (4, 5) with sum 9.
	 * 
	 */
	
	public static boolean isSubsetSum(int set[], int n, int sum)
	{
		// Brute Force approach is generate every subset of set of size which is 2^n and check if any sums to sum
		// this is Exponential approach. 
		// A better but still exponential approach which cuts at either when we find a sum or we exhaust all elements.
		// but still as a tree grows down either by taking that element in consideration or not
		
		// algo is
		/*
		 * We divide isSubsetSum problem into 2 subproblems as
		 * 1) include current element and call isSubsetSum with a, n-1, sum=sum-a[n-1]
		 * 2) exclude current element and call isSubsetSum with a, n-1, sum
		 * Base condition to break are
		 * 1) if(sum==0) return true
		 * 2) if(n==0 and sum!=0) return false
		 */
		
		// Base Cases
		if(sum==0)
			return true;
		if(n==0 && sum!=0)
			return false;
		
		
		// If last element is greater than sum, then ignore it
		if(set[n-1]>sum)
			return isSubsetSum(set, n-1, sum);
		
		/* else, check if sum can be obtained by any of the following
	      	(a) excluding the last element	(b) including the last element  */
		return isSubsetSum(set, n-1, sum) || isSubsetSum(set, n-1, sum-set[n-1]);
	}
}
