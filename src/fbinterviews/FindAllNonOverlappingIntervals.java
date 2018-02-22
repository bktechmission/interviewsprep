package fbinterviews;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class FindAllNonOverlappingIntervals {
	public static void main(String[] args)
	{
		List<Event> list = new ArrayList<Event>();
		list.add(new Event(2,4));
		list.add(new Event(1,3));
		list.add(new Event(1,4));
		list.add(new Event(4,6));
		list.add(new Event(5,7));
		list.add(new Event(6,8));
		list.add(new Event(4,5));
		list.add(new Event(5,6));
		list.add(new Event(1,5));
		list.add(new Event(7,8));
		list.add(new Event(8,9));
		list.add(new Event(10,12));
		list.add(new Event(11,12));
		list.add(new Event(12,14));

		
		// Sort on End time
		Collections.sort(list, new EventComparator());
		
		System.out.println("Total Events are : " + list.toString());
		int count = findNonOverlappingEvents(list);
		
		System.out.println("\n Total Number of Non Overlapped Events is " + count);
		
	}
	
	public static int findNonOverlappingEvents(List<Event> list)
	{
		Event prev = list.get(0);
		int count = 0;
		for(int i=1;i<list.size();i++)
		{
			Event curr = list.get(i);
			if(prev.end <= curr.start)
			{
				count++;
				System.out.println(count+". Event is "+ prev);
				prev = curr;
			}
		}
		// prev will be pointed to the last one to print as non overlapped
		count++;
		System.out.println(count+". Event is "+ prev);
		
		return count;
	}
}

class Event {
	int start;
	int end;
	
	public Event(int a, int b)
	{
		this.start = a;
		this.end = b;
	}
	
	@Override
	public String toString()
	{
		return "{"+this.start+","+this.end+"}";
	}
}

class EventComparator implements Comparator<Event>{

	@Override
	public int compare(Event o1, Event o2) {
		return Integer.compare(o1.end, o2.end);
	}
	
	
}
