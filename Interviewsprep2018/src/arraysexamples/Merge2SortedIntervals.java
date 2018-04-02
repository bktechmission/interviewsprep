package arraysexamples;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Merge2SortedIntervals {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		merge2SortedIntervalsExample();
	}
	
	static void merge2SortedIntervalsExample() {
		List<Interval> l1 = new ArrayList<>();
		l1.add(new Interval(1, 5));
		l1.add(new Interval(10, 14));
		l1.add(new Interval(16, 18));
		l1.add(new Interval(20, 24));
		l1.add(new Interval(30, 38));
		
		List<Interval> l2 = new ArrayList<>();
		l2.add(new Interval(2, 6));
		l2.add(new Interval(8, 10));
		l2.add(new Interval(11, 20));
		
		List<Interval> result = merge2SortedIntervals(l1,l2);
		
		System.out.println(result);
		
		List<Interval> l3 = new ArrayList<>();
		l3.add(new Interval(1, 5));
		l3.add(new Interval(2, 3));
		l3.add(new Interval(4, 9));
		l3.add(new Interval(7, 11));
		System.out.println(mergeIntervals(l3));
		
		
		List<Interval> newResult = insertInterval(result,new Interval(20,25));
		System.out.println(newResult);
	}
	
	
	static List<Interval> insertInterval(List<Interval> list, Interval newInterval) {
		
		List<Interval> result = new ArrayList<>();
		for(Interval cur: list) {
			if(newInterval.end< cur.start) {
				result.add(newInterval);
				newInterval = cur;
			}
			else if(newInterval.start > cur.end) {
				result.add(cur);
			}
			else {
				newInterval = Interval.mergeIntevals(newInterval, cur);
			}
		}
		
		result.add(newInterval);
		
		return result;
	}
	
	static List<Interval> mergeIntervals(List<Interval> list){
		if(list==null||list.size()==1) {
			return list;
		}
		
		// sort intervals based on start time
		Collections.sort(list, (i1,i2)->{
			return (i1.start != i2.start)?(i1.start-i2.start):(i1.end-i2.end);
		});
		
		List<Interval> result = new ArrayList<>();
		Interval prev = list.get(0);
		for(Interval cur: list) {
			if(prev.end<cur.start) {
				result.add(prev);
				prev = cur;
			}
			else {
				prev = Interval.mergeIntevals(prev, cur);
			}
		}
		
		result.add(prev);
		
		return result;
	}
	
	static List<Interval> merge2SortedIntervals(List<Interval> l1, List<Interval>l2){
		if(l1==null && l2==null) {
			return null;
		}
		
		if(l1==null) {
			return l2;
		}
		
		if(l2==null) {
			return l1;
		}
		
		
		List<Interval> result = new ArrayList<>();
		int i = 0;
		int j = 0;
		
		int m = l1.size(); int n = l2.size();
		
		while(i<m && j<n) {
			Interval i1 = l1.get(i);
			Interval i2 = l2.get(j);
			
			// first check if result's last interval is overlap free
			if(result.size()>0) {
				Interval curInterval = result.get(result.size()-1);
				if(curInterval.isOverlap(i1) || curInterval.isOverlap(i2)) {
					if(curInterval.isOverlap(i1)) {
						curInterval = Interval.mergeIntevals(curInterval, i1);
						i++;
					}
					else {
						curInterval = Interval.mergeIntevals(curInterval, i2);
						j++;
					}
					
					result.remove(result.size()-1);
					result.add(curInterval);
					
					continue;
					
				}
			}
			
			// if we reached at this point means result is not having any overlap with either i1, i2
			// now just check is i1,i2 if there is any overlap
			if(i1.isOverlap(i2)) {
				Interval newInterval = Interval.mergeIntevals(i1, i2);
				result.add(newInterval);
				i++;
				j++;
			}
			else if(i1.end < i2.start) {
				result.add(i1);
				i++;
			}
			else {
				result.add(i2);
				j++;
			}
		}
		
		if(j<n) {
			l1 = l2;
			i = j;
			m = n;
		}
		
		while(i<m) {
			Interval curResult = result.get(result.size()-1);
			Interval i1 = l1.get(i);
			if(curResult.isOverlap(i1)) {
				i1 = Interval.mergeIntevals(curResult, i1);
				result.remove(result.size()-1);
			}
			result.add(i1);
			i++;
		}
		
		return result;
	}

	static class Interval implements Comparable<Interval>{
		int start;
		int end;
		
		Interval(int start, int end){
			this.start = start;
			this.end = end;
		}
			
		@Override
		public int compareTo(Interval o) {
			// TODO Auto-generated method stub
			return (this.start!=o.start) ? this.start-o.start : (this.end-o.end);
		}
		
		public boolean isOverlap(Interval o) {
			//    this   s1------e1							     		     s1------e1
			//    o                   s2------e2                   s2------e2
			if(					this.end<o.start   || 				this.start>o.end) {
				return false;
			}
			return true;
		}
		
		public static Interval mergeIntevals(Interval i1, Interval i2) {
			Interval newInterval = new Interval(Math.min(i1.start, i2.start),Math.max(i1.end, i2.end));
			return newInterval;
		}
		
		public String toString() {
			return "{s:"+start+", e:"+end+"}";
		}
	}
}
