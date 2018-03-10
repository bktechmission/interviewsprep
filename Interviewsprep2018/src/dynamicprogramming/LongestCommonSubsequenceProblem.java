package dynamicprogramming;

import java.util.Arrays;

public class LongestCommonSubsequenceProblem {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		lcsExample();
	}
	static void lcsExample() {
		String s1 = "RAMCFGVBTRDER";
	    String s2 = "OPLIKDRCFEGBTUQDELR";
	 
	    char[] X=s1.toCharArray();
	    char[] Y=s2.toCharArray();
	    int m = X.length;
	    int n = Y.length;
	    
	    int[][] cache = new int[s1.length()][s2.length()];
	    // fill cache with -1 to check if we have counted the value yet or not as zero is a valid ans, we cant use 0
	    for(int[] c:cache) {
	    		Arrays.fill(c, -1);
	    }
	    
	    System.out.println("Base Recursion Length of LCS is" + " " +
	                                  lcs( X, Y, m, n ) );
	    
	    System.out.println("Length of LCS is" + " " +
	    		lcsMemoWay( X, Y, m, n, cache) );
	    
	    System.out.println("BottomUpDP Length of LCS is" + " " +
	    		lcsBottomUpDP( X, Y, m, n ) );
	    
	}
	
	static int lcs(char[] X, char[] Y, int m, int n) {
		if(m==0||n==0) {
			return 0;
		}
		
		if(X[m-1]==Y[n-1]) {
			return 1 + lcs(X,Y,m-1, n-1);
		}
		
		return Math.max(lcs(X,Y,m-1, n), lcs(X,Y,m, n-1));
	}
	
	static int lcsMemoWay(char[] X, char[] Y, int m, int n,int[][] memo) {
		if(m==0||n==0) {
			return 0;
		}
		
		// if we have already calculated ans
		if(memo[m-1][n-1]!=-1) {
			return memo[m-1][n-1];
		}
		
		int result = 0;
		if(X[m-1]==Y[n-1]) {
			result = 1 + lcs(X,Y,m-1, n-1);
		}
		else {
			result = Math.max(lcs(X,Y,m-1, n), lcs(X,Y,m, n-1));
		}
		
		// update cache and return result
		memo[m-1][n-1] = result;
		return memo[m-1][n-1];
	}
	
	static int lcsBottomUpDP(char[] X, char[] Y, int m, int n) {
		int[][]dp = new int[m+1][n+1];
		
		// first col will be zero
		for(int i=0;i<=m;i++) {
			dp[i][0]= 0;
		}
		
		// first row should be zero
		for(int j=0;j<=n;j++) {
			dp[0][j]= 0;
		}
		
		for(int i=1;i<=m;i++) {
			for(int j=1;j<=n;j++) {
				if(X[i-1]==Y[j-1]) {
					dp[i][j] = 1+dp[i-1][j-1];
				}
				else {
					dp[i][j] = Math.max(dp[i-1][j],dp[i][j-1]);
				}
			}
		}
		
		int i=m;
		int j=n;
		StringBuilder sb = new StringBuilder();
		while(i>0&&j>0) {
			if(X[i-1]==Y[j-1]) {
				sb.append(X[i-1]);
				i--;j--;
			}
			else if(dp[i-1][j]>dp[i][j-1]){
				i--;
			}
			else {
				j--;
			}
		}
		
		System.out.println(sb.reverse().toString());
		
		return dp[m][n];
	}
}
