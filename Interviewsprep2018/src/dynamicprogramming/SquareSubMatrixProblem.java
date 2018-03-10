package dynamicprogramming;

import java.util.Arrays;
import java.util.Random;

public class SquareSubMatrixProblem {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		largestSquareSubArrayExample();
	}
	
	static void largestSquareSubArrayExample() {
		int m = 4;
		int n = 3;
		int[][] matrix = new int[m][n];
		int total = m*n;
		
		Random rand = new Random();
		for(int i=0; i<total*.90; i++) {
			int r = rand.nextInt(total);
			int newi = r/n;
			int newj = r%n;
			matrix[newi][newj] = 1;
		}
		
		for(int[]x:matrix) {
			System.out.println(Arrays.toString(x));
		}
		
		// generate all valid sub-squares
		for(int i=0;i<m;i++) {
			for(int j=0;j<n;j++) {
				System.out.print("\nSquare starts at i:"+ i +" j:"+j);
				for(int l=1;l<=Math.min(m-i, n-j);l++) {
					System.out.print("\nSquare of size "+ l);
					for(int p=i; p<i+l; p++) {
						System.out.println();
						for(int q=j; q<j+l; q++) {
							System.out.print(matrix[p][q] +", ");
						}
					}
				}
				
			}
		}
		
	}
	
	
}
