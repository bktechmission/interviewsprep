package dynamicprogramming;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FindMinCoinsForChangeProblem {
	public static void main(String[] args) {
		
		//countWaysToMakeChange();
		findMinCoinsExample();

	}
	
	static void countWaysToMakeChange() {
		int arr[] = {1,6,10};
        int m = arr.length;
        int n = 5453;
        System.out.println("Number of ways to make change "+countWaysToMakeChange(arr, n, m-1));
        
        
        
	}
	
	static void findMinCoinsExample(){
		int[] coins = {1,6,10};
		int c = 26;	// 6+6+10+1
		System.out.println("mini coins are: "+makeChange(coins,c));
		System.out.println("mini coins are: "+findMinCoinsToMakeChange(coins,c,coins.length-1));
		
		int[] cache = new int[c+1];
		Arrays.fill(cache,-1);
		System.out.println("mini coins are: "+makeChangeMemo(coins,c,cache));
		
		System.out.println("mini coins are: "+makeChangeDP(coins,c));
		
		
		/*
		long start = System.currentTimeMillis();
		System.out.println("changes are  "+ findMinCoinsToMakeChange(coins, c,coins.length-1));
		long end = System.currentTimeMillis();
		System.out.println("total time took : "+(end-start)+" millis");
		//System.out.println("changes are  "+ makeChange(coins, c));
		
		int[][]cache = new int[c+1][coins.length];
		for(int[]ch:cache) {
			Arrays.fill(ch, -1);
		}
		start = System.currentTimeMillis();
		System.out.println("With DP Memo changes are  "+ findMinCoinsToMakeChangeWithMemo(coins, c,coins.length-1,cache));
		end = System.currentTimeMillis();
		System.out.println("With DP Memo total time took : "+(end-start)+" millis");*/
	}
	
	static int countWaysToMakeChange(int[] coins, int c,int k) {
		if(c==0)
			return 1;
		if(k<0) {
			return 0;
		}
		
		if(c-coins[k]<0) {
			return countWaysToMakeChange(coins, c,k-1);
		}
		
		return countWaysToMakeChange(coins, c,k-1)+(countWaysToMakeChange(coins, c-coins[k],k));
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
	
	static int findMinCoinsToMakeChangeWithMemo(int[] coins, int c, int k,int[][]cache) {
		if(c==0)
			return 0;
		
		if(k<0)
			return Integer.MAX_VALUE;
		if(cache[c][k]!=-1) {
			return cache[c][k];
		}
		// if coin value is higher than c, that means we cant take this coin in consideration, exclude this coin
		int result = 0;
		if(c-coins[k]<0)
			result = findMinCoinsToMakeChangeWithMemo(coins, c, k-1,cache);		// k-1 means exclude the coin
		else {
			result = Math.min(1+findMinCoinsToMakeChangeWithMemo(coins, c-coins[k], k,cache), findMinCoinsToMakeChangeWithMemo(coins, c, k-1,cache));
		}
		cache[c][k] = result;
		return result;
	}
	
	
	static int makeChangeMemo(int[] coins, int change,int[]cache) {
		if (change == 0) return 0;
		
		if(cache[change]!=-1) {
			return cache[change];
		}
		int minCoins = Integer.MAX_VALUE;
		
		
		// Try removing each coin from the total and
		// see how many more coins are required
		for (int coin : coins) {
			// Skip a coin if it’s value is greater
			// than the amount remaining
			if (change - coin >= 0) {
				int currMinCoins = makeChange(coins, change - coin);
				if (currMinCoins < minCoins)
					minCoins = currMinCoins;
			}
		}
		cache[change] = minCoins+1;
		
		// Add back the coin removed recursively
		return cache[change];
	}
	
	static int makeChangeDP(int[] coins, int change) {
		if (change == 0) return 0;
		
		int[] dp = new int[change+1];
		for(int i = 1; i <= change; i++) {
			int minCoins = Integer.MAX_VALUE;
			for (int coin : coins) {
				// Skip a coin if it’s value is greater
				// than the amount remaining
				if (i - coin >= 0) {
					int currMinCoins = makeChange(coins, i - coin);
					if (currMinCoins < minCoins)
						minCoins = currMinCoins;
				}
			}
			dp[i] = minCoins+1;
		}
		return dp[change];
	}
	
	static int makeChange(int[] coins, int change) {
		if (change == 0) return 0;
		
		
		int minCoins = Integer.MAX_VALUE;
		
		
		// Try removing each coin from the total and
		// see how many more coins are required
		for (int coin : coins) {
			// Skip a coin if it’s value is greater
			// than the amount remaining
			if (change - coin >= 0) {
				int currMinCoins = makeChange(coins, change - coin);
				if (currMinCoins < minCoins)
					minCoins = currMinCoins;
			}
		}
		// Add back the coin removed recursively
		return minCoins + 1;
	}
	

}
