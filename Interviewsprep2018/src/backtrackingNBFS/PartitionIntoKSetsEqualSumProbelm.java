package backtrackingNBFS;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PartitionIntoKSetsEqualSumProbelm {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		partitionIntoKSetsOfEqualSumExample();
	}

	static void partitionIntoKSetsOfEqualSumExample() {
		int[] arr = {2, 1, 4, 5, 6};
		int K = 3;
		
		/*
		 * we can divide above array into 3 parts with equal
			sum as [[2, 4], [1, 5], [6]]
		 */
		
		//  If total number of partitions are more than N, then
	    // division is not possible
		if(arr.length<K) {
			System.out.println("total elements are lesser than k!");
			return;
		}
		
		int sum = 0;
		for(int i=0;i<arr.length;i++) {
			sum+=arr[i];
		}
		
		// if array sum is not divisible by K then we can't divide
	    // array into K partitions
		if(sum%K!=0) {
			System.out.println("No way we can divide into k subsets of equal size!");
		}
		
		//  the sum of each subset should be subset (= sum / K)
		int sumToMatch = sum/K;
		
		boolean[] taken = new boolean[arr.length];
		int[] subsetsSum = new int[K];
		List<List<Integer>> subsetsIndicies = new ArrayList<>(K);
		
		if(getThePartitionOfKSetsEqualSum(arr, taken, subsetsSum, 0, new ArrayList<Integer>(),
										subsetsIndicies, K, sumToMatch))
		{
			System.out.println("Such Partition is Possible : ");
			System.out.print("[");
			Set<Integer> indicesMatchedSet = new HashSet<>();
			for(List<Integer> l: subsetsIndicies) {
				System.out.print("{");
				for(Integer x: l) {
					indicesMatchedSet.add(x);
					System.out.print(arr[x]+",");
				}
				System.out.print("}");
			}
			
			// last set will be the one that we have not indices in set
			System.out.print("{");
			for(int i=0;i<arr.length;i++) {
				if(!indicesMatchedSet.contains(i)) {
					System.out.print(arr[i]+",");
				}	
			}
			System.out.print("}");
			System.out.print("]");
		}
		else {
			System.out.println("No Such Partition Possible!..");
		}

	}
	
	static boolean getThePartitionOfKSetsEqualSum(int[] arr, boolean[] taken, 
					int[] subsetsSum, int si, List<Integer> curIndicesList,
					List<List<Integer>> subsetsIndicies,
					int K, int sumToMatch) {
		
		
		if(subsetsSum[si] == sumToMatch) {
			
			// if we have reach sith to K-2 that means k splits are possible and last 
			// split will have sum sumToMatch and we dont need to calculate sith==k-1 th
			if(si == K-2) {
				List<Integer> indList = new ArrayList<>(curIndicesList);
				subsetsIndicies.add(indList);
				return true;
			}
			
			// mark these indices are final ans and try further new indices for si+1 positions sum
			List<Integer> indList = new ArrayList<>(curIndicesList);
			subsetsIndicies.add(indList);
			
			if(getThePartitionOfKSetsEqualSum(arr, taken, 
												subsetsSum, si+1, new ArrayList<Integer>(),
												subsetsIndicies, K, sumToMatch)) {
				return true;
			}
			
			subsetsIndicies.remove(subsetsIndicies.size()-1);
			return false;
		}
		
		
		for(int i=0; i<arr.length; i++) {
			if(taken[i]) {
				continue;
			}
			
			int temp = subsetsSum[si] + arr[i];
			// if adding an element to sith sum makes sum greater than sumToMatch we dont take that element
			if(temp <= sumToMatch) {
				// mean we have taken ith element to make subsets[si th sum]
				// mark take ith
				taken[i] = true;
				subsetsSum[si] += arr[i];
				curIndicesList.add(i);
				
				if(getThePartitionOfKSetsEqualSum(arr, taken, subsetsSum, si, curIndicesList,
																subsetsIndicies, K, sumToMatch)){
					return true;
				}
				
				// back track now
				taken[i] = false;
				subsetsSum[si] -= arr[i];
				curIndicesList.remove(curIndicesList.size()-1);
			}
		}
		return false;
		
	}
	
}
