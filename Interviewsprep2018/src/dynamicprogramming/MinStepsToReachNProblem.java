package dynamicprogramming;

import java.util.Arrays;

public class MinStepsToReachNProblem {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		minStepExample();
	}
	
	// either subtract 1 or div by 2 or div by 3
	static void minStepExample() {
		int n=9000;
		//System.out.println(countMinSteps(n));
		
		int[] cache = new int[n+1];
		Arrays.fill(cache, -1);
		System.out.println(countMinStepsTopDownMemo(n, cache));

		System.out.println(countMinStepsBottomUp(n));
		
	}
	
	static int countMinSteps(int n) {
		if(n==0||n==1)
			return 0;
		int minSteps = 1 + countMinSteps(n-1);
		if(n%2==0) {
			minSteps = Math.min(minSteps, 1 + countMinSteps(n/2));
		}
		if(n%3==0) {
			minSteps = Math.min(minSteps, 1 + countMinSteps(n/3));
		}
		return minSteps;
	}
	
	static int countMinStepsTopDownMemo(int n, int[]cache) {
		if(n==0||n==1)
			return 0;
		if(cache[n]!=-1) {
			return cache[n];
		}
		int minSteps = 1+ countMinStepsTopDownMemo(n-1,cache);
		if(n%2==0) {
			minSteps = Math.min(minSteps, 1 + countMinStepsTopDownMemo(n/2,cache));
		}
		if(n%3==0) {
			minSteps = Math.min(minSteps, 1 + countMinStepsTopDownMemo(n/3,cache));
		}
		cache[n] = minSteps;
		return minSteps;
	}
	
	static int countMinStepsBottomUp(int n) {
		int[]cache=new int[n+1];
		cache[0] = 0;
		cache[1] = 0;
		for(int i=2;i<=n;i++) {
			int min = 1+cache[i-1];
			if(i%2==0) {
				min = Math.min(min, 1+cache[i/2]);
			}
			if(i%3==0) {
				min = Math.min(min, 1+cache[i/3]);
			}
			cache[i] = min;
		}
		return cache[n];
	}

}
