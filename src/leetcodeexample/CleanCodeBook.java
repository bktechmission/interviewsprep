package leetcodeexample;

import java.util.Arrays;
import java.util.Random;

public class CleanCodeBook {
	public static void main(String[] args) {
		uniquePathExample();
	}
	
	
	static void uniquePathExample(){
		int m = 20;
		int n = 15;
		int[][] grid = new int[m][n];
		
		int values = m*n;
		Random rand = new Random();
		for(int i=0;i<4;i++) {
			int randi= rand.nextInt(values);
			int x = randi/n;
			int y = randi%n;
			grid[x][y] = 1;
		}
		
		
		long start = System.currentTimeMillis();
		System.out.println(uniquePaths(0,0,m,n));
		long end = System.currentTimeMillis();
		System.out.println("time took was "+(end-start)/1000 +" secs");
		
		int[][]cache = new int[m][n];
		for(int i=0;i<m;i++) {
			Arrays.fill(cache[i], -1);
		}
		start = System.currentTimeMillis();
		System.out.println(uniquePaths(0,0,m,n,cache));
		end = System.currentTimeMillis();
		System.out.println("time took was "+(end-start)/1000 +" secs");
		
		start = System.currentTimeMillis();
		System.out.println(uniquePathsBottomUp(m,n));
		end = System.currentTimeMillis();
		System.out.println("time took was "+(end-start)/1000 +" secs");
		
		start = System.currentTimeMillis();
		System.out.println(uniquePathsBottomUpWithObstacle(grid));
		end = System.currentTimeMillis();
		System.out.println("time took was "+(end-start)/1000 +" secs");
		
	}
	
	static int uniquePaths(int r, int c, int m, int n){
		if(r>=m||c>=n) {
			return 0;
		}
		
		if(r==m-1&&c==n-1) {
			return 1;
		}

		return uniquePaths(r+1,c,m,n) + uniquePaths(r,c+1,m,n);
		
	}
	static int count =0;
	static int uniquePaths(int r, int c, int m, int n, int[][] cache) {
		if(r>=m||c>=n) {
			return 0;
		}
		
		if(r==m-1||c==n-1) {
			cache[r][c] = 1;
		}
		else if(cache[r][c]==-1) {
			cache[r][c] = uniquePaths(r+1,c,m,n,cache) + uniquePaths(r,c+1,m,n,cache);
		}
		return cache[r][c];
	}
	
	
	static int uniquePathsBottomUp(int m, int n) {
		int[][] cache = new int[m][n];
		for(int i=m-1;i>=0;i--) {
			for(int j=n-1;j>=0;j--) {
				if(i==m-1||j==n-1) {
					cache[i][j] = 1;
				}else {
					cache[i][j] = cache[i][j+1] + cache[i+1][j];
				}
			}
		}
		return cache[0][0];
		
	}
	
	static int uniquePathsBottomUpWithObstacle(int[][]grid) {
		int m = grid.length;
		int n = grid[0].length;
		int[][] cache = new int[m][n];
		for(int i=m-1;i>=0;i--) {
			for(int j=n-1;j>=0;j--) {
				if(grid[i][j]!=1) {
					if(i==m-1||j==n-1) {
						cache[i][j] = 1;
					}else {
						cache[i][j] = cache[i][j+1] + cache[i+1][j];
					}
				}else {
					cache[i][j] = 0;
				}
			}
		}
		return cache[0][0];
		
	}
	
	
}
