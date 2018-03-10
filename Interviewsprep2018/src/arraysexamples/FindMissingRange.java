package arraysexamples;

import java.util.LinkedList;
import java.util.List;

public class FindMissingRange {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		findMissingRangeExample();
	}
	
	static void findMissingRangeExample() {
		int[]a = {3,6,8,10,50};
		int start = 0;
		int end = 99;
		List<String> ranges = getMissingRangeFinder(a,start,end);
		
		System.out.println(ranges);
	}
	
	//   <-1>  0,  1, <1+1...3-1>,   3, <3+1.....50-1> ,50, <...>, 75  <76>
	static List<String> getMissingRangeFinder(int[]a, int start, int end){
		List<String> ranges = new LinkedList<>();
		
		int prev = -1;
		for(int i=0;i<=a.length;i++) {
			int cur = i==a.length?end+1:a[i];
			if(cur-prev>=2) {
				String rangeString = (prev+1==cur-1)?(""+(prev+1)):((prev+1)+"->"+(cur-1));
				ranges.add(rangeString);
			}
			prev = cur;
		}
		
		return ranges;
	}

}
