package tests;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class StringQuestions {
	
	public static void main(String[] args)
	{
		String[] stopWrods = new String[] {"are","these","is","a","I","did","in","I"};
		Set<String> stopWords = new HashSet<String>(Arrays.asList(stopWrods));


		try {
			Map<String, Integer> wordFreqMap = Files.lines(Paths.get("/Users/bpanwar/target/words.txt"))
			.map(line->line.split("\\s+"))	// Stream<String[]>
			.flatMap(Arrays::stream)
			.filter(x->!stopWords.contains(x))
			.collect(Collectors.groupingBy(x->x,Collectors.reducing(0,e->1,Integer::sum)));
			wordFreqMap.entrySet().stream()
			.sorted((e1,e2)->e2.getValue().compareTo(e1.getValue()))
			.forEach(x->System.out.println(x.getKey()+ " "+x.getValue()));
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
		System.out.println("number is "+stringToInteger("-124354334"));
		List<Integer> list = new ArrayList<Integer>();
		list.add(8);
		list.add(5);
		list.add(8);
		plusOne(list);
		System.out.println("plusOne : "+list);
		plusAny(list, 5);
		System.out.println("plusOne : "+list);
		String a = "reverse me for a string";
		System.out.println("reverse me for a string:->"+reverseStringApproach1st(a) );
		char[] b = a.toCharArray();
		reverseString(b);
		System.out.println("reverse me for a string:->"+String.valueOf(b));
		
	}
	
	public static int stringToInteger(String str)
	{
		final int maxDiv10 = Integer.MAX_VALUE/10;
		// " +1234353"
		int i=0,n=str.length();
		while(i<n&&Character.isWhitespace(str.charAt(i))) i++;
		int sign=1;
		if(i<n && str.charAt(i)=='+')
		{
			sign=1;
			i++;
		}
		else if(i<n && str.charAt(i)=='-'){
			sign=-1;
			i++;
		}
		int num=0;
		while(i<n&&Character.isDigit(str.charAt(i)))
		{
			int digit = Character.getNumericValue(str.charAt(i));
			if(num>maxDiv10 || num==maxDiv10&&digit>=8)	// Max Integer can be 2147483647
			{
				return sign>0?Integer.MAX_VALUE:Integer.MIN_VALUE;
			}
			num=num*10+digit;
			i++;
		}
		return sign*num;
	}
	
	public static String reverseStringApproach1st(String str)	// need to pass in String, we will create another StringBuilder, for stating from left of original String
	{
		int j=str.length();
		StringBuilder reversed = new StringBuilder();
		for(int i=str.length()-1;i>=0;i--)
		{
			if(str.charAt(i)==' ')
			{
				j=i;
			}
			else if(i==0 || str.charAt(i-1)==' ')
			{
				if(reversed.length()!=0)
				{
					reversed.append(" ");
				}
				reversed.append(str.substring(i, j));
				
			}
		}
		return reversed.toString();
	}
	
	public static void reverseString(char[] a)
	{
		reverseWord(a,0,a.length);
		for(int i=0,j=0;j<=a.length;j++)
		{
			if(j==a.length || a[j]==' ')
			{
				reverseWord(a,i,j);
				i=j+1;
			}
		}
	}
	
	public static void reverseWord(char[] a,int start, int end)
	{
		int i=start,j=end-1;
		while(i<j)
		{
			char temp = a[i];
			a[i++] = a[j];
			a[j--] = temp;
		}
	}
	
	public boolean isStringValidPalindrome(String str)
	{
		// if leading or trailing spaces just remove those;
		str = str.trim();
		int i=0, j=str.length();
		while(i<j)
		{
			while(i<j&&Character.isWhitespace(str.charAt(i))) i++;
			while(i<j&&Character.isWhitespace(str.charAt(j))) j--;
			if(str.charAt(i)!=str.charAt(j))
				return false;
		}
		return true;
	}
	
	public boolean isNumberAPalindrome(int x)
	{
		assert x >= 0;
		int div=1;
		while(x/div>=10)
		{
			div*=10;
		}
		while(x!=0)
		{
			int l = x/div;
			int r = x%10;
			if(l!=r)
				return false;
			x = (x%div)/10;
			div/=100;
		}
		return true;
	}
	
	public static void plusOne(List<Integer>list)
	{
		for(int i=list.size()-1;i>=0;i--)
		{
			int digit = list.get(i);
			if(digit<9)
			{
				list.set(i, digit+1);
				return;
			}
			else
			{
				list.set(i, 0);
			}
		}
		// this case will handle 9 9 9  9 case
		list.add(0);
		list.set(0, 1);
		return;
	}
	
	public static void plusAny(List<Integer>list, int num)
	{
		int carry = num;
		for(int i=list.size()-1;i>=0;i--)
		{
			int digit = list.get(i);
			int sum = digit+carry;
			int newDigit = sum%10;
			carry = sum/10;
			list.set(i, newDigit);
		}
		if(carry>0)
		{
			list.add(0, carry);
		}
		return;
	}
	
	public List<Integer> spiralMatrix(int[][]a)
	{
		List<Integer> list = new ArrayList<Integer>();
		if(a.length==0) return list;
		int m= a.length, n=a[0].length;
		int row=0,col=-1;
		while(true)
		{
			for(int i=0;i<n;i++)
			{
				list.add(a[row][++col]);
			}
			if(--m == 0) break;
			for(int i=0;i<m;i++)
			{
				list.add(a[++row][col]);
			}
			if(--n == 0) break;
			for(int i=0;i<n;i++)
			{
				list.add(a[row][--col]);
			}
			if(--m == 0) break;
			for(int i=0;i<m;i++)
			{
				list.add(a[--row][col]);
			}
			if(--n == 0) break;
		}
		return list;
	}
	
	
	
}
