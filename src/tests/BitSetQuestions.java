package tests;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class BitSetQuestions {
	public static void main(String[] args)
	{
		double random = Math.random();
		System.out.println("random number is :" + ((random*(52-0))+0));
		
		int[]a={1,2,3,4,5,6,7};
		shiftKRightAnArray(a,3);
		Arrays.stream(a).forEach(e->System.out.print(e));
		Integer[] b = {2,3,4,5,6,7,8,9};
		List<Integer> c = Arrays.stream(a).boxed().collect(Collectors.toList());
		for(int k:c)
		{
			System.out.println(k);
		}
		
		double x = -123.3221;
		long p = Double.doubleToLongBits(x);
		System.out.println("double in bits:");
		for(int i=63;i>=0;i--)
		{
			System.out.print((p>>>i&1));
		}
		System.out.println();
		System.out.println(p);
		System.out.println((int)p^(p>>>32));
		
		System.out.println((p&(0x7ff000000000000L))>>>63);
		
		ForkJoinPool commonPool = ForkJoinPool.commonPool();
		System.out.println(commonPool.getParallelism());    // 3
	}
	
	public int singleNumberWithRepeat3Times(int[]a)
	{
		// all numbers repeat 3 times except one
		int[] count = new int[32];
		Arrays.fill(a, 0);
		int result =0;
		for(int i=0;i<32;i++)
		{
			for(int j=0;j<a.length;j++)
			{
				if(((a[j]>>i)&1)==1)
				{
					count[i]++;
				}
			}
			result|=(count[i]%3)<<i;
		}
		return result;
		/*
		 * Case 1: l%k != 0. (k can be multiples of l, e.g. 3 and 1)

			Solution: Recall "result|=((key%3)<<i);" for leetcoder's initial post. Replace key%3 with: (key%k)/(l%k).

			Case 2: l%k == 0. (e.g. k=1 and l=2) Solution: Replace key%3 with: (key - key%l)/l
		 */
	}
	
	
	public int singleOddNumberWithRepeatOnesEvenTimes(int[]a)
	{
		int result = 0;
		for(int i=0;i<a.length;i++)
		{
			result^=a[i];
		}
		return result;
	}

	// find an element in rotated array
	public int findAnElementInRotatedArray(int[] a, int k, int l, int r)		// logn
	{
		if(l>r)
			return -1;
		int m = (l+r)/2;
		if(a[m]==k)
			return m;
		if(a[l]<=a[m])
		{
			if(k>=a[l]&&k<=a[m])
				return findAnElementInRotatedArray(a,k,l,m-1);
			return findAnElementInRotatedArray(a,k,m+1,r);
		}
		if(k>=a[m]&&k<=a[r])
			return findAnElementInRotatedArray(a,k,m+1,r);
		return findAnElementInRotatedArray(a,k,l,m-1);
	}
	
	
	public int findMinElementInRotatedArray(int[]a)		//O(logn)
	{
		int l=0,r=a.length-1;
		while(l<r&&a[l]>=a[r])
		{
			int m=(l+r)/2;
			if(a[m]>a[r])
			{
				l = m+1;
			}
			else
			{
				r = m;
			}
		}
		return a[l];
	}
	
	public void rotateASquareImage(int[][]a)
	{
		int n = a.length;
		for(int layer=0;layer<n/2;layer++)
		{
			int first = layer;
			int last = n-layer-1;
			for(int i=first;i<=last;i++)
			{
				int offset = i-first;
				int top = a[first][i];
				a[first][i] = a[last-offset][first];
				a[last-offset][last] = a[last][last-offset];
				a[last][last-offset] = a[i][last];
				a[i][last] = top;
			}
		}
	}
	
	public static void shiftKRightAnArray(int[]a, int k)
	{
		int n = a.length;
		int shift = k%n;
		reverseArray(a,n-shift,n-1);
		reverseArray(a,0,n-shift-1);
		reverseArray(a,0,n-1);
	}
	
	public static void reverseArray(int[]a,int start, int end)
	{
		while(start<end)
		{
			int temp = a[start];
			a[start++] = a[end];
			a[end--] = temp;
		}
	}
	
	public int[] find2SumPairInRotatedArray(int[]a, int t)
	{
		int i=0;
		for(i=0;i<a.length-1;i++)
		{
			if(a[i]>=a[i+1])
				break;
		}
		int l = i+1;
		int r = i;
		int n = a.length;
		while(l!=r)
		{
			if(a[l]+a[r]==t)
				return new int[] {l,r};
			else if(a[l]+a[r] < t)
			{
				l = (l+1)%n;
			}
			else
			{
				r = (10+r-1)%n;
			}
		}
		return new int[]{};
	}
}
