package leetcodeexample;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Questions1_10 {
	
	static int[] a = {11,9,5,20,4,15};
	public static void main(String[] args) {
		//twoSumProblemUnsortedArraysBruteForce(a,24);
		//twoSumProblemUnsortedArraysOofN(a,24);
		//twoSumProblemSortAndIterate(a,24);
		//System.out.println(generateRandom0To9());
		//ListNode list1 = generateListNodeRandom(10);
		//ListNode list2 = generateListNodeRandom(10);
		//printListNodes(list1);
		
		//printListNodes(list2);
		//printListNodes(sum2Lists(list1,list2));
		lengthOfLongestSubstring("aab");
		lengthOfLongestSubstringOOfN("abccdaeefghij");
		System.out.println(reverseString("neelam"));
		System.out.println(isPalindrome("abcbae"));
		System.out.println(inplaceIsPalindrome("abcba"));
		System.out.println(longestPalindromeSubsString("abcdeedcbas")); // c, ca, cab, caba;  a, ab, aba;  b, ba;  a
		// DP p(i,j )  { true if si...sj is palindrome, false that means p(i,j) = p(i+1,j-1) AND si==sj
		// base condition is pii = true   because si==si  every single character is palindrome in itself
		// pii+1 = true if si==si+1
		System.out.println(longestPalindromeSubsStringByDP("abccba"));
		
	}
	
	static ListNode sum2Lists(ListNode l1, ListNode l2)
	{
		ListNode dummyHead = new ListNode(0);
		ListNode current = dummyHead;
		int carry = 0;
		while(l1!=null || l2!=null)
		{
			int x = l1!=null?l1.val:0;
			int y = l2!=null?l2.val:0;
			int sum = x + y + carry;
			carry = sum>=10?1:0;
			ListNode temp = new ListNode(sum%10);
			current.next = temp;
			current = temp;
			if(l1!=null)
			{
				l1=l1.next;
			}
			if(l2!=null)
			{
				l2=l2.next;
			}
			
		}
		if(carry>0)
		{
			ListNode temp = new ListNode(carry);
			current.next = temp;
			current = temp;
		}
		return dummyHead.next;
	}
	
	static void printListNodes(ListNode node)
	{
		while(node!=null)
		{
			System.out.print(node.val +" ");
			node = node.next;
		}
		System.out.println();
	}
	
	// Approach 1st need to find if compliment exists in the array as complimentOfX = target-x;
	public static void twoSumProblemUnsortedArraysBruteForce(int[] a, int target)
	{
		for(int i=0;i<a.length;i++)		//O(n)
		{
			int compliment = target - a[i];
			for(int j=i+1;j<a.length;j++)  //O(n)
			{
				if(compliment == a[j]) {
					System.out.println("find 2sum pairs at index "+i +" = " + a[i] +" & at index "+j+" = "+a[j] +" which sums to "+target );
				}
			}
		}
	}
	
	// Approach 2nd: First sort and then iterate so NlogN for sort and O n for iterate, O(1) space
	public static void twoSumProblemSortAndIterate(int[]a, int target)
	{
		//first sort the array nlogn
		Arrays.sort(a);
		
		Arrays.stream(a).forEach(x -> System.out.print(x + " "));
		System.out.println();
		int i = 0, j = a.length-1;
		while(i<j)
		{
			int sum = a[i]+a[j];
			if(sum == target)
			{
				System.out.println("find 2sum pairs at index "+i +" = " + a[i] +" & at index "+j+" = "+a[j] +" which sums to "+target );
				i++;j--;
			}
			else if(sum<target)
			{
				i++;
			}
			else
			{
				j--;
			}
		}
	}
	
	// Approach 3rd For each element check if its compliment exists in HashMap; 
	public static void twoSumProblemUnsortedArraysOofN(int[] a, int target)
	{
		Map<Integer,Integer> mapForLookups = new HashMap<>();
		for(int i=0;i<a.length;i++)		//O(n)
		{
			int compliment = target - a[i];
			if(mapForLookups.containsKey(compliment)) {	// O(1)
				System.out.println("find 2sum pairs at index "+i +" = " + a[i] +" & at index "+mapForLookups.get(compliment)+" = "+compliment +" which sums to "+target );
			}
			else {
				mapForLookups.put(a[i],i);
			}
		}
	}
	
	static class ListNode{
		int val;
		ListNode next;
		ListNode(int val)
		{
			this.val = val;
		}
	}
	
	static ListNode generateListNodeRandom(int length) throws IllegalArgumentException
	{
		if(length<1)
		{
			throw new IllegalArgumentException("Please provide length greater than 0");
		}
		ListNode start = new ListNode(generateRandom0To9());
		ListNode looper = start;
		if(length==1)
			return start;
		for(int i=1;i<length;i++) {
			ListNode temp = new ListNode(generateRandom0To9());
			looper.next = temp;
			looper = temp;
		}
		return start;
	}
	
	static int generateRandom0To9()
	{
		return (int)(0+Math.random()*10);
	}
	
	
	
	public static int lengthOfLongestSubstring(String s) {
        // if just one char then return from here
		if(s.length()<2)
        		return s.length();
		StringBuilder answer = new StringBuilder();
        String finalLongestString = "";
        int maxLenFoundSoFar = 0;
        for(int i=0;i<s.length();i++)
        {
        		int startingPoint = i;
            Set<Character> occurenceCheck = new HashSet<>();
            answer.append(s.charAt(startingPoint));
            occurenceCheck.add(s.charAt(startingPoint));
        		for(int checker=startingPoint+1;checker<s.length();checker++)
        		{
        			if(!occurenceCheck.contains(s.charAt(checker)))
        			{
        				answer.append(s.charAt(checker));
        				occurenceCheck.add(s.charAt(checker));
        			}
        			else
        			{
        				break;
        			}
        		}
        		if(maxLenFoundSoFar<answer.length())
			{
				maxLenFoundSoFar = answer.length();
				finalLongestString = answer.toString();
			}
        		answer.setLength(0);
        }
        System.out.println(finalLongestString);
        return maxLenFoundSoFar;
    }
	
	public static int lengthOfLongestSubstringOOfN(String s) {
		int i=0,j=0;
		Map<Character, Integer> quickLookup = new HashMap<>();
		int n = s.length();
		String finalAnswer = "";
		StringBuilder loopingBuilder = new StringBuilder();
		int maxLen = 0;
		while(i<n && j<n)
		{
			if(!quickLookup.containsKey(s.charAt(j)))
			{
				loopingBuilder.append(s.charAt(j));
				quickLookup.put(s.charAt(j), j++);
				
			}
			else
			{
				if(maxLen<loopingBuilder.length())
				{
					maxLen = loopingBuilder.length();
					finalAnswer = loopingBuilder.toString();
				}
				
				loopingBuilder.setLength(0);
				i=quickLookup.get(s.charAt(j))+1;  // as we found the match, get the index of   abcdeefgha
				quickLookup.clear();
				
			}
		}
		if(maxLen<loopingBuilder.length())
		{
			maxLen = loopingBuilder.length();
			finalAnswer = loopingBuilder.toString();
		}
		System.out.println(finalAnswer);
		return maxLen;
	}
	
	public static String reverseString(String s)
	{
		StringBuilder ans = new StringBuilder();
		for(int i=s.length()-1;i>=0;i--)
		{
			ans.append(s.charAt(i));
		}
		
		return ans.toString();
	}
	
	public static boolean isPalindrome(String s)
	{
		if(s.equals(reverseString(s)))
			return true;
		return false;
	}
	
	public static boolean inplaceIsPalindrome(String s) 
	{
		int i=0,j=s.length()-1;
		while(i<j) {
			if(s.charAt(i++)!=s.charAt(j--))
				return false;
		}
		return true;
	}
	
	public static String longestPalindromeSubsString(String s) 
	{
		String bestAnsSoFar = "";
		for(int i=0;i<s.length();i++)
		{
			for(int j=i+1;j<=s.length();j++)
			{
				String currentAns = s.substring(i, j);
				if(inplaceIsPalindrome(currentAns))
				{
					if(bestAnsSoFar.length()<currentAns.length()) {
						bestAnsSoFar =  currentAns;
					}
				}
			}
		}
		return bestAnsSoFar;
	}
	
	public static String longestPalindromeSubsStringByDP(String s) 
	{
		
		// first fill in the base case
		// 1. case where single chars  P[i,i]  = true
		// 2. case with 2 chars P[i,i+1] = true if only s(i) == s(i+1)
		boolean[][] resultHolder = new boolean[s.length()][s.length()];
		int n=s.length();
		for(int i=0;i<n;i++)
		{
			resultHolder[i][i]=true;
			if(i+1<n)
			{
				resultHolder[i][i+1]=(s.charAt(i)==s.charAt(i+1));
				resultHolder[i+1][i]=resultHolder[i][i+1];
			}	
		}
		int start = 0;
		int end = start+1;
		for(int i=0;i<n;i++)
		{
			for(int j=i+1;j<n;j++)
			{
				
				resultHolder[i][j] = j>i+1?(resultHolder[i+1][j-1] && (s.charAt(i) == s.charAt(j))):resultHolder[i][j];
				resultHolder[j][i] = resultHolder[i][j];
				
				if(resultHolder[i][j]) 
				{
					if((end-start)<(j-i+1))
					{
						end = j+1;
						start = i;
					}
				}
			}
		}
		for(int i=0;i<n;i++)
		{
			for(int j=0;j<n;j++)
			{
				System.out.print(resultHolder[i][j] +" ");
			}
			System.out.println();
		}
		return s.substring(start, end);
	
	}
	
}
