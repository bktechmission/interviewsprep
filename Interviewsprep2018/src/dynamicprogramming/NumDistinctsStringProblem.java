package dynamicprogramming;

public class NumDistinctsStringProblem {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		numOfDistinctsString();
	}
	
	//    rabbbit
	//      ^
	//	  rabbit
	///   i-1,j-1,  i-1,j     or  i-1,j
	static void numOfDistinctsString() {
		String S = "rabbbit";
		String T = "rabbit";
		
		int[][] table = new int[S.length() + 1][T.length() + 1];
		
		for (int i = 0; i < S.length(); i++)
			table[i][0] = 1;
		
		
		for (int i = 1; i <= S.length(); i++) {
			for (int j = 1; j <= T.length(); j++) {
				if (S.charAt(i - 1) == T.charAt(j - 1)) {
					table[i][j] += table[i - 1][j] + table[i - 1][j - 1];
				} else {
					table[i][j] += table[i - 1][j];
				}
			}
		}
	 
		System.out.println("number of distinct subsequence : "+table[S.length()][T.length()]);
		
		
	}
	
	

}
