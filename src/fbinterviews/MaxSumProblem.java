package fbinterviews;

public class MaxSumProblem {
	
	public static void main(String[] args)
	{
		int[] a = {-70,-10,2,10,-21,12,1,-3,31};
		//int[] a = {-1,-2,-3};
		findAndPrintSums(a);
		int[] b = {3,2,5,-10,-7};   // Given an array of positive numbers
		printMaxSumOfNonAdjacentElements(b);
	}
	
	
	// Largest Sum Contiguous Subarray: Kadane's algorithm,   O(n)
	public static void findAndPrintSums(int[] a)
	{
		int start = 0;
		int end = 0;
		
		int maxCurr = 0;
		int maxToBeReturn = 0;
		
		int j = 0;
		for(int i=0;i<a.length;i++)
		{
			maxCurr += a[i];
			if(maxCurr > maxToBeReturn)
			{
				maxToBeReturn = maxCurr;
				start = j;
				end = i;
			}
			
			if(maxCurr<0)
			{
				maxCurr = 0;
				j = i+1;
			}
		}
		
		System.out.println("max Sum is "+maxToBeReturn +" and occurs from indices : " +  start+ " to " +  end);
		
	}
	
	
	
	// Given an array of positive numbers, Maximum sum such that no two elements are adjacent
	/*
	 * Maximum sum such that no two elements are adjacent
	Question: Given an array of positive numbers, find the maximum sum of a subsequence with the constraint that no 2 numbers in the sequence should be adjacent in the array.
 	So 3 2 7 10 should return 13 (sum of 3 and 10) or 3 2 5 10 7 should return 15 (sum of 3, 5 and 7).
 	Answer the question in most efficient way.

	Algorithm:
	Loop for all elements in arr[] and 
	maintain two sums 
		incl : where incl = Max sum including the previous element 
		and excl = Max sum excluding the previous element.

	Max sum excluding the current element will be max(incl, excl)
	and max sum including the current element will be excl + current element 
	(Note that only excl is considered because elements cannot be adjacent).

	At the end of the loop return max of incl and excl.
	 */
	// O(n)
	public static void printMaxSumOfNonAdjacentElements(int[] a)
	{
		int incl = a[0];
		int excl = 0;
		
		for(int i=1; i<a.length; i++)
		{
			// Max sum excluding the current element will be max(incl, excl)
			int excl_curr = Math.max(incl,excl);
			
			//max sum including the current element will be excl + current element
			incl = excl + a[i];
			
			// Update excl
			excl = excl_curr;
			
		}
		
		System.out.println("max is : " + Math.max(incl, excl));
	}

}
