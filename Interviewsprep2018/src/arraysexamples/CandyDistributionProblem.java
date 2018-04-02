package arraysexamples;

import java.util.Arrays;

public class CandyDistributionProblem {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		candyDistributionExample();
	}
	
	static void candyDistributionExample() {
		int[] ratings =  {5, 6, 2, 2, 4, 8, 9, 5, 4, 0, 5, 1};
		
		int[]candies = new int[ratings.length];
		
		// first lets distribute one candies to each
		for(int i=0; i<ratings.length; i++) {
			candies[i] = 1;
		}
		
		// now lets check the constrains starting from left to right
		for(int i=1; i<ratings.length; i++) {
			if(ratings[i] > ratings[i-1]) {
				// lets put one from candy then previous one
				candies[i] = candies[i-1] + 1;
			}
		}
		
		// now lets check constraint from right side to left side
		for(int i=ratings.length-2; i>=0; i--) {
			if(ratings[i] > ratings[i+1] && candies[i] < candies[i+1]+1) {
				candies[i] = candies[i+1] +1;
			}
		}
		
		System.out.println(Arrays.toString(candies));
		
		int sum=0;
		for(int i=0;i<candies.length;i++) {
			sum+= candies[i];
		}
		
		System.out.println("Total candies needed: "+sum);
	}
}
