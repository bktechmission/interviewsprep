package backtrackingNBFS;

public class SudokuSolverProblem {
	
	static int size = 9;
	static int bsize = (int) Math.sqrt(size);
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	static void sudokuSolverExample() {
		int[][] board = new int[size][size];
		solveSudoku(board);
	}
	
	static boolean solveSudoku(int[][] board) {
		int row = -1;
		int col = -1;
		boolean isEmpty = false;
		
		// try find any _ indicating empty cell to fill
		for(int i=0;i<size;i++) {
			for(int j=0;j<size;j++) {
				if(board[i][j]=='_') {
					row = i;
					col = j;
					isEmpty = true;
					break;
				}
			}
			if(isEmpty)
				break;
		}
		
		if(!isEmpty)
			return true;
		
		
		// if we reach here that means we have find one spot where we can start to solve it.
		// try every placement of 1->9
		for(int num=1;num<=size;num++) {
			if(canPlace(board,row,col,num)) {
				board[row][col] = num;
				if(solveSudoku(board)) {
					return true;
				}
				board[row][col] = 0;
			}
		}
		return false;
	}
	
	
	static boolean canPlace(int[][]board, int row, int col, int num) {
		if(!isRowSafe(board,row,num)) {
			return false;
		}
		if(!isColSafe(board,col,num)) {
			return false;
		}
		if(!isBoxSafe(board,row,col, num)) {
			return false;
		}
		return true;
	}
	
	static boolean isRowSafe(int[][]board, int row, int num) {
		for(int k=0;k<size;k++) {
			if(board[row][k]==num) {
				return false;
			}
		}
		return true;
	}
	
	static boolean isColSafe(int[][]board, int col, int num) {
		for(int k=0;k<size;k++) {
			if(board[k][col]==num) {
				return false;
			}
		}
		return true;
	}
	
	static boolean isBoxSafe(int[][]board, int row, int col, int num) {
		int rowToStartWith = row - row%bsize;
		int colToStartWith = col - col%bsize;
		
		for(int i=rowToStartWith;i<rowToStartWith+bsize;i++) {
			for(int j=colToStartWith;j<colToStartWith+bsize;j++) {
				if(board[i][j]==num) {
					return false;
				}
			}
		}
		return true;
	}
}
