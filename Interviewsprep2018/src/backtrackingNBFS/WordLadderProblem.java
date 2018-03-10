package backtrackingNBFS;

import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

public class WordLadderProblem {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		wordLadderExample();
	}
	
	static void wordLadderExample() {
		String start = "hit";
		String end = "cog";
		Set<String> dic = new HashSet<>();
		dic.add("hot");
		dic.add("dot");
		dic.add("dog");
		dic.add("lot");
		dic.add("log");
		
		dic.add(start);
		dic.add(end);
		
		List<Deque<String>> finalList = new LinkedList<>();
		System.out.println("Found Word Ladder Depth First Search way : ");
		getMinimumStepsLadderDFS(new WordNode(start,0,null), end, new HashSet<>(), dic, finalList);
		for(Deque<String> s: finalList) {
			System.out.println(s);
		}
		
		finalList = new LinkedList<>();
		System.out.println("Found Word Ladder Breadth First Search way : ");
		getMinimumStepsLadderBFS(start, end, dic, finalList);
		
		for(Deque<String> s: finalList) {
			System.out.println(s);
		}
	}
	
	
	static void getMinimumStepsLadderBFS(String start, String end, Set<String>dic, List<Deque<String>> finalList){
		Queue<WordNode> q =  new LinkedList<>();
		
		q.add(new WordNode(start,0,null));
		Set<String> visited = new HashSet<>();
		
		int minimumLevelAnsFound = Integer.MAX_VALUE;
		
		while(!q.isEmpty()) {
			WordNode node = q.poll();
			String word = node.word;
			
			// first mark all conditions
			// first check not valid cases return in case we found one
			if(visited.contains(word) ||!dic.contains(word)) {
				continue;
			}
			
			if(node.level>minimumLevelAnsFound)
				continue;
			
			// now check are we at the end, the one we were looking for
			if(word.equalsIgnoreCase(end)) {
				if(minimumLevelAnsFound == Integer.MAX_VALUE) {
					minimumLevelAnsFound = node.level;
				}
				if(minimumLevelAnsFound==node.level) {
					Deque<String> wordList = new LinkedList<>();
					WordNode cur = node;
					while(cur!=null) {
						wordList.addFirst(cur.word);
						cur = cur.parent;
					}
					finalList.add(wordList);
				}
				continue;
				
			}
			
			
			// Else first mark this as visited
			visited.add(word);
			
			// explore all its neighbors
			char[] arr = word.toCharArray();
			for(int i=0;i<arr.length;i++) {
				for(char c = 'a'; c<='z'; c++) {
					char temp = arr[i];
					if(temp==c) {
						continue;
					}
					
					arr[i] = c;
					String newWord = String.valueOf(arr);
					q.offer(new WordNode(newWord,node.level+1,node));
					arr[i] = temp;		//backtrack;
				}
			}
			
			// as we are returning from this configuration just mark this as unvisited 
			visited.remove(word);
		}
	}
	
	static int minLevelAtAns = Integer.MAX_VALUE;
	static void getMinimumStepsLadderDFS(WordNode node, String end, 
			Set<String> visited, Set<String>dic, 
			List<Deque<String>> finalList){
		
		String word = node.word;
		
		if(visited.contains(word) ||!dic.contains(word)) {
			return;
		}

		if(minLevelAtAns<node.level) {
			return;
		}
		
		if(word.equalsIgnoreCase(end)) {
			if(minLevelAtAns == Integer.MAX_VALUE) {
				minLevelAtAns = node.level;
			}
			
			if(minLevelAtAns>=node.level) {
				minLevelAtAns = node.level;
				Deque<String> list = new LinkedList<>();
				WordNode cur = node;
				while(cur!=null) {
					list.addFirst(cur.word);
					cur = cur.parent;
				}
				finalList.add(list);
			}
			return;
		}

		// first mark as visited
		visited.add(word);
		
		// now explore all paths
		char[] arr = word.toCharArray();
		for(int i=0;i<arr.length;i++) {
			for(char c='a'; c<='z';c++) {
				char temp = arr[i];
				if(temp==c) {
					continue;
				}
				
				arr[i] = c;
				String newWord = String.valueOf(arr);
				getMinimumStepsLadderDFS(new WordNode(newWord,node.level+1,node),end,visited,dic,finalList);
				arr[i] = temp; // backtrack
			}
		}
		
		visited.remove(word);
		
	}
	
	
	static class WordNode{
		int level;
		String word;
		WordNode parent;
		WordNode(String word,int level,WordNode parent){
			this.word = word;
			this.level = level;
			this.parent = parent;
		}
	}
}
