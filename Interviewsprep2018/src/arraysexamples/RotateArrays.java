package arraysexamples;

import java.util.Arrays;

public class RotateArrays {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		findMinRotatedExample();
	}
	
	
	static void findMinRotatedExample() {
		int[] a= {6,7,8,9,2,3,4};
		System.out.println(findMinInSortedRotateArray(a));
	}
	
	static int findMinInSortedRotateArray(int[]a){
		int i = 0;
		int j = a.length-1;
		while(i<=j) {
			if(i==j||a[i]<a[j])
			{
				return a[i];
			}
			int mid = (i+j)/2;
			if(a[mid]>=a[i]) {
				i = mid+1;
			}
			else{
				j = mid;
			}
		}
		return -1;
	}
	
	static int findMinInUnsortedArray(int[]a,int i, int j){
		if(i==j) {
			return a[i];
		}
		
		int mid = (i+j)/2;
		int m1 = findMinInUnsortedArray(a,i,mid);
		int m2 = findMinInUnsortedArray(a,mid+1,j);
		return Math.min(m1, m2);
	}
	
	public static void rotateArrayExample() {
		int[]a = {22,33,44,55,66,77,88,99};
		int k=12;
		rotateArray(a,k);
		System.out.println("After rotate : "+Arrays.toString(a));
		
		System.out.println("Min is "+findMinInSortedRotateArray(a));
		
		int[]b= {45,3,7,90,6,1,66,0,33};
		System.out.println("min in unsorted array: "+findMinInUnsortedArray(b,0,b.length-1));
	}
	
	private static void rotateArray(int[]a, int k){
		k=k%a.length;
		reverseArray(a,0,a.length-k-1);
		reverseArray(a,a.length-k,a.length-1);
		reverseArray(a,0,a.length-1);
		
	}
	
	private static void reverseArray(int[]a,int i, int j) {
		while(i<j) {
			int temp = a[i];
			a[i] = a[j];
			a[j] = temp;
			i++;
			j--;
		}
	}

	static int shiftedArrSearch(int[] a, int num) {
		// your code goes here

		int low = 0;
		int high = a.length - 1;

		// give us the partition point
		while (low <= high) {
			if (a[low] <= a[high]) {
				break;
			}
			int mid = (low + high) / 2;

			if (a[low] < a[mid]) {
				low = mid + 1;
			} else {
				high = mid;
			}
		}

		// low will be minima
		// first check on left sorted array
		int check = bsearch(a, 0, low - 1, num);
		if (check != -1) {
			return check;
		}

		// right sorted array
		return bsearch(a, low, a.length - 1, num);

	}

	static int bsearch(int[] a, int start, int end, int num) {
		while (start <= end) {
			int mid = (start + end) / 2;
			if (a[mid] == num) {
				return mid;
			} else if (a[mid] < num) {
				start = mid + 1;
			} else {
				end = mid - 1;
			}
		}
		return -1;
	}
}
