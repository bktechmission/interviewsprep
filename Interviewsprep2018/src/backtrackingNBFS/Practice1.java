package backtrackingNBFS;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Random;
import java.util.Set;

public class Practice1 {

	public static void main(String[] args) {
		//wordLadderExample();
		
		//mergeKSortedArraysExample();
		
		//generateAllParenthesisExample();
		
		//minSizeSubArraySum();
		
		//longestSubStringWithoutRepeatingChars();
		//longestSubStringWithoutRepeatingCharsOptimized();
		//minWindownSubstring();
		
		//longestSubstringWithAtMostKUniqChars();
		
		palindromePairsExample();
		wordPatternMatchExample();
		
		partitionArrayIntoKSubsets();
	}
	
	static void partitionArrayIntoKSubsets() {
		int[] a = {2, 1, 4, 5, 6};
		int k = 3;
		partitionArrayIntoKSubsets(a,k);
		
	}
	
	static boolean partitionArrayIntoKSubsets(int[]a, int k) {
		int n = a.length;
		if(k==1) {
			return true;
		}
		
		if(k>n) {
			return false;
		}
		
		int sum = 0;
		for(int i=0;i<a.length;i++) {
			sum += a[i];
		}
		if(k>sum) {
			return false;
		}
		
		if(sum%k != 0) {
			return false;
		}
		
		int eachSetSum = sum/k;
		int[] subsetssum = new int[k];
		
		boolean[] taken = new boolean[a.length];
		List<List<Integer>> finalResults = new ArrayList<>(k);
		
		boolean ans = canWeDivideIntoKSets(a, taken, k, n, subsetssum, eachSetSum, 0, finalResults, new ArrayList<Integer>());

		if(ans) {
			System.out.println("can be divied into k sets of equals sums as : ");
			Set<Integer> indices = new HashSet<>();
			int i = 0;
			for(List<Integer> setIndList : finalResults) {
				System.out.println(i+++" set indices are: "+setIndList);
				indices.addAll(setIndList);
			}
			
			List<Integer> newlist = new ArrayList<>();
			for(int t=0;t <a.length;t++) {
				if(!indices.contains(t)) {
					newlist.add(t);
				}
			}
			System.out.println(i+++" set indices are: "+newlist);
		}
		
		return ans;
		
		
	}
	
	static boolean canWeDivideIntoKSets(int[]a, boolean[] taken,
			int K, int N, int[] subsetssum, int eachSetSum, int si, 
			List<List<Integer>> finalResults, List<Integer> cursithIndicies ){
		
		if(subsetssum[si] == eachSetSum) {
			
			finalResults.add(new ArrayList<>(cursithIndicies));
			
			if(si == K-2) {
				return true;
			}
			
			boolean ans = canWeDivideIntoKSets(a, taken, K, N, subsetssum, eachSetSum, si+1, 
													finalResults, new ArrayList<Integer>());
			
		    if(!ans) {
		    		finalResults.remove(finalResults.size()-1);
		    }
		    return ans;
		}
		
		for(int i=0; i<a.length; i++) {
			if(!taken[i]) {
				
				int tempsum = subsetssum[si]  + a[i];
				
				if(tempsum <= eachSetSum) {
					taken[i] = true;
					subsetssum[si] = subsetssum[si] + a[i];
					cursithIndicies.add(i);
					
					boolean ans = canWeDivideIntoKSets(a, taken, K, N, subsetssum, eachSetSum, si, 
																	finalResults, cursithIndicies);
					
					cursithIndicies.remove(cursithIndicies.size()-1);
					taken[i] = false;
					subsetssum[si] = subsetssum[si] - a[i];
					
					if(ans) {
						return true;
					}
				}
			}
		}
		return false;
	}
	
