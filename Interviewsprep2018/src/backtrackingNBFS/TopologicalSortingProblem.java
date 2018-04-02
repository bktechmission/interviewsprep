package backtrackingNBFS;

import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public class TopologicalSortingProblem {
	public static void main(String[] args) {
		taskExecutionSequenceExample();
	}
	
	
	/*
	 *  			   a	    b   d
	 * 			    \  /    |
	 *                Y		Y
	 *                c     e
	 *                 \   / |
	 *                   Y   Y
	 *                   f    g
	 */
	static void taskExecutionSequenceExample() {
		DependencyGraph graph = new DependencyGraph();
		graph.addDependencyEdge("a", "c");
		graph.addDependencyEdge("b", "c");
		graph.addDependencyEdge("d", "e");
		graph.addDependencyEdge("e", "g");
		graph.addDependencyEdge("e", "f");
		graph.addDependencyEdge("c", "f");
		//graph.addDependencyEdge("f", "d");			//this edge make cycle so no sequence can be made

		System.out.println("Sequence is : "+graph.getExecutionSequence());
		//System.out.println(graph.isCyclicUsingColor());
		System.out.println("All sequence are :");
		List<List<String>> finalResult = graph.getAllExecutionSequences();
		for(List<String> l: finalResult) {
			System.out.println("seq is : "+l);
		}
		
		
	}
	
	static class DependencyGraph{
		final Map<String, TaskNode> graph;
		DependencyGraph(){
			graph = new HashMap<>();
		}
		
		// a->b  a is pre, b is dep    pre->dep
		public void addDependencyEdge(String pre, String dep){
			TaskNode prereq = graph.get(pre);
			if(prereq == null) {
				prereq = new TaskNode(pre);
				graph.put(pre, prereq);
			}
			
			TaskNode dependency = graph.get(dep);
			if(dependency == null) {
				dependency = new TaskNode(dep);
				graph.put(dep, dependency);
			}
			
			prereq.dependencyList.add(dependency);
			dependency.preReqList.add(prereq);
		}
		
		public List<String> getExecutionSequence() {
			
			// first lets get the nodes without any depenecy
			Map<String, Integer> indegreeCount = new HashMap<>();
			Queue<TaskNode> nodesqbfs = new LinkedList<>();
			
			for(String id: graph.keySet()) {
				TaskNode curNode = graph.get(id);
				if(curNode.getPreReqList().size()==0) {
					nodesqbfs.add(curNode);
				}else {
					indegreeCount.put(curNode.id, curNode.preReqList.size());
				}
			}
			
			// now try bfs with all nodes in q
			List<String> finalResult = new ArrayList<>();
			while(!nodesqbfs.isEmpty()) {
				TaskNode curNode = nodesqbfs.poll();
				finalResult.add(curNode.id);	// take the first node from queue and put in output
				
				for(TaskNode dependency: curNode.dependencyList) {	// then for each of each neighbors, reduce indegree
					//dep.preReqList.remove(nd);
					if(indegreeCount.containsKey(dependency.id)) {
						// reduce indegree of that node by 1
						indegreeCount.put(dependency.id, indegreeCount.get(dependency.id)-1);
						// if indegree becomes 0 means we have executed all prereqs
						if(indegreeCount.get(dependency.id)==0){
							nodesqbfs.offer(dependency);		// we add only when in degree becomes 0
							indegreeCount.remove(dependency.id);
						}
					}
				}
			}
			
			// if final list has all nodes, that means we can build a sequence path
			if(finalResult.size() == graph.keySet().size()) {
				System.out.println("Can be excuted in sequence & sequence is "+finalResult);
				return finalResult;
			}else {
				System.out.println("Some Cycle exists so cant be executed in sequence!...");
			}
			return new ArrayList<>();
		}
		
		// BackTracking 
		List<List<String>> getAllExecutionSequences(){
			
			List<List<String>> finalResult = new ArrayList<>();
			Set<String> visited = new HashSet<>();
			allTopologicalSorts(visited,finalResult, new ArrayList<>());
			return finalResult;
		}
		
		void allTopologicalSorts(Set<String> visited, List<List<String>> finalResult, List<String> curSet) {

			if(curSet.size()==graph.keySet().size()) {
				finalResult.add(new ArrayList<>(curSet));
				return;
			}
			
			for(String v: graph.keySet()) {
				TaskNode nd = graph.get(v);
				if(!visited.contains(v) && nd.preReqList.size()==0) {
					
					for(TaskNode dep : nd.dependencyList) {
						dep.preReqList.remove(nd);
					}
					visited.add(v);
					curSet.add(v);
					
					allTopologicalSorts(visited, finalResult,curSet);
					
					//back track for some other path to come through
					for(TaskNode dep : nd.dependencyList) {
						dep.preReqList.add(nd);
					}
					visited.remove(v);
					curSet.remove(curSet.size()-1);
				}
			}
			
		}
		
		static enum Color{
			WHITE, GRAY, BLACK;
		}
		
		boolean isCyclicUndirectedGraph(String v, Set<String> visited, String parent) {
			
			visited.add(v);
			
			TaskNode nd = graph.get(v);
			for(TaskNode neighbor: nd.getDependencyList()) {	// a->b  b is in dependency list or a neighbor
				//If an adjacent is not visited, then recur for that adjacent
				if(!visited.contains(neighbor.id)) {
					if(isCyclicUndirectedGraph(neighbor.id, visited, nd.id)) {
						return true;
					}
				}else if(!neighbor.id.equals(parent)) {
					 // If an adjacent is visited and not parent of current vertex, then there is a cycle.
					return true;
				}
			}
			return false;
		}
		
		boolean isCyclic() {
			Set<String> exploring = new HashSet<String>();
			Set<String> explored = new HashSet<String>();
			Deque<String> path = new LinkedList<>();
			
			for(String v: graph.keySet()) {
				if(!explored.contains(v) && isThereAnyCycle(v, explored, exploring, path)) {
					return true;
				}
			}
			System.out.print("\nNo Cycyle and a execution path can be constructed : {");
			while(!path.isEmpty()) {
				System.out.print(path.pop()+", ");
			}
			System.out.print("}");
			return false;
		}
		
		private boolean isThereAnyCycle(String v, Set<String> explored, Set<String> exploring, Deque<String> path) {
			if(exploring.contains(v)) {
				System.out.println("Cycle Found at vertex "+ v);
				return true;
			}
			
			exploring.add(v);
			TaskNode nd = graph.get(v);
			for(TaskNode neighbor: nd.dependencyList) {
				if(!explored.contains(neighbor.id)) {
					if(isThereAnyCycle(neighbor.id, explored, exploring, path)) {
						return true;
					}
				}
			}
			
			explored.add(v);
			exploring.remove(v);
			path.push(v);
			return false;
		}
		
		boolean isCyclicUsingColor() {
			Map<String, Color> colormap = new HashMap<>();
			// first color all nodes as WHITE as unvisited set
			for(String v: graph.keySet()) {
				colormap.put(v, Color.WHITE);
			}
			
			Deque<String> path = new LinkedList<>();		// reversed finished time nodes
			
			for(String v: graph.keySet()) {
				// pick a vertex to start with in unvisited set, and explore DFS
				if(colormap.get(v)==Color.WHITE && isThereAnyCycleColor(v, colormap, path)) {
					return true;
				}
			}
			
			System.out.print("\nNo Cycyle and a execution path can be constructed : {");
			while(!path.isEmpty()) {
				System.out.print(path.pop()+", ");
			}
			System.out.print("}");
			return false;
		}
		
		private boolean isThereAnyCycleColor(String v, Map<String, Color> colormap, Deque<String> path) {
			// first thing check if the vertex is currently already in GRAY set means we are trying to explore all children
			// of that node but we came back to that node through some path, so there is some cycle
			// that means a back-edge
			if(colormap.get(v)==Color.GRAY) {
				System.out.println("Cycle Found at vertex "+ v);
				return true;
			}
			
			colormap.put(v, Color.GRAY);
			TaskNode curNode = graph.get(v);
			for(TaskNode neighbor: curNode.dependencyList) {
				if(colormap.get(neighbor.id) == Color.WHITE && isThereAnyCycleColor(neighbor.id, colormap, path)) {
					return true;
				}
			}
			
			// once we have explored all adjacent of current node, we can move that node to full explored as BLACK
			colormap.put(v, Color.BLACK);
			path.push(v);
			return false;
		}
		
	}
	
	static class TaskNode{
		String id;
		Set<TaskNode> preReqList;
		Set<TaskNode> dependencyList;
		
		public TaskNode(String id) {
			this.id = id;
			preReqList = new HashSet<TaskNode>();
			dependencyList = new HashSet<TaskNode>();
		}
		
		public Set<TaskNode> getPreReqList(){
			return preReqList;
		}
		
		public Set<TaskNode> getDependencyList(){
			return dependencyList;
		}
		
		public String getId() {
			return id;
		}
		
		public String toString() {
			return id;
		}
		
		public int hashCode() {
			return id.hashCode();
		}
		
		public boolean equals(Object o) {
			if(o==null || this.getClass()!=o.getClass()) {
				return false;
			}
			
			if(this == o) {
				return true;
			}
			
			TaskNode other = (TaskNode) o;
			return this.id.equals(other.id);
		}
	}
}
