package interviewsprep;

import java.util.HashSet;
import java.util.Set;

public class DuplicatesRemoval 
{
	public static void main(String[] args)
	{
		int[] a = {1,2,3,3,4,4,5,5,6,7,7,7,7,7,7,7};
		int end = removeDupsFromSortedArray(a);
		for(int i =0; i<=end; i++)
		{
			System.out.print(a[i]+",");
		}
		
		int[] b = {1,13,12,1234,5,5,2,3,4,4,5,6,3,3,4,5,3,3,3,3,3,45};
		b = removeDupsFromUnsortedArray(b);
		System.out.println();
		for(int i =0; i<b.length; i++)
		{
			System.out.print(b[i]+",");
		}
		
		System.out.println();
		System.out.println(isAnagram("Mary", "army"));
	}
	
	// O(n)
	public static int removeDupsFromSortedArray(int[]a)
	{
		int i = 0;
		if(a.length == 1) 
			return i;
		
		int j = 1;
		while(j<a.length)
		{
			if(a[i] != a[j])
			{
				i++;
				a[i] = a[j];
			}
			j++;
		}
		return i;
	}
	
	// O(n) : Set based approach
	public static int[] removeDupsFromUnsortedArray(int[]a)
	{
		if(a.length < 2) return a;
		
		Set<Integer> set = new HashSet<Integer>();
		
		for(int x: a)
		{
			set.add(x);
		}
		
		int[] b = new int[set.size()];
		int i = 0;
		for(int x:set)
		{
			b[i++] = x;
		}
		return b;
		
	}
	
	
	public static boolean isAnagram(String s, String b)
	{
		if(s.length() != b.length())
		{
			return false;
		}
		
		int[] check = new int[26];
		s = s.toUpperCase();
		b = b.toUpperCase();
		for(int i=0; i<s.length(); i++)
		{
			//System.out.print(s.charAt(i)-'A' + "....");
			check[s.charAt(i)-'A']++;
			check[b.charAt(i)-'A']--;
		}
		
		for(int i=0; i<s.length(); i++)
		{
			if(check[s.charAt(i)-'A'] != 0)
			{
				return false;
			}
		}
		
		return true;
	}
	
	
	
}
