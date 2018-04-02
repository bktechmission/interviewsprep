package stringproblems;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

public class KDistanceApartStringsProblem {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		kdistanceApartCharsInAString();
	}
	
	static void kdistanceApartCharsInAString() {
		String s = "aabccabc";
		
		String result = kapartStrings(s,3);
		
		System.out.println(result);
		
		
	}
	
	static String kapartStrings(String s, int k) {
		Map<Character, Integer> map = new HashMap<>();
		
		for(char c:s.toCharArray()) {
			Integer val = map.get(c);
			if(val!=null) {
				map.put(c, val+1);
			}
			else {
				map.put(c, 1);
			}
		}
		
		Queue<Character> q = new PriorityQueue<>( new Comparator<Character>() {
			public int compare(Character c1, Character c2) {
				if(map.get(c1)!=map.get(c2)) {
					return map.get(c2)-map.get(c1);
				}else {
					return c1.compareTo(c2);
				}
			}
		});
		
		for(Character c:  map.keySet()) {
			q.offer(c);
		}
		
		
		StringBuilder sb = new StringBuilder();
		
		int len = s.length();
		while(!q.isEmpty()) {
			int cnt = Math.min(k, len);
			List<Character> temp = new ArrayList<>();
			for(int i=0;i<cnt;i++) {
				if(q.isEmpty()) {
					return "";
				}
				
				char c = q.poll();
				sb.append(c);
				
				map.put(c, map.get(c)-1);
				
				if(map.get(c)>0) {
					temp.add(c);
				}
				else {
					map.remove(c);
				}
				len--;
			}
			
			for(char c: temp) {
				q.offer(c);
			}
		}
		
		return sb.toString();
	}
	
}
