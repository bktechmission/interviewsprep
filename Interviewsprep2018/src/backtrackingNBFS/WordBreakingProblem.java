package backtrackingNBFS;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class WordBreakingProblem {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		breakStringInWordsExample();
	}
	
	static void breakStringInWordsExample() {
		String s = "iloveicecreamandmango";
		
		String[] words = {"mobile","samsung","sam","sung",
                "man","mango", "icecream","and",
                "go","i","love","ice","cream"};
		Set<String> dic = new HashSet<String>(Arrays.asList(words));
		List<List<String>> finalResult = new ArrayList<>();
		wordBreakExample(s,0,finalResult, new ArrayList<>(), dic);
		
		for(List<String> l:finalResult) {
			System.out.println(l);
		}
		
	}
	
	static void wordBreakExample(String s, int start, List<List<String>> finalResult, 
			List<String> curList, Set<String> dic){
		
		
		// if we are able to reach end of string, that means all splits we did was valid words forming strings
		if(s.length()==0) {
			List<String> list = new ArrayList<>(curList);
			finalResult.add(list);
			return;
		}
		
		// otherwise try all valid split
		// a valid split: where we can form a word from start till i
		// Process all prefixes one by one
		for(int splitAt=start+1; splitAt<=s.length(); splitAt++) {
			String sub = s.substring(start, splitAt);
			if(dic.contains(sub)) {
				curList.add(sub);
				wordBreakExample(s.substring(splitAt),0,finalResult,curList,dic);
				curList.remove(curList.size()-1);	// backtrack
			}
		}
	}

}
