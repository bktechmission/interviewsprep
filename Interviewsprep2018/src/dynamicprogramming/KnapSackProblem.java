package dynamicprogramming;

import java.util.Deque;
import java.util.LinkedList;

public class KnapSackProblem {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		knapsackExample();
	}
	
	static void knapsackExample() {
		int val[] = new int[]{1,4,5,7};
	    int wt[] = new int[]{1,3,4,5};
	    int  W = 7;
	    int n = val.length;
	    System.out.println(knapSack(W, wt, val, n));
	    System.out.println(ksdynamicPorblem(W, wt, val, n));
	    
	    
	}
	
	static int  knapSack(int W, int[] wt, int[] val, int n) {
		if(W==0||n==0) {
			return 0;
		}
		
		if(wt[n-1]>W)
		{
			return knapSack(W,wt,val,n-1);
		}
		
		return Math.max(val[n-1]+knapSack(W-wt[n-1],wt,val,n-1), knapSack(W,wt,val,n-1));
	}
	
	static int ksdynamicPorblem(int W, int[] wt, int[] val, int n) {
		int[][]dp = new int[n+1][W+1];
		
		for(int i=0;i<=n;i++) {
			for(int w=0;w<=W;w++) {
				if (i==0 || w==0)
					dp[i][w] = 0;
				else if(wt[i-1]>w) {
					dp[i][w] = dp[i-1][w];
				}
				else {
					dp[i][w] = Math.max(val[i-1]+dp[i-1][w-wt[i-1]], dp[i-1][w]);
				}
			}
		}
		
		int i=n;
		int w=W;
		Deque<Integer> stack = new LinkedList<>();
		while(i>0&&w>0) {
			if(dp[i][w]!=dp[i-1][w]) {
				stack.push(i);
				w = w - wt[i-1];
				i--;
			}
			else {
				i--;
			}
		}
		
		while(!stack.isEmpty()) {
			System.out.print("  Pick item "+stack.pop() + "  ");
		}
		
		return dp[n][W];
		
	}
}
