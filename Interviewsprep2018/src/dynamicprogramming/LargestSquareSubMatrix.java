package dynamicprogramming;

import java.util.Arrays;
import java.util.Random;

public class LargestSquareSubMatrix {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		largestSquareMatrixExample();
	}
	
	static void largestSquareMatrixExample() {
		int m = 10;
		int n = 9;
		
		boolean[][] matrix = new boolean[m][n];
		
		Random rand = new Random();
		int total = m*n;
		for(int i=0;i<total*2;i++) {
			int p = rand.nextInt(total);
			int newr = p/n;
			int newc = p%n;
			matrix[newr][newc] = true;
		}
		
		for(boolean[] p : matrix) {
			System.out.println(Arrays.toString(p));
		}
		
		System.out.println("MaxLen of Subsqure Matrix: "+findLargestSubSquare(matrix));
	}
	
	static int findLargestSubSquare(boolean[][]matrix) {
		
		int max = 0;
		int starti = 0;
		int startj = 0;
		for(int i=0;i<matrix.length;i++) {
			for(int j=0;j<matrix[0].length;j++) {
				if(matrix[i][j]) {
					int temp = squareSubmatrix(matrix,i,j);
					if(max<temp) {
						max = temp;
						starti = i;
						startj = j;
					}
				}
			}
		}
		
		System.out.println("Biggest Subsquare Matrix starts at "+starti+" , " +startj+"  with len"+max);
		return max;
	}
	
	static int squareSubmatrix(boolean[][] matrix, int i, int j) {
		// Base case at bottom or right of the matrix
		if(i==matrix.length||j==matrix[0].length) {
			return 0;
		}
		
		if(!matrix[i][j]) {
			return 0;
		}
		
		return 1+Math.min(Math.min(squareSubmatrix(matrix,i+1,j),squareSubmatrix(matrix,i,j+1)),
				squareSubmatrix(matrix,i+1,j+1));
	}
	
	
	static int starti = 0;
	static int startj = 0;
}
