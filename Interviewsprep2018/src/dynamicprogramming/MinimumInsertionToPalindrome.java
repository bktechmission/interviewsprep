package dynamicprogramming;

public class MinimumInsertionToPalindrome {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		minimumCountOfInsertionToFromPalindrome();
	}
	
	static void minimumCountOfInsertionToFromPalindrome() {
		String s = "geeks";
		int i = 0;
		int j = s.length()-1;
		System.out.println(minCountToFormPalindrome(s,i,j));
		System.out.println(shortestFormPalindromeDP(s));
		
	}
	
	
	// abca
	static int minCountToFormPalindrome(String s, int i, int j) {
		if(i>j) {
			return Integer.MAX_VALUE;
		}
		
		if(i==j) {
			return 0;
		}
		
		if(i+1==j) {
			if(s.charAt(i)==s.charAt(j))
				return 0;
			else
				return 1;
		}
		
		if(s.charAt(i)==s.charAt(j)) {
			return minCountToFormPalindrome(s,i+1,j-1);
		}
		
		return 1 + Math.min(minCountToFormPalindrome(s,i+1,j), minCountToFormPalindrome(s,i,j-1));
		
	}
	
	static int minCountToFormPalindromeDP(String s, int i, int j) {
		if(i>j) {
			return Integer.MAX_VALUE;
		}
		
		if(i==j) {
			return 0;
		}
		
		if(i+1==j) {
			if(s.charAt(i)==s.charAt(j))
				return 0;
			else
				return 1;
		}
		
		if(s.charAt(i)==s.charAt(j)) {
			return minCountToFormPalindrome(s,i+1,j-1);
		}
		
		return 1 + Math.min(minCountToFormPalindrome(s,i+1,j), minCountToFormPalindrome(s,i,j-1));
		
	}
	
	static String shortestFormPalindromeDP(String s) {
		int mid = s.length()/2;
		int j = 0;
		for(int i=mid; i>=0;i--) {
			int j1 = expandAroundCenter(s, i, i);
			int j2 =  expandAroundCenter(s, i, i+1);
			j = Math.max(j1, j2);
		}
		
		StringBuilder sb = new StringBuilder(s.substring(j, s.length()));
		return sb.reverse().toString()+s;
		
	}
	
	static int expandAroundCenter(String s, int i, int j) {
		int l = i;
		int r = j;
		while(l>=0 && r<= s.length()-1 && s.charAt(i)==s.charAt(j)) {
			l--;
			j--;
		}
		return j;
	}
}
