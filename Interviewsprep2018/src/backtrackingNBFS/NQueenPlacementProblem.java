package backtrackingNBFS;

import java.util.Arrays;

public class NQueenPlacementProblem {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		nQueenPlacementExample();
	}
	
	static void nQueenPlacementExample() {
		int[][] board = new int[4][4];
		
		if(nQSolve(board,0)) {
			System.out.println("Queen Placed Successfully: ");
			for(int[]b: board) {
				System.out.println(Arrays.toString(b));
			}
			
		}
		else {
			System.out.println("No Such Queen Placement Possible!. ");
		}
	}
	
	static boolean nQSolve(int[][]board,int col) {
		int N = board[0].length;
		
		if(col>=N) {
			return true;
		}

		/* Consider this column and try placing
	       this queen in all rows one by one */
	    for (int i = 0; i < N; i++)
	    {
	    		if(canPlace(board,i,col)) {
	    			board[i][col] = 1;
	    			if(nQSolve(board,col+1)) {
	    				return true;
	    			}
	    			/* If placing queen in board[i][col]
	                doesn't lead to a solution, then
	                remove queen from board[i][col] */
	    			board[i][col] = 0;		// BACKTRACK
	    		}
	    }
	    return false;
	}
	
	
	static boolean canPlace(int[][]board, int row, int col) {
		if(!isRowSafeTillCol(board,row,col)) {
			return false;
		}
		
		if(!isTopLeftDiagonalSafe(board, row, col)) {
			return false;
		}
		
		if(!isBottomLeftDiagonalSafe(board, row, col)) {
			return false;
		}
		
		return true;
	}
	
	static boolean isRowSafeTillCol(int[][]board, int row, int col) {
		for(int j=0; j<col; j++) {
			if(board[row][j]==1) {
				return false;
			}
		}
		
		return true;
	}
	
	static boolean isTopLeftDiagonalSafe(int[][]board, int row, int col) {
		int i = row, j = col;
		while(i>=0&&j>=0) {
			if(board[i][j]==1) {
				return false;
			}
			i--;
			j--;
		}
		return true;
	}
	
	static boolean isBottomLeftDiagonalSafe(int[][]board, int row, int col) {
		int i = row, j = col;
		while(i<=board.length-1&&j>=0) {
			if(board[i][j]==1) {
				return false;
			}
			i++;
			j--;
		}
		return true;
	}
	
}
