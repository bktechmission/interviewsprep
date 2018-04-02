package dynamicprogramming;

import java.util.Arrays;

public class OptimalBinarySearchTreeProblem {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		minCostBSTExample();
		
	}
	
	static void minCostBSTExample() {
		int[] input = {10,12,20,35,46};
		int[] freq = {34,8,50,21,16};
		
		int cost = minCostRec(input, freq,0, input.length-1, 1);
		System.out.println("min cost is : "+cost);
		System.out.println("min cost with DP is : "+minCostRecWithDP(input,freq));
		
	}
	
	static int minCostRec(int[] input, int[] freq, int low, int high, int level) {
		if(low>high) {
			return 0;
		}
		
		int min = Integer.MAX_VALUE;
		
		for(int rootIndex = low; rootIndex <= high; rootIndex++) {
			int temp = minCostRec(input, freq, low, rootIndex-1, level+1) 
					+ minCostRec(input, freq, rootIndex+1, high, level+1)
					+ level*freq[rootIndex];
			if(temp<min) {
				min = temp;
			}
		}
		return min;
	}
	
	
	static int minCostRecWithDP(int[] input, int[] freq) {
		int n = input.length;
		int[][] dp = new int[n][n];
		int[][] rootsPlacement =  new int[n][n];
		
		for(int i=0;i<n;i++) {
			dp[i][i] = freq[i];
		}
		
		for(int l=2; l<=n; l++) {
			for(int i=0;i<n-l+1;i++) {
				int j = i+l-1;
				int sum = sumOfFreq(freq,i,j);
				int min = Integer.MAX_VALUE;
				int root = -1;
				for(int k=i;k<=j;k++) {
					int temp =     ( (k-1 < i) ? 0 : dp[i][k-1]) 
								+  ( (k+1 > j) ? 0 : dp[k+1][j]) 
							    + sum;
					if(temp<min) {
						min = temp;
						root = k;
					}
				}
				dp[i][j] = min;
				rootsPlacement[i][j] = root;
			}
		}
		
		for(int[] p:rootsPlacement) {
			System.out.println(Arrays.toString(p));
		}
		
		return dp[0][n-1];
	}
	
	static int sumOfFreq(int[] freq, int i, int j) {
		int sum = 0;
		for(int k=i; k<=j; k++) {
			sum += freq[k];
		}
		return sum;
	}
}
