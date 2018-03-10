package backtrackingNBFS;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;

public class NumberIslandsProblem {
	static class Pair {
		int i;
		int j;

		Pair(int i, int j) {
			this.i = i;
			this.j = j;
		}
	}
	
	
	static int getBiggestIsland(int[][] binaryMatrix) {
		int m = binaryMatrix.length;
		int n = binaryMatrix[0].length;
		int size = 0;
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				if (binaryMatrix[i][j] == 1) {
					int newSize = getIslandSize(binaryMatrix, i, j);
					size = Math.max(size, newSize);
				}
			}
		}
		return size;
	}

	static int getNumberOfIslands(int[][] binaryMatrix) {
		int m = binaryMatrix.length;
		int n = binaryMatrix[0].length;
		int count = 0;
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				if (binaryMatrix[i][j] == 1) {
					markPositionsWithStack(binaryMatrix, i, j);
					count++;
				}
			}
		}
		return count;
	}

	static int[] dx = { -1, 1, 0, 0 };
	static int[] dy = { 0, 0, -1, 1 };

	static int getIslandSize(int[][]binaryMatrix,int i, int j) {
		if(!isValid(binaryMatrix, i, j)) {
			return 0;
		}
		
		//  this size will be atleast as we came through at least 1 here
		int size = 1;
		binaryMatrix[i][j] = 0;
		
		for(int d=0;d<dx.length;d++) {
			int newi = i+dx[d];
			int newj = j+dy[d];
			size+=getIslandSize(binaryMatrix,newi,newj);
		}
		return size;
	}
	
	static void markPositionsWithStack(int[][] binaryMatrix, int i, int j) {
		Deque<Pair> stack = new LinkedList<>();
		Pair p = new Pair(i, j);

		stack.push(p);

		while (!stack.isEmpty()) {
			Pair pos = stack.pop();

			// if this is valid cordinate
			if (!isValid(binaryMatrix, pos.i, pos.j))
				continue;

			binaryMatrix[pos.i][pos.j] = 0;

			// extract neighbors
			for (int k = 0; k < dx.length; k++) {
				int newi = pos.i + dx[k];
				int newj = pos.j + dy[k];
				Pair x = new Pair(newi, newj);
				stack.push(x);
			}
		}
	}

	static void markPositionsWithQueue(int[][] binaryMatrix, int i, int j) {
		Queue<Pair> queue = new LinkedList<>();
		Pair p = new Pair(i, j);

		queue.offer(p);

		while (!queue.isEmpty()) {
			Pair pos = queue.poll();

			// if this is valid cordinate
			if (!isValid(binaryMatrix, pos.i, pos.j))
				continue;

			binaryMatrix[pos.i][pos.j] = 0;

			// extract neighbors
			for (int k = 0; k < dx.length; k++) {
				int newi = pos.i + dx[k];
				int newj = pos.j + dy[k];
				Pair x = new Pair(newi, newj);
				queue.offer(x);
			}
		}
	}

	static boolean isValid(int[][] a, int i, int j) {
		if (i < 0 || i >= a.length || j < 0 || j >= a[0].length || a[i][j] == 0) {
			return false;
		}
		return true;
	}

	public static void main(String[] args) {
		int[][] binaryMatrix = { { 0, 1, 0, 1, 0 }, 
								{ 0, 0, 1, 1, 1 }, 
								{ 1, 0, 0, 1, 0 }, 
								{ 0, 1, 1, 0, 0 },
								{ 1, 0, 1, 0, 1 } };

		System.out.println("number of islands are : " + getNumberOfIslands(binaryMatrix));
		
		
		int[][] binaryMatrix1 = { { 0, 1, 0, 1, 0 }, 
				{ 0, 0, 1, 1, 1 }, 
				{ 1, 0, 0, 1, 0 }, 
				{ 0, 1, 1, 0, 0 },
				{ 1, 0, 1, 0, 1 } };
		System.out.println("Biggest size of islands are : "+ getBiggestIsland(binaryMatrix1));
	}
}
