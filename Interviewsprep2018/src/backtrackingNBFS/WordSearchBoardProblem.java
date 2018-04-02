package backtrackingNBFS;

public class WordSearchBoardProblem {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		wordSearchOnBoardExample();
	}
	
	static void wordSearchOnBoardExample() {
		char[][] board = {
				{'A','B','C','E'},
				{'S','F','C','S'},
				{'A','D','E','E'}
				
		};
		
		String word = "SEE";
		
		for(int i=0;i<board.length;i++) {
			for(int j=0;j<board[0].length;j++) {
				if(dfsSearch(board,i,j,word,0)) {
					System.out.println("find the word: ");
					return;
				}
			}
		}
	}
	
	static boolean dfsSearch(char[][]board, int i, int j, String word, int k) {
		if(MatrixFillUtil.isPosOutSideGrid(board, i, j)) {
			return false;
		}
		
		if(board[i][j]=='#') {
			return false;
		}
		
		if(k==word.length()) {
			return true;
		}
		
		if(board[i][j] != word.charAt(k)) {
			return false;
		}
		
		// now explore all path from here
		// first mark this pos as visited
		char prevChar = board[i][j];
		board[i][j] = '#';
		
		if(			dfsSearch(board,i+1,j,word,k+1) 
				||  dfsSearch(board,i-1,j,word,k+1)  
				||  dfsSearch(board,i,j-1,word,k+1) 
				||  dfsSearch(board,i,j+1,word,k+1)) {
			return true;
		}
		
		board[i][j] = prevChar;
		
		return false;
	}
}
