package priorityqueueexamples;

import java.util.Comparator;
import java.util.Deque;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

public class KClosestPointsToOriginProblem {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		kpointClosestToOriginExample();	// nlogk
	}
	static void kpointClosestToOriginExample() {
		int k = 5;
		Point[] points = {	new Point(2,4),
							new Point(1,2),
							new Point(4,3),
							new Point(3,6),
							new Point(4,1),
							new Point(3,9),
							new Point(5,4),
							new Point(4,7),
							new Point(5,5),
							new Point(3,10),
							new Point(2,8)
						};
		
		
		Queue<Point> pq = new PriorityQueue<>(new Comparator<Point>() {
			@Override
			public int compare(Point o1, Point o2) {	
				return o2.dist - o1.dist;
			}
		});
		
		for(Point p: points) {
			if(pq.size()<k) {
				pq.offer(p);
				continue;
			}
			
			if(pq.peek().dist > p.dist) {
				pq.poll();
				pq.offer(p);
			}
		}
		
		Deque<Point> finalList = new LinkedList<>();
		while(!pq.isEmpty()) {
			finalList.push(pq.poll());
		}
		
		System.out.println("Closests Point to Origin are: ");
		int i = 1;
		while(!finalList.isEmpty()) {
			System.out.print("\np"+i++ +". "+finalList.pop());
		}
	}
	
	static class Point implements Comparable<Point>{
		int x;
		int y;
		int dist;
		
		Point(int x, int y){
			this.x = x;
			this.y = y;
			this.dist =  (int) (Math.pow(this.x, 2)+Math.pow(this.y, 2));
		}

		@Override
		public int compareTo(Point o) {
			
			return dist-o.dist;
		}
		
		public String toString() {
			return "{x:"+x+",y:"+y+", dist:"+dist+"}";
		}
	}

}
