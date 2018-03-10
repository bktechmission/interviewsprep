package backtrackingNBFS;

import java.util.Arrays;
import java.util.Random;

public class ConnectedCellsProblem {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		connectedCellsExample();
	}
	
	static void connectedCellsExample() {
		int m = 10;
		int n = 20;
		int[][] matrix = new int[m][n];
		Random rand = new Random();
		int total = m*n;
		for(int i=0;i<total*0.50;i++) {
			int num = rand.nextInt(total);
			int r = num/n;
			int c = num%n;
			matrix[r][c] = 1;
		}
		
		for(int i=0;i<m;i++) {
			System.out.println(Arrays.toString(matrix[i]));
		}
		
		int size = 0;
		int countRegions=0;
		for(int i=0;i<m;i++) {
			for(int j=0;j<n;j++) {
				if(matrix[i][j]==1) {
					int temp = getRegionSize(matrix,i,j);
					size = Math.max(size,temp);
					countRegions++;
				}
			}
		}
		System.out.println("Number of regions are "+countRegions);
		System.out.println("Max size found is "+size);
	}
	
	static int[] dx = {-1,-1,-1,0,0,1,1,1};
	static int[] dy = {-1,0,1,-1,1,-1,0,1};
	
	static int getRegionSize(int[][]matrix, int r, int c) {
		if(!isValidPosition(matrix,r,c)) {
			return 0;
		}
		
		if(matrix[r][c]==0) {
			return 0;
		}
		
		matrix[r][c] = 0;
		int size = 1;
		for(int i=0;i<dx.length;i++) {
			for(int j=0;j<dy.length;j++) {
				size+=getRegionSize(matrix, r+dx[i],c+dy[j]);
			}
		}
		
		return size;
	}
	
	static boolean isValidPosition(int[][]matrix, int r, int c) {
		if(r<0||r>=matrix.length||c<0||c>=matrix[0].length) {
			return false;
		}
		
		return true;
	}

}
