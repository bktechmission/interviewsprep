package stringproblems;

import java.util.Arrays;

public class LongestSubstringWithoutRepetitionCharsProblem {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		uniqueCharsMaximumLengthString();
	}
	static void uniqueCharsMaximumLengthString() {
		String s = "abcdefci";
		
		int maxLen = Integer.MIN_VALUE;
		int startMaxAt = 0;
		int j = 0;
		
		boolean[] exists = new boolean[256];
		for(int i=0;i<s.length();i++) {
			while(exists[s.charAt(i)]) {
				exists[s.charAt(j)] = false;
				j++;
			}
			
			exists[s.charAt(i)] = true;
			int curLen = i-j+1;
			if(maxLen<curLen) {
				maxLen = curLen;
				startMaxAt = j;
			}
		}
		
		System.out.println("Max string found at "+startMaxAt + "  len "+maxLen + "  string is "+s.substring(startMaxAt, startMaxAt+maxLen));
		
	}
	

	
	static void uniqueCharsMaximumLengthStringOptimized() {
		String s = "abcdefci";
		
		int maxLen = Integer.MIN_VALUE;
		int startMaxAt = 0;
		int j = 0;
		
		int[] exists = new int[256];
		Arrays.fill(exists, -1);
		
		for(int i=0;i<s.length();i++) {
			if(exists[s.charAt(i)]!=-1) {
				if(exists[s.charAt(i)]>j) {
					j = exists[s.charAt(i)] + 1;		
				}
			}
			exists[s.charAt(i)] = i;
			int curLen = i-j+1;
			if(maxLen<curLen) {
				maxLen = curLen;
				startMaxAt = j;
			}
		}
		
		System.out.println("Max string found at "+startMaxAt + "  len "+maxLen + "  string is "+s.substring(startMaxAt, startMaxAt+maxLen));
		
	}

}
