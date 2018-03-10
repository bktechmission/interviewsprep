package dynamicprogramming;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SubstringProblems {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		System.out.println("Longest Palindromic Substring with DP : "
						+longestPalindromicSubstringWithDP("abcabcbamaloppolaji"));
		
		System.out.println("Longest Palindromic Substring with Expand Around Center: "
						+longestPalindromicSubstringWithExpandAroundCenter("abcabcbamaloppolaji"));
		
		String s1 = "abbcaac";
		System.out.println("Number of Palindromic Substrings with Expand Around Center: "
				+(countSubStringsArePalindrome(s1)-s1.length()));
		
		System.out.println("Number of Palindromic Substrings with Expand Around Center 2 loops: "
				+(countSubStringsArePalindrome2ForLoop(s1)-s1.length()));
		
		
		printAllPalindromePartitionsExample();
		
		longestCommonSubstringExample();
	}
	
	static void longestCommonSubstringExample() {
		String s1 = "abcdefg";
		String s2 = "deabcklomo";
		
		int m = s1.length();
		int n = s2.length();
		
		int[][] dp = new int[m+1][n+1];
		int maxLen = 0;
		int end = 0;
		int start = 0;
		int lastStartWas = 0;
		StringBuilder sb = new StringBuilder();
		for(int i=1;i<=m;i++) {
			for(int j=1;j<=n;j++) {
				if(s1.charAt(i-1)==s2.charAt(j-1)) {
					dp[i][j] = 1 + dp[i-1][j-1];
					
					if(dp[i][j]>maxLen) {
						maxLen = dp[i][j];
						end = i-1;
						start = end-(maxLen)+1;
						if(lastStartWas==start) {
							sb.append(s1.charAt(end));
						}
						else {
							sb.setLength(0);
							sb.append(s1.substring(start, end+1));
						}
					}
				}
			}
		}
		
		System.out.println("Longest Common Substring are : "+ sb.toString());
	}
	
	// O(n^2) time and O(n^2) space
	static String longestPalindromicSubstringWithDP(String s) {
		int n = s.length();
		boolean[][] dp = new boolean[n][n];
		
		// single char is always palindrome
		for(int i=0;i<n;i++) {
			dp[i][i] = true;
		}
		
		int start = 0;
		int end = 0;
		int maxLen = 1;
		for(int l=2;l<=n; l++) {
			for(int i=0;i<n-l+1;i++) {
				int j = i+l-1;
				if(s.charAt(i)==s.charAt(j)) {
					if(i+1==j) {
						dp[i][j] = true;	//If there are only 2 characters and both are same
					}
					else {		
						dp[i][j] = dp[i+1][j-1];
					}
					
					if(dp[i][j]) {
						int len = j-i+1;
						if(maxLen<len) {
							maxLen = len;
							start = i;
							end = j;
						}
					}
				}
			}
		}
		
		return s.substring(start, end+1);
	}
	
	static List<String> allPalindromicSubstringPartitionsWithDP(String s) {
		List<String> result = new ArrayList<String>();
		if (s == null)
			return result;
	 
		if (s.length() <= 1) {
			result.add(s);
			return result;
		}
		int n = s.length();
		boolean[][] dp = new boolean[n][n];
		
		for(int l=1;l<=n; l++) {
			for(int i=0;i<n-l+1;i++) {
				int j = i+l-1;
				if(s.charAt(i)==s.charAt(j)) {
					if(l==1||l==2) {
						dp[i][j] = true;	//If there are only 2 characters and both are same
					}
					else {		
						dp[i][j] = dp[i+1][j-1];
					}
					
					if(dp[i][j]) {
						result.add(s.substring(i, j + 1));
					}
				}
			}
		}
		
		System.out.println("min cuts are : "+minCut(dp,n));
		
		return result;
	}
	
	static String longestPalindromicSubstringWithExpandAroundCenter(String s) {
		int start = 0;
		int end = 0;
		int maxLen = 0;
		for(int i=0;i<s.length();i++) {
			int len1 = expAroundCenter(s,i,i);
			int len2 = expAroundCenter(s,i,i+1);
			int len = Math.max(len1, len2);
			if(len>maxLen) {
				maxLen = len;
				start = i-(len-1)/2;
				end = i+(len)/2;
			}
		}
		return s.substring(start, end+1);
	}
	
	static int expAroundCenter(String s, int i, int j) {
		int l=i; int r=j;
		while(l>=0&&r<s.length() && s.charAt(l)==s.charAt(r)) {
			l--;
			r++;
		}
		
		return ((r-1)-(l+1)+1);
	}
	
	static int expAroundCenterCount(String s, int i, int j) {
		int l=i; int r=j;
		int count = 0;
		while(l>=0&&r<s.length() && s.charAt(l)==s.charAt(r)) {
			l--;
			r++;
			count++;
		}
		
		return count;
	}
	
	//O(n^2)
	static int countSubStringsArePalindrome(String s) {
		
		int count = 0;
		for(int i=0;i<s.length();i++) {
			int c1 = expAroundCenterCount(s,i,i);
			int c2 = expAroundCenterCount(s,i,i+1);
			count+= c1+c2;
		}
		return count;
	}
	
	// O(n^2)+O(n^2) = O(n^2)
	static int countSubStringsArePalindrome2ForLoop(String s) {
		
		int count = 0;
		
		// this is for odd length palindrome   aba, center is b
		for(int i=0;i<s.length();i++) {
			for(int j=0; (i+j)<s.length() && (i-j)>=0; j++) {
				if(s.charAt(i-j)!=s.charAt(i+j)) {
					break;
				}
				else {
					count++;
				}
			}
		}
		
		
		// this is for even length palindrome   abba, center is vacant space b/w b&b
		for(int i=0;i<s.length();i++) {
			for(int j=0; (i+j+1)<s.length() && (i-j)>=0; j++) {
				if(s.charAt(i-j)!=s.charAt(i+j+1)) {
					break;
				}
				else {
					count++;
				}
			}
		}
		
		return count;
		
	}
	
	static void printAllPalindromePartitionsExample() {
		String s = "abadebbe";
		List<List<String>> finalResult = new ArrayList<List<String>>();
		List<String> partition = new ArrayList<String>();
		
		getAllPalindrome(s,finalResult,partition,0);
		
		int min = Integer.MAX_VALUE;
		int index = 0;
		for(int i=0; i<finalResult.size();i++) {
			if(finalResult.get(i).size()<min) {
				min = finalResult.get(i).size();
				index = i;
			}
		}
		
		System.out.println("SubStrings after Minimum cuts are "+finalResult.get(index));
		
		System.out.println(finalResult);
		
		System.out.println("All Palindrome substring are : \n"+ allPalindromicSubstringPartitionsWithDP(s));
	}
	
	static void getAllPalindrome(String s, List<List<String>> finalResult, List<String> partition, int start) {
		if(start==s.length()) {
			List<String> finalPartition = new ArrayList<String> (partition);
			finalResult.add(finalPartition);
			return;
		}
		
		for(int i=start+1;i<=s.length();i++) {
			String sub = s.substring(start, i);
			if(isPalindrome(sub, 0, sub.length()-1)) {
				partition.add(sub);
				getAllPalindrome(s,finalResult,partition,i);
				partition.remove(partition.size()-1);
			}
		}
	}
	
	
	/*
	//O(n^3) time and O(n^2) space
	static int minimumPartitionToMakePalindromes(String s) {
		int n = s.length();
		
		int[][] dp = new int[n][n];
		int[][] brackets = new int[n][n];
		
		// single chars are already palindrome so no cut is required
		for(int i=0;i<n;i++) {
			dp[i][i] = 0;
		}
		
		for(int i=0;i<n-1;i++) {
			if(s.charAt(i)!=s.charAt(i+1)) {
				dp[i][i+1] = 1;
			}
		}
		
		for(int l=3; l<=n; l++) {
			for(int i=0; i<n-l+1; i++) {
				int j = i+l-1;
				if(!isPalindrome(s,i,j)) {
					// try to put partition k=[i,j)
					dp[i][j] = Integer.MAX_VALUE;
					for(int k=i;k<j;k++) {
						int val = dp[i][k] + dp[k+1][j];
						if(dp[i][j]>val) {
							dp[i][j] = val;
							brackets[i][j] = k;
						}
					}
				}
			}
		}
		
		for(int[]p: dp) {
			System.out.println(Arrays.toString(p));
		}
		//printPartitions(s,dp,0,n-1);
		
		return dp[0][n-1];
		
	}
	
	static void printPartitions(String s, int[][]brackets, int i, int j) {
		if(isPalindrome(s,i,j)) {
			System.out.println(s.substring(i, j+1));
			return;
		}
		
		System.out.println("(");
		
		printPartitions(s, brackets, i, brackets[i][j]);
		
		printPartitions(s, brackets, brackets[i][j]+1,j);
		
		System.out.println(")");
	}
	*/
	static boolean isPalindrome(String s, int i, int j) {
		while(i<j) {
			if(s.charAt(i)!=s.charAt(j)) {
				return false;
			}
			i++; j--;
		}
		return true;
	}
	
	static int minCut(boolean[][] pal,int n) {
		int[] minCut = new int[n];
		
		Arrays.fill(minCut, Integer.MAX_VALUE);
		
		minCut[0] = 0;
		
		for(int i=1;i<n;i++) {
			if(pal[0][i]) {
				minCut[i] = 0;
			}
			else {
				int temp = Integer.MAX_VALUE;
				for(int j=0;j<i;j++) {
					if(pal[j+1][i] && temp>minCut[j]+1) {
						temp = minCut[j]+1;
					}
				}
				 minCut[i] = temp;
			}
		}
		
		return minCut[minCut.length-1];
	}
	
	
	//https://www.programcreek.com/2014/06/leetcode-shortest-palindrome-java/

}
