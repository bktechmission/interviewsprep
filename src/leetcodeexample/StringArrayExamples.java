package leetcodeexample;

import java.util.Arrays;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public class StringArrayExamples {
	
	public static void main(String[] args) {
		rotateArrayExample();
		reverseWordsInAStringExample();
		rpnExample();
		isomorphicExample();
		wordLadderExample();
		workBreakExample();
		stringMatchAlgo();
		kthLargestElementExample();
		medianOf2SortedArraysExample();
	}
	
	static void medianOf2SortedArraysExample() {
		int[]a= {2,6,9,10,15,20,25};
		int[]b= {3,4,6,7,8,9};
		// 2 3 4 6  6 7  8  9  9  10  15 20  25
		System.out.println("median of 2sorted Arrays : "+medianOf2SortedArray(a, b));
	}
	
	static void kthLargestElementExample() {
		int[] a = {2,19,13,5,17,1,10,4,9,6,21};
		
		System.out.println("3rd largest element is : "+getKthLargest(a,3));
		System.out.println("Sorted Sequence: "+ Arrays.toString(a));
		int[] b = {2,19,13,5,17,1,10,4,9,6,21};
		System.out.println("3rd largest element is : "+getKthLargestQuickSelect(b, 3));
	}
	
	static void quickSort(int[]a) {
		System.out.println("Before quick sort "+Arrays.toString(a));
		
		quickSort(a,0,a.length-1);
	}
	
	private static void quickSort(int[]a,int start, int end) {
		if(start<end) {
			int pivot = a[end];
			int left = start;
			int right = end;
			while(true) {
				while(left<right&&a[left]<pivot) 	left++;
				while(left<right&&a[right]>=pivot)	right--;
				if(left==right) {
					break;
				}
				swap(a,left,right);
			}
			swap(a,end,left);
			
			quickSort(a,start,left-1);
			quickSort(a,left+1,end);
		}
	}
	
	
	public static int getKthLargest(int[]a, int k) {
		//Arrays.sort(a);
		quickSort(a);
		return a[a.length-k];
	}
	
	public static int getKthLargestQuickSelect(int[]a, int k) {
		return getKthSmallest(a, a.length-k+1,0,a.length-1);
	}
	
	public static int getKthSmallest(int[]a, int k, int start, int end) {
		int pivot = a[end];
		int left=start;
		int right=end;
		while(true) {
			while(left<right&&a[left]<pivot)		left++;
			while(left<right&&a[right]>=pivot)	right--;
			
			if(left==right) {
				break;
			}
			
			swap(a,left,right);
		}
		
		swap(a,left,end);
		if(k==left+1) {
			return a[left];
		}
		else if(k<left+1) {
			return getKthSmallest(a,k,start,left-1);
		}
		else {
			return getKthSmallest(a,k,left+1,end);
		}
	}
	
	static void swap(int[]a, int i, int j){
		int temp = a[i];
		a[i] = a[j];
		a[j] = temp;
	}
	public static void stringMatchAlgo() {
		String needle = "seema";
		String haystack = "bhupende wife is seema malik panwar";
		int i = strStrWithKMP(needle, haystack);
		if(i==-1) {
			System.out.println("did not find match");
		}
		else {
			System.out.println("match found at index "+i +" and match is "+haystack.substring(i,i+needle.length()));
		}
	}
	
	public static void workBreakExample() {
		String s = "leetcode";
		String[] dic = {"leet","code"};
		Set<String> set = new HashSet<>(Arrays.asList(dic));
		
		System.out.println("Can be segmented using dictionary : "+wordBreakHelper(s,set,0));
	}
	
	
	static boolean wordBreakHelper(String s, Set<String> dic, int start) {
		if(start==s.length())
			return true;
		
		for(String word:dic) {
			int len = word.length();
			int end = start+len;
			
			if(end>s.length()) {
				continue;
			}
			
			if(s.substring(start, end).equals(word)) {
				return wordBreakHelper(s,dic,end);
			}
		}
		return false;
	}
	
	
	public static int strStr(String needle, String haystack) {
		if(needle==null||haystack==null || needle.length()==0 || haystack.length()==0) {
			return -1;
		}
		
		if(haystack.length()<needle.length())
		{
			return -1;
		}
		
		if(needle.length()==haystack.length()
				&& haystack.equalsIgnoreCase(needle)) {
			return 0;
		}
		
		int i=0;
		int j=0;
		while(i<(haystack.length()+1-needle.length())) {
			int nextIndexToStartFrom = i+1;
			j=0;
			while(j<needle.length()&&haystack.charAt(i)==needle.charAt(j)) {
				i++;
				j++;
			}
			if(j==needle.length()) {
				return nextIndexToStartFrom-1;
			}
			i = nextIndexToStartFrom;
		}
		
		return -1;
	}
	
	
	public static int strStrWithKMP(String needle, String haystack) {
		int[] lps = precomputeLPS(needle);
		 int i = 0, j=0;
		 while(j<haystack.length()) 
		 {
			 if(needle.charAt(i)==haystack.charAt(j)) 
			 {
				 i++;
				 j++;
			 }
			else 
			{
				if(i!=0) {
					i = lps[i-1];
				}
				else {
					j++;
				}
			}
			 if(i==needle.length()) {
				 return j-needle.length();
			 }
		 }
		
		return -1;
	}
	
	static int[] precomputeLPS(String pattern) {
		int[] lps = new int[pattern.length()];
		lps[0] = 0;
		int i=0, j=1;
		
		while(j<pattern.length()) {
			if(pattern.charAt(i)==pattern.charAt(j)) {
				lps[j] = i+1;
				i++;
				j++;
			}
			else {
				if(i!=0) {
					i = lps[i-1];
				}
				else {
					lps[j] = 0;
					j++;
				}
			}
		}
		return lps;
	}
	
	static void wordLadderExample() {
		String start = "hit";
		String end = "cog";
		Set<String> set = new HashSet<String>();
		set.add("hot");
		set.add("dot");
		set.add("dog");
		set.add("lot");
		set.add("log");
		
		System.out.println("took steps:  "+ transform(start,end,set));
	}
	
	static class WordNode{
	    String word;
	    int numSteps;
	 
	    public WordNode(String word, int numSteps){
	        this.word = word;
	        this.numSteps = numSteps;
	    }
	}
	
	static int transform(String start, String end, Set<String> dic){
		// bfs we need a queue
		Queue<WordNode> nextToVisit = new LinkedList<WordNode>();
		nextToVisit.add(new WordNode(start,1));
		dic.add(end);
		
		while(!nextToVisit.isEmpty()) {
			WordNode wordNode = nextToVisit.poll();
			String word = wordNode.word;
			
			if(word.equalsIgnoreCase(end)) {
				return wordNode.numSteps;
			}
			
			char[] array = word.toCharArray();
			for(int i=0;i<array.length;i++) {
				for(char c='a';c<='z';c++) {
					char temp = array[i];
					array[i] = c;
					
					String newWord = String.valueOf(array);
					if(dic.contains(newWord)) {
						nextToVisit.add(new WordNode(newWord,wordNode.numSteps+1));
						dic.remove(newWord);
					}
					
					array[i] = temp;
				}
			}
		}
		
		return 0;
	}
	
	
	static int medianOf2SortedArray(int[]a, int[]b) {
		int x = a.length;
		int y = b.length;
		if(x>y) {
			return medianOf2SortedArray(b,a);
		}
		
		int lowx = 0;
		int highx = x;
		while(lowx<=highx) {
			int px = (lowx+highx)/2;
			int py = (x+y+1)/2-px;
			
			int maxLeftPx = px==0?Integer.MIN_VALUE:a[px-1];
			int minRightPx = px==x?Integer.MAX_VALUE:a[px];
			
			int maxLeftPy = py==0?Integer.MIN_VALUE:b[py-1];
			int minRightPy = py==y?Integer.MAX_VALUE:b[py];
			if(maxLeftPx<=minRightPy&&maxLeftPy<=minRightPx) {
				if((x+y)%2==0) {
					return (Math.max(maxLeftPx, maxLeftPy) +Math.min(minRightPx, minRightPy))/2;
				}
				else {
					return Math.max(maxLeftPx, maxLeftPy);
				}
			}
			else if(maxLeftPx>minRightPy) {
				highx=px-1;
			}
			else {
				lowx=px+1;
			}
		}
		
		return -1;
	}
	
	static void rpnExample() {
		String[] s = {"2", "1", "+", "3", "*"};
		System.out.println("rpn result should be 9, and the result is :-> "+evalRPN(s));
	}
	
	
	//["2", "1", "+", "3", "*"] -> ((2 + 1) * 3) -> 9
	public static int evalRPN(String[] s) {
		String validOperators = "+-*/";
		Deque<String> stack = new LinkedList<String>();
		
		for(String token:s) {
			if(!validOperators.contains(token.trim())) {
				stack.push(token.trim());
			}
			else {
				int x = Integer.valueOf(stack.pop());
				int y = Integer.valueOf(stack.pop());
				switch(token.trim()) {
				case "+":
					stack.push(String.valueOf(x + y));
					break;
				case "-":
					stack.push(String.valueOf(x - y));
					break;
				case "*":
					stack.push(String.valueOf(x * y));
					break;
				case "/":
					stack.push(String.valueOf(x / y));
					break;
				}
			}
		}
		
		return Integer.valueOf(stack.pop());
	}
	
	static void isomorphicExample() {
		System.out.println(isIsomorphic("egg","bdd"));
	}
	// if they mapped char 1 to 1 map
	// example,"egg" and "add" are isomorphic, "foo" and "bar" are not.
	public static boolean isIsomorphic(String s1, String s2) {
		if(s1==null||s2==null)
	        return false;
	 
	    if(s1.length()!=s2.length())
	        return false;
	    
		Map<Character, Character> map = new HashMap<Character, Character>();
		
		for(int i =0;i<s1.length();i++) {
			char x = s1.charAt(i);
			char y = s2.charAt(i);
			if(!map.containsKey(x)) {
				if(map.containsValue(y)) {
					return false;
				}
				map.put(x, y);
				
			}else {
				if(map.get(x)!=y) {
					return false;
				}
			}
		}
		return true;
	}
	
	
	public static void reverseWordsInAStringExample() {
		String result = reverseWordsInAString("seema is nice girl");
		System.out.println("revrese string is "+result);
	}
	
	//  seema is nice girl    amees si ecin lrig  now  reverse whole  girl         seema
	public static String reverseWordsInAString(String s) {
		char SPACE = ' ';
		int i=0; 
		int j=0;
		char[] a = s.toCharArray();

		while(j<s.length()) {
			if(a[j]!=SPACE) {
				j++;
			}
			else {
				reverse(a,i,j-1);
				j++;
				i=j;
			}
		}
		reverse(a,i,j-1);
		reverse(a,0,a.length-1);
		
		return String.valueOf(a);
	}
	
	public static void reverse(char[] s, int i, int j) {
		while(i<j) {
			char temp = s[i];
			s[i] = s[j];
			s[j] = temp;
			i++;
			j--;
		}
	}
	
	public static void rotateArrayExample() {
		int[] a = {1,2,3,4,5,6};
		rotateArray(a,2);
		System.out.println(Arrays.toString(a));
	}
	
	public static void rotateArray(int[] a, int k) {
		reverseArrays(a,0,a.length-k-1);
		reverseArrays(a,a.length-k,a.length-1);
		reverseArrays(a,0,a.length-1);
	}

	public static void reverseArrays(int[]a, int i,int j) {
		while(i<j) {
			int temp = a[i];
			a[i] = a[j];
			a[j] = temp;
			i++;j--;
		}
	}
}


