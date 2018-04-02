package dynamicprogramming;

import java.util.Arrays;
import java.util.Random;

public class LargestSquareSubMatrix {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		largestSquareMatrixExample();
	}
	
	static void largestSquareMatrixExample() {
		int m = 8;
		int n = 7;
		int[][] matrix = new int[m][n];
		for(int[] a: matrix) {
			Arrays.fill(a, 1);
		}
		
		
		int total = m*n;
		Random rand = new Random();
		for(int i=0;i<total*.60;i++) {
			int pos = rand.nextInt(total);
			int newi = pos/n;
			int newj = pos%n;
			matrix[newi][newj] = 0;
		}
		
		for(int[]a: matrix) {
			System.out.println(Arrays.toString(a));
		}
		
		int maxSize = Integer.MIN_VALUE;
		int starti = -1;
		int startj = -1;
		for(int i=0;i<m;i++) {
			for(int j=0;j<n;j++) {
				if(matrix[i][j]==1) {
					int len = getMaxSizeSubSquare(matrix, i, j);
					if(len>maxSize) {
						maxSize = len;
						starti = i;
						startj = j;
					}
				}
			}
		}
		
		System.out.println("MaxSubSquare exists at start {"+starti+","+startj+ "}  with len "+maxSize);
		
		getMaxSizeSubSquare(matrix);
		
	}
	
	static int getMaxSizeSubSquare(int[][]matrix, int i, int j) {
		if(i<0||i>=matrix.length || j<0 || j>=matrix[0].length) {
			return 0;
		}
		
		if(matrix[i][j]==0) {
			return 0;
		}
		
		return 1+Math.min(getMaxSizeSubSquare(matrix, i+1,j),Math.min(getMaxSizeSubSquare(matrix,i,j+1),getMaxSizeSubSquare(matrix,i+1,j+1)));
		
	}
	
	static int getMaxSizeSubSquare(int[][]matrix) {
		int m = matrix.length;
		int n = matrix[0].length;
		int[][] dp =new int[m][n];
		
		for(int i=0;i<m;i++) {
			for(int j=0;j<n;j++) {
				if(i==0||j==0) {
					dp[i][j] = matrix[i][j]==1?1:0;
				}
				else {
					if(matrix[i][j]==1) {
						dp[i][j] = 1 + Math.min(dp[i-1][j], Math.min(dp[i][j-1], dp[i-1][j-1]));
					}
				}
			}
		}
		
		int maxLen = Integer.MIN_VALUE;
		int endi = -1;
		int endj = -1;
		for(int i=0;i<m;i++) {
			for(int j=0;j<n;j++) {
				if(dp[i][j]>maxLen) {
					maxLen = dp[i][j];
					endi = i;
					endj = j;
				}
			}
		}
		
		System.out.println("Biggest SubSquare end at {"+endi + ","+endj+"}  with len "+maxLen);
		return maxLen;
	}
}
