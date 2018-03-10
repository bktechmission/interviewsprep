package backtrackingNBFS;

public class GenerateValidParenthesisProblem {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		generateAllValidParenthesis(0,0,3,"");
	}
	
	static int index=0;
	static void generateAllValidParenthesis(int left, int right, int n, String out) {
		
		if(left==n&&right==n) {
			System.out.println(index++ +"."+out);
			return;
		}
		
		if(left<n) {
			generateAllValidParenthesis(left+1,right,n,out+"(");
		}
		if(right<left) {
			generateAllValidParenthesis(left,right+1,n,out+")");
		}
	}

}
