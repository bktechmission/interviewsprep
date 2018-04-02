package backtrackingNBFS;

import java.util.Arrays;
import java.util.Random;

public class MatrixFillUtil {
	static final Random rand = new Random();
	static void fillMatrixWithRange(int[][] matrix, int range) {
		for(int i=0;i<matrix.length;i++) {
			for(int j=0;j<matrix[0].length;j++) {
				matrix[i][j] = rand.nextInt(range);
			}
		}
	}
	
	static void fillMatrixAllOnes(int[][] matrix) {
		for(int[] x: matrix) {
			Arrays.fill(x, 1);
		}
	}
	
	static void fillMatrixAllZeros(int[][] matrix) {
		for(int[] x: matrix) {
			Arrays.fill(x, 0);
		}
	}
	
	static void fillMatrixRandomSpotsWithK(int[][]matrix, int counts, int k) {
		int m = matrix.length;
		int n = matrix[0].length;
		int total = m*n;
		for(int i=0;i<counts;i++) {
			int num = rand.nextInt(total);
			int newi = num/n;
			int newj = num%n;
			matrix[newi][newj] = k;
		}
	}
	
	static boolean isPosOutSideGrid(int[][]matrix, int i, int j) {
		if(i<0||i>=matrix.length || j<0 || j>=matrix[0].length) {
			return true;
		}
		
		return false;
	}
	static boolean isPosOutSideGrid(char[][]matrix, int i, int j) {
		if(i<0||i>=matrix.length || j<0 || j>=matrix[0].length) {
			return true;
		}
		
		return false;
	}
}
