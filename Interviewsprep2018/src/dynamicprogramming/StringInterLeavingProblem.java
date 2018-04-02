package dynamicprogramming;

import java.util.LinkedList;
import java.util.List;

public class StringInterLeavingProblem {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		stringInterleavingExample();
		
		System.out.println("mod test "+ ((-26)%10));
	}
	
	static void stringInterleavingExample() {
		String s1  = "aab";
		String s2 = "xyz";
		
		List<String> allInterleavingStrings = new LinkedList<>();
		getAllInterleavingStrings(s1,s2, 0,0,"",allInterleavingStrings);
		System.out.println(allInterleavingStrings);
		
		System.out.println("2 strings interleaving in test : "+ checkIfStringInterleave(s1,s2,"axyzab"));
		
		System.out.println("Recursive check : "+isInterleavedRec(s1,s2,"axyzab",0,0,0));
		System.out.println("DP check: "+isInterleavedWithDP(s1,s2,"axyzab"));
		
	}
	
	static void getAllInterleavingStrings(String s1, String s2, int i1, int i2, String result, List<String> finalResult){
		//Base case: If all characters of str1 and str2 have been 
	    // included in output string, then print the output string
		if(s1.length()==i1 && s2.length()==i2) {
			finalResult.add(result);
		}
		
		if(s1.length()!=i1) {
			getAllInterleavingStrings(s1, s2, i1+1, i2, result+s1.charAt(i1), finalResult);
		}
		

		if(s2.length()!=i2) {
			getAllInterleavingStrings(s1, s2, i1, i2+1, result+s2.charAt(i2), finalResult);
		}
	}
	
	// works with same chars in s1 and s2
	static boolean isInterleavedRec(String s1, String s2, String text, int i1, int i2, int k) {
		if(i1==s1.length() && i2==s2.length() && k==i1+i2) {
			return true;
		}
		
		return (s1.charAt(i1)==text.charAt(k)&&(isInterleavedRec(s1, s2, text, i1+1, i2, k+1)))
				|| (s2.charAt(i2)==text.charAt(k) && isInterleavedRec(s1, s2, text, i1, i2+1, k+1));
	}
	
	static boolean isInterleavedWithDP(String s1, String s2, String text) {
		boolean[][] dp = new boolean[s1.length()+1][s2.length()+1];
		
		if(s1.length() + s2.length() != text.length()) {
			return false;
		}
		
		for(int i=0; i<=s1.length(); i++) {
			for(int j=0; j<=s2.length(); j++) {
				//  i=2, j=3  =>  i-1+j-1+1 = i+j-1 > 4
				int k = i+j-1;
				// two empty strings have an empty string
	            // as interleaving
				if(i==0 && j==0) {
					dp[i][j] = true;
				}
				// s1 is empty
				else if(i==0) {
					dp[i][j] = dp[i][j-1];
				}
				// s2 is empty
				else if(j==0) {
					dp[i][j] = dp[i-1][j];
				}
				// Current character of text matches with current character of s1,
	            // but doesn't match with current character of s2
				else if(s1.charAt(i-1) == text.charAt(k) && s2.charAt(j-1) != text.charAt(k)) {
					dp[i][j] = dp[i-1][j];
				}
				// Current character of text matches with current character of s2,
	            // but doesn't match with current character of s1
				else if(s1.charAt(i-1) != text.charAt(k) && s2.charAt(j-1) == text.charAt(k)) {
					dp[i][j] = dp[i][j-1];
				}
				// Current character of C matches with that of both A and B
				else if(s1.charAt(i-1) == text.charAt(k) && s2.charAt(j-1) == text.charAt(k)) {
					dp[i][j] = dp[i-1][j] || dp[i][j-1];
				}
			}
		}
		
		return dp[s1.length()][s2.length()];
	}
	
	
	// String s1 and s2 does not have common char
	static boolean checkIfStringInterleave(String s1, String s2, String test) {
		
		if(s1.length()+s2.length()!=test.length()) {
			return false;
		}
		
		int i = 0;
		int j = 0;
		int k = 0;
		while(k!=test.length()) {
			// Match first character of test with first character
	        // of s1. If matches then move s1 to next 
			if(test.charAt(k)==s1.charAt(i)) {
				i++;
			}
			// Else Match first character of test with first 
	        // character of s2. If matches then move s2 to next 
			else if(test.charAt(k)==s2.charAt(j)) {
				j++;
			}
			// If doesn't match with either s1 or s2, then return
	        // false
			else {
				return false;
			}
			k++;
		}
		
		return true;
	}
}
