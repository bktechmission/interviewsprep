package interviewsprep;

import java.util.Arrays;

public class FindMedians 
{
	public static void main(String[] args)
	{
		int[] a = {11,20,21,25,29,32};
		System.out.println(median(a,a.length));
		int[] b = {1,15,19,20,22,23};   
		//1,11,15,19,20,20,21,22,23,25,29,32
		
		// 1,2,3,4,5,6,7,8,8,10,10,11
		System.out.println(median2SortedArrayWithSameSize(a,b,a.length));
	}
	

	// O(N): During merging phase of two arrays, at number (temp[n] + temp[n-1])/2
	// O(logN): Median of Medians
	static int median2SortedArrayWithSameSize(int[]a, int[]b,int n)
	{
		
		if (n <= 0)
	        return -1;
		int m1,m2;
		
		if(n==1)
		{
			return (a[0]+b[0])/2;
		}
		if(n==2)
		{
			return (Math.max(a[0],b[0])+Math.min(a[1],b[1]))/2;
		}
		
		m1 = median(a,a.length);
		m2 = median(b,b.length);
		
		if(m1==m2)
		{
			return m1;
		}
		else if(m1<m2)
		{
			if(n%2==0)
			{
				return median2SortedArrayWithSameSize(Arrays.copyOfRange(a, n/2-1, n), Arrays.copyOfRange(b, 0, n/2+1), n/2+1);
			}
			else
			{
				return median2SortedArrayWithSameSize(Arrays.copyOfRange(a, n/2, n), Arrays.copyOfRange(b, 0, n/2+1), n/2+1);
			}
		}
		else
		{
			if(n%2==0)
			{
				return median2SortedArrayWithSameSize(Arrays.copyOfRange(b, n/2-1, n), Arrays.copyOfRange(a, 0, n/2+1),n/2+1);
			}
			else
			{
				return median2SortedArrayWithSameSize(Arrays.copyOfRange(b, n/2, n), Arrays.copyOfRange(a, 0, n/2+1), n/2+1);
			}
		}
		
		
	}
	
	//O(1)
	/* Function to get median of a sorted array */
	static int median(int arr[], int n)
	{
		// size is even
	    if ((n&1) == 0)
	        return (arr[n/2-1] + arr[n/2])/2;
	    else
	        return arr[n/2];
	}
	
}
