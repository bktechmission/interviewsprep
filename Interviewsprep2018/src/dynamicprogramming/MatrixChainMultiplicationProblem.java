package dynamicprogramming;

import java.util.Arrays;

public class MatrixChainMultiplicationProblem {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		matrixChainExample();
	}
	
	static void matrixChainExample() {
		int[] p = {10,20,10,20,40,20,30,50,40,60,20,90,10,30,40,50,20,30,40,10};
		long start = System.currentTimeMillis();
		System.out.println(" base recursion : ");
		System.out.println(costOfMatrixChainMultiplication(p,1,p.length-1));
		long end = System.currentTimeMillis();
		System.out.println("time took with base recursion : "+(end-start)+" millis");
		
		start = System.currentTimeMillis();
		int[][] cache = new int[p.length][p.length];
		for(int[] c:cache) {
			Arrays.fill(c, -1);
		}
		System.out.println("  \n\nmemo+recursion : ");
		System.out.println(costOfMatrixChainMultiplicationWithMemo(p,1,p.length-1,cache));
		end = System.currentTimeMillis();
		System.out.println("time took with memo+recursion : "+(end-start)+" millis");
		
		start = System.currentTimeMillis();
		System.out.println("  \n\nDP Bottom Up : ");
		System.out.println("\n"+costOfMatrixChainMultiplicationWithTabulationBottomUp(p));
		end = System.currentTimeMillis();
		System.out.println("time took with DP Bottom Up : "+(end-start)+" millis");
	}
	
	static int costOfMatrixChainMultiplication(int[]p, int i, int j) {
		// cost is zero when multiplying one matrix.
		if(i==j) {
			return 0;
		}
			
		int min = Integer.MAX_VALUE;
		for(int k=i;k<j;k++) {
			int cost = costOfMatrixChainMultiplication(p,i,k)
						+ costOfMatrixChainMultiplication(p,k+1,j)
						+ p[i-1]*p[k]*p[j];
			if(min>cost) {
				min = cost;
			}
		}
		
		return min;
	}
	
	
	static int costOfMatrixChainMultiplicationWithMemo(int[]p, int i, int j, int[][] cache) {
		// cost is zero when multiplying one matrix.
		if(i==j) {
			return 0;
		}
		if(cache[i][j]!=-1) {
			return cache[i][j];
		}
		int min = Integer.MAX_VALUE;
		for(int k=i;k<j;k++) {
			int cost = costOfMatrixChainMultiplicationWithMemo(p,i,k,cache)
						+ costOfMatrixChainMultiplicationWithMemo(p,k+1,j,cache)
						+ p[i-1]*p[k]*p[j];
			if(min>cost) {
				min = cost;
			}
		}
		cache[i][j] = min;
		return min;
	}
	
	static int costOfMatrixChainMultiplicationWithTabulationBottomUp(int[]p) {
		int n = p.length;
		
		//Minimum number of scalar multiplications
	    //needed to compute the matrix A[i]A[i+1]...A[j] =
	    //A[i..j] where dimension of A[i] is p[i-1] x p[i] */
		int[][] dp = new int[n][n];
		
		// bracket[i][j] stores optimal break point in
	    // subexpression from i to j.
		int[][] bracket = new int[n][n];
		
		// cost is zero when multiplying one matrix.
		for(int i=1;i<n;i++) {
			dp[i][i] = 0;
		}
		
		// l is chain length.
		for(int l = 2; l<n; l++) {
			for(int i = 1; i<n-l+1; i++) {
				int j = i+l-1;
				dp[i][j] = Integer.MAX_VALUE;
				for(int k=i;k<j;k++) {
					int temp = (dp[i][k] + dp[k+1][j])+ (p[i-1]*p[k]*p[j]);
					if(temp<dp[i][j])
					{
						dp[i][j] = temp;
						// Each entry bracket[i,j]=k shows
	                    // where to split the product arr
	                    // i,i+1....j for the minimum cost
						bracket[i][j] = k;
					}
				}
			}
		}
		
		// The first matrix is printed as 'A', next as 'B',
	    // and so on
		printBrackets(bracket,1,bracket[1].length-1);
		return dp[1][n-1];
	}
	
	static char name = 'A';
	static void printBrackets(int[][]bracket, int i, int j) {
		// If only one matrix left in current segment
		if(i==j) {
			System.out.print(name);
			name++;
			return;
		}
		
		System.out.print("(");
		
		// Recursively put brackets around subexpression
	    // from i to bracket[i][j].
		printBrackets(bracket,i,bracket[i][j]);
		
		 // Recursively put brackets around subexpression
	    // from bracket[i][j] + 1 to j.
		printBrackets(bracket,bracket[i][j]+1,j);
		
		System.out.print(")");
		
	}
	
}
