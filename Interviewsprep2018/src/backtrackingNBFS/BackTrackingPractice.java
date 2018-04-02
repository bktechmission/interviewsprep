package backtrackingNBFS;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class BackTrackingPractice {
	public static void main(String[] args) {
		lengthOfLongestIncreasingPathExample();
	}
	
	static void lengthOfLongestIncreasingPathExample() {
		int m = 200;
		int n = 230;
		int[][] matrix = new int[m][n];
		MatrixFillUtil.fillMatrixWithRange(matrix, 200);
		
		List<int[]> resultPath = new LinkedList<>();
		List<int[]> curPath = new LinkedList<>();
		
		for(int i=0;i<m;i++) {
			for(int j=0;j<n;j++) {
				curPath.add(new int[] {i,j});
				dfsLongestIncreasingPath(matrix,i,j,1,resultPath,curPath, Integer.MIN_VALUE);
				curPath.remove(curPath.size()-1);
			}
		}
		
		for(int[] mat:matrix) {
			System.out.println(Arrays.toString(mat));
		}
		
		System.out.println("\nPath is: {");
		for(int[] p: resultPath) {
			System.out.println(Arrays.toString(p) +" -> ");
		}
		System.out.print("}");
	}
	
	static int[] dx = {0,0,-1,1};
	static int[] dy = {-1,1,0,0};
	
	static void dfsLongestIncreasingPath(int[][] matrix, int i, int j, int len, List<int[]>resultPath, List<int[]>curPath, int compareVal) {
		if(MatrixFillUtil.isPosOutSideGrid(matrix,i,j)) {
			return;
		}
		
		// already visited this node in current path
		if(matrix[i][j]==-1 || matrix[i][j]<compareVal) {
			return;
		}
		
		
		if(curPath.size()>resultPath.size()) {
			resultPath.clear();
			resultPath.addAll(curPath);
		}
		
		for(int k=0;k<dx.length;k++) {
			int r = i+dx[k];
			int c = j+dy[k];
			
			curPath.add(new int[] {r,c});
			int prevVal = matrix[i][j];
			matrix[i][j] = -1; 
			
			dfsLongestIncreasingPath(matrix,r, c, len+1, resultPath, curPath, prevVal);
			
			matrix[i][j] = prevVal;
			curPath.remove(curPath.size()-1);
		}
		
	}
}
