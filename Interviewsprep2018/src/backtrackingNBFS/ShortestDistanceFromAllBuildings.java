package backtrackingNBFS;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class ShortestDistanceFromAllBuildings {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		findShortestDistanceFromAllBuildings();
		
		// now lets try bfs
		System.out.println("\n\nDoing BFS way");
		findShortestDistanceFromAllBuildingsBFS();
	}
	
	static void findShortestDistanceFromAllBuildings() {
		int m = 3;
		int n = 4;
		int[][] grid = {{0,0,1,0},{0,0,2,0},{2,1,2,0}};
		
		// 0 means empty land
		// 1 means building
		// 2 means obstacle
		m = grid.length;
		n = grid[0].length;
		int[][] paths = new int[m][n];
		int[][] distance = new int[m][n];
		int[][] pathsTotal = new int[m][n];
		int[][] distTotal = new int[m][n];
		
		int numOfBuildings = 0;
		
		for(int i = 0; i<grid.length; i++) {
			for(int j = 0; j<grid[0].length; j++) {
				if(grid[i][j] == 1) {	//start walk from a building
					// start dfs
					boolean[][] visited = new boolean[m][n];
					paths = new int[m][n];
					distance = new int[m][n];
					dfsWalk(grid, i, j, i,j, visited, paths, distance,0);
					
					// merge number of buildings we can reach
					for(int r=0; r<paths.length; r++) {
						for(int c=0; c<paths[0].length; c++ ) {
							pathsTotal[r][c] += paths[r][c];
						}
					}
					for(int r=0; r<distTotal.length; r++) {
						for(int c=0; c<distTotal[0].length; c++ ) {
							distTotal[r][c] += distance[r][c];
						}
					}
					

					numOfBuildings++;
				}
			}
		}
		
		
		for(int[] p: distTotal) {
			System.out.println(Arrays.toString(p));
		}
		System.out.println("\n");
		for(int[] p: pathsTotal) {
			System.out.println(Arrays.toString(p));
		}
		System.out.println();
		
		
		int minDistances = Integer.MAX_VALUE;
		for(int i=0; i<pathsTotal.length; i++) {
			for(int j=0; j<pathsTotal[0].length; j++){
				if(pathsTotal[i][j] == numOfBuildings && distTotal[i][j]<minDistances) {
					minDistances = distTotal[i][j];
					System.out.println("Spot to build house is at empty land: {"+i +", "+j+"}" + " minDistance found is "+minDistances);
				}
			}
		}
		
	}
	
	static void dfsWalk(int[][]grid, int i, int j, int oi, int oj, boolean[][]visited, int[][]paths, int[][]distance, int level) {

		if(MatrixFillUtil.isPosOutSideGrid(grid,i,j)) {
			return;
		}
		
		if(visited[i][j] || ((i!=oi || j!=oj) && grid[i][j]!=0)) {
			return;
		}

		visited[i][j] = true;

		// now fill what we need to fill here
		paths[i][j] = 1;
		distance[i][j] = (distance[i][j] != 0) ? Math.min(distance[i][j],level) : level;
		
		// explore all neighbors
		dfsWalk(grid, i, j-1, oi, oj, visited, paths, distance, level+1);
		dfsWalk(grid, i, j+1, oi, oj, visited, paths, distance, level+1);
		dfsWalk(grid, i-1, j, oi, oj, visited, paths, distance, level+1);
		dfsWalk(grid, i+1, j, oi, oj, visited, paths, distance, level+1);
		
		visited[i][j] = false;
	}
	
	
	static void findShortestDistanceFromAllBuildingsBFS(){
		int m = 3;
		int n = 4;
		int[][] grid = {{0,0,1,0},{0,0,2,0},{2,1,2,0}};
		
		// 0 means empty land
		// 1 means building
		// 2 means obstacle
		m = grid.length;
		n = grid[0].length;
		int[][] paths = new int[m][n];
		int[][] distance = new int[m][n];
		
		int numOfBuildings = 0;
		
		for(int i = 0; i<grid.length; i++) {
			for(int j = 0; j<grid[0].length; j++) {
				if(grid[i][j] == 1) {	//start walk from a building
					// start dfs
					doBFSWalk(grid,i,j, paths, distance);
					numOfBuildings++;
				}
			}
		}
		
		for(int[]p:paths) {
			System.out.println(Arrays.toString(p));
		}
		System.out.println("\n");
		for(int[]p:distance) {
			System.out.println(Arrays.toString(p));
		}
		
		int minDistances = Integer.MAX_VALUE;
		for(int i=0; i<paths.length; i++) {
			for(int j=0; j<paths[0].length; j++){
				if(paths[i][j] == numOfBuildings && distance[i][j]<minDistances) {
					minDistances = distance[i][j];
					System.out.println("Spot to build house is at empty land: {"+i +", "+j+"}" + " minDistance found is "+minDistances);
				}
			}
		}
	}
	
	static class GridNode{
		int r;
		int c;
		int level;
		GridNode(int r, int c, int level){
			this.r = r;
			this.c = c;
			this.level = level;
		}
	}
	static void doBFSWalk(int[][]grid, int i, int j, int[][]paths, int[][]distance) {
		// lets create a queue and visited array
		Queue<GridNode> q = new LinkedList<>();
		q.add(new GridNode(i,j,0));
		
		int m = grid.length;
		int n = grid[0].length;
		boolean[][] visited = new boolean[m][n];
		
		int oi = i;
		int oj = j;
		
		while(!q.isEmpty()) {
			GridNode node = q.poll();
			i = node.r;
			j = node.c;
			
			if(MatrixFillUtil.isPosOutSideGrid(grid, i, j)) {
				continue;
			}
			
			if(visited[i][j] || (oi!=i||oj!=j)&&grid[i][j]!=0) {
				continue;
			}
			
			visited[i][j] = true;
			
			// fill details now
			paths[i][j]++;
			distance[i][j] += node.level;
			
			// explore all neighbrs, please add them to the queue
			q.offer(new GridNode(i-1, j, node.level+1));
			q.offer(new GridNode(i+1, j, node.level+1));
			q.offer(new GridNode(i, j-1, node.level+1));
			q.offer(new GridNode(i, j+1, node.level+1));
		}
		
		
	}
}
