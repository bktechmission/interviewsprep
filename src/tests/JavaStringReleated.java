package tests;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JavaStringReleated {

	public static void main(String[] args)
	{
		String s = "abcdefghijklmn";
		System.out.println(isHavingUniqueChars(s));
		System.out.println(isPermutationOfSameString("abcdee","acbede"));
		System.out.println(isAPermuationOfAnangram("abccdedabcc"));
		String[] chars = {"abcde","efghij","ijklmn","acbed","ijefgh","gijhfe","knmlij","cabed"};
		System.out.println();
		Arrays.stream(chars).forEach(x->{System.out.print(x+", ");});
		Arrays.sort(chars,anagramComparator);
		System.out.println();
		Arrays.stream(chars).forEach(x->{System.out.print(x+", ");});
		
		String[] chars1 = {"abcdee","efghij","ijklmn","acbeed","ijefgh","gijhfe","knmlij","cabeed"};
		Map<String,List<String>> map = groupByAnagram(chars1);
		System.out.println();
		map.forEach((k,v)->{
			System.out.println(k+":"+v);
		});
		int[] c1 = {2,5,6,8,9,10,11,15,19};
		int[] c2 = {1,2,3,4,5,7,8,10,20,21};
		System.out.println();
		merge2ArraysInPlace(c1,c2);
		Arrays.stream(c1).forEach(x->{System.out.print(x+" ");});
		System.out.println();
		Arrays.stream(c2).forEach(x->{System.out.print(x+" ");});
		
		Map<String,Integer> map1 = new HashMap<String,Integer>();
		map1.put("Hi",1);
		map1.put("Hello",1);
		map1.put("h1", 2);
		System.out.println(map1.isEmpty());
		
		
	}
	private static final Comparator<String> anagramComparator = new Comparator<String>(){
		@Override
		public int compare(String s1, String s2)
		{
			return sort(s1).compareTo(sort(s2));
		}
	};
	
	public static Map<String, List<String>> groupByAnagram(String[] a)
	{
		Map<String,List<String>> map = new HashMap<String, List<String>>();
		for(String item: a)
		{
			String sortedItem = generalSort(item);
			if(!map.containsKey(sortedItem))
			{
				map.put(sortedItem, new ArrayList<String>());
			}
			List<String> temp = map.get(sortedItem);
			temp.add(item);
		}
		return map;
	}
	
	public static String generalSort(String s)
	{
		int[] charsFreqMap=new int[26];
		for(char c : s.toLowerCase().toCharArray())
		{
			charsFreqMap[c-'a']++;
		}
		
		StringBuilder sb = new StringBuilder();
		for(int i=0;i<26;i++)
		{
			if(charsFreqMap[i]>0)
			{
				sb.append((char)('a'+i));
				sb.append(charsFreqMap[i]);
			}
		}
		return sb.toString();
	}
	
	public static String sort(String s)
	{
		char[] content = s.toLowerCase().toCharArray();
		Arrays.sort(content);
		return new String(content);
	}
	
	public static boolean isHavingUniqueChars(String s)
	{
		if(s.isEmpty())
			return false;
		boolean[] exists = new boolean[256];
		for(char c: s.toLowerCase().toCharArray())
		{
			if(exists[c])
			{
				return false;
			}
			else
			{
				exists[c]= true;
			}
		}
		return true;
	}
	
	public static boolean isPermutationOfSameString(String s1, String s2)
	{
		if(s1.length()!=s2.length())
			return false;
		if(s1.isEmpty())
			return true;
		int[] freqChars = new int[26];
		for(char c: s1.toLowerCase().toCharArray())
		{
			freqChars[c-'a']++;
		}
		
		for(char c: s2.toLowerCase().toCharArray())
		{
			freqChars[c-'a']--;
			if(freqChars[c-'a']<0)
				return false;
		}
		return true;
		
	}
	
	public static boolean isAPermuationOfAnangram(String s)
	{
		int[] charsFreq = new int[26];
		for(char c: s.toLowerCase().toCharArray())
		{
			charsFreq[c-'a']++;
		}
		
		int oddCount=0;
		for(int i=0;i<26;i++)
		{
			if(charsFreq[i]%2==1)
			{
				oddCount++;
				if(oddCount>1)
					return false;
			}
		}
		return true;
	}
	
	// O(B.length)
	public static void merge2ArraysWithABigSize(int[] a, int[] b, int lastA, int lastB)
	{
		int indexA = lastA-1;	// 0-6	7
		int indexB = lastB-1;	//0-8	9
		int index = lastA+lastB-1;
		while(indexB>=0)
		{
			if(indexA>=0 && a[indexA]>b[indexB])
			{
				a[index--] = a[indexA--];
			}
			else
			{
				a[index--] = a[indexB--];
			}
		}
	}
	
	public static void merge2ArraysInPlace(int[]a, int[]b)
	{
		// O(N^2) algo
		int m = a.length;
		int n = b.length;
		for(int i=n-1;i>=0;i--)
		{
			int last = a[m-1];
			int j = m-1;
			boolean foundAny =false;
			while(j>=0)
			{
				if(a[j]>b[i])	// find the first element which is smaller than b[i]. shift elements to right in a which are greater than b[i]
				{
					foundAny = true;
					if(j<m-1)
					{
						a[j+1]=a[j];
					}
					j--;
				}
				else
				{
					break;
				}
			}
			if(foundAny)
			{
				if(j<m-1){
					a[j+1]=b[i];
				}
				else{
					a[j]=b[i];
				}
				b[i] =last;
			}
		}
	}
	
	public static int minimalDistance(int[]a,int[]b,int[]c)
	{
		int minimumGlobal = Integer.MAX_VALUE;
		int[] optIndicies = {0,0,0};
		int i=0,j=0,k=0;
		while(i<a.length || j<b.length || k<c.length)
		{
			int minimumCurrent = Math.max(Math.abs(a[i]-b[j]),Math.max(Math.abs(a[i]-c[k]),Math.abs(b[j]-c[k])));
			if(minimumCurrent<minimumGlobal)
			{
				minimumGlobal = minimumCurrent;
				optIndicies[0] = i;
				optIndicies[1] = j;
				optIndicies[2] = k;
			}
			
			if(a[i]<b[j]&&a[i]<c[k] && i<a.length-1)
			{
				i++;
			}
			else if(b[j]<c[k] && j<b.length-1)
			{
				j++;
			}
			else if(k<c.length-1)
			{
				k++;
			}
		}
		return minimumGlobal;
	}
	
}
