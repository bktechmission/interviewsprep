package bfsexamples;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class WordLadderExample {

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
		
		List<List<WordNode>> finalResult = new LinkedList<>();
		wordLadderHelper(start, end, dic, finalResult);
		
		for(List<WordNode> result : finalResult) {
			System.out.println("WordLadder is : "+result);
		}
	}
	
	static class WordNode{
		String word;
		int level;
		WordNode parent;
		WordNode(String word, int level, WordNode parent){
			this.word = word;
			this.level = level;
			this.parent = parent;
		}
		
		public String toString() {
			return word;
		}
	}
	
	
	static void wordLadderHelper(String start, String end, Set<String> dic, List<List<WordNode>> finalResult) {
		Set<String> unvisited = new HashSet<>();
		Set<String> visited = new HashSet<>();
		unvisited.addAll(dic);
		unvisited.add(end);
		
		Queue<WordNode> nextNodeToTry = new LinkedList<>();
		nextNodeToTry.offer(new WordNode(start,0,null));
		int minLevel = Integer.MAX_VALUE;
		int prevLevel = 0;
		while(!nextNodeToTry.isEmpty()) {
			WordNode node = nextNodeToTry.poll();
			String word = node.word;
			int curLevel = node.level;
			if(word.equalsIgnoreCase(end)) 
			{
				if(minLevel==Integer.MAX_VALUE) {
					minLevel = curLevel;
				}
				if(minLevel==curLevel) {
					Deque<WordNode> stack = new LinkedList<>();
					while(node!=null) {
						stack.push(node);
						node = node.parent;
					}
					List<WordNode> list = new LinkedList<>();
					while(!stack.isEmpty()) {
						list.add(stack.pop());
					}
					finalResult.add(list);
				}
				continue;
			}
			
			if(prevLevel<curLevel) {
				unvisited.removeAll(visited);
			}
			prevLevel = curLevel;
			
			char[] chararray = word.toCharArray();
			for(int i=0;i<chararray.length;i++) {			
				for(char c='a';c<='z';c++) {
					char temp = chararray[i];
					if(temp!=c) {
						chararray[i] = c;
					}
					
					String newWord = new String(chararray);
					if(unvisited.contains(newWord)) {
						nextNodeToTry.offer(new WordNode(newWord,node.level+1,node));
						visited.add(newWord);
					}
					chararray[i] = temp;
				}	
			}	
		}
	}

}