	static void wordPatternMatchExample() {
		String ptr = "abab";
		String str =  "redblueredblue";
		Map<Character, String> map = new HashMap<>();
		Set<String> checkedStrings = new HashSet<>();
		System.out.println("word pattern match found: "+isPatternMatched(str,0,ptr,0,map,checkedStrings));
		
	}
	
	
	static boolean isPatternMatched(String str, int start, String ptr, int pi, 
			Map<Character,String> map, Set<String> checkedStrings) {
		
		if(start==str.length() && pi == ptr.length()) {
			return true;
		}
		
		if(start>=str.length() || pi >= ptr.length()) {
			return false;
		}
		
		char c = ptr.charAt(pi);
		for(int p = start+1; p<=str.length(); p++) {
			String sub = str.substring(start, p);
			
			if(!map.containsKey(c) && !checkedStrings.contains(sub)) {
				map.put(c, sub);
				checkedStrings.add(sub);
				
				if(isPatternMatched(str, p, ptr, pi+1, map, checkedStrings)) {
					return true;
				}
				map.remove(c);
				checkedStrings.remove(sub);
			}
			else if(map.containsKey(c) && map.get(c).equals(sub)) {
				if(isPatternMatched(str, p, ptr, pi+1, map, checkedStrings)) {
					return true;
				}
			}
		}
		return false;
		
	}
	
	
	static void longestSubstringWithAtMostKUniqChars() {
		String s = "abbcaacdcbef";
		
		int start = 0;
		int cur = 0;
		int k = 3;
		
		int numU = 0;
		int[] counts = new int[256];
		
		int maxLen = Integer.MIN_VALUE;
		int maxLenStart = 0;
		
		while(cur != s.length()) {
			if(counts[s.charAt(cur)] == 0) {
				numU++;
			}
			counts[s.charAt(cur)]++;
			
			while(numU>k) {
				counts[s.charAt(start)]--;
				if(counts[s.charAt(start)] == 0) {
					numU--;
				}
				start++;
			}
			
			if(maxLen < (cur-start+1)) {
				maxLen = (cur-start+1);
				maxLenStart = start;
			}
			cur++;
		}
		
		System.out.println("MaxString starts at "+s.substring(maxLenStart, maxLenStart+maxLen));
	}
	
	static void minWindownSubstring() {
		String s = "ADOBECODEBANC";
		String T = "ABC";
		Set<Character> set = new HashSet<>();
		for(char c: T.toCharArray()) {
			set.add(c);
		}
		
		int start = 0;
		int cur = 0;
		
		int numU = 0;
		int[] counts = new int[256];
		
		int minLen = Integer.MAX_VALUE;
		int minLenStart = 0;
		
		while(cur != s.length()) {
			if(!set.contains(s.charAt(cur))) {
				cur++;
				continue;
			}
			
			if(counts[s.charAt(cur)]==0) {
				numU++;
			}
			counts[s.charAt(cur)]++;
			
			while(numU == set.size()) {
				if(!set.contains(s.charAt(start))){
					start++;
					continue;
				}
				
				if(minLen > (cur-start+1)) {
					minLen =  (cur-start+1);
					minLenStart = start;
				}

				counts[s.charAt(start)]--;
				if(counts[s.charAt(start)] == 0) {
					numU--;
				}
				start++;
			}
			cur++;
		}
		
		System.out.println("Min Window string: "+s.substring(minLenStart, minLenStart+minLen));
	}
	
	static void palindromePairsExample() {
		String[] words = {"bat","tab","cat"};
		List<List<Integer>> finalResult = new ArrayList<>();
		palindromePairs(words,finalResult);
		System.out.println(finalResult);
	}
	
	static void palindromePairs(String[] words,List<List<Integer>> finalResult) {
		
		Map<String,Integer> map = new HashMap<String, Integer>();
		
		for(int i=0;i<words.length;i++) {
			map.put(words[i], i);
		}
		
		for(int i=0;i<words.length;i++) {
			StringBuilder word = new StringBuilder(words[i]);
			
			if(isPalindrome(word.toString(),0,word.length()-1)) {
				if(map.containsKey("")) {
					List<Integer> s = new ArrayList<>();
					s.add(map.get(word.toString()));
					s.add(map.get(""));
					finalResult.add(s);
				}
			}
			
			if(map.containsKey(word.reverse().toString())) {
				List<Integer> s = new ArrayList<>();
				s.add(map.get(word.toString()));
				s.add(map.get(word.reverse().toString()));
				finalResult.add(s);
			}
			
			String str = word.toString();
			for(int p=1;p<str.length();p++) {
				StringBuilder left = new StringBuilder(str.substring(0,p));
				StringBuilder right = new StringBuilder(str.substring(p));
				
				//   abacd  check dc exists then dc+abacd
				if(isPalindrome(left.toString(), 0, left.length()-1)) {
					if(map.containsKey(right.reverse().toString())) {
						List<Integer> s = new ArrayList<>();
						s.add(map.get(right.reverse().toString()));
						s.add(map.get(str));
						finalResult.add(s);
					}
				}
				
				// cdaba check for dc match
				if(isPalindrome(right.toString(), 0, right.length()-1)) {
					if(map.containsKey(left.reverse().toString())) {
						List<Integer> s = new ArrayList<>();
						s.add(map.get(str));
						s.add(map.get(left.reverse().toString()));
						finalResult.add(s);
					}
				}
			}
		}
	}
	
	static boolean isPalindrome(String word, int i, int j) {
		while(i<j) {
			if(word.charAt(i)!=word.charAt(j)) {
				return false;
			}
			i++;
			j--;
		}
		return true;
	}
	
