package dynamicprogramming;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LongestPalindromicSubsequenceProblem {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		longestPalindromeExample();
	}
	
	
	static void longestPalindromeExample() {
		String s = "cabbardad";
		
		System.out.println("base recursion lps : "+lps(s,0,s.length()-1));
		
		System.out.println("Bottom Up DP  lps : "+lpsWithDP(s));

	}
	
	// Returns the length of the longest palindromic subsequence in seq
	static int lps(String s, int i, int j) {
		// Base Case 1: If there is only 1 character
		if(i==j) {
			return 1;
		}
		
		// Base Case 2: If there are only 2 characters and both are same
		if(s.charAt(i)==s.charAt(j) && i+1==j) {
			return 2;
		}
		
		// If the first and last characters match
		if(s.charAt(i)==s.charAt(j)) {
			return 2 + lps(s,i+1,j-1);
		}
		
		// If the first and last characters do not match
		return Math.max(lps(s,i+1,j), lps(s,i,j-1));
				
	}
	
	static int lpsWithDP(String s) {
		int n = s.length();
		
		int[][] dp = new int[n][n];
		
		// single char is always palindrome
		for(int i=0;i<n;i++) {
			dp[i][i] = 1;
		}
		
		for(int l=2;l<=n; l++) {
			for(int i=0;i<n-l+1;i++) {
				int j = i+l-1;
				if(s.charAt(i)==s.charAt(j)) {
					if(i+1==j) {
						dp[i][j] = 2;	//If there are only 2 characters and both are same
					}
					else {
						
						dp[i][j] = 2+dp[i+1][j-1];
					}
				}
				else {
					dp[i][j] = Math.max(dp[i+1][j], dp[i][j-1]);
				}
			}
		}
		
		return dp[0][n-1];
	}

}
