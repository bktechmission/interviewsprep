package interviewsprep;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

public class HeapUsedProblems {

	public static void main(String[] args)
	{
		int[] a = {10,20,3,40,50,2,60,1,4,5,6,7,8,9,11};
		int[] ret = maximizeMyProfit(a);
		System.out.println(Arrays.toString(ret));
		
		int[] b = {1,3,4,11,9,8,10,2};
		two_sumProblemMoreSpace(b,12);
		two_sumProblemMoreTimeV2(b,12);
		
		int[] c = {3,-2,4,-3,-12,2,11,-19,100,-2,10};
		largestSumContiguousSubarray(c);
		findLeastDistanceBetween3Words();
		
	}
	
	public static int[] maximizeMyProfit(int[] a)
	{
		Queue<Integer> pq = new PriorityQueue<Integer>();
		int profit = Integer.MIN_VALUE;
		int buy = 0;
		int sell = 0;
		for(int i=0;i<a.length; i++)
		{
			pq.add(a[i]);
			if((a[i]-pq.peek())>profit)
			{
				sell = a[i];
				buy = pq.peek();
				profit = sell-buy;
			}
		}
		
		System.out.println("Print Priority Queue:");
		for(int x: pq)
		{
			System.out.print(x+" ");
		}
		System.out.print("\n");
		
		int[] ret = new int[3];
		ret[0]= profit;
		ret[1]=buy;
		ret[2]=sell;
		return ret;
		
	}
	
	
	// Onlogn: Search for additive inverse of item= (x-item)
	public static void two_sumProblemMoreTime(int[]a, int x)
	{
		long start = System.nanoTime();
		Arrays.sort(a);		// nlogn
		for(int i=0;i<a.length;i++)
		{
			int index = Arrays.binarySearch(a, (x-a[i]));
			if(index >= 0 && index!=i)
			{
				System.out.println(i+";"+index);
			}
		}
		long end = System.nanoTime();
		
		System.out.println("Time taken = "+(end-start)/1000);
		
	}
	
	public static void two_sumProblemMoreTimeV2(int[]a, int x)
	{
		long start = System.nanoTime();
		Arrays.sort(a);		// nlogn
		int i = 0;
		int j = a.length-1;
		while(i<j)
		{
			int sum = a[i]+a[j];
			if(sum==x)
			{
				System.out.println(i+";"+j);
				i++;j--;
			}
			else if(sum<x)
			{
				i++;
			}
			else
			{
				j--;
			}
		}
		long end = System.nanoTime();
		
		System.out.println("Time taken = "+(end-start)/1000);
		
	}
	
	// O(n) time and O(n) space: Hash Map {Value,List of Indicies}, No sorting required
	public static void two_sumProblemMoreSpace(int[]a, int x)
	{
		long start = System.nanoTime();
		Map<Integer, List<Integer>> map = new HashMap<Integer, List<Integer>>();
		for(int i=0;i<a.length;i++)
		{
			if(!map.containsKey(a[i]))
			{
				map.put(a[i], new ArrayList<Integer>());
			}
			List<Integer> indicies = map.get(a[i]);
			indicies.add(i);
		}
		
		for(int i = 0;i<a.length;i++)
		{
			if(map.containsKey(x-a[i]))
			{
				List<Integer> indicies = map.get(x-a[i]);
				for(int k : indicies)
				{
					if(k!=i)
					{
						System.out.println(i+";"+k);
					}
				}
			}
		}
		long end = System.nanoTime();
		
		System.out.println("Time taken = "+(end-start)/1000);
	}
	
	// Array must have atleast one +ve number: Kadane’s Algorithm: O(n)
	public static void largestSumContiguousSubarray(int[]a)
	{
		// keep 2 counts, sumMax return this. Also sum upto current
		int sumCurrent=0,sumMax=0;
		
		int start = 0, end=0;
		for(int i=0; i<a.length;i++)
		{
			if(sumCurrent==0)
			{
				start=i;
			}
			sumCurrent = sumCurrent+a[i];
			
			if(sumCurrent<0)
			{
				sumCurrent =0;
			}
			/* Do not compare for all elements. Compare only   
	        when  sumCurrent > 0 */
			else if(sumCurrent > sumMax)
			{
				sumMax = sumCurrent;
				end = i;
			}
			
		}
		
		System.out.println("Max sum is : "+ sumMax +", start: " +start+", end: "+end);
		
	}
	
	public static void findLeastDistanceBetween3Words()
	{
		int[] a = {5,9,17};			//job
		int[] b = {4,13,18};		//in
		int[] c = {8,19,21};		//google
		
		int i=0,j=0,k=0;
		int minVal = Integer.MAX_VALUE;
		int currentVal;
		int[] offsets = {0,0,0};
		
		while(i<a.length-1 || j<b.length-1 || k<c.length-1)
		{
			currentVal = maxDist(a[i],b[j],c[k]);
			if(currentVal < minVal)
			{
				minVal = currentVal;
				offsets[0] = i;
				offsets[1] = j;
				offsets[2] = k;
			}
			
			if(a[i]<b[j]&&a[i]<c[k]&&i<a.length-1)
			{
				i++;
			}
			else if(b[j]<c[k]&&j<b.length-1)
			{
				j++;
			}
			else
			{
				k++;
			}
		}
		
		System.out.println("positions are : "+a[offsets[0]] +","+ b[offsets[1]] + ","+c[offsets[2]] +" and min val is "+minVal);
		
	}
	
	public static int maxDist(int x, int y, int z)
	{
		return Math.max(Math.abs(x-y),Math.max(Math.abs(x-z), Math.abs(y-z)));
	}
	
}
