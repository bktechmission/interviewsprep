package dynamicprogramming;

import java.util.Deque;
import java.util.LinkedList;

public class MyDPExamples {
	public static void main(String[] args) {
		
		System.out.println(isMatchRecursive("xbylmz", "x?y*z"));
		knapsackExample();
		lcsExample();
	}
	
	static void knapsackExample() {
		int val[] = new int[]{1,4,5,7};
	    int wt[] = new int[]{1,3,4,5};
	    int  W = 7;
	    int n = val.length;
	    System.out.println(knapSack(W, wt, val, n));
	    System.out.println(ksdynamicPorblem(W, wt, val, n));
	}
	
	static void lcsExample() {
		String s1 = "AGGTAB";
	    String s2 = "GXTXAYB";
	 
	    char[] X=s1.toCharArray();
	    char[] Y=s2.toCharArray();
	    int m = X.length;
	    int n = Y.length;
	 
	    System.out.println("Length of LCS is" + " " +
	                                  lcs( X, Y, m, n ) );
	    
	    System.out.println("Length of LCS is" + " " +
	    		lcsBottomUpDP( X, Y, m, n ) );
	    
	}
	
	static int  knapSack(int W, int[] wt, int[] val, int n) {
		if(W==0||n==0) {
			return 0;
		}
		
		if(wt[n-1]>W)
		{
			return knapSack(W,wt,val,n-1);
		}
		
		return (Math.max(val[n-1]+knapSack(W-wt[n-1],wt,val,n-1), knapSack(W,wt,val,n-1)));
	}
	
	static int lcs(char[] X, char[] Y, int m, int n) {
		if(m==0||n==0) {
			return 0;
		}
		
		if(X[m-1]==Y[n-1]) {
			return 1+ lcs(X,Y,m-1, n-1);
		}
		
		return Math.max(lcs(X,Y,m-1, n), lcs(X,Y,m, n-1));
	}
	
	static int lcsBottomUpDP(char[] X, char[] Y, int m, int n) {
		int[][]dp = new int[m+1][n+1];
		
		// first col will be zero
		for(int i=0;i<=m;i++) {
			dp[i][0]= 0;
		}
		
		// first row should be zero
		for(int j=0;j<=n;j++) {
			dp[0][j]= 0;
		}
		
		for(int i=1;i<=m;i++) {
			for(int j=1;j<=n;j++) {
				if(X[i-1]==Y[j-1]) {
					dp[i][j] = 1+dp[i-1][j-1];
				}
				else {
					dp[i][j] = Math.max(dp[i-1][j],dp[i][j-1]);
				}
			}
		}
		
		int i=m;
		int j=n;
		StringBuilder sb = new StringBuilder();
		while(i>0&&j>0) {
			if(X[i-1]==Y[j-1]) {
				sb.append(X[i-1]);
				i--;j--;
			}
			else if(dp[i-1][j]>dp[i][j-1]){
				i--;
			}
			else {
				j--;
			}
		}
		
		System.out.println(sb.reverse().toString());
		
		return dp[m][n];
	}
	
	static int ksdynamicPorblem(int W, int[] wt, int[] val, int n) {
		int[][]dp = new int[n+1][W+1];
		
		for(int i=0;i<=n;i++) {
			for(int w=0;w<=W;w++) {
				if (i==0 || w==0)
					dp[i][w] = 0;
				else if(wt[i-1]>w) {
					dp[i][w] = dp[i-1][w];
				}
				else {
					dp[i][w] = Math.max(val[i-1]+dp[i-1][w-wt[i-1]], dp[i-1][w]);
				}
			}
		}
		
		int i=n;
		int w=W;
		Deque<Integer> stack = new LinkedList<>();
		while(i>0&&w>0) {
			if(dp[i][w]!=dp[i-1][w]) {
				stack.push(i);
				w = w - wt[i-1];
				i--;
			}
			else {
				i--;
			}
		}
		
		while(!stack.isEmpty()) {
			System.out.print("  Pick item "+stack.pop() + "  ");
		}
		
		return dp[n][W];
		
	}
	
	/**
     * Recursive and slow version of wild card matching.
     */
    public static boolean isMatchRecursive(String s, String p) {
        return isMatchRecursiveUtil(s.toCharArray(), p.toCharArray(), 0, 0);
    }

    private static boolean isMatchRecursiveUtil(char[] text, char[] pattern, int pos1, int pos2) {
        if (pos2 == pattern.length) {
            return text.length == pos1;
        }

        if (pattern[pos2] != '*') {
        		return pos1 < text.length && ((text[pos1] == pattern[pos2]) || pattern[pos2] == '?')
					&& isMatchRecursiveUtil(text, pattern, pos1 + 1, pos2 + 1);
        }
        
        //if we have a***b then skip to the last *
        while (pos2 < pattern.length - 1 && pattern[pos2 + 1] == '*') {
            pos2++;
        }
        pos1--;
        while (pos1 < text.length) {
            if (isMatchRecursiveUtil(text, pattern, pos1 + 1, pos2 + 1)) {
                return true;
            }
            pos1++;
        }
        return false;
    }
    
    private boolean matchRegexRecursive(char text[], char pattern[], int pos1, int pos2){
        //if pos2 has reached end of pattern means pos2 should also reach end of text for match
        //to happen
        if(pos2 == pattern.length) { 
            return pos1 == text.length;
        } 
      
        //if next character is not * means either current value at pattern and text should be same
        //or current value at pattern should be . in which case you can skip one character of text
        if(pos2 == pattern.length - 1 || pattern[pos2+1] != '*') {
            return pos1 < text.length && ((text[pos1] == pattern[pos2]) || pattern[pos2] == '.') 
            		&& matchRegexRecursive(text, pattern, pos1+1, pos2+1);
        }
  
        //if we have case like abc and ad*bc so here we totally skip d*
        if(matchRegexRecursive(text, pattern, pos1, pos2+2)){
            return true;
        }
  
        //For case like abbc and ab*c match first b with b* and then next b to c. If that does not work out
        //then try next b with b* and then c with c and so on.
        //if pattern current val is . then skip one character at time from text till we either reach end of text
        //or a match is found
        while(pos1 < text.length && (text[pos1] == pattern[pos2] || pattern[pos2] == '.')){
            if( matchRegexRecursive(text, pattern, pos1+1, pos2+2)){
                return true;
            }
            pos1++;
        }
        return false;
    }
    
	static boolean isMatch(String text1, String pattern1) {
		// your code goes here
		int i = 0;
		int j = 0;
		char[] text = text1.toCharArray();
		char[] pattern = pattern1.toCharArray();
		while (i < text1.length() && j < pattern1.length()) {
			if ((text[i] == pattern[j]) || (pattern[j] == '.')) {
				i++;
				j++;
				continue;
			} else if (pattern[j] == '*') {
				// for 1 or more match
				while (i < text1.length() && text[i] == pattern[j - 1]) {
					i++;
				}
				j++;

			} else if (pattern[j + 1] == '*') {
				j = j + 2;
			} else {
				return false;
			}
		}
		if (j == pattern1.length() && i == text1.length()) {
			return true;
		}

		return false;
	}
    
    
	
	
}
