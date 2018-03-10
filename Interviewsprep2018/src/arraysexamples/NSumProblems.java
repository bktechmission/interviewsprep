package arraysexamples;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class NSumProblems {
	public static void main(String[] args) {
		twoSumExampleUnSortedArrayBruteForce();
		
		twoSumExampleUnSortedArray();
		
		twoSumExampleSortingWay();
		
		
		threeSumExample();
		
		fourSumExample();
		fourSumExampleWithHashMap();
		
		fiveSumExample();
		
	}
	
	static void twoSumExampleUnSortedArrayBruteForce() {
		System.out.println("2Sum BruteForce way: ");
		int[] a = {1,3,2,6,4,8,11,4,18,7};
		int sum = 8;
		System.out.println(Arrays.toString(find2SumBF(a,sum)));
		
	}
	
	// O(n^2)
	static int[] find2SumBF(int[]a, int sum) {
		for(int i=0;i<a.length-1;i++) {
			for(int j=i+1;j<a.length;j++) {
				if(a[i]+a[j]==sum) {
					return new int[] {a[i],a[j]};
				}
			}
		}
		return new int[] {};
	}
	
	// a+b = s			BF= n^2
	//  O(N) time and O(N) space
	static void twoSumExampleUnSortedArray() {
		System.out.println("2Sum UnSortedHashMap way: ");
		int[] a = {1,3,2,6,4,8,11,4,18,7};
		int sum = 8;
		
		Map<Integer,Integer> map = new HashMap<>();
		
		for(int i=0;i<a.length;i++) {
			int comp = sum-a[i];
			if(map.containsKey(comp)) {
				System.out.println("entries are {"+ a[map.get(comp)]+", "+a[i] + "}");
			}
			map.put(a[i], i);
		}
	}
	
	// a+b = s			BF= n^2
	//  O(NLogN) time  InPlace
	static void twoSumExampleSortingWay() {
		System.out.println("2Sum Sorting way: ");
		int[] a = {1,3,2,6,4,8,11,4,18,7};
		int sum = 8;
		Arrays.sort(a);
		int i=0,j=a.length-1;
		while(i<j) {
			int curSum = a[i]+a[j];
			if(curSum == sum) {
				System.out.println("entries are {"+ a[i]+", "+a[j] + "}");
				i++;
				j--;
			}
			else if(curSum<sum) {
				i++;
			}
			else {
				j--;
			}
		}
	}
	
	// a+b+c = s			BF= n^3
	// we cant do better than n^2, so better be sort nlogn and then for each a try twoSumAbove Approach 
	// so time complexity is O(n^2) and Inplace
	static void threeSumExample() {
		System.out.println("3Sum Sorting way: ");
		int[] a = {1,3,2,6,4,8,11,4,18,7};
		int sum = 8;
		
		Arrays.sort(a);
		
		for(int i=0;i<a.length-2;i++) {
			int j = i+1; int k = a.length-1;
			while(j<k) {
				int curSum = a[i]+a[j]+a[k];
				if(curSum==sum) {
					System.out.println("entries are {"+ a[i]+", "+a[j] +", "+ a[k]+"}");
					j++;k--;
				}
				else if(curSum<sum) {
					j++;
				}
				else {
					k--;
				}
			}
		}
	}
	
	// a+b+c+d = s		BF= n^4
	// We cant do better than n^3
	static void fourSumExample() {
		System.out.println("4Sum Sorting way: ");
		int[] a = {1,3,2,6,-4,4,8,11,18,7};
		int sum = 8;
		
		Arrays.sort(a);
		
		for(int i=0; i<a.length-3; i++) {
			for(int j=i+1; j<a.length-2; j++) {
				int k = j+1, l = a.length-1;
				while(k<l) {
					int curSum = a[i]+a[j]+a[k]+a[l];
					if(curSum==sum) {
						System.out.println("entries are {"+ a[i]+", "+a[j] +", "+ a[k]+ ","+a[l]+"}");
						k++; l--;
					}
					else if(curSum<sum) {
						k++;
					}
					else {
						l--;
					}
				}
			}
		}
		
	}
	
	static class Pair{
		int i;
		int j;
		
		Pair(int i, int j){
			this.i = i;
			this.j = j;
		}
	}
	// O(n^2k where k is pair size) with O(n^2) space
	static void fourSumExampleWithHashMap() {
		System.out.println("4Sum HashMap way: ");
		int[] a = {1,3,2,6,-4,4,8,11,18,7};
		int sum = 8;
		
		Map<Integer, List<Pair>> map = new HashMap<>();
		
		for(int i=0; i<a.length; i++) {
			for(int j=i+1; j<a.length; j++) {
				int curSum = a[i]+a[j];
				List<Pair> list = map.get(curSum);
				if(list==null) {
					list = new ArrayList<>();
					map.put(curSum, list);
				}
				list.add(new Pair(i,j));
			}
		}
		
		Set<String> set = new HashSet<String>();
		for(int i=0; i<a.length; i++) {
			for(int j=i+1; j<a.length; j++) {
				int diff = sum - (a[i]+a[j]);
				
				List<Pair> list = map.get(diff);
				
				if(list!=null) {
					for(Pair p : list) {
						if(i<p.i && i<p.j && j<p.i && j<p.j) {
							String s = i+""+j+""+p.i+""+p.j;
							char[] c = s.toCharArray();
							Arrays.sort(c);
							s = String.valueOf(c);
							if(!set.contains(s)) {
								System.out.println("entries are {"+ a[i]+", "+a[j] +", "+ a[p.i]+ ","+a[p.j] +"}");
								set.add(s);
							}
						}
					}	
				}
			}
		}
		
	}
	
	// a+b+c+d+e = s		BF= n^5
	// O(n4)
	static void fiveSumExample() {
		System.out.println("5Sum Sorted way: ");
		int[] a = {1,3,2,6,-4,8,11,4,18,7};
		int sum = 8;
		
		Arrays.sort(a);
		
		for(int i=0;i<a.length-4;i++) {
			for(int j=i+1;j<a.length-3;j++) {
				for(int k=j+1;k<a.length-2;k++) {
					int l = k+1;
					int m = a.length-1;
					while(l<m) {
						int curSum = a[i]+a[j]+a[k]+a[l]+a[m];
						if(curSum==sum) {
							System.out.println("entries are {"+ a[i]+", "+a[j] +", "+ a[k]+ ","+a[l] +", "+a[m] +"}");
							l++;
							m--;
						}
						else if(curSum<sum) {
							l++;
						}
						else {
							m--;
						}
					}
				}
			}
		}
	}
	
}