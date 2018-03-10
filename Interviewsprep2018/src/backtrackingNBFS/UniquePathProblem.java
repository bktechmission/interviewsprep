package backtrackingNBFS;

import java.util.Arrays;
import java.util.Random;

public class UniquePathProblem {
	public static void main(String[] args) {
		uniquePathsExample();
	}
	
	static void uniquePathsExample() {
		int m=6, n=10;
		int[][] paths = new int[m][n];
		
		//System.out.println("total paths are : "+ countPaths(paths));
		
		int total = m*n;
		Random rand = new Random();
		for(int i=0;i<total*0.05;i++) {
			int size = rand.nextInt(total);
			int r = size/n;
			int c = size%n;
			paths[r][c] = 1;
		}
		
		long start = System.currentTimeMillis();
		System.out.println("total paths with obstractions are : "+ countPathsWithObstraction(paths));
		long end = System.currentTimeMillis();
		System.out.println("time took base recursion : "+ (end-start)+" mills");
		start = System.currentTimeMillis();
		System.out.println("total paths memo with obstractions are : "+ countPathsWithObstractionWithMemo(paths));
		end = System.currentTimeMillis();
		System.out.println("time took memo with recursion : "+ (end-start)+" mills");
		
		start = System.currentTimeMillis();
		System.out.println("total paths bottom up DP obstractions are : "+ countPathsBottomUp(paths));
		end = System.currentTimeMillis();
		System.out.println("time took memo with recursion : "+ (end-start)+" mills");
		
		
	}
	
	static int countPaths(int[][]paths) {
		return countPathsHelper(paths,0,0);
	}
	
	static int countPathsHelper(int[][]paths, int i, int j) {
		if(i==paths.length||j==paths[0].length) {
			return 0;
		}
		
		if(i==paths.length-1&&j==paths[0].length-1) {
			return 1;
		}
		
		return countPathsHelper(paths,i+1,j) + countPathsHelper(paths,i,j+1);
		
	}
	
	static int countPathsWithObstraction(int[][]paths) {
		return countPathsWithObstractionHelper(paths,0,0);
	}
	
	static int countPathsWithObstractionHelper(int[][]paths, int i, int j) {
		if(i==paths.length||j==paths[0].length || paths[i][j]==1) {
			return 0;
		}
		
		if(i==paths.length-1&&j==paths[0].length-1) {
			return 1;
		}
		
		return countPathsWithObstractionHelper(paths,i+1,j) + countPathsWithObstractionHelper(paths,i,j+1);
		
	}
	
	
	static int countPathsWithObstractionWithMemo(int[][]paths) {
		int[][] cache = new int[paths.length][paths[0].length];
		for(int[]c:cache) {
			Arrays.fill(c, -1);
		}
		return countPathsWithObstractionHelperWithMemo(paths,0,0,cache);
	}
	
	static int countPathsWithObstractionHelperWithMemo(int[][]paths, int i, int j,int[][]cache) {
		if(i==paths.length||j==paths[0].length || paths[i][j]==1) {
			return 0;
		}
		
		if(i==paths.length-1&&j==paths[0].length-1) {
			return 1;
		}
		
		if(cache[i][j]>=0) {
			return cache[i][j];
		}
		
		int result = countPathsWithObstractionHelperWithMemo(paths,i+1,j,cache) + countPathsWithObstractionHelperWithMemo(paths,i,j+1,cache);
		cache[i][j] = result;
		return result;
	}
	
	
	static int countPathsBottomUp(int[][]paths) {
		int m = paths.length;
		int n = paths[0].length;
		
		int[][] dp = new int[m+1][n+1];
		dp[paths.length-1][paths[0].length-1] = 1;	
		// just to make sure we dont need fill last row before hand so just added extras row and col with 0s

		for(int r = paths.length-1; r>=0; r--) {
			for(int c = paths[0].length-1; c>=0; c--) {
				if(r==paths.length-1&&c==paths[0].length-1)
					continue;
				if(paths[r][c]==0) {
					dp[r][c] = dp[r+1][c]+dp[r][c+1];
				}
			}
		}
		
		return dp[0][0];
	}
	
}
