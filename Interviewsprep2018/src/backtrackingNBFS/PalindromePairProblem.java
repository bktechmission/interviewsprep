package backtrackingNBFS;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PalindromePairProblem {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		palindromePairsExample();
	}
	
	
	static void palindromePairsExample() {
		String[] words = {"bat","tab","cat"};
		List<List<Integer>> finalResult = new ArrayList<>();
		palindromePairs(words,finalResult);
		System.out.println(finalResult);
	}
	
	static void palindromePairs(String[] words,List<List<Integer>> finalResult) {
		
		Map<String,Integer> map = new HashMap<String, Integer>();
		
		for(int i=0;i<words.length;i++) {
			map.put(words[i], i);
		}
		
		for(int i=0;i<words.length;i++) {
			StringBuilder word = new StringBuilder(words[i]);
			
			if(isPalindrome(word.toString(),0,word.length()-1)) {
				if(map.containsKey("")) {
					List<Integer> s = new ArrayList<>();
					s.add(map.get(word.toString()));
					s.add(map.get(""));
					finalResult.add(s);
				}
			}
			
			if(map.containsKey(word.reverse().toString())) {
				List<Integer> s = new ArrayList<>();
				s.add(map.get(word.toString()));
				s.add(map.get(word.reverse().toString()));
				finalResult.add(s);
			}
			
			String str = word.toString();
			for(int p=1;p<str.length();p++) {
				StringBuilder left = new StringBuilder(str.substring(0,p));
				StringBuilder right = new StringBuilder(str.substring(p));
				
				//   abacd  check dc exists then dc+abacd
				if(isPalindrome(left.toString(), 0, left.length()-1)) {
					if(map.containsKey(right.reverse().toString())) {
						List<Integer> s = new ArrayList<>();
						s.add(map.get(right.reverse().toString()));
						s.add(map.get(str));
						finalResult.add(s);
					}
				}
				
				// cdaba check for dc match
				if(isPalindrome(right.toString(), 0, right.length()-1)) {
					if(map.containsKey(left.reverse().toString())) {
						List<Integer> s = new ArrayList<>();
						s.add(map.get(str));
						s.add(map.get(left.reverse().toString()));
						finalResult.add(s);
					}
				}
			}
		}
	}
	
	static boolean isPalindrome(String word, int i, int j) {
		while(i<j) {
			if(word.charAt(i)!=word.charAt(j)) {
				return false;
			}
			i++;
			j--;
		}
		return true;
	}

}
