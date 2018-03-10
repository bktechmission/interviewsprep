package backtrackingNBFS;

import java.util.Arrays;

public class RatInMazeProblem {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ratInMazeExample();
	}
	
	static void ratInMazeExample(){
		 int[][] maze  =  { {1, 0, 0, 0},
			        			{1, 1, 0, 1},
			        			{0, 1, 0, 0},
			        			{1, 1, 1, 1}
			    				};
		 
		 int[][] paths = new int[maze.length][maze[0].length];
		 
		 if(hasPathInMaze(maze,paths,0,0)) {
			 System.out.println("Path is: ");
			for(int[]p:paths) {
				System.out.println(Arrays.toString(p));
			}
		 }
		 else {
			 System.out.println("No such path exists!");
		 }
	}
	
	
	static int[] dx = {-1,1,0,0};
	static int[] dy = {0,0,-1,1};
	
	static boolean hasPathInMaze(int[][]maze, int[][]paths, int i, int j) {
		// if we are not in grid, return false;
		if(!isLocInGrid(maze,i,j)) {		
			return false;
		}
		
		// if we are at the end, return true we found a path
		if(i==maze.length-1 && j==maze[0].length-1) {
			paths[i][j] = 1;
			return true;
		}
		
		// if we are 0, return false;
		if(maze[i][j]==0) {
			return false;
		}
		
		
		// if we are at node, which is one of the node in curPath that leds us to this node, 
		// so avoid loop and return false;
		if(paths[i][j]==1) {
			return false;
		}
		
		
		// now backtracking work starts
		// first make paths as visited
		paths[i][j] = 1;
		
		// try all child paths originating from this point
		for(int move = 0; move<dx.length; move++) {
			int newi = i + dx[move];
			int newj = j + dy[move];
			if(hasPathInMaze(maze, paths, newi, newj)) {
				return true;
			}
		}
		
		// if no path is found reset visited and return false
		paths[i][j] = 0;
		return false;
		
	}
	
	
	static boolean isLocInGrid(int[][]maze, int i, int j) {
		if(i<0||i>=maze.length||j<0||j>=maze[0].length) {
			return false;
		}
		
		return true;
	}
}
