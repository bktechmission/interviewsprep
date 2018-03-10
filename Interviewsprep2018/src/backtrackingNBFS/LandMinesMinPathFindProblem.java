package backtrackingNBFS;

import java.util.LinkedList;
import java.util.Queue;

public class LandMinesMinPathFindProblem {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		findMinPathFinderExample();
	}
	
	static void findMinPathFinderExample() {
		int[][] landMine = {
							{1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
							{1, 0, 1, 1, 1, 1, 1, 1, 1, 1},
							{1, 1, 1, 0, 1, 1, 1, 1, 1, 1},
							{1, 1, 1, 1, 0, 1, 1, 1, 1, 1},
							{1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
							{1, 1, 1, 1, 1, 0, 1, 1, 1, 1},
							{1, 0, 1, 1, 1, 1, 1, 1, 0, 1},
							{1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
							{1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
							{0, 1, 1, 1, 1, 0, 1, 1, 1, 1},
							{1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
							{1, 1, 1, 0, 1, 1, 1, 1, 1, 1}
						   };
		
		int m = landMine.length;
		int n = landMine[0].length;
		
		// mark 0 adjacent as zeros as those are unsafe
		for(int i=0; i<m; i++) {
			for(int j=0; j<n; j++) {
				// i,j is bomb make sure we mark all four neighbors as unsafe
				if(landMine[i][j]==0) {
					for(int mov=0;mov<dx.length;mov++) {
						int newi = i+dx[mov];
						int newj = j+dy[mov];
						if(isValidGrid(landMine,newi,newj)) {	
							// we mark as -1 as these are neighbors for 0, 
							// we cant mark zero as otherwise whole might collapse to zero as we are if landMine[i][j]==0
							landMine[newi][newj] = -1;
						}
					}
				}
			}
		}
		
		// now mark above -1 as 0 where ever you -1
		for(int i=0; i<m; i++) {
			for(int j=0; j<n; j++) {
				if(landMine[i][j]==-1) {
					landMine[i][j] = 0;
				}
			}
		}
		
		
		// now it is converted to usual problem of rat in maze where 0 means wall and 1 means path
		// we can start from either row in first col which is 1
		int minPathLen = Integer.MAX_VALUE;
		int rowToStart = 0;
		int[][] visited = new int[m][n];
		for(int i=0;i<landMine.length;i++) {
			if(landMine[i][0]==1) {
				minPathLenFound = Integer.MAX_VALUE;
				findMinPath(landMine, i, 0,visited,0,n);
				if(minPathLen > minPathLenFound) {
					rowToStart = i;
					minPathLen = minPathLenFound;
				}
			}
		}
		
		System.out.println("Minimum length found is: "+minPathLen+ " starting at row "+(rowToStart+1));
		
		for(int i=0;i<landMine.length;i++) {
			if(landMine[i][0]==1) {
				minPathLenFound = Integer.MAX_VALUE;
				findMinPathBFS(landMine, i, 0,n);
				if(minPathLen > minPathLenFound) {
					rowToStart = i;
					minPathLen = minPathLenFound;
				}
			}
		}
		System.out.println("BFS Minimum length found is: "+minPathLen+ " starting at row "+(rowToStart+1));
	}
	
	static boolean isValidGrid(int[][]landMine, int i, int j) {
		if(i<0||i>=landMine.length||j<0||j>=landMine[0].length) {
			return false;
		}
		
		return true;
	}
	
	static int[] dx = {-1,1,0,0};
	static int[] dy = {0,0,-1,1};
	
	static int minPathLenFound = Integer.MAX_VALUE;
	static void findMinPath(int[][]landMine, int i, int j, int[][]visited, int len, int C) {
		
		// if i,j is outside grid, return
		if(!isValidGrid(landMine, i, j)) {
			return;
		}
		
		// i,j is bomb or neighbor of bomb, return
		if(landMine[i][j]==0){
			return;
		}
		
		// if i,j is a node in the path we came from, return
		if(visited[i][j]==1) {
			return;
		}
		
		// if we are going in a path where len is becoming greater than already found one shortest path return
		if(minPathLenFound<len) {
			return;
		}
		
		// we reached the end col, that was traget, record the length if it minimum
		if(j==C-1) {
			if(minPathLenFound>len) {
				minPathLenFound = len;
			}
		}

		// first mark that position as visited
		visited[i][j]=1;
		
		for(int mov = 0; mov<dx.length; mov++) {
			int newi = i+dx[mov];
			int newj = j+dy[mov];
			findMinPath(landMine, newi, newj, visited, len+1, C);
		}
		
		visited[i][j]=0;		// backtrack
		
	}
	
	static class LDMNode{
		int i;
		int j;
		int pathLen;
		LDMNode(int i, int j, int pathLen){
			this.i = i;
			this.j = j;
			this.pathLen = pathLen;
		}
	}
	
	static void findMinPathBFS(int[][]landMine, int r, int c, int C) {
		
		int[][]visited = new int[landMine.length][landMine[0].length];
		
		Queue<LDMNode> q = new LinkedList<LDMNode>();
		q.offer(new LDMNode(r,c,0));
		
		while(!q.isEmpty()) {
			LDMNode node = q.poll();
			int i = node.i;
			int j = node.j;
			int len = node.pathLen;
			
			// if i,j is outside grid, continue next node
			if(!isValidGrid(landMine, i, j)) {
				continue;
			}
			// i,j is bomb or neighbor of bomb, continue next node
			if(landMine[i][j]==0){
				continue;
			}
			// if i,j is a node in the path we came from,  continue next node
			if(visited[i][j]==1) {
				continue;
			}
			
			// if we are going in a path where len is becoming greater than already found one shortest path return
			if(minPathLenFound<len) {
				continue;
			}
			
			// the moment we reached last cell, we know it will be minimum len as we used BFS, so return
			if(j==C-1) {
				minPathLenFound = len;
				return;
			}
			
			visited[i][j]=1;
			
			for(int mov = 0; mov<dx.length; mov++) {
				int newi = i+dx[mov];
				int newj = j+dy[mov];
				q.offer(new LDMNode(newi, newj, len+1));
			}
		}
	}
}
