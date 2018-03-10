package stringproblems;

import java.util.HashSet;
import java.util.Set;

public class SmallesSubstringAllCharsProblem {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		smallestSubStringExample();
		
		
		longestLenWithAtMostKUniqueChars();
		shortestLenWithAtLeastKUniqueChars();
		
	}
	  // 01234567891234	
	//   abcbccbaeabcaa   k = 3
	// i               ^
	// j          ^
	//   uniq=3   maxLen=8       startAt=0     {a:3  b:1   c:1   d:   e:0  }
	static void longestLenWithAtMostKUniqueChars() {
		int k = 3;
		String s = "abcbccbaefffeebc";
		
		int[] count = new int[256];
		int maxLen = 0; int j = 0; int maxStartAt = 0 ; int uniqueChars = 0;
		for(int i=0; i<s.length();i++) {
			if(count[s.charAt(i)]==0) {
				uniqueChars++;
			}
			count[s.charAt(i)]++;
			
			while(uniqueChars>k) {
				count[s.charAt(j)]--;
				if(count[s.charAt(j)]==0) {
					uniqueChars--;
				}
				j++;
			}
			
			int curLen = i-j+1;
			if(maxLen<curLen) {
				maxLen = curLen;
				maxStartAt = j;
			}
		}
		
		System.out.println("Max String with at most "+k+" uniqueChars start at "+maxStartAt+ "  with Len "+maxLen +"   string is  "+s.substring(maxStartAt, maxStartAt+maxLen));
		
	}
	
	//   abbacddcca
	// i     ^
	// j   ^
	// minLen=0     uniq=2       startAt=         count={a:1   b:0   c:1   d:   } 
	
	static void shortestLenWithAtLeastKUniqueChars() {
		int k = 3;
		String s = "abbccbbbbababaaaaeaeaaafffeeebbbbeeeeccc";
		
		int[] count = new int[256];
		int minLen = Integer.MAX_VALUE; int j = 0; int minStartAt = 0 ; int uniqueChars = 0;
		for(int i=0; i<s.length();i++) {
			if(count[s.charAt(i)]==0) {
				uniqueChars++;
			}
			count[s.charAt(i)]++;
			
			if(uniqueChars==k && (i-j)+1==k) {
				minLen = k;
				break;
			}
			
			while(uniqueChars==k) {
				count[s.charAt(j)]--;
				if(count[s.charAt(j)]==0) {
					minLen = (i-j)+1;
					minStartAt = j;
					uniqueChars--;
				}
				j++;
			}
		}
		
		System.out.println("Min String with at least "+k+" uniqueChars start at "+minStartAt+ "  with Len "+minLen +"   string is  "+s.substring(minStartAt, minStartAt+minLen));
		
	}
	
	
	static void smallestSubStringExample() {
		char[] dic = {'x','y','z'};
		String s = "xybyazyzyaxyxz";
		
		Set<Character> set = new HashSet<>();
		for(char c: dic) {
			set.add(c);
		}
		
		// now just have a window length
		int[] counts = new int[256];
		int uniqueCount = 0; 
		int minLength = Integer.MAX_VALUE;  
		int minStartAt = 0;
		int j = 0;
		for(int i=0;i<s.length();i++) {
			// if it not important char skip it
			if(!set.contains(s.charAt(i))) {
				continue;
			}
			
			// first check if it is a new char in our window j-->i, count will be zero first ime, please update the uniqueCounts
			if(counts[s.charAt(i)]==0) {
				uniqueCount++;
			}
			// Immediately increment the count
			counts[s.charAt(i)]++;
			
			//  10-5 + 1 = len, best case will be i to j window is same size # of chars so we find minimum already just break 
			if( (i-j+1)==set.size() && uniqueCount==set.size() ) {
				minLength = set.size();
				break;
			}
			
			
			// else do this increment j  untill we remain valid in window i--->j  # of unique chars remain all chars
			while(uniqueCount==set.size()) {
				if(!set.contains(s.charAt(j))) {
					j++;
					continue;
				}
				
				counts[s.charAt(j)]--;
				if(counts[s.charAt(j)]==0) {
					int len = (i-j)+1;
					if(minLength>len) {
						minLength = len;
						minStartAt = j;
					}
					uniqueCount--;
				}
				j++;
			}
		}
		
		
		System.out.println("min len found is "+minLength + "  start at "+minStartAt + "  string is "+s.substring(minStartAt, minStartAt+minLength));
	}
}
