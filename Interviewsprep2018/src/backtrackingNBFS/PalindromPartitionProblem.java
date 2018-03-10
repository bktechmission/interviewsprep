package backtrackingNBFS;

import java.util.ArrayList;
import java.util.List;

public class PalindromPartitionProblem {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		partitionStringAsPalindromesExample();
	}
	
	static void partitionStringAsPalindromesExample() {
		String s = "banana";
		
		List<List<String>> finalResult =  new ArrayList<>();
		solveForAllPalindromePartitionsSplits(s, 0, 
				finalResult, new ArrayList<>());
		for(List<String>l:finalResult) {
			System.out.println(l);
		}
		
	}
	
	static void solveForAllPalindromePartitionsSplits(String s, int start, 
			List<List<String>> finalResult, List<String>curList) {
		
		// if we reach the end of s means we have found all splits which are palindromes, add to finalResult list
		if(s.length()==0) {
			List<String> list = new ArrayList<>(curList);
			finalResult.add(list);
			return;
		}
		
		// otherwise try all splits from start+1 till == length
		for(int splitPoint = start+1; splitPoint<=s.length(); splitPoint++) {
			String sub = s.substring(start, splitPoint);
			if(isPalindrome(sub)) {
				curList.add(sub);
				solveForAllPalindromePartitionsSplits(s.substring(splitPoint),0,finalResult,curList);
				curList.remove(sub); 	// backtrack
			}
		}
	}
	
	static boolean isPalindrome(String s) {
		int i = 0;
		int j = s.length()-1;
		while(i<j) {
			if(s.charAt(i)!=s.charAt(j)) {
				return false;
			}
			i++;
			j--;
		}
		
		return true;
	}

}
