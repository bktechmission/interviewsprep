package leetcodeexample;

import java.util.Arrays;
import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

public class ArrayExamplesFromSite {
	public static void main(String[] args) {
		rotateArrayExample();
		wordLadderExample();
		medianOf2SortedArray();
		
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
		
		for(int i=0;i<=m;i++) {
			dp[i][0]= 0;
		}
		
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
	
	static void medianOf2SortedArray(){
		int[] a = {12,23,34,45,46,47};
		int[] b = {1,3,5,6,8,11,13,15,18,19,21,24,26,29};
		
		//1,3,5,6,8,11,12,13,15,18,19,21,23,24,26,29,34,45,46,47;
		
		System.out.println(medianOf2SortedArrayHelper(a,b));
		
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
    
    
	static double medianOf2SortedArrayHelper(int[]a, int[]b){
		int m = a.length;
		int n = b.length;
		if(m>n) {
			return medianOf2SortedArrayHelper(b,a);
		}
		
		int lowx = 0;
		int highx = m;
		
		while(lowx<=highx) {
			int partitionx = (lowx+highx)/2;
			int partitiony = (m+n+1)/2-partitionx;
			
			int maxLeftX = partitionx==0?Integer.MIN_VALUE: a[partitionx-1];
			int minRightX = partitionx==m?Integer.MAX_VALUE : a[partitionx];
			
			int maxLeftY = partitiony==0?Integer.MIN_VALUE: b[partitiony-1];
			int minRightY = partitiony==n?Integer.MAX_VALUE : b[partitiony];
			
			if(maxLeftX<=minRightY && maxLeftY<=minRightX) {
				if((m+n)%2==0) {
					return (Math.max(maxLeftX, maxLeftY)+Math.min(minRightX, minRightY))/2.0;
				}
				else {
					return (Math.max(maxLeftX, maxLeftY));
				}
			}
			else if(maxLeftX>minRightY) {
				highx = partitionx-1;
			}
			else {
				lowx = partitionx+1;
			}
		}
		
		throw new IllegalArgumentException();
		
		
	}
	
	static void wordLadderExample() {
		String start = "hit";
		String end = "cog";
		Set<String> dic = new HashSet<>();
		dic.add("hot");
		dic.add("dot");
		dic.add("dog");
		dic.add("lot");
		dic.add("log");
		
		List<List<WordNode>> finalResult = new LinkedList<>();
		wordLadderHelper(start, end, dic, finalResult);
		
		for(List<WordNode> result : finalResult) {
			System.out.println("WordLadder is : "+result);
		}
	}
	
	static class WordNode{
		String word;
		int level;
		WordNode parent;
		WordNode(String word, int level, WordNode parent){
			this.word = word;
			this.level = level;
			this.parent = parent;
		}
		
		public String toString() {
			return word;
		}
	}
	
	
	static void wordLadderHelper(String start, String end, Set<String> dic, List<List<WordNode>> finalResult) {
		Set<String> unvisited = new HashSet<>();
		Set<String> visited = new HashSet<>();
		unvisited.addAll(dic);
		unvisited.add(end);
		
		Queue<WordNode> nextNodeToTry = new LinkedList<>();
		nextNodeToTry.offer(new WordNode(start,0,null));
		int minLevel = Integer.MAX_VALUE;
		int prevLevel = 0;
		while(!nextNodeToTry.isEmpty()) {
			WordNode node = nextNodeToTry.poll();
			String word = node.word;
			int curLevel = node.level;
			if(word.equalsIgnoreCase(end)) 
			{
				if(minLevel==Integer.MAX_VALUE) {
					minLevel = curLevel;
				}
				if(minLevel==curLevel) {
					Deque<WordNode> stack = new LinkedList<>();
					while(node!=null) {
						stack.push(node);
						node = node.parent;
					}
					List<WordNode> list = new LinkedList<>();
					while(!stack.isEmpty()) {
						list.add(stack.pop());
					}
					finalResult.add(list);
				}
				continue;
			}
			
			if(prevLevel<curLevel) {
				unvisited.removeAll(visited);
			}
			prevLevel = curLevel;
			
			char[] chararray = word.toCharArray();
			for(int i=0;i<chararray.length;i++) {
				
				for(char c='a';c<='z';c++) {
					char temp = chararray[i];
					if(temp!=c) {
						chararray[i] = c;
					}
					
					String newWord = new String(chararray);
					if(unvisited.contains(newWord)) {
						nextNodeToTry.offer(new WordNode(newWord,node.level+1,node));
						visited.add(newWord);
					}
					chararray[i] = temp;
				}
				
				
			}
			
			
		}
		
		
	}
	
	static int findMinInSortedRotateArray(int[]a){
		int i=0;
		int j=a.length-1;
		while(i<=j) {
			if(i==j||a[i]<a[j])
			{
				return a[i];
			}
			int mid = (i+j)/2;
			if(a[mid]>a[i]) {
				i=mid+1;
			}
			else{
				j=mid;
			}
		}
		return -1;
	}
	
	static int findMinInUnsortedArray(int[]a,int i, int j){
		if(i==j) {
			return a[i];
		}
		
		int mid = (i+j)/2;
		int m1 = findMinInUnsortedArray(a,i,mid);
		int m2 = findMinInUnsortedArray(a,mid+1,j);
		return Math.min(m1, m2);
	}
	
	public static void rotateArrayExample() {
		int[]a = {22,33,44,55,66,77,88,99};
		int k=12;
		rotateArray(a,k);
		System.out.println("After rotate : "+Arrays.toString(a));
		
		System.out.println("Min is "+findMinInSortedRotateArray(a));
		
		int[]b= {45,3,7,90,6,1,66,0,33};
		System.out.println("min in unsorted array: "+findMinInUnsortedArray(b,0,b.length-1));
	}
	
	private static void rotateArray(int[]a, int k){
		k=k%a.length;
		reverseArray(a,0,a.length-k-1);
		reverseArray(a,a.length-k,a.length-1);
		reverseArray(a,0,a.length-1);
		
	}
	
	private static void reverseArray(int[]a,int i, int j) {
		while(i<j) {
			int temp = a[i];
			a[i] = a[j];
			a[j] = temp;
			i++;
			j--;
		}
	}
}
