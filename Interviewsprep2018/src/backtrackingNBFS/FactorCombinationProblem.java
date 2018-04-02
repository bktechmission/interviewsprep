package backtrackingNBFS;

import java.util.ArrayList;
import java.util.List;

public class FactorCombinationProblem {
	public static void main(String[] args) {
		printAllFactorsProblem();
	}
	
	static void printAllFactorsProblem() {
		int n = 12;
		List<List<Integer>> finalResult = new ArrayList<>();
		List<Integer> curResult = new ArrayList<>();
		
		getAllFactors(2,1, finalResult, curResult, n);
		for(List<Integer> l: finalResult) {
			System.out.println(l);
		}
		
	}
	
	static void getAllFactors(int start, int product, List<List<Integer>>finalResult, List<Integer>curResult, int n) {
		if(start>n||product>n) {
			return;
		}
		
		if(product==n) {
			finalResult.add(new ArrayList<Integer>(curResult));
			return;
		}
		
		for(int i=start; i<n; i++) {
			if(i*product>n) {
				break;
			}
			
			if(n%i==0) {
				curResult.add(i);
				getAllFactors(i, product*i, finalResult, curResult, n);
				curResult.remove(curResult.size()-1);
			}
		}
	}
}
