package leetcodeexample;

import java.util.Arrays;

public class DynamicProgrammingExamples {
	public static void main(String[] args) {
		longestPalindromicExample();
	}
	
	
	static void longestPalindromicExample() {
		String s = "FGBCDBCBDE";
		System.out.println("LPS BruteForceWay: "+longestPalindromicSubstring(s));
		System.out.println("LPS DPForceWay: "+logestPalinSubstringDP(s));
		
	}
	
	static String logestPalinSubstringDP(String s) {
		int n =s.length();
		boolean[][]dp = new boolean[n][n];
		
		
		int maxLen=1; int maxStart=0;
		
		// single chars are always palindrome
		for(int i=0;i<n;i++) {
			dp[i][i] = true;
		}
		
		// now try for len= 2 to n-1
		for(int k=2;k<=n;k++) {
			for(int i=0;i<n-k+1;i++) {
				int j=i+k-1;
				if(k==2&&s.charAt(i)==s.charAt(j)) {
					dp[i][j] = true;
				}
				else if(dp[i+1][j-1] && s.charAt(i)==s.charAt(j)) {
					dp[i][j] = true;
				}
				if(dp[i][j] && maxLen<k) {
					maxLen=k;
					maxStart = i;
				}
			}
		}
		
		String finalAns = s.substring(maxStart, maxStart+maxLen);
		
		return finalAns;
	}

	static String longestPalindromicSubstring(String s){
		return bruteForce(s);
	}
	
	static String bruteForce(String s){
		int maxLen =0;
		int startMax = 0;
		for(int i=0;i<s.length();i++) {  // m 
			for(int j=i+1;j<=s.length();j++) {	//m
				String subs = s.substring(i, j);		// On
				char[] chars = subs.toCharArray();  // On
				if(isPalindrome(chars)) {			// On
					if(maxLen<chars.length) {
						maxLen = chars.length;
						startMax = i;
					}
				}
			}
		}
		
		return s.substring(startMax,startMax+maxLen);
	}
	
	
	static boolean isPalindrome(char[] a){
		int start = 0;
		int end = a.length-1;
		while(start<end) {
			if(a[start]!=a[end]) {
				return false;
			}
			start++;
			end--;
		}
		return true;
	}
}


