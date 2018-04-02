package dynamicprogramming;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SubsetSumProblem {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		solveSubSetSunProblem();
	}
	
	static void solveSubSetSunProblem() {
		int[] set = {2,4,7,11,34,21};
		int target = 26;
		
		boolean ans = isSubSetSumExists(set,target, set.length);
		System.out.println("sum exists : "+ans);
		System.out.println("sum exists with DP : "+isSubSetSumExistsWithDP(set,target));
		
		int[][]cache = new int[set.length+1][target+1];
		for(int[]c:cache) {
			Arrays.fill(c, -1);
		}
		System.out.println("sum exists with Memo : "+isSubSetSumExistsWithMemo(set,target, set.length,cache));
		
	}
	
	static boolean isSubSetSumExists(int[]set,int target,int n) {
		if(target==0) {
			return true;
		}
		
		if(target!=0&&n==0) {
			return false;
		}
		
		if(target-set[n-1]<0) {
			return isSubSetSumExists(set, target, n-1);
		}
		
		return isSubSetSumExists(set, target, n-1) || isSubSetSumExists(set, target-set[n-1], n-1);
	}
	
	
	static boolean isSubSetSumExistsWithMemo(int[]set,int target,int n, int[][]cache) {
		if(target==0) {
			return true;
		}
		
		if(target!=0&&n==0) {
			return false;
		}
		
		if(cache[n][target]!=-1) {
			if(cache[n][target]==0) {
				return false;
			}
			else {
				return true;
			}
		}
		
		boolean result = false;
		if(target-set[n-1]<0) {
			result =  isSubSetSumExistsWithMemo(set, target, n-1,cache);
		}
		else {
			result = isSubSetSumExistsWithMemo(set, target, n-1,cache) || isSubSetSumExistsWithMemo(set, target-set[n-1], n-1,cache);
		}
		
		if(result) {
			cache[n][target] = 1;
		}
		else {
			cache[n][target] = 0;
		}
		return result;
	}
	
	static boolean isSubSetSumExistsWithDP(int[]set,int target) {
		boolean[][]dp = new boolean[set.length+1][target+1];
		for(int i=0;i<=set.length;i++) {
			dp[i][0] = true;		// we can always make target sum ==0 with empty set so all rows are true for 0 sum
		}
		
		for(int j=1;j<=target;j++) {
			dp[0][j] = false;	// we cant form any sum with 0 elements except 0	
		}
		
		// for rest check incl,exclude
		for(int i=1;i<=set.length;i++) {
			for(int j=1; j<=target;j++) {
				if(j-set[i-1]<0) {
					dp[i][j] = dp[i-1][j];
				}
				else {
					dp[i][j] = dp[i-1][j];
					if(!dp[i][j]) {
						dp[i][j] = dp[i-1][j-set[i-1]];
					}
				}
			}
		}
		
		List<Integer> resultSet = new ArrayList<>();
		
		if(dp[set.length][target]) {
			findSubSet(dp,set, set.length,target,resultSet);
			System.out.print("\nfinalSet is {");
			for(int a: resultSet) {
				System.out.print(a+", ");
			}
			System.out.println("}");
		}
		
		return dp[set.length][target];
	}
	
	static void findSubSet(boolean[][]dp, int[]set, int n, int t,  List<Integer> resultSet) {
		while(n>0&&t>0) {
			if(dp[n][t]) {
				if(!dp[n-1][t]) {
					resultSet.add(set[n-1]);
					t -= set[n-1];
					n -= 1;
				}
				else {
					n-=1;
				}
			}
			else {
				break;
			}
		}
	}
}
