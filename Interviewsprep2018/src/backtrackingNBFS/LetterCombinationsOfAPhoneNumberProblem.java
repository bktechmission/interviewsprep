package backtrackingNBFS;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class LetterCombinationsOfAPhoneNumberProblem {
	public static void main(String[] args) {
		phoneKeyPadProble();
	}
	
	static void phoneKeyPadProble() {
		Map<Character, String> map = new HashMap<Character, String>();
	    map.put('2', "abc");
	    map.put('3', "def");
	    map.put('4', "ghi");
	    map.put('5', "jkl");
	    map.put('6', "mno");
	    map.put('7', "pqrs");
	    map.put('8', "tuv");
	    map.put('9', "wxyz");
	    map.put('0', "");
	    
	    List<String> finalResult = new LinkedList<>();
	    String s = "23";
	    
	    getResultSet(s,finalResult, "", 0,map);
	    
	   System.out.println("final list : "+finalResult);
	 
	}
	
	static void getResultSet(String s, List<String> finalResult, String curResult, int i,
			Map<Character, String> map) {
		if(i==s.length()) {
			finalResult.add(curResult);
			return;
		}
		
		char c = s.charAt(i);
		String word = map.get(c);
		for(int j=0;j<word.length();j++) {
			getResultSet(s,finalResult,curResult+word.charAt(j),i+1,map);
		}
	}
}
