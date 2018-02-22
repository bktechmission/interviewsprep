package leetcodeexample;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class DynamicaProblems {
	static int counter = 1;
	
	public static void main(String[] args)
	{
		//Problem 1st: Calcualte Nth-1 fibbonacci number  0, 1, 1, 2, 3, 5, 8, 13
		//long start = System.currentTimeMillis();
		//System.out.println(fib(46-1));
		//fibUsingMemoization(46-1);
		//System.out.println(fibUsingTabulationWithReduceSpace(46-1));
		//long end = System.currentTimeMillis();
		//System.out.println("time took is : "+ (end-start)/(1000) + " seconds");
		longestSubstringWithoutRepetition("abcdef");
		int[] sequence = {3,1,2,8,4,10};
		longestIncreasingSubsequence(sequence);
		longestSubstringWithoutRepetitionOOfN("abcdefdbaijkhl");
		
		longestSubStringWithKUniqueChars("aabacbebebe",3);
		String s = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaabbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffgggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggghhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiijjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz";
		
		System.out.println(longestSubStringWithAtleastKRepetition(s,301));
		
		System.out.println(are2StringAnagramOOfN("abbc","acbb"));
		
		printAllSortedPermutationOfAString("ABCC");
		rankOfAPermutation("CCAB");
		rankOfAPermutationOfN("CCAB");
		
		printAllPermutation("1312".toCharArray(),2);
		printAllCombination("1312".toCharArray(),2);
		
		List words = Arrays.asList("abbc","aabb","aaab","acbb","aaba","abab","baaa","bbaa");
		groupAnagramsTogether(words);
		char[] c = "bhupender".toCharArray();
		reverseString(c,0,c.length-1);
		System.out.println(c);
		System.out.println(reverseSentence("I am bhupender panwar"));
		bruteForceLongestPalindromicSubString("bmamc");
		longestPalindromeSubstringUsingDP("bmambcc");
		printAllLengthSubstringPalindromeUsingDP("bmambccaacc");
		//printAllLengthSubsequencesPalindromeUsingDP("bmambcc");
		longestPalindromeSubstringWithCenterApproach("abcba");
		long start = System.currentTimeMillis();
		findPattenInAStringBruteForce("abcabeabcabeabcabeabcabeabcabeabcabeabcabeabcabeabcabeabcabeabcabxabcabeabcabxabcabeabcabeabcabeabcabx", "abcabx");
		long end = System.currentTimeMillis();
		System.out.println("time took is in sec: "+(end-start));
		start = System.currentTimeMillis();
		findPattenInAStringKNP("abcabeabcabeabcabeabcabeabcabeabcabeabcabeabcabeabcabeabcabeabcabxabcabeabcabxabcabeabcabeabcabeabcabx", "abcabx");
		end = System.currentTimeMillis();
		System.out.println("time took is in sec: "+(end-start));
		findPattenInAStringKNP("aaaabaaab","aa");
		checkStringOfNTType("abcabcabc");
	}
	
	// base approach using 2^n recursoin
	public static int fib(int n)
	{
		// base condition of recursion to break
		if(n<=1)
			return n;
		return fib(n-1) + fib(n-2);
	}
	
	// solve using DP
	public static void fibUsingMemoization(int n)
	{
		int[] lookup = new int[n+1];
		// initialize all entries to something that can tell its value not intialized yet
		// put -1
		for(int i=0;i<=n;i++)
		{
			lookup[i] = -1;
		}
		System.out.println(fibWithMemo(n,lookup));
	}
	public static int fibWithMemo(int n, int[] lookup)
	{
		if(lookup[n]==-1)
		{
			if(n<=1)
				lookup[n] = n;
			else
				lookup[n] = fibWithMemo(n-1,lookup) + fibWithMemo(n-2,lookup);
		}
		return lookup[n];
	}
	
	public static int fibUsingTabulation(int n)
	{
		int[] table = new int[n+1];
		// put base entries in table
		table[0] = 0;
		table[1] = 1;
		// fill for rest of table 
		for(int i=2;i<=n;i++)
		{
			table[i] = table[i-1] + table[i-2];
		}
		return table[n];
	}
	public static int fibUsingTabulationWithReduceSpace(int n)
	{
		int x = 0;
		int y = 1;
		if(n<=0)
			return x;
		if(n==1)
			return y;
		// fill for rest of table 
		for(int i=2;i<=n;i++)
		{
			int temp = x+y;
			x =y;
			y =temp;
		}
		return y;
	}
	
	
	public static void longestIncreasingSubsequence(int[] arr)
	{
		int n = arr.length;
		List<List<Integer>> solution = new ArrayList<>(n);	// memoization for Dynamic Programming
		// first intialize all single LIS to 1
		for(int i=0;i<n;i++)
		{
			List<Integer> list = new ArrayList<>();
			list.add(arr[i]);
			solution.add(list);
		}
		
		for(int i=1;i<n;i++)	// ending at ith index find LIS
		{
			for(int j=0;j<i;j++)		// start from start till i
			{
				// check if jth is less and current list we have is smaller, that means we can consider jth LIS
				if(arr[j] < arr[i] && solution.get(i).size()<solution.get(j).size()+1)
				{
					List<Integer> temp = new ArrayList<>(solution.get(j));
					temp.add(arr[i]);
					solution.set(i, temp);
					
				}
			}
			
		}
		//now print all incresing subsequence
		for(int i=0; i<solution.size();i++)
		{
			System.out.println(solution.get(i));
		}
		
	}
	
	// Problem longest substring string without repetition
	// naive approach is for each substring s i=0->n for j=i->n check if there is any duplicate how many substring can we have n^2
	public static void longestSubstringWithoutRepetition(String s)  // O of N^2
	{
		if(s.length()<=1)
		{
			System.out.println(s);
			return;
		}
		boolean[] charLookup = new boolean[26];
		StringBuilder sb = new StringBuilder();
		String maxString = "";
		for(int i=0;i<s.length();i++)
		{
			charLookup[(s.charAt(i)-'a')] = true;
			sb.append(s.charAt(i));
			for(int j=i+1;j<s.length();j++)
			{
				if(!charLookup[s.charAt(j)-'a'])
				{
					charLookup[s.charAt(j)-'a'] = true;
					sb.append(s.charAt(j));
				}
				else {
					break;
				}
			}
			if(maxString.length()<sb.length())
			{
				maxString = sb.toString();
			}
			sb.setLength(0);
			charLookup = new boolean[26];
		}
		System.out.println(maxString);
	}
	
	public static void longestSubstringWithoutRepetitionOOfN(String s)  // O of 2N
	{
		if(s.length()<=1)
		{
			System.out.println(s);
			return; 
		}
		int[] charLookup = new int[26];
		Arrays.fill(charLookup,-1);
		String maxString = "";
		int start=0,end=0;
		while(end<s.length())
		{
			if(charLookup[s.charAt(end)-'a']!=-1)
			{
				if(maxString.length()<(end-start))
				{
					maxString = s.substring(start, end);
				}
				start = Math.max(start, charLookup[s.charAt(end)-'a']+1);
			}
			charLookup[s.charAt(end)-'a'] = end;
			end++;
		}
		if(maxString.length()<(end-start))
		{
			maxString = s.substring(start, end);
		}
		System.out.println(maxString);
	}
	
	public static void longestSubstringWithoutRepetitionOOf2N(String s)  // O of 2N
	{
		if(s.length()<=1)
		{
			System.out.println(s);
			return; 
		}
		boolean[] charLookup = new boolean[26];
		String maxString = "";
		int start=0,end=0;
		while(end<s.length())
		{
			if(!charLookup[s.charAt(end)-'a'])
			{
				charLookup[s.charAt(end)-'a'] = true;
				end++;
			}
			else
			{
				if(maxString.length()<(end-start))
				{
					maxString = s.substring(start, end);
				}
				charLookup[s.charAt(start)-'a'] = false;
				start++;
			}
		}
		if(maxString.length()<(end-start))
		{
			maxString = s.substring(start, end);
		}
		System.out.println(maxString);
	}
	
	// Problem longest SubString with at k unique
	// This function calculates number of unique characters
	// using a associative array count[]. Returns true if
	// no. of characters are less than required else returns
	// false.
	public static boolean isValid(int[] counts, int k)
	{
		int val = 0;
		for(int i=0;i<26;i++)
		{
			if(counts[i]!=0)
			{
				val++;
			}
		}
		// Return true if k is greater than or equal to val
		return k>=val;
	}
	
	//Considering function “isValid()” takes constant time, time complexity of above solution is O(n).
	public static void longestSubStringWithKUniqueChars(String s, int k)
	{
		if(s.length()<k)
		{
			System.out.println("Less than k chars");
			return;
		}
		
		// Associative array to store the count of characters
		int[] count = new int[26];
		int uniqueChars = 0;
		// Traverse the string, Fills the associative array
	    // count[] and count number of unique characters
		for(int i=0;i<s.length();i++)
		{
			if(count[s.charAt(i)-'a']==0)
			{
				uniqueChars++;
			}
			count[s.charAt(i)-'a']++;
		}
		// If there are not enough unique characters, show
	    // an error message.
		if(uniqueChars<k)
		{
			System.out.println("Not enough unique characters");
			return;
		}
		
		count = new int[26];
		count[s.charAt(0)-'a']++;
		int start = 0, end =1;
		
		// Also initialize values for result longest window
		int maxLen = 1;
		int maxStart = 0;
		while(end<s.length())
		{
			 // Add the character 's[i]' to current window
			count[s.charAt(end)-'a']++;
			
			// If there are more than k unique characters in
	        // current window, remove from left side
			while(!isValid(count,k))
			{
				count[s.charAt(start)-'a']--;
				start++;
			}
			
			// Update the max window size if required
			if((end-start+1)>(maxLen))
			{
				maxLen = end-start+1;
				maxStart = start;
			}
			
			// increase end of window by 1
			end++;
		}
		
		System.out.println(s.substring(maxStart,maxStart+maxLen));
	}
	
	// Problem longest String with at least k repetition
	public static int longestSubStringWithAtleastKRepetition(String s, int k) {
		// if string length is less than required minimum chars then return empty
		
		if(s.length()<k)
		{
			return 0;
		}
		// first lets build the counts
		Map<Character, Integer> count = new HashMap<Character, Integer>();
		for(int i=0;i<s.length();i++)
		{
			if(!count.containsKey(s.charAt(i))) {
				count.put(s.charAt(i), 1);
			}
			else
			{
				count.put(s.charAt(i), count.get(s.charAt(i))+1);
			}
		}
		//System.out.println(count);
		// now lets find chars which freq is less than k, those will become splitset
		Set<Character> splitset = new HashSet<Character>();
		for(Character c:count.keySet())
		{
			if(count.get(c)<k)
			{
				splitset.add(c);
			}
		}
		
		// if split size is zero means we find the correct string with all chars freq at least k, return s
		if(splitset.size()==0)
		{
			return s.length();
		}
		
		// lets try to find the first split point
		int start =0, end =0;
		int maxLen = 0;
		boolean splitHasNotFound = false;
		while(end<s.length() && !splitHasNotFound)
		{	
			// split at first split point, find on left of that and then right and take max
			if(splitset.contains(s.charAt(end)))
			{
				int splitEndPoint = end;
				while(splitEndPoint>0 && splitset.contains(s.charAt(splitEndPoint)))
					splitEndPoint--;
				int leftMax = start>=splitEndPoint?0:longestSubStringWithAtleastKRepetition(s.substring(start, splitEndPoint+1),k);
				splitEndPoint = end+1;
				while(splitEndPoint<s.length() && splitset.contains(s.charAt(splitEndPoint)) )
					splitEndPoint++;
				int rightMax = splitEndPoint>=s.length()?0:longestSubStringWithAtleastKRepetition(s.substring(splitEndPoint),k);
				splitHasNotFound = true;
				maxLen = leftMax >= rightMax?leftMax:rightMax;
			}
			end++;
		}
		return maxLen;
	}
	
	// problem one String smaller one is an anagram in larger String
	
	
	// problem 2 strings are anagram of each other
	public static boolean are2StringAnagramOOfNlogN(String s1, String s2)
	{
		if(s1.length()!=s2.length())
			return false;
		
		char[] arr1 = s1.toCharArray();
		char[] arr2 = s2.toCharArray();
		Arrays.sort(arr1);
		Arrays.sort(arr2);
		return Arrays.equals(arr1, arr2);
	}
	public static boolean are2StringAnagramOOfN(String s1, String s2)
	{
		if(s1.length()!=s2.length())
			return false;
		
		int[] counts = new int[26];
		for(int i=0;i<s1.length();i++)
		{
			counts[s1.charAt(i)-'a']++;
			counts[s2.charAt(i)-'a']--;
		}
		for(int i=0;i<26;i++)
		{
			if(counts[i]!=0)
			{
				return false;
			}
		}
		return true;
	}
	
	// check what is rank of a String in Sorted Increasing Permutation
	//public 
	
	// Print all Sorted Permuations a String
	public static void printAllSortedPermutationOfAString(String s)
	{
		char[] arr = s.toCharArray();
		Arrays.sort(arr);

		int printNumber = 1;
		int firstIndex = 0;
		int ceilIndex = 0;
		while(true)
		{
			// that is the first index sorted permutation
			System.out.println(printNumber+": " + String.valueOf(arr));
			
			firstIndex = findFirstRightMostElementIndex(arr);
			
			if(firstIndex == -1)
			{
				break;
			}
			
			ceilIndex = findCeilingIndex(arr,firstIndex);
			
			swap(arr,firstIndex, ceilIndex);
			Arrays.sort(arr,firstIndex+1,arr.length);
			printNumber++;
		}
	}
	
	public static int findFirstRightMostElementIndex(char[] arr) {
		for(int i=arr.length-2;i>=0;i--)
		{
			if(arr[i]<arr[i+1])
				return i;
		}
		return -1;
	}
	
	// ceiling is greater element than index but smaller than rest on right
	public static int findCeilingIndex(char[] arr, int index)
	{
		int ceilIndex = index+1;
		for(int i=ceilIndex+1;i<arr.length;i++)
		{
			if(arr[index]<arr[i] && arr[ceilIndex] > arr[i])
			{
				ceilIndex = i;
			}
		}
		return ceilIndex;
	}
	
	public static void swap(char[] arr, int i, int j)
	{
		if(i!=j)
		{
			char temp = arr[i];
			arr[i] = arr[j];
			arr[j] = temp;
		}
	}
	
	// nPr
	public static void printAllPermutation(char[] a, int r)
	{
		int n = a.length;
		counter = 0;
		char[] data = new char[r];
		Arrays.sort(a);
		permute(a,0,n-1,r,0,data);
	}
		
	// print all permutations of a String
	public static void permute(char[] a, int l, int h, int r, int index, char[] data)
	{
		if(index==r)
		{
			System.out.println((counter)+": "+ String.valueOf(data));
			counter++;
		}
		else
		{
			for(int i=l;i<=h;i++)
			{
				swap(a,i,l);
				data[index] = a[l];
				permute(a,l+1,h,r,index+1,data);
				swap(a,i,l); // backtracking
				while(i<h && a[i]==a[i+1])
					i++;
			}
			
		}
	}
	
	// nCr
	public static void printAllCombination(char[] a, int r)
	{
		int n = a.length;
		counter = 0;
		char[] data = new char[r];
		Arrays.sort(a);
		combine(a,0,n-1,r,0,data);
	}
	
	public static void combine(char[] a, int l, int h, int r, int index, char[] data)
	{
		if(index==r)
		{
			// we filled all data, now print
			System.out.println((counter)+": "+ String.valueOf(data));
			counter++;
			return;
		}

		for(int i=l;i<=h; i++)
		{
			data[index] = a[i];
			combine(a,i+1,h,r,index+1,data);
			while(i<h && a[i]==a[i+1])
				i++;
		}
	}
	
	public static String countCharsAndEncode(String word)
	{
		int[] charsCount = new int[26];
		for(int i=0;i<word.length();i++)
		{
			charsCount[word.charAt(i)-'a']++;
		}
		
		StringBuilder sb = new StringBuilder();
		for(int i=0;i<26;i++)
		{
			if(charsCount[i]!=0)
			{
				char character = (char)('a'+i);
				sb.append(""+character+charsCount[i]);
			}
		}
		return sb.toString();
	}
	
	
	// group anagrams together from a sentence.
	public static Map<String,List<String>> groupAnagramsTogether(List<String> words){
		
		Map<String,List<String>> lookup = new HashMap<String,List<String>>(); 
		for(String word: words)
		{
			//Arrays.sort(temp);
			String key = countCharsAndEncode(word);
			if(!lookup.containsKey(key))
			{
				List<String> newList = new ArrayList<String>();
				lookup.put(key, newList);				
			}
			lookup.get(key).add(word);
		}
		lookup.forEach((k,v) -> {
			System.out.println("for key: "+ k +" anagroup is: "+ v);
		});
		return lookup;
	}
	
	// String reversal
	public static void reverseString(char[] s, int start, int end)
	{
		if(s.length<=1)
			return;
		while(start<end)
		{
			char c = s[start];
			s[start] = s[end];
			s[end] = c;
			start++;
			end--;
		}
	}
	
	// Sentence reversal, keep words unchnaged
	public static String reverseSentence(String s)
	{
		if(s.length()<=1)
			return s;
		
		char[] array = s.toCharArray();
		reverseString(array,0,array.length-1);
		int current = 0;
		int n = array.length;
	
		while(current<n)
		{
			int start=current;
			while(current<n && array[current]!=' ')
			{
				current++;
			}
			if(current<n) 
			{
				int end = current;
				reverseString(array,start,end-1);
				while(array[current]==' ')
				{
					current++;
				}
			}
		}
		return String.valueOf(array);
	}
	
	// check if a String is palindrome
	public static boolean isPalindrome(String word)
	{
		if(word.length()<=1)
			return true;
		int i=0,j=word.length()-1;
		while(i<j)
		{
			if(word.charAt(i)!=word.charAt(j))
			{
				return false;
			}
			i++;j--;
		}
		return true;
	}
	
	// Longest Palindromic Substring in a String
	// Brute force N^3
	public static void bruteForceLongestPalindromicSubString(String word) 
	{
		int maxLen = 0;
		int startOf = 0;
		for(int i=0; i<word.length();i++)
		{
			for(int j=i;j<word.length();j++)
			{
				String currentString = word.substring(i, j+1);
				
				if(isPalindrome(currentString) && maxLen<currentString.length()) {
					maxLen = currentString.length();
					startOf = i;
				}
				System.out.println("substring is "+word.substring(i, j+1));
			}
		}
		if(maxLen>0)
		{
			System.out.println("We have found maxPalindrome is : "+ word.substring(startOf,startOf+maxLen));
		}
		else {
			System.out.println("No Max Palindrome Exists");
		}
		
	}
	// For DP
	// we know single chars are always palindrome and for 2 chars we need to check array[i]==array[j] that means palindrome for 2 chars
	// for rest we can use 
	// P[i,j] = p[i+1,j-1] && a[i]==a[j]
	// p[i,i+1] = a[i] = a[i+1]
	// p[i,i] = true
	public static void longestPalindromeSubstringUsingDP(String word)
	{
		int n = word.length();
		boolean[][] p = new boolean[n][n];

		// All substrings of length 1 are palindromes
	    int maxLength = 1;
		for(int i=0;i<n;i++)
		{
			p[i][i] = true;
		}
		
		// check for sub-string of length 2.
		for(int i=0;i<n-1;i++)
		{
			if(word.charAt(i)==word.charAt(i+1)) {
				p[i][i+1] = true;
			}
		}
		for(boolean[] arr: p)
		{
			System.out.println(Arrays.toString(arr));
		}
		
		// Check for lengths greater than 2. k is length
	    // of substring
		int start=0;
		int maxLen=1;
		for(int k=3;k<n;k++)
		{
			 // Fix the starting index
			for(int i=0;i<n-k+1;i++)
			{
				// Get the ending index of substring from
	            // starting index i and length k
				int j = i+k-1;
				
				// checking for sub-string from ith index to
	            // jth index iff str[i+1] to str[j-1] is a
	            // palindrome
				if(p[i+1][j-1] && word.charAt(i)==word.charAt(j))
				{
					p[i][j] = true;
					
					if(maxLen<k)
					{
						start=i;
						maxLen = k;
					}
				}
			}
		}
		for(boolean[] arr: p)
		{
			System.out.println(Arrays.toString(arr));
		}
		System.out.println("found maxlen is : " + word.substring(start, start+maxLen));
	}
	
	
	public static void printAllLengthSubstringPalindromeUsingDP(String word)
	{
		int n = word.length();
		boolean[][] p = new boolean[n][n];
		int[][] plenghts = new int[n][n];

		// All substrings of length 1 are palindromes
	    int maxLength = 1;
		for(int i=0;i<n;i++)
		{
			p[i][i] = true;
			plenghts[i][i] = 1;
		}
		
		// check for sub-string of length 2.
		for(int i=0;i<n-1;i++)
		{
			if(word.charAt(i)==word.charAt(i+1)) {
				p[i][i+1] = true;
				plenghts[i][i+1] = 2;
			}
		}
		
		// Check for lengths greater than 2. k is length
	    // of substring
		int start=0;
		int maxLen=1;
		for(int k=3;k<n;k++)
		{
			 // Fix the starting index
			for(int i=0;i<n-k+1;i++)
			{
				// Get the ending index of substring from
	            // starting index i and length k
				int j = i+k-1;
				
				// checking for sub-string from ith index to
	            // jth index iff str[i+1] to str[j-1] is a
	            // palindrome
				if(p[i+1][j-1] && word.charAt(i)==word.charAt(j))
				{
					p[i][j] = true;
					plenghts[i][j] = plenghts[i+1][j-1]+2;
					if(maxLen<k)
					{
						start=i;
						maxLen = k;
					}
				}
			}
		}
		System.out.println("found maxlen is : " + word.substring(start, start+maxLen));
		for(int[] arr: plenghts)
		{
			System.out.println(Arrays.toString(arr));
		}
		int index =1;
		for(int i =0;i<plenghts.length;i++)
		{
			for(int j=i;j<plenghts.length;j++) {
				if(plenghts[i][j]!=0)
				{
					System.out.println(index+++". palindrome string is : " + word.substring(i,i+plenghts[i][j]));
				}
			}
		}
	}
	
	
	public static void longestPalindromeSubstringWithCenterApproach(String word) {
		int n = word.length();
		int start=0; int end=0;
		for(int i=0;i<n;i++)
		{
			int l1 = expandAroundCenter(word,i,i);
			int l2 = expandAroundCenter(word,i,i+1);
			int len = Math.max(l1, l2);
			if((end-start+1)<len)
			{
				start = i-(len-1)/2;
				end = i+len/2;
			}
		}
		System.out.println(word.substring(start, end+1));
	}
	
	public static int expandAroundCenter(String word, int left, int right)
	{
		while(left>=0&&right<word.length()&&word.charAt(left)==word.charAt(right))
		{
			left--;
			right++;
		}
		return right-left-1;
	}
	

	
	// Knuth Moris Pattern Match // On*p
	public static void findPattenInAStringBruteForce(String text, String pattern)
	{
		if(text.length()==0 || pattern.length() ==0)
		{
			System.out.println("pattern not found");
			return;
		}
		// brute force
		for(int i=0; i<text.length();i++)
		{
			int j = 0;
			while(j<pattern.length() && text.charAt(i+j)==pattern.charAt(j))
			{
				j++;
			}
			if(j==pattern.length())
			{
				System.out.println("found pattern " +pattern+ " at start of index " + (i+1) + " in the text string "+ text);
				j=0;
			}
		}
		System.out.println("no pattern exists");
	}
	
	// now knuth approach
	public static void findPattenInAStringKNP(String text, String pattern)		// abcabfghabcdijkabcabxl,  abcabx
	{
		if(text.length()==0 || pattern.length() ==0)
		{
			System.out.println("pattern not found");
			return;
		}
		int[] lps = preComputeLPS(pattern);
		// brute force
		
		// now match will start
		int j=0, i=0;
		int prevStart=0;
		while(i<text.length())
		{
			if(j<pattern.length() && text.charAt(i)==pattern.charAt(j))
			{
				i++;
				j++;
			}
			else {
				prevStart = i+1;
				if(j!=0)
				{
					j = lps[j-1];
				}
				else {
					i++;
				}
			}
			if(j==pattern.length())
			{
				System.out.println("found pattern " +pattern+ " at start of index " + (i-pattern.length()+1) + " in the text string "+ text);
				j = lps[j-1];
				if(lps[j]!=0)
					i = ++prevStart;
				j=0;
			}
		}
		System.out.println("no pattern exists");
	}
	
	public static int[] preComputeLPS(String pattern)		// abcabx
	{
		int[] lps = new int[pattern.length()];
		
		// if we found the match then increment i and j and lps[i] = j+1
		// else if j still point to 0 that means we can send more back so just increment i++; if not zero then j = lps[j-1]
		
		// length of the previous longest prefix suffix
		int j=0,i=1;
		
		 // lps[0] is always 0
		lps[0] = 0;
		
		// the loop calculates lps[i] for i = 1 to M-1
		while(i<pattern.length()) {
			if(pattern.charAt(j)==pattern.charAt(i))
			{
				lps[i] = j+1;			// abcde  matches so lps = {0,0,0,1,2,0}
				j++;
				i++;
			}
			else {  // (pat[i] != pat[j])
				if(j!=0)  // if (len != 0)
				{
					j=lps[j-1];
					// Also, note that we do not increment
	                // i here
				}
				else { // if (len == 0)
					lps[i] = 0;
					i++;
				}
			}
		}
		return lps;
	}
	
	// check if a String S is of type S =nT where T is a pattern and n>=2
	public static void checkStringOfNTType(String s)
	{
		int n = s.length();
		if(n<=1)
		{
			System.out.println("Only single character.");
			return;
		}
		int[] lps = preComputeLPS(s);
		int nlps = lps[n-1];
		if(nlps==0)
		{
			System.out.println("can not form S=nT type");
		}
		boolean check = (n%(n-nlps) == 0);
		if(check)
		{
			System.out.println("whole string can be formed as S="+s + "  n="+(n/(n-nlps))+" and T="+s.substring(0, (n-nlps)));
		}
		else {
			System.out.println("can not form S=nT type");
		}
	}
	
	public static int fact(int n) {
		return n<=1?1:n*fact(n-1);
	}
	
	// rank of permutation of a string
	public static void rankOfAPermutation(String s)
	{
		int rank=0; int n = s.length();
		int nFactorialMultiplicationFactor = fact(n);
		for(int i=0; i<n;i++)
		{
			int countOfSmallerChars = 0;
			for(int j=i+1;j<n;j++) {
				if(s.charAt(i)>s.charAt(j))
				{
					countOfSmallerChars++;
				}
			}
			nFactorialMultiplicationFactor = nFactorialMultiplicationFactor/(n-i);
			rank = rank + countOfSmallerChars*nFactorialMultiplicationFactor;
		}
		System.out.println("rank is :" + rank);
	}
	
	public static void buildFreqTable(int[] counts, String s)
	{
		for(int i=0;i<s.length();i++)
		{
			counts[s.charAt(i)]++;
		}
		
		for(int i=1;i<256;i++)
		{
			counts[i] = counts[i-1]+counts[i];
		}
	}
	
	public static void decrementCountsStartFromCharC(char c, int[] counts)
	{
		for(int i=c;i<256;i++)
		{
			counts[i]--;
		}
	}
	
	public static void rankOfAPermutationOfN(String s)
	{
		int rank=0; int n = s.length();
		int nFactorialMultiplicationFactor = fact(n);
		int[] counts = new int[256];
		buildFreqTable(counts,s);
		for(int i=0; i<n;i++)
		{
			int countOfSmallerChars = counts[s.charAt(i)-1];
			System.out.println("smaller count is "+countOfSmallerChars);
			nFactorialMultiplicationFactor = nFactorialMultiplicationFactor/(n-i);
			rank = rank + countOfSmallerChars*nFactorialMultiplicationFactor;
			decrementCountsStartFromCharC(s.charAt(i),counts);
		}
		System.out.println("rank is :" + rank);
	}
	
	
}
