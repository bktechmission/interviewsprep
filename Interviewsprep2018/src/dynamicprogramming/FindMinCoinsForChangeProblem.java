package dynamicprogramming;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FindMinCoinsForChangeProblem {
	public static void main(String[] args) {
		
		//countWaysToMakeChange();
		findMinCoinsExample();

	}

	
	static void findMinCoinsExample() {
		int change = 7;
		int[] coins = {3,5,1};
		//System.out.println(minCoinsRec(coins,change));
		
		int[]cache = new int[change+1];
		Arrays.fill(cache, -1);
		System.out.println(minCoinsWithMemo(coins,change,cache));
		
		System.out.println(minCoinsWithDP(coins,change));
		System.out.println("num of ways to make change: "+ countWaysToMakeAChange(coins,change,coins.length,""));
		System.out.println("num of ways to make change with DP: "+ countWaysToMakeAChangeWithDP(coins,change));
		
		
	}
	
	static int minCoinsRec(int[]coins, int change) {
		if(change==0) {
			return 0;
		}
		
		int minCoins = Integer.MAX_VALUE-1;
		
		for(int coin: coins) {
			if(change-coin>=0) {
				int temp = 1 + minCoinsRec(coins,change-coin);
				if(minCoins>temp) {
					minCoins = temp;
				}
			}
		}
		return minCoins;
	}
	
	
	// Brute force solution. Go through every
	// combination of coins that sum up to c to
	// find the minimum number
	static int findMinCoinsToMakeChange(int[] coins, int c, int k) {
		if(c==0)
			return 0;
		
		if(k<0)
			return Integer.MAX_VALUE;
		
		// if coin value is higher than c, that means we cant take this coin in consideration, exclude this coin
		if(c-coins[k]<0)
			return findMinCoinsToMakeChange(coins, c, k-1);		// k-1 means exclude the coin
		
		return Math.min(1+findMinCoinsToMakeChange(coins, c-coins[k], k), findMinCoinsToMakeChange(coins, c, k-1));
	}
	
	
	static int countWaysToMakeAChange(int[]coins, int change, int n, String s) {
		if(change == 0) {
			System.out.println("Found a way to make change with denominations: "+s);
			return 1;
		}
		
		if(n==0) {
			return 0;
		}
		
		if(change-coins[n-1]<0) {
			return countWaysToMakeAChange(coins, change, n-1, s);
		}
		
		return countWaysToMakeAChange(coins,change-coins[n-1],n, s.isEmpty()?(""+coins[n-1]):(s+(", ")+coins[n-1])) 
				+ countWaysToMakeAChange(coins,change,n-1,s);
	}
	
	static int countWaysToMakeAChangeWithDP(int[]coins, int change) {
		int[][] dp = new int[coins.length+1][change+1];
		
		// base condition: when we are given 0 as to make a change, we can always make 0 zero change by {} no coins
		for(int i=0;i<=coins.length;i++) {
			dp[i][0] = 1;	// one way with empty set to make a change of 0
		}
		
		for(int i=1;i<=coins.length;i++) {
			for(int j=1;j<=change;j++) {
				if(j-coins[i-1]<0) {
					// exclude the ith coin
					dp[i][j] = dp[i-1][j];
				}else {
					// include and exclude the coin
					dp[i][j] = (dp[i][j-coins[i-1]])+(dp[i-1][j]);
				}
			}
		}
		
		return dp[coins.length][change];
	}
	
	static int minCoinsWithMemo(int[]coins, int change, int[]cache) {
		if(change==0) {
			return 0;
		}
		if(cache[change]!=-1) {
			return cache[change];
		}
		
		int minCoins = Integer.MAX_VALUE-1;
		
		for(int coin: coins) {
			if(change-coin>=0) {
				int temp = 1 + minCoinsWithMemo(coins,change-coin,cache);
				if(minCoins>temp) {
					minCoins = temp;
				}
			}
		}
		
		cache[change] = minCoins;
		return minCoins;
	}
	
	static int minCoinsWithDP(int[]coins, int change) {
		int[] dp = new int[change+1];
		int[] coinsPicked = new int[change+1];
		
		
		
		Arrays.fill(dp, Integer.MAX_VALUE-1);
		Arrays.fill(coinsPicked, -1);
		
		
		dp[0] = 0;	// we can always make 0 change with 0 coins
		for(int i=0; i<coins.length; i++) {
			for(int c=0; c<=change; c++) {
				if(c-coins[i]>=0) {
					if(dp[c-coins[i]]+1 < dp[c]) {
						dp[c] = dp[c-coins[i]] + 1;
						coinsPicked[c] = i;
					}
				}
			}
		}
		System.out.print("\nCoins Picked are: {");
		printCoinsPicked(coinsPicked, coins, change);
		System.out.println("}");
		return dp[change];
	}
	
	static void printCoinsPicked(int[]coinsPicked,int[]coins, int change) {
		if(coinsPicked[coinsPicked.length-1]==-1) {
			System.out.print("No Solution exists!");
			return;
		}
		int c = change;
		
		while(c != 0) {
			int indexOfPickedCoin = coinsPicked[c];
			System.out.print(coins[indexOfPickedCoin]+ ",");
			c = c - coins[indexOfPickedCoin];
		}
	}
}
