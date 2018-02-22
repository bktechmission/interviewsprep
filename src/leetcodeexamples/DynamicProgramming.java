package leetcodeexamples;

import java.util.Arrays;

public class DynamicProgramming {
	public static void main(String[] args) {
		longestSubStringWithoutRepitionOofN("abcdefcbghijk");
	}
	
	public static void longestSubStringWithoutRepition(String s){
		StringBuilder sb = new StringBuilder();
		//Set<Character> lookup = new HashSet<Character>();
		boolean[] lookup = new boolean[128];
		String maxSubString = "";
		for(int i=0;i<s.length();i++)
		{
			sb.append(s.charAt(i));
			lookup[s.charAt(i)] = true;
			for(int j=i+1;j<s.length();j++)
			{
				if(!lookup[s.charAt(j)]) {
					sb.append(s.charAt(j));
					lookup[s.charAt(j)]=true;
				}
				else
				{
					break;
				}
			}
			if(maxSubString.length()<sb.length()) {
				maxSubString = sb.toString();
			}
			sb.setLength(0);
			lookup = new boolean[256];
		}
		System.out.println(maxSubString);
	}
	public static void longestSubStringWithoutRepitionOof2N(String s){
		
		int i =0,j=0, n=s.length();
		boolean[] lookup = new boolean[128];
		int start = 0;
		int end =0;
		while(i<n && j<n)
		{
			if(!lookup[s.charAt(j)]) {
				lookup[s.charAt(j)] = true;
				j++;
			}
			else {
				if((j-i) > (end-start)+1)
				{
					start = i;
					end = j-1;
				}

				lookup[s.charAt(i)] = false;
				i++;
				
			}
		}
		if((j-i) > (end-start)+1)
		{
			start = i;
			end = j-1;
		}

		
		System.out.println(s.substring(start, end+1));
	}
	
public static void longestSubStringWithoutRepitionOofN(String s){
		int n=s.length();
		int[] lookup = new int[128];
		Arrays.fill(lookup, -1);
		int start = 0;
		int end =0;
		for(int i=0,j=0;j<n;j++)
		{
			if(lookup[s.charAt(j)]!=-1) {
				i=Math.max(lookup[s.charAt(j)]+1, i);
			}
			lookup[s.charAt(j)] =j;
			if((j-i) > (end-start))
			{
				start = i;
				end = j;
			}
		}

		System.out.println(s.substring(start, end+1));
	}
	
}
