package leetcodeexample;

import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

/*
1------->2---->4--->7--->8-->6
 \        
  \ --->3--->5
        \--->6
       */
public class GraphProblems {

	public static void main(String[] args) {
		Graph graph = new Graph();
		graph.addEdge(1, 2);
		graph.addEdge(1, 3);
		graph.addEdge(2, 4);
		graph.addEdge(4, 7);
		graph.addEdge(7, 8);
		graph.addEdge(8, 6);
		
		graph.addEdge(3, 5);
		graph.addEdge(3, 6);
		
		System.out.println("Path exists between 1 and 5  "+graph.isPathExists(1, 8));
		
		System.out.println("Path exists between 1 and 5  "+graph.isPathExistsBFS(1, 8));
	}
}


class Graph{
	Map<Integer, Node> lookup = new HashMap<>();
	
	void addEdge(int source, int destination){
		Node s = lookup.get(source);
		Node d = lookup.get(destination);
		if(s==null) {
			s = new Node(source);
			lookup.put(source, s);
		}
		if(d==null) {
			d = new Node(destination);
			lookup.put(destination, d);
		}
		s.adjacent.add(d);
	}
	
	private Node getNode(int id) {
		return lookup.get(id);
	}

	boolean isPathExists(int s, int d){
		Node sn=getNode(s);
		Node dn=getNode(d);
		Set<Integer> visited = new HashSet<>();
		return isPathExistsDFSIterative(sn,dn,visited);
	}
	
	private boolean isPathExistsDFS(Node s, Node d, Set<Integer> visited) {
		if(visited.contains(s.id)) {
			return false;
		}
		
		visited.add(s.id);
		if(s==d) {
			return true;
		}
		System.out.println("neighbor of src:"+s.id +"  neighbors are:"+s.adjacent);
		for(Node neighbor:s.adjacent) {
			if(!visited.contains(neighbor.id) && isPathExistsDFS(neighbor,d,visited)) {
				return true;
			}
		}
		
		return false;
	}
	
	private boolean isPathExistsDFSIterative(Node s, Node d, Set<Integer> visited) {
		if(s==d) {
			return true;
		}
		Deque<Node> stack = new LinkedList<>();		// stack to mimic recursion
		stack.push(s);
		while(!stack.isEmpty()) {
			s = stack.pop();
			if(visited.contains(s.id)) {
				continue;
			}
			
			visited.add(s.id);
			if(s==d) {
				return true;
			}
			
			System.out.println("neighbor of src:"+s.id +"  neighbors are:"+s.adjacent);
			for(Node neighbor:s.adjacent) {
				if(!visited.contains(neighbor.id)) {
					stack.push(neighbor);
				}
			}
		}
		
		return false;
	}
	
	boolean isPathExistsBFS(int s, int d) {
		Node sn=getNode(s);
		Node dn=getNode(d);
		
		Queue<Node> nextToVisit = new LinkedList<>();
		nextToVisit.offer(sn);
		
		// always have this otherwise graph can have cycle
		Set<Integer> visited = new HashSet<>();
		
		while(!nextToVisit.isEmpty()) {
			Node p = nextToVisit.poll();
			
			if(visited.contains(p.id)) {
				continue;
			}
			System.out.println("neighbor of src:"+p.id +"  neighbors are:"+p.adjacent);
			if(dn==p) {
				return true;
			}
			
			visited.add(p.id);
			for(Node neighbor:p.adjacent) {
				if(!visited.contains(neighbor.id)){
					nextToVisit.offer(neighbor);
				}
			}
		}
		return false;
	}
	
	class Node{
		int id;
		List<Node> adjacent=new LinkedList<>();
		
		Node(int id){
			this.id=id;
		}
		
		public String toString() {
			return String.valueOf(this.id);
		}
	}
}


