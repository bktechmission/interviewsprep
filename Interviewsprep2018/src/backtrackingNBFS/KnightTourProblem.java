package backtrackingNBFS;

import java.util.Arrays;

public class KnightTourProblem {
	public static void main(String[] args) {
		knightTourExample();
	}
	
	static int[] dx = {-2,-2,2,2,-1,1,-1,1};
	static int[] dy = {-1,1,-1,1,-2,-2,2,2};
	
	static void knightTourExample() {
		int n = 5;   // 8X8 grid
		
		int[][] sol = new int[n][n];
		for(int[]s:sol) {
			Arrays.fill(s, -1);
		}
	
		
		if(solveKT(n,sol,0,0,0)) {
			System.out.println("Path is: ");
			for(int[]s:sol) {
				System.out.println(Arrays.toString(s));
			}
		}
		else {
			System.out.println("No such path exists! ");
		}
		
	}
	
	static boolean solveKT(int N, int[][]sol, int i, int j, int movei) {
		// check we are still inside grid
		if(!isLocInGrid(N,i,j)) {
			return false;
		}
		
		// if we are done with moves
		if(movei==N*N) {
			//sol[i][j] = movei;
			return true;
		}
		
		//check we are revisiting an existing path node
		if(sol[i][j]!=-1) {
			return false;
		}
		
		// put ithmove in path
		sol[i][j] = movei;
		
		for(int move = 0; move<dx.length; move++) {
			int newi = i + dx[move];
			int newj = j + dy[move];
			
			if(solveKT(N,sol,newi,newj,movei+1)) {
				return true;
			}
		}
		
		// backtrack and return false in case we did not find a path
		sol[i][j] = -1;
		return false;
	}
	
	static boolean isLocInGrid(int n, int i, int j) {
		if(i<0||i>=n||j<0||j>=n) {
			return false;
		}
		
		return true;
	}
}
