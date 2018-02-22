package interviewsprep;

import java.util.Random;

public class SortingAlgos {
	public static void main(String[] args)
	{
		int n = 3000000;
		int[] a = new int[n];
		for(int i =0; i<n; i++)
		{
			Random rand = new Random();
			a[i] = rand.nextInt(n) + 1;
		}
		//for(int x : a)
		{
			//System.out.print(x+",");
		}
		
		double start = System.currentTimeMillis();
		//bubbleSort(a);
		//insertionSort(a);
		//selectionSort_nonOptimized(a);
		//selectionSort(a);
		//mergesort(a, 0, a.length-1);  
		//heapsort(a,a.length-1);
		quicksort(a, 0, a.length-1);

		double end = System.currentTimeMillis();
		
		System.out.println("Total sort time : " + (end-start));
		//for(int x : a)
		{
			//if(x!=0)
			//	System.out.print(x+",");
		}
	}
	

	public static void heapsort(int[]a, int n)
	{
		heapify(a,n);			// O(n)
		for(int i = n ; i>=1; i--)			// O(nlogn)
			delmax(a,1,i);
	}
	
	public static void delmax(int[]a, int i, int n)
	{
		if(i<n)
		{
			int temp  = a[i];
			a[i] = a[n];
			a[n] = temp;
			adjust(a,1,n-1);
		}
	}
	
	public static void heapify(int[]a, int n)
	{
		for(int i=n/2; i>=1; i--)
			adjust(a,i,n);
	}
	
	public static void adjust(int[]a, int i, int n)
	{
		int x = a[i];
		int j = 2*i;
		
		while(j<=n)
		{
			if(j<n && a[j]<a[j+1])
				j = j +1;
			
			if(x >= a[j])
				break;
			
			a[j/2] = a[j];
			j = j*2;
		}
		
		a[j/2] = x;
	}
	
	
	public static void mergesort(int[]a, int start, int end)
	{
		if(start<end)
		{
			int mid = (start+end)/2;	
			mergesort(a,start, mid);
			mergesort(a,mid+1, end);
			merge(a, start, mid, end);
		}
	}
	
	public static void merge(int[]a, int start, int mid, int end)
	{
		int i = start;  //0
		int j = mid+1;   //2
		
		int len = end - start + 1;
		int[] b = new int[len];
		int k = 0;
		while(i <= mid && j <= end)
		{
			if(a[i] < a[j])
			{
				b[k++] = a[i++];
			}
			else if(a[i] > a[j])
			{
				b[k++] = a[j++];
			}
			else
			{
				b[k++] = a[j++];
				i++;
			}
			
		}
		
		while(i<=mid)
		{
			b[k++] = a[i++];
		}
		
		while(j<=end)
		{
			b[k++] = a[j++];
		}
		
		for(k=0; k<len; k++)
		{
			a[start+k] = b[k];
		}
		
	}
	
	/*
	// O(nlogn)
	public static void mergesort(int[] a)
	{
		int len = a.length;
		if(len < 2) return;
		
		int mid = len/2;
		
		int[] left = new int[mid];
		int[] right = new int[len-mid];
		
		for(int i = 0; i<mid; i++)
		{
			left[i] = a[i]; 
		}
		
		for(int j = mid; j<len; j++)
		{
			right[j-mid] = a[j];
		}
		
		mergesort(left);			//T(n/2)
		mergesort(right);			//T(n/2)
		merge(left,right,a);
	}
	
	public static void merge(int[] left, int[] right, int[] a)
	{
		int l = left.length;
		int r = right.length;
		
		int i = 0 , j = 0, k = 0;
		
		while(i < l && j < r)
		{
			if(left[i] <= right[j])
			{
				a[k++] = left[i++];
			}
			else
			{
				a[k++] = right[j++];
			}
		}
		
		while(i<l)
		{
			a[k++] = left[i++];
		}
		
		while(j<r)
		{
			a[k++] = right[j++];
		}
	}
*/
	
	public static void quicksort(int[] a, int start, int end)
	{
		if(start < end)
		{
			int pindex = randomized_partitions(a, start, end);			// c*n
			quicksort(a, start, pindex - 1);				// T(n/2)
			quicksort(a, pindex + 1, end);					// T(n/2)
		}
	}
	
	public static int randomized_partitions(int[] a, int start, int end)
	{
		Random rand = new Random();
		int pivotIndex = rand.nextInt((end-start) + 1) + start;		
		int temp = a[pivotIndex];		// Get the random pivot value
		a[pivotIndex] = a[end];
		a[end] = temp;
		
		return partitions(a, start, end);
	}
	
	public static int partitions(int[] a, int start, int end)
	{
		int pivot = a[end];			// End element is pivot
		int pindex = start;			// Start pindex at start
		
		for(int i = start; i < end; i++)
		{
			if(a[i] <= pivot)
			{
				int temp = a[pindex]; 
				a[pindex] = a[i];
				a[i] = temp;
				pindex++;
			}
		}
		
		int temp = a[pindex];
		a[pindex] = a[end];
		a[end] = temp;
		return pindex;
	}
	
	
	
	void heapst(int[]a, int n)
	{
		heapifyme(a,n);
		for(int i=n; i<1;i--)
		{
			delmaxme(a,1,i);
		}
	}
	
	void delmaxme(int[]a,int i, int n)
	{
		if(i<n)
		{
			
			int temp = a[i];
			a[i] = a[n];
			a[n] = temp;
			adjustme(a,1,n-1);
			
		}
	}
	
	
	void heapifyme(int[]a, int n)
	{
		for(int i=n/2;i>=1;i--)
		{
			adjustme(a,i,n);
		}
	}
	
	void adjustme(int[]a, int i, int n)
	{
		int x = a[i];
		int j = 2*i;
		
		while(j<=n)
		{
			if(a[j]<a[j+1])
				j = j+1;
			
			if(x>=a[j])
				break;
			
			a[j/2] = a[j];
			j = 2*j;
		}
		
		a[j/2] = x;
	}
	

	public static void selectionSort(int[]a)
	{
		for(int i=0;i<a.length-1;i++)
		{
			int curr_min_index = i; int curr_min_val = a[i];
			for(int j=i+1;j<a.length;j++)
			{
				if(curr_min_val>a[j])
				{
					curr_min_val = a[j];
					curr_min_index = j;
				}
			}
			if(curr_min_index != i)
			{
				int temp = a[i];
				a[i] = a[curr_min_index];
				a[curr_min_index] = temp;
			}
			
		}
	}
	
	public static void selectionSort_nonOptimized(int[]a)
	{
		for(int i=0;i<a.length-1;i++)
		{
			for(int j=i+1;j<a.length;j++)
			{
				if(a[i]>a[j])
				{
					int temp = a[i];
					a[i] = a[j];
					a[j] = temp;
				}
			}
			
		}
	}
	
	public static void bubbleSort(int[]a)
	{
		for(int i=0;i<a.length-1;i++)
		{
			boolean swapped = false;
			for(int j=0;j<a.length-i-1;j++)
			{
				if(a[j]>a[j+1])
				{
					int temp = a[j];
					a[j] = a[j+1];
					a[j+1] = temp;
					swapped = true;
				}
			}
			if(!swapped)
				break;
		}
	}
	
	public static void insertionSort(int[]a)
	{
		for(int i=1;i<a.length;i++)
		{
			for(int j=i;j>0;j--)
			{
				if(a[j-1]>a[j])
				{
					int temp = a[j-1];
					a[j-1] = a[j];
					a[j] = temp;
				}
				else
				{
					break;
				}
			}
		}
	}
	
}
