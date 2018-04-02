package dynamicprogramming;

public class DPPractice11 {
	public static void main(String[] args) {
		findNeedleInHayStack();
	}
	
	static void findNeedleInHayStack() {
		String pattern = "IsA";
		String text = "BhupenderIsAComputerEngineer";
		
		System.out.println(patternFoundRec(pattern, text, 0));
		System.out.println(patternFoundIterative(pattern, text));
		System.out.println("By RabinKarp: "+ patternFoundRabinKarp(pattern, text));
		System.out.println("By KMP: "+ patternFoundKMP(pattern, text));
		
		
	}
	
	static boolean patternFoundRec(String pattern, String text, int i) {
		if((i + pattern.length()) > text.length()) {
			return false;
		}
		
		if(text.charAt(i) == pattern.charAt(0) 
				&& text.substring(i, i+pattern.length()).equalsIgnoreCase(pattern)) {
			return true;
		}
		return patternFoundRec(pattern, text, i+1);
	}
	
	static boolean patternFoundIterative(String pattern, String text) {
		
		int j = 0; int i=0;
		for(; i<text.length()-pattern.length()+1; i++) {
			j = 0;
			while(j<pattern.length() && text.charAt(i+j) == pattern.charAt(j)) {
				j++;
			}
			
			if(j==pattern.length()) {
				break;
			}
		}
		if(j==pattern.length()) {
			System.out.println("Pattern is matched started at index "+i + " on text "+text);
			return true;
		}
		else {
			System.out.println("No Pattern is matched.");
			return false;
		}
		
	}
	
	static final int prime = 101;
	
	static int patternFoundRabinKarp(String pattern, String text) {
		
		if(text.length() < pattern.length()) {
			return -1;
		}
		
		long phash = getHashCode(pattern, 0, pattern.length()-1);
		long thash = getHashCode(text, 0, pattern.length()-1);
		
		for(int i=1; i< text.length() - pattern.length() + 1; i++) {
			if(phash == thash && checkEquals(text, i-1, pattern)) {
				return i-1;
			}
			else {
				// Rolling Hash Function O(1), remove old char and add new char hash p.length-1
				thash = recalculateHash(text, pattern, thash, i-1);
			}
		}
		return -1;
	}
	
	static boolean checkEquals(String text, int i, String pattern) {
		int j = 0;
		while(j<pattern.length()) {
			if(text.charAt(i+j)!=pattern.charAt(j)) {
				return false;
			}
			j++;
		}
		return true;
	}
	
	// hash = [ str[i]*prime^0 + str[i+1]*prime^1 + str[i+2]*prime^2 + ..... + str[i+p-1]*prime^p-1 ] p is pattern length
	static long recalculateHash(String s, String p, long oldHash, int oldChari) {
		long newHash = oldHash - s.charAt(oldChari);	// rolling out old char
		newHash = newHash/prime;		// divide by prime 
		newHash += s.charAt(oldChari+p.length())*Math.pow(prime, p.length()-1);	// then add new char ascii*prime^p-1
		return newHash;
	}
	
	static long getHashCode(String s, int start, int end) {
		long hash = 0;
		
		for(int i=start; i<=end; i++) {
			hash += (long) (s.charAt(i)*Math.pow(prime, i));
		}
		return hash;
	}
	
	public static int patternFoundKMP(String pattern, String text) {
		
		int[] lps = computeLPS(pattern);
		
		int j=0; int i=0;
		while(i<text.length()) {
			if(text.charAt(i)==pattern.charAt(j)) {
				i++;
				j++;
			}
			else {
				if(j!=0) {
					j = lps[j-1];
				}
				else {
					i++;
				}
			}
			if(j==pattern.length()) {
				System.out.println("found pattern at "+(i-pattern.length()));
				break;
			}
		}
		if(j==pattern.length()) {
			return (i-pattern.length());
		}
		return -1;
	}
	
	static int[] computeLPS(String pattern) {
		int[] lps = new int[pattern.length()];
		lps[0] = 0;
		
		int i=1;
		int j=0;
		while(i<pattern.length()) {
			if(pattern.charAt(i)==pattern.charAt(j)) {
				j++;
				lps[i] = j;
				i++;
			}else {
				if(j!=0) {
					j = lps[j-1];
				}
				else {
					lps[i] = 0;
					i++;
				}
			}
		}
		return lps;
	}
	
}
