package dynamicprogramming;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;
import java.util.Set;

public class DPPractice1 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		longestCommonSubsequenceExample();
		generateMiniPalindromePartitions();
	}
	
	static void longestCommonSubsequenceExample(){
		String s1 = "abcd";
		String s2 = "gikjlabckloabpdd";
		
		/*int l = findLCS(s1,s2, s1.length(),s2.length());
		
		System.out.println("lcs is "+l);
		
		System.out.println("Longest Common Substring : "+longestCommonSubsString(s1,s2));
		
		permutationsExample();
		
		ksproblem();
		
		countNumberOfWaysToMakeChange();
		
		countPathsExample();*/
		
		palindromicNewExample();
	}
	
	static void palindromicNewExample() {
		String s = "abcacbdef";
		System.out.println(logestPalSubstringDP(s));
		
		minimumInsertionPalindrome();
	}
	static String logestPalSubstringDP(String s) {
		boolean[][] dp = new boolean[s.length()][s.length()];
		int m = s.length();
		for(int i=0;i<m;i++) {
			dp[i][i] = true;
		}
		int maxLen = 1;
		int maxStart=0;
		int maxEnd=0;
		for(int l=2; l<=m;l++) {
			for(int i=0;i<m-l+1;i++) {
				int j = i+l-1;
				if(s.charAt(i)==s.charAt(j)) {
					if(i+1==j) {
						dp[i][i] = true;
					}
					else {
						dp[i][j] = dp[i+1][j-1];
					}
				}
				
				if(dp[i][j] && maxLen<(l)) {
					maxLen = l;
					maxStart = i;
					maxEnd = j;
				}
			}
		}
		
		System.out.println("max palindrom is at : "+s.substring(maxStart, maxStart+maxLen));
		return s.substring(maxStart, maxStart+maxLen);
		
	}
	
	
	static void generateMiniPalindromePartitions() {
		String s = "abcbreraaro";
		
		List<String> finalResult = new LinkedList<>();
		
		bfsTraversalOfPartitioning(s, finalResult);
		System.out.println(finalResult);
		
	}
	
	static class PNode{
		String word;
		int level;
		String partitionedSoFar = "";
		PNode(String word, int level, String partitionedSoFar){
			this.word = word;
			this.level = level;
			this.partitionedSoFar = partitionedSoFar;
		}
	}
	
	static void bfsTraversalOfPartitioning(String s, List<String> finalResult) {
		
		Queue<PNode> q = new LinkedList<>();
		
		q.offer(new PNode(s,0,""));
		
		int minLeveFound = Integer.MAX_VALUE;
		Set<String> triedSet = new HashSet<>();
		
		while(!q.isEmpty()) {
			PNode node = q.poll();
			String word = node.word;
			int level = node.level;
			
			if(word.length()==0) {
				if(minLeveFound>level) {
					String res = node.partitionedSoFar;
					finalResult.add(res);
					minLeveFound = level;
					continue;
				}
			}
			
			if(triedSet.contains(word) || minLeveFound<level) {
				continue;
			}
			
			triedSet.add(word);
			
			for(int i=1; i<=word.length(); i++) {
				
				String sub = word.substring(0, i);
				
				System.out.println("sub is "+sub);
				if(isPalindrome(sub,0,sub.length()-1)) {
					System.out.println("pal sub is "+sub);
					q.offer(new PNode(word.substring(i), level+1, node.partitionedSoFar+" "+sub));
				}
			}
		}
	}
	
	static boolean isPalindrome(String word, int s, int e) {
		int l = s;
		int r = e;
		while(l<r) {
			if(word.charAt(l) != word.charAt(r)) {
				return false;
			}
			l++;
			r--;
		}
		return true;
	}
	
	static void minimumInsertionPalindrome() {
		String s = "abcd";
		int maxOverallj = Integer.MIN_VALUE;
		for(int i=s.length()/2;i>=0;i--){
			int j1 = expandAroundCenterNew1(s,i,i);
			int j2 = expandAroundCenterNew1(s,i,i+1);
			int maxj = Math.max(j1, j2);
			
			maxOverallj = Math.max(maxOverallj, maxj);
		}
		StringBuilder sb = new StringBuilder(s.substring(maxOverallj+1));
		String minPalindrome = sb.reverse().toString() + s;
		System.out.println("Min String is "+minPalindrome);
	}
	
	static int expandAroundCenterNew1(String s, int i, int j) {
		int l = i;
		int r = j;
		while(l>=0&&r<s.length()&& s.charAt(l)==s.charAt(r)) {
			l--;
			j++;
		}
		if(l<0) {
			return r;
		}
		return Integer.MIN_VALUE;
	}
	
	static void countPathsExample() {
		int m = 2;
		int n = 3;
		int[][] grid = new int[m][n];
		
		
		int[][]cache =  new int[m][n];
		for(int[]c : cache) {
			Arrays.fill(c, -1);
		}
		
		int totalPaths = countPathsMemo(grid, 0,0,cache);
		System.out.println("total paths are "+totalPaths);
		
		totalPaths = countPathsBottomUp(grid);
		System.out.println("total paths are "+totalPaths);
		
		
		int[][] valueGrid = new int[m][n];
		Random rand = new Random();
		for(int i=0;i<valueGrid.length;i++) {
			for(int j=0;j<valueGrid[0].length;j++) {
				valueGrid[i][j]  = rand.nextInt(20);
			}
		}
		
		for(int[] c: valueGrid) {
			System.out.println(Arrays.toString(c));
		}
		
		System.out.println(maxValueOfMaxValuePath(valueGrid,0,0));
	}
	
	static int maxValueOfMaxValuePath(int[][]grid, int i, int j) {
		if(!isInGrid(grid,i,j)) {
			return 0;
		}
		
		if(isAtEnd(grid,i,j)) {
			return grid[i][j];
		}
		
		return grid[i][j]+Math.max(maxValueOfMaxValuePath(grid,i+1,j) , maxValueOfMaxValuePath(grid,i,j+1));
	}
	
	static int countPaths(int[][]grid, int i, int j) {
		if(!isInGrid(grid,i,j)) {
			return 0;
		}
		if(grid[i][j]==1) {
			return 0;
		}
		
		if(isAtEnd(grid,i,j)) {
			return 1;
		}
		
		return countPaths(grid,i+1,j) + countPaths(grid,i,j+1);
	}
	
	static int countPathsMemo(int[][]grid, int i, int j,int[][]cache) {
		if(!isInGrid(grid,i,j)) {
			return 0;
		}
		
		if(grid[i][j]==1) {
			return 0;
		}

		if(isAtEnd(grid,i,j)) {
			return 1;
		}
		
		if(cache[i][j]!=-1) {
			return cache[i][j];
		}
		
		
		int result = countPathsMemo(grid,i+1,j,cache) + countPathsMemo(grid,i,j+1,cache);
		cache[i][j] = result;
		return cache[i][j];
	}
	
	static int countPathsBottomUp(int[][]grid) {
		int m = grid.length;
		int n = grid[0].length;
		
		int[][]dp = new int[m+1][n+1];
		dp[m-1][n-1] = 1;
		
		for(int i=m-1; i >=0;i--) {
			for(int j=n-1;j>=0;j--) {
				if(i==m-1&&j==n-1) {
					continue;
				}
				dp[i][j] = dp[i+1][j] + dp[i][j+1];
			}
		}
		
		return dp[0][0];
	}
	
	
	static boolean isInGrid(int[][]grid, int i, int j) {
		if(i<0||i>=grid.length || j<0 || j>=grid[0].length) {
			return false;
		}
		
		return true;
	}
	
	static boolean isAtEnd(int[][]grid, int i, int j) {
		if(i==grid.length-1&&j==grid[0].length-1) {
			return true;
		}
		return false;
	}
	
	static void ksproblem() {
		int[] wt = {1,3,4,5,2};
		int[] val = {1,4,5,7,4};
		int W = 9;
		int n = val.length;
		
		System.out.println("max val is "+ kssolve(wt,val, W, n));
	}
	
	static int kssolve(int[]wt, int[]val, int W, int n) {
		if(W==0 || n==0) {
			return 0;
		}
		
		if(W-wt[n-1]<0) {
			return kssolve(wt, val, W, n-1);
		}
		
		return Math.max(val[n-1]+kssolve(wt, val, W-wt[n-1], n-1), kssolve(wt, val, W, n-1));
	}
	
	static int findLCS(String s1, String s2, int m, int n) {
		if(m==0||n==0) {
			return 0;
		}
		
		if(s1.charAt(m-1)==s2.charAt(n-1)) {
			return 1 + findLCS(s1,s2,m-1,n-1);
		}
		
		return Math.max(findLCS(s1,s2,m-1,n),findLCS(s1,s2,m,n-1));
	}
	
	static void countNumberOfWaysToMakeChange() {
		int[] coins = {1,3,4};
		int change = 10;
		
		System.out.println("# of ways to make change : "+countNumberOfWaysToMakeChange(coins, change,coins.length));
		System.out.println("mini coins required: "+findMinCoins(coins, 10));
	}
	
	static int countNumberOfWaysToMakeChange(int[]coins, int change, int n) {
		// if we have made change reach = 0 that means we have found a path to reach zero so just return 1
		if(change==0) {
			return 1;
		}
		
		// if we have not made change 0 but all coins are over that means we cant find a way to make a change and coins are over
		if(n<=0) {
			return 0;
		}
		
		// check if the coin is greater then we have to avoid the coin
		if(change-coins[n-1]<0) {
			return countNumberOfWaysToMakeChange(coins,change, n-1);
		}
		
		// other try both and add including and exluding
		return countNumberOfWaysToMakeChange(coins,change-coins[n-1],n) + countNumberOfWaysToMakeChange(coins,change,n-1);
	}
	
	static void palindromicExample() {
		String s = "agbdba";
		
		int len = longestPalindromicSubsequence(s,0,s.length()-1);
		System.out.println("Longest Palindromic subsequence is of length: "+len);
		
		
		//longestPalindromicSubsequence
		len = longestPalindromicSubsequence(s);
		System.out.println("DP Longest Palindromic subsequence is of length: "+len);
		
		s = "abc";
		int count = findMinInsertionToFormPalindrome(s,0,s.length()-1);
		System.out.println("Min insertion required to make "+ s +" palindrome: "+count);
		
		
		int c = findMinInsertionToFormPalindromeWithDP(s);
		System.out.println("Min insertion required to make "+ s +" palindrome: "+c);
	}
	
	static int longestPalindromicSubsequence(String s, int i, int j) {
		if(i==j) {
			return 1;
		}
		
		if(s.charAt(i)==s.charAt(j))
		{
			if(i+1==j) {
				return 2;
			}
			return 2 +  longestPalindromicSubsequence(s,i+1,j-1);
		}
		
		return Math.max(longestPalindromicSubsequence(s,i,j-1), longestPalindromicSubsequence(s,i+1,j));
	}
	
	//		aba			abc   
	//      ^ ^			 ^^
	static int findMinInsertionToFormPalindrome(String s, int i, int j) {
		
		if(i>j) {
			return Integer.MAX_VALUE;
		}
		
		// for single char is always palindrom so we dont need to insert anything
		if(i==j) {
			return 0;
		}
		
		if(i+1==j) {
			//  aa
			if(s.charAt(i)==s.charAt(j)) {
				return 0;
			}
			else {
				// ab
				return 1;
			}
		}
		
		if(s.charAt(i)==s.charAt(j)) {
			
			return findMinInsertionToFormPalindrome(s,i+1,j-1);
		}
		
		return 1+Math.min(findMinInsertionToFormPalindrome(s,i,j-1), findMinInsertionToFormPalindrome(s,i+1,j));
	}
	
	
	static int findMinInsertionToFormPalindromeWithDP(String s) {
		int n = s.length();
		int[][] dp = new int[n][n];
		
		for(int[]d:dp) {
			Arrays.fill(d, Integer.MAX_VALUE);
		}
		
		for(int l = 1; l<=n; l++) {
			for(int i=0; i<n-l+1; i++) {
				int j = i+l-1;
				if(i==j) {
					dp[i][j] = 0;
				}
				else if(s.charAt(i)==s.charAt(j)) {
					dp[i][j] = dp[i+1][j-1];
				}else {
					dp[i][j] = 1 + Math.min(dp[i+1][j], dp[i][j-1]);
				}
			}
		}
		
		return dp[0][n-1];
	}
	
	static int longestPalindromicSubsequence(String s) {
		int m = s.length();
		int[][] dp = new int[m][m];

		for(int l=1;l<=m;l++) {
			for(int i=0;i<m-l+1;i++) {
				int j = i+l-1;
				if(s.charAt(i)==s.charAt(j)) {
					if(i==j) {
						dp[i][j] = 1;
					}
					else if(i+1==j) {
						dp[i][j] = 2;
					}
					else{
						dp[i][j] = 2 + dp[i+1][j-1];
					}
				}
				else {
					dp[i][j] = Math.max(dp[i][j-1], dp[i+1][j]);
				}
			}
		}
		
		
		for(int[] c:dp) {
			System.out.println(Arrays.toString(c));
		}
		
		String palSubsequence = findPalindromeSubsequence(s,0,s.length()-1,dp);
		System.out.println("longest palindromic subsequence: "+palSubsequence);
		
		return dp[0][s.length()-1];
		
	}
	
	static String findPalindromeSubsequence(String s, int i, int j, int[][]dp) {
		if(i==j) {
			return String.valueOf(s.charAt(i));
		}
		
		if(s.charAt(i) == s.charAt(j)) {
			if(i+1 == j) {
				return String.valueOf(s.charAt(i)) + s.charAt(j);
			}
			return String.valueOf(s.charAt(i)) + findPalindromeSubsequence(s, i+1, j-1,dp) + s.charAt(j);
		}
		
		if(dp[i][j-1] > dp[i+1][j]) {
			return findPalindromeSubsequence(s, i, j-1, dp);
		}
		else {
			return findPalindromeSubsequence(s, i+1, j, dp);
		}
	}
	
	static int findMinCoins(int[]coins, int change) {
		if(change==0) {
			return 0;
		}
		
		int minCoins = change;
		for(int coin:coins) {
			if(change-coin>=0) {
				int temp = findMinCoins(coins,change-coin);
				minCoins = Math.min(minCoins, temp);
			}
		}
		
		return 1+minCoins;
	}
	
	
	
	static int longestCommonSubsString(String s1, String s2) {
		
		int m = s1.length();
		int n = s2.length();
		int[][] dp = new int[m+1][n+1];

		int end = 0;
		int maxLen = 0;
		for(int i=0;i<m;i++) {
			for(int j=0;j<n;j++) {
				if(i==0||j==0) {
					dp[i][j] = 0;
				}
				else if(s1.charAt(i-1)==s2.charAt(j-1)) {
					dp[i][j] = 1 + dp[i-1][j-1];
					if(dp[i][j]>maxLen) {
						maxLen = dp[i][j];
						end = i-1;
					}
				}
			}
		}
		
		System.out.println("Longest Substring : "+s1.substring(end-maxLen+1, end+1));
		return maxLen;
	}
	
	static void permutationsExample() {
		String s1 = "abc";
		List<String> finalResult = new LinkedList<String>();
		
		// npn for string not char[]
		permutation(s1,"",finalResult,0);
		
		System.out.println("Permutations are : ");
		for(int i=0;i<finalResult.size();i++) {
			System.out.println((i+1)+"."+finalResult.get(i));
		}
		
		char[] a = {'6','7','8'};
		finalResult.clear();
		permutation(a,0,a.length,finalResult);
		System.out.println("new Permutations are : ");
		for(int i=0;i<finalResult.size();i++) {
			System.out.println((i+1)+"."+finalResult.get(i));
		}
		
		char[] set = {'1','2','3','4','5'};
		finalResult.clear();
		generatePowerSet(set,new Character[set.length],0,0,finalResult);
		System.out.println("All sets are : ");
		for(int i=0;i<finalResult.size();i++) {
			System.out.println((i+1)+"."+finalResult.get(i));
		}
		
	}
	
	static void permutation(String suffix, String prefix, List<String> finalResult, int start) {
		if(suffix.length()==0) {
			finalResult.add(prefix);
			return;
		}
		
		for(int i=start; i<suffix.length(); i++) {
			permutation(suffix.substring(start, i)+suffix.substring(i+1), prefix+suffix.charAt(i),finalResult,0);
		}
	}
	
	
	static void permutation(char[] a, int start, int n, List<String> finalResult) {
		if(start==n) {
			finalResult.add(String.valueOf(a.clone()));
			return;
		}
		
		for(int i=start;i<a.length;i++) {
			swap(a,start,i);
			permutation(a,start+1,n,finalResult);
			swap(a,start,i);
		}
	}
	
	static void swap(char[] a, int i, int j) {
		char temp = a[i];
		a[i] = a[j];
		a[j] = temp;
	}
	
	static void generatePowerSet(char[]a, Character[]data, int start, int index, List<String> finalResult) {
		if(index==a.length) {
			StringBuilder sb = new StringBuilder();
			for(Character c:data) {
				if(c!=null) {
					sb.append(c);
				}
			}
			finalResult.add(sb.toString());
			return;
		}
		
		// let exclude the start char
		data[index] = null;
		generatePowerSet(a,data,start+1,index+1,finalResult);
		
		// now include
		data[index] = a[start];
		generatePowerSet(a,data,start+1,index+1,finalResult);
	}
}
