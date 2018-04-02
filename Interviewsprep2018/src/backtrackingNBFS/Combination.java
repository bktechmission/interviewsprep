package backtrackingNBFS;

import java.util.ArrayList;
import java.util.List;

public class Combination {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		combinationNCRExample();
	}
	
	static void combinationNCRExample() {
		int n = 10;
		int r = 3;
		
		List<List<Integer>> finalResult = new ArrayList<>();
		List<Integer> curResult = new ArrayList<>();
		
		getAllCombinations(n,1,finalResult, curResult, 0, r);
		for(List<Integer> l: finalResult) {
			System.out.println(l);
		}
	}
	
	static void getAllCombinations(int n, int start, List<List<Integer>> finalResult, List<Integer> curResult, int k, int r) {
		if(k==r) {
			finalResult.add(new ArrayList<>(curResult));
			return;
		}
		
		for(int i=start; i<=n; i++) {
			curResult.add(i);
			getAllCombinations(n,i+1,finalResult, curResult, k+1, r);
			curResult.remove(curResult.size()-1);
		}
	}
	
}
