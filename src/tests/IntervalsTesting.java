package tests;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class IntervalsTesting {

	static final Interval[] intervals = {new Interval(3,5), new Interval(6,8), new Interval(9,12), new Interval(14,19)};
	public static void main(String[] args)
	{
		Interval intervalNew = new Interval(7,10);
		List<Interval> result = new ArrayList<Interval>();
		
		for(Interval interval : intervals)
		{
			if(interval.end<intervalNew.start)
			{
				result.add(interval);
			}
			else if(interval.start>intervalNew.end)
			{
				result.add(intervalNew);
				intervalNew = interval;
			}
			else if(interval.end>=intervalNew.start||interval.start<=intervalNew.end)
			{
				Interval merged = new Interval(Math.min(interval.start, intervalNew.start),Math.max(interval.end, intervalNew.end));
				intervalNew = merged;
			}
		}
		result.add(intervalNew);
		result.forEach(System.out::println);
		
		Interval[] kk = {new Interval(3,5), new Interval(4,10), new Interval(9,12), new Interval(14,19)};
		mergeOverlappingIntervals(kk);
	}
	
	
	private static void mergeOverlappingIntervals(Interval[] intervals)
	{
		System.out.println();
		for(Interval interval: intervals)
		{
			System.out.print(interval+" ");
		}
		Arrays.sort(intervals,intervalComparator);
		
		ArrayList<Interval> result = new ArrayList<Interval>();
		Interval prev = intervals[0];
		System.out.println();
		for(Interval cur: intervals)
		{
			
			if(prev.end>cur.start)
			{
				Interval merged = new Interval(prev.start,Math.max(prev.end, cur.end));
				prev = merged;
			}
			else
			{
				result.add(prev);
				prev = cur;
			}
		}
		result.add(prev);
		result.forEach(System.out::println);
	}
	
	private static Comparator<Interval> intervalComparator = new Comparator<Interval>(){

		@Override
		public int compare(Interval o1, Interval o2) {
			return (o1.start-o2.start);
		}
		
	};
}

class Interval
{
	int start;
	int end;
	Interval(int start, int end)
	{
		this.start = start;
		this.end = end;
	}
	public String toString()
	{
		return "["+start+","+end+"]";
	}
}


