package dynamicprogramming;

public class RegexMatchWithDPProblem {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		wildCardExample();
	}
	
	// Wild Card Example
	private static void wildCardExample() {
		String text = "aijklbmctreydev";
		String wildcard = "a*b?c*de?*";
		
		String text2 = "abbbbbcfd";
		String regex = "a.*c.d.*";
		System.out.println("Is a WildCard Match : "+isMatchWildCard(text,wildcard,0,0));
		System.out.println("Is a Regex Match : "+isMatchRegex(text2,regex,0,0));
		
	}
	
	
	private static boolean isMatchRegex(String text, String pattern, int t, int p) {
		// if that pattern is exhausted then check text is still
		if(p==pattern.length()) {
			return t==text.length();
		}
		
		// if we are at last char
		// or next char is not *, then just match == or . with current char   ae   a.  or ae
		if(p==pattern.length()-1 || pattern.charAt(p+1)!='*') {
			return (t<text.length()
					&& (text.charAt(t)==pattern.charAt(p)||pattern.charAt(p)=='.')
					&& isMatchRegex(text,pattern,t+1,p+1));
		}
	
		// zero occurrence of b*  in text acde			ab*cde
		if(isMatchRegex(text,pattern,t,p+2)) {
			return true;
		}
		
		// one or more occurrence of b*   abbbcde	ab*cde, first b matches with b* or second b matches with b*
		if(t<text.length() && (text.charAt(t)==pattern.charAt(p)||pattern.charAt(p)=='.')) {
			return isMatchRegex(text,pattern,t+1,p+2) || isMatchRegex(text,pattern,t+1,p);
		}				     //first b matches with b*		 try second b matches with b*
	
		return false;
	}
	
	private static boolean isMatchWildCard(String text, String pattern, int t, int p) {
		// if that pattern is exhausted then check text is still
		if(p==pattern.length()) {
			return t==text.length();
		}
		
		// when current char is not *, then just match == or ?   ae   a?  or ae
		if(pattern.charAt(p)!='*') {
			return (t<text.length()
					&& (text.charAt(t)==pattern.charAt(p)||pattern.charAt(p)=='?')
					&& isMatchWildCard(text,pattern,t+1,p+1));
		}
		
		// zero occurrence of *  in text acde			a*cde
		if(isMatchRegex(text,pattern,t,p+1)) {
			return true;
		}
		
		// if pattern char is * , 
		// then try every match starting from cur in text   aeb   a*eb   for this to match first do t-- and then 
		// 		call again with isMatch t+1 and p+1, 
		// otherwise try again with t+1 and p still remain same at pattern  p+1
		// first e match with next e in pattern or aeikljeb  a*b  first e match with zero or more sequence and then same with i and so on
		// until we last e that match with e in pattern and then it will be full match.
		if(t<text.length()) {
			return isMatchWildCard(text,pattern,t+1,p+1) || isMatchWildCard(text,pattern,t+1,p);
		}
		return false;
	}

}
