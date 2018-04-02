package backtrackingNBFS;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

public class LargestDivisibleSubsetProblem {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		largestSubSetDivExample();
		
	}
	
	static void largestSubSetDivExample() {
		int[] a = {2,4,10,5,3,6,8,20,21,100};
		Arrays.sort(a);
		List<Integer> finalResult = new ArrayList<>();
		List<Integer> result = new ArrayList<>();
		getLargestSubsets(a,0,result,finalResult);
		
		System.out.println(finalResult);
		
		
		int[] b = {2,4,10,5,3,6,8,20,21,100};
		System.out.println("Longest Increasing Subsequence : ");
		Deque<Integer> list = findLongestIncreasingSubsequence(b);
		while(!list.isEmpty()) {
			
			System.out.print(list.pop()+", ");
		}
		
		list = findLongestDivisibleSubset(b);
		System.out.println("\nLogest Divisible Subsets: ");
		while(!list.isEmpty()) {
			
			System.out.print(list.pop()+", ");
		}
	}
	
	static void getLargestSubsets(int[] a,int start, List<Integer>result, List<Integer>finalResult) {
		if(result.size()>finalResult.size()) {
			finalResult.clear();
			finalResult.addAll(result);
		}
		
		if(start==a.length) {
			return;
		}
		
		for(int i = start; i<a.length; i++) {
			if(result.size()==0) {
				result.add(a[i]);
				getLargestSubsets(a,i+1,result,finalResult);
				result.remove(result.size()-1);
			}
			else {
				int top = result.get(result.size()-1);
				if(a[i]%top==0) {
					result.add(a[i]);
					getLargestSubsets(a,i+1,result,finalResult);
					result.remove(result.size()-1);
				}
			}
		}
	}
	
	
	static Deque<Integer> findLongestIncreasingSubsequence(int[]a) {
		
		int m = a.length;
		int[] lis = new int[m];
		int[] index = new int[m];
		
		Arrays.fill(lis, 1);
		Arrays.fill(index, -1);
		
		int maxLen = Integer.MIN_VALUE;
		int maxIndex = -1;
		
		for(int i=1; i<m; i++) {
			for(int j=0; j<i; j++) {
				if(a[j] < a[i] && lis[j]+1 > lis[i]) {
					lis[i] = lis[j] + 1;
					index[i] = j;
				}
			}
			
			if(maxLen<lis[i]) {
				maxLen = lis[i];
				maxIndex = i;
			}
		}
		
		Deque<Integer> lisResult = new LinkedList<>();
		int i = maxIndex;
		while(i>=0) {
			lisResult.push(a[i]);
			i = index[i];
		}
		
		return lisResult;
	}
	
	static Deque<Integer> findLongestDivisibleSubset(int[]a) {
		
		int m = a.length;
		int[] lis = new int[m];
		int[] index = new int[m];
		
		Arrays.fill(lis, 1);
		Arrays.fill(index, -1);
		
		int maxLen = Integer.MIN_VALUE;
		int maxIndex = -1;
		
		for(int i=1; i<m; i++) {
			for(int j=0; j<i; j++) {
				if(a[i]%a[j]==0 && lis[j]+1 > lis[i]) {
					lis[i] = lis[j] + 1;
					index[i] = j;
				}
			}
			
			if(maxLen<lis[i]) {
				maxLen = lis[i];
				maxIndex = i;
			}
		}
		
		Deque<Integer> lisResult = new LinkedList<>();
		int i = maxIndex;
		while(i>=0) {
			lisResult.push(a[i]);
			i = index[i];
		}
		
		return lisResult;
	}
}
