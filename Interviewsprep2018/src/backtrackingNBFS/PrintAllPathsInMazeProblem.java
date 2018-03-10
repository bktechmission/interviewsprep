package backtrackingNBFS;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class PrintAllPathsInMazeProblem {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		checkPathExample();
	}
	
	static void checkPathExample() {
		int[][] maze = {{1,1,0},{1,1,0},{0,1,1}};
		int m = maze.length;
		int n = maze[0].length;
		
		int[][] paths = new int[m][n];
		System.out.println("base maze ");
		for(int[]mz:maze) {
			System.out.println(Arrays.toString(mz));
		}
		
		System.out.println(".........");
		System.out.println(isPathExistFromSrcToDest(maze,paths,0,0));
		for(int[]mz:paths) {
			System.out.println(Arrays.toString(mz));
		}
		
		List<List<Pair>> finalResult = new ArrayList<>();
		List<Pair> curPath = new ArrayList<>();
		Set<String> visited = new HashSet<>();
		
		getAllPathsFromMaze(maze,0,0,finalResult,curPath,visited);
		
		for(List<Pair> ans: finalResult) {
			System.out.println(ans);
		}
		
	}
	
	static int[] dx = {-1,1,0,0};
	static int[] dy = {0,0,-1,1};
	static boolean isPathExistFromSrcToDest(int[][]maze, int[][] paths, int i, int j) {
		// first check is we are in matrix grid
		if(!isValidSapce(maze,i,j)) {
			return false;
		}
		
		
		// check if we are at the destination
		if(i==maze.length-1&&j==maze[0].length-1) {
			paths[i][j] = 1;
			for(int[]p:paths) {
				System.out.println(Arrays.toString(p));
			}
			return true;
		}
		
		// check if we hit a wall
		if(maze[i][j]==0) {
			return false;
		}
		
		// check we are trying to visit a node that we came from here, so it will be a loop if we do so
		if(paths[i][j]==1) {
			return false;
		}
		
		// mark this node visited and put in path
		paths[i][j] = 1;
		
		// explore all possibilities from here
		for(int d=0;d<dx.length;d++) {
			int newi = i+dx[d];
			int newj = j+dy[d];
			if(isPathExistFromSrcToDest(maze,paths,newi,newj)) {
				return true;
			}
		}
		
		// now backtrack and return false as we did not get any path from i,j
		// remove from visited and remove from path, here paths server both purpose
		paths[i][j] = 0;
		return false;
	}
	
	static boolean isValidSapce(int[][]maze, int i, int j) {
		if(i<0||i>=maze.length||j<0||j>=maze.length) {
			return false;
		}
		
		return true;
	}
	
	static class Pair{
		int r;
		int c;
		
		Pair(int i, int j){
			this.r=i;
			this.c =j;
		}
		
		public String toString() {
			return "{r:"+r+",c:"+c+"}";
		}
		
	}
	
	
	static void getAllPathsFromMaze(int[][]maze, int i, int  j, 
			List<List<Pair>> finalResult, 
			List<Pair> curPath, 
			Set<String> visited) {
		
		// check we are still in square, if not then return
		if(!isValidSapce(maze, i, j)) {
			return;
		}
		
		// reached end, store ans and return
		if(i==maze.length-1&&j==maze[0].length-1) {
			Pair p = new Pair(i,j);
			curPath.add(p);
			
			List<Pair> newList = new ArrayList<>(curPath);
			finalResult.add(newList);
			
			curPath.remove(curPath.size()-1);
			return;
		}
		
		// check if it is wall
		if(maze[i][j]==0) {
			return;
		}
		
		// if we reached a node that is one of the node in curPath then return to avoid loop
		String key = i+":"+j;
		if(visited.contains(key)) {
			return;
		}
		
		// let first put in visited
		visited.add(key);
		//lets put the ans in our temp path
		curPath.add(new Pair(i,j));
		
		
		// now lets try all further ans
		getAllPathsFromMaze(maze,i-1,j,finalResult,curPath,visited);
		getAllPathsFromMaze(maze,i+1,j,finalResult,curPath,visited);
		getAllPathsFromMaze(maze,i,j-1,finalResult,curPath,visited);
		getAllPathsFromMaze(maze,i,j+1,finalResult,curPath,visited);
		
		
		//now for back track clear set states we did in this call for visited and path
		visited.remove(key);
		curPath.remove(curPath.size()-1);
		
		
	}

}
