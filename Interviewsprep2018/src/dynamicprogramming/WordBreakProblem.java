package dynamicprogramming;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class WordBreakProblem {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		wordbreakproblem();
	}
	
	
	static void wordbreakproblem() {
		String s = "catsanddog";
		Set<String> dic = new HashSet<String>();
		dic.add("cat");
		dic.add("sand");
		dic.add("and");
		dic.add("cats");
		dic.add("dog");
		
		List<String> finalResults = new ArrayList<>();
		checkIfCanBeBrokedInWordsBKTrack(s, dic, 0, s.length(), finalResults, "");
		System.out.println("BackTrack: All Words broken: "+finalResults);
		
		
		System.out.println("\nDP => Can we break into words: "+ checkWordBreakDP(s,dic));
		
		System.out.println("DP => All Words broken: "+ getAllWordBreakDP(s,dic));
		
	}
	
	static void checkIfCanBeBrokedInWordsBKTrack(String s, Set<String> dic, int start, int n, 
			List<String> finalResults,
			String prefix){
		
		if(start == n) {
			finalResults.add(prefix.trim());
			return;
		}
		
		
		for(int p=start+1; p<=n; p++) {
			String sub = s.substring(start, p);
			if(dic.contains(sub)) {
				String newPrefixForDownPass = prefix + " "+ sub;
				checkIfCanBeBrokedInWordsBKTrack(s, dic, p, n, finalResults, newPrefixForDownPass);
			}
		}
	}
	
	
	static boolean checkWordBreakDP(String s, Set<String> dic) {
		int[] dp = new int[s.length()+1];
		Arrays.fill(dp, -1);
		dp[0] = 0;
		
		for(int start=0; start<s.length(); start++) {
			if(dp[start]!=-1) {
				for(int p=start+1; p<=s.length(); p++) {
					String sub = s.substring(start, p);
					if(dic.contains(sub)) {
						dp[p] = start; 
					}
				}
			}
		}
		
		return dp[s.length()]!=-1;
	}
	
	
	static List<String> getAllWordBreakDP(String s, Set<String> dic) {
		
		List<String>[] dp = new ArrayList[s.length()+1];
		Arrays.fill(dp, null);
		dp[0] = new ArrayList<>();
		
		for(int start=0; start<s.length(); start++) {
			if(dp[start]!=null) {
				for(int p=start+1; p<=s.length(); p++) {
					String sub = s.substring(start, p);
					if(dic.contains(sub)) {
						if(dp[p]==null) {
							dp[p] = new ArrayList<>();
						}
						dp[p].add(sub); 
					}
				}
			}
		}
		
		if(dp[s.length()]==null) {
			return new ArrayList<String>();
		}else {
			ArrayList<String> result = new ArrayList<String>();
	        dfsTraversals(dp, result, "", s.length());
	        return result;
		}
	}
	
	static void dfsTraversals(List<String>[] dp, ArrayList<String> result, String prefix, int i) {
		if(i==0) {
			result.add(prefix.trim());
			return;
		}
		
		for(String s : dp[i]) {
			String newPrefixForDownPass = s + " " + prefix;
			dfsTraversals(dp,result, newPrefixForDownPass, i-s.length());
		}
	}
}