	static void longestSubStringWithoutRepeatingChars() {
		String s = "abcadbef";
		
		int start = 0;
		int cur = 0;
		boolean[] exists = new boolean[256];
		
		int maxLen = Integer.MIN_VALUE;
		int maxLenStart = 0;
		
		while(cur!=s.length()) {
			while(exists[s.charAt(cur)]) {
				exists[s.charAt(start)] = false;
				start++;
			}
			exists[s.charAt(cur)]=true;
			
			if(maxLen<(cur-start+1))
			{
				maxLen = cur-start+1;
				maxLenStart = start;
			}
			cur++;
		}
		
		System.out.println("Longest String is : "+s.substring(maxLenStart, maxLenStart+maxLen));
	}
	
	static void longestSubStringWithoutRepeatingCharsOptimized() {
		String s = "abcadbef";
		
		int start = 0;
		int cur = 0;
		int[] exists = new int[256];
		
		int maxLen = Integer.MIN_VALUE;
		int maxLenStart = 0;
		
		while(cur!=s.length()) {
			if(exists[s.charAt(cur)] != 0) {
				if(exists[s.charAt(cur)] > start) {
					start = exists[s.charAt(cur)] +1;
				}
			}
			exists[s.charAt(cur)]=cur;
			
			if(maxLen<(cur-start+1))
			{
				maxLen = cur-start+1;
				maxLenStart = start;
			}
			cur++;
		}
		
		System.out.println("Longest String is : "+s.substring(maxLenStart, maxLenStart+maxLen));
	}
	
	static void minSizeSubArraySum() {
		int[] arr =  {2,1,4,2,4,3};
		int T = 7;
		
		int start = 0;
		int i = 0;
		int curSum = 0;
		int minLen = Integer.MAX_VALUE;
		int minLenStart = -1;
		while(i<arr.length) {
			curSum += arr[i];
			
			while(curSum >= T) {
				if(minLen > (i-start+1)) {
					minLen = i-start+1;
					minLenStart = start;
				}
				
				curSum -= arr[start];
				start++;
				
			}
			i++;
		}
		
		System.out.println("minlen is "+minLen);
		System.out.println("min array start at "+minLenStart + "  end at "+(minLenStart+minLen-1));
		
		
	}
	
	static void generateAllParenthesisExample(){
		int n = 3;
		generateAllParen(n,0,0,"");
	}
	
	static void generateAllParen(int n, int l, int r, String prefix) {
		
		if(l==r && l==n) {
			System.out.println(prefix);
			return;
		}
		
		if(l<n) {
			generateAllParen(n,l+1,r,prefix+"(");
		}
		
		if(r<l) {
			generateAllParen(n,l,r+1,prefix+")");
		}
	}
	
	
	
	static void wordLadderExample() {
		Set<String> dic = new HashSet<String>();
		
		String[] words = {"hot","dot","dog","lot","log"};
		dic.addAll(Arrays.asList(words));
		
		String start = "hit";
		String end = "cog";
		
		List<List<WordNode>> finalResult = new ArrayList<>();
		exploreWordLadder(start,end, dic, finalResult);
		System.out.println(finalResult);
		
	}
	
	static void exploreWordLadder(String start, String end, Set<String> dic, List<List<WordNode>> finalResult) {
		
		Queue<WordNode> q = new LinkedList<>();
		
		q.offer(new WordNode(start,0,null));
		
		dic.add(end);
		dic.add(start);
		
		Set<String> visited = new HashSet<>();
		int minLevel = Integer.MAX_VALUE;
		
		while(!q.isEmpty()) {
			WordNode n = q.poll();
			String word = n.word;
			int level = n.level;
			if(word.equals(end)) {
				if(minLevel==Integer.MAX_VALUE) {
					minLevel = level;
				}
				
				if(minLevel == level) {
					LinkedList<WordNode> temp = new LinkedList<>();
					WordNode cur = n;
					while(cur!=null) {
						temp.addFirst(cur);
						cur = cur.p;
					}
					finalResult.add(temp);
				}
				
				continue;
			}
			
			if(minLevel<level || visited.contains(word) || !dic.contains(word)) {
				continue;
			}
			
			
			visited.add(word);
			
			char[] arr = word.toCharArray();
			for(int i=0;i<arr.length;i++) {
				for(char c = 'a';c<='z';c++) {
					char temp = arr[i];
					if(c!=temp) {
						arr[i] = c;
					}
					q.offer(new WordNode(new String(arr),level+1,n));
					arr[i] = temp;
				}
			}
		}
	}
	
	
	static class WordNode{
		String word;
		int level;
		WordNode p;
		
		WordNode(String word, int level, WordNode p){
			this.word = word;
			this.level = level;
			this.p = p;
		}
		
		public String toString() {
			return word;
		}
	}
}


