package interviewsprep;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class Bitmasking 
{
	public final static DateTimeFormatter UTCDateTimeFormatter = DateTimeFormat.forPattern("MM/dd/yyyy").withZoneUTC();
	public static void main(String[] args)
	{
		
		// Even/Odd Occurrences of a number in unsorted array
		int[] a = {1,2,2,1,1,2,3,3,3,3,4,4,2,2,4,3,3,4,2};		// 1 appears odd 3 times
		findOddNumber(a);	
		int[] b = {1,2,2,3,3,1,3,1,2,2,4,3,4,3,4,3,3,4,5,4};		// 2 appears even 4 times
		findEvenNumber(b);
		
		
		// Divide/multiply by 2^i: shift right/left by i
		// calculate 3.5x  ----> 4x - x/2
		int x = 2;
		calc(x);
		
		// check if a num is even:  (x/2)*2==x or LSB should be 1
		x = 10;
		System.out.println(isEven(x));
		
		// check if a num is 2^n: check num is any power of 2: n&n-1==0
		x = 15;
		System.out.println(isPowerOf2(x));
		
		
		// add 2 numbers: sum (^) and carry (&<<1)
		int m=-1, n=-1;
		System.out.println(sums(m,n));
		
		// bits palindrome check
		System.out.println(isPalindrome(21,4));
		
		swap();
		
		greaterCheck();
		
		
		List<String> abc = new ArrayList<String>();
		
		abc.add("1");
		abc.add("2");
		abc.add("3");
		abc.add("4");
		
		System.out.println(abc.toString().substring(1,abc.toString().length()-1));
		
		
		DateTime cDate = DateTime.now();
 		DateTime fDate = cDate.plusDays(7);
 		String checkinDate =  UTCDateTimeFormatter.print(cDate);
 		String checkoutDate = UTCDateTimeFormatter.print(fDate);
 		
 		System.out.println(checkinDate +":" + checkoutDate);
 		
		System.out.println("finding time on Calendar...");
		freeTimeOnCalendar();
	}
	
	private static boolean isPalindrome(int x, int left_bit_index)
	{
		
		int right =0;
		int left = left_bit_index;
		while(right<left)
		{
			
			if(((1&(x>>right)) ^ (1&(x>>left))) == 1)
			{
				return false;
			}
			left--;
			right++;
		}
		
		return true;
	}
	
	// swap 2 numbers
	private static void swap()
	{
		int x = 10, y = 21;
		if(x!=y)
		{
			x = x^y;	// x = x+y
			y = x^y; 	// y = x-y
			x = x^y;	// x = x-y
		}
		System.out.println(x + "," + y);
	}
	
	// if check
	private static void greaterCheck()
	{
		int a = 20;
		
		int b = 21;
		
		int temp = a-b;
		
		System.out.println("check is "+ (temp>>31));
		if((temp>>>31) == 1)
		{
			System.out.println("b is greater");
		}
		else
		{
			System.out.println("a is greater");
		}
		
		System.out.println(((~0)+1));
		
		
		SimpleDateFormat sdfDate = new SimpleDateFormat("MM/dd/yyyy");//dd/MM/yyyy
		Calendar calendar = Calendar.getInstance();
 		Date now = calendar.getTime();
 		calendar.add(Calendar.WEEK_OF_YEAR, 1);
 		
 		String strDate = sdfDate.format(calendar.getTime());
		System.out.println(strDate);
		
		strDate = sdfDate.format(now);
		System.out.println(strDate);
	}
	
	
	public static int sums(int x, int y)
	{
		if(y==0) return x;
		return sums((x^y),((x&y)<<1));   // 0,4
	}
	
	// use shift instead of x%2==0
	public static boolean isEven(int x)
	{
		return ((x&1)==0);	// last bit should not be 1
	}
	
	public static boolean isPowerOf2(int x)
	{
		// if x is +ve then (x & -x == x)
		return ((x&(x-1))==0);		// 16&(16-1) == 0
	}
	
	public static void calc(int x)
	{
		//calculate 3.5x  ----> 2^2x - x/2^1
		System.out.println(((x<<2)-(x>>1)));
	}
	
	public static void findOddNumber(int[] a)
	{
		 int bitted_x = 0;
		 for(int x:a)
		 {
			 bitted_x = bitted_x ^ x;
		 }
		 
		 System.out.println(bitted_x);
	}
	
	
	public static void findEvenNumber(int[] b)
	{
		 Set<Integer> set = new HashSet<Integer>();
		 int bitted_x = 0;
		 for(int x:b)
		 {
			 bitted_x = bitted_x ^ x;
			 set.add(x);
		 }
		 
		 for(int x:set)
		 {
			 bitted_x = bitted_x ^ x;
		 }
		 
		 System.out.println(bitted_x);
	}
	
	private static void freeTimeOnCalendar()
	{
		List<TimeInterval> person1 = new ArrayList<TimeInterval>();
		List<TimeInterval> person2 = new ArrayList<TimeInterval>();
		
		person1.add(new TimeInterval(8,9));
		person1.add(new TimeInterval(10,11));
		person1.add(new TimeInterval(12,1));
		
		person2.add(new TimeInterval(8,9));
		person2.add(new TimeInterval(11,12));
		person2.add(new TimeInterval(12,15));
		
		int startTimeInDay = 8;
		int endTimeInDay = 20;
		
		Set<String> set = new HashSet<String>();
		
		for(int i=startTimeInDay; i<endTimeInDay; i++)
		{
			set.add(i+":"+(i+1));
		}
		
		
		for(TimeInterval x: person1)
		{
			int start = x.getStart();
			int end = x.getEnd();
			
			for(int i=start; i<end; i++)
			{
				set.remove(i+":"+(i+1));
			}
			
		}
		
		for(TimeInterval x: person2)
		{
			int start = x.getStart();
			int end = x.getEnd();
			
			for(int i=start; i<end; i++)
			{
				set.remove(i+":"+(i+1));
			}
			
		}
		
		List<TimeInterval> list = new ArrayList<TimeInterval>();
		
		
		
		for(String x:set)
		{
			list.add(new TimeInterval(x));
		}
		
		Collections.sort(list, new TimeIntervalComparator());
		
		for(TimeInterval x:list)
		{
			System.out.println(x);
		}
	}
	

	static class TimeInterval {
		int start;
		int end;
		
		public TimeInterval(int start, int end)
		{
			this.start = start;
			this.end = end;
		}
		
		public TimeInterval(String str)
		{
			String[] split =  str.split(":");
 			this.start = Integer.parseInt(split[0].trim());
			this.end = Integer.parseInt(split[1].trim());
		}
		
		public int getStart()
		{
			return this.start;
		}
		
		public int getEnd()
		{
			return this.end;
		}
		
		public String toString()
		{
			return "("+this.start+","+this.end+")";
		}
	}
	
	
}

class TimeIntervalComparator implements Comparator<Bitmasking.TimeInterval>
{
	@Override
	public int compare(Bitmasking.TimeInterval o1, Bitmasking.TimeInterval o2) 
	{
		return o1.start - o2.start;
	}
}


